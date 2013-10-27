package com.brightgenerous.injection.mybatis.guice;

import static com.brightgenerous.commons.ObjectUtils.*;
import static com.brightgenerous.commons.StringUtils.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.dbcp.BasicDataSourceProvider;
import org.mybatis.guice.transactional.Transactional;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.injection.Filter;
import com.brightgenerous.injection.ImplResolver;
import com.brightgenerous.injection.mybatis.TransactionalMethodInterceptor;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.name.Names;

public abstract class InjectorFactory implements com.brightgenerous.datasource.InjectorFactory,
        Serializable {

    private static final long serialVersionUID = -1832713081625601163L;

    private volatile InjectorConfig config;

    private transient volatile Injector injector;

    private transient volatile Injector injectorRollback;

    protected InjectorFactory() {
    }

    protected InjectorConfig getConfig() {
        if (config == null) {
            synchronized (this) {
                if (config == null) {
                    config = createConfig();
                    if (config == null) {
                        throw new IllegalStateException();
                    }
                }
            }
        }
        return config;
    }

    protected abstract InjectorConfig createConfig();

    @Override
    public Injector getInjector() {
        return getInjector(false);
    }

    @Override
    public Injector getInjector(boolean rollbackOnly) {
        if (rollbackOnly) {
            return injectorRollback();
        }
        return injector();
    }

    protected Injector injector() {
        if (injector == null) {
            synchronized (this) {
                if (injector == null) {
                    injector = createInjector(getConfig(), false);
                }
            }
        }
        return injector;
    }

    protected Injector injectorRollback() {
        if (injectorRollback == null) {
            synchronized (this) {
                if (injectorRollback == null) {
                    injectorRollback = createInjector(getConfig(), true);
                }
            }
        }
        return injectorRollback;
    }

    @Override
    public void initialize() {
        onInitialize();
    }

    protected void onInitialize() {
    }

    @Override
    public void verify() {
        SqlSessionManager sqlSessionManager = getInjector().getInstance(SqlSessionManager.class);
        if (sqlSessionManager != null) {
            try (SqlSessionManager ssm = sqlSessionManager) {
                if (!ssm.isManagedSessionStarted()) {
                    ssm.startManagedSession(ExecutorType.SIMPLE, (TransactionIsolationLevel) null);
                }
                try (Connection connection = ssm.getConnection()) {
                    if (connection != null) {
                        DatabaseMetaData dmd = connection.getMetaData();
                        if (dmd != null) {
                            if (!compareBeanToTable(dmd, getBeanClasses(getConfig()))) {
                                throw new IllegalStateException("compareBeanToTable returns false.");
                            }
                        }
                    }
                    ssm.rollback(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @SuppressWarnings("unused")
    protected boolean compareBeanToTable(DatabaseMetaData dmd, Set<Class<?>> beanClasses)
            throws SQLException {
        return true;
    }

    @Override
    public void destroy() {
        onDestroy();

        config = null;
        injector = null;
        injectorRollback = null;
    }

    protected void onDestroy() {
    }

    protected Injector createInjector(final InjectorConfig config, final boolean rollbackOnly) {
        final Set<Class<?>> transactionClazzs = getTransactionClasses(config);

        return Guice.createInjector(new MyBatisModule() {

            @Override
            protected void initialize() {

                if (isNotNoSize(transactionClazzs)) {
                    MethodInterceptor updateInterceptor;
                    if (rollbackOnly) {
                        updateInterceptor = new TransactionalMethodInterceptor() {

                            @Transactional(rollbackOnly = true)
                            @Override
                            protected void setting() {
                            }
                        };
                    } else {
                        updateInterceptor = new TransactionalMethodInterceptor();
                    }
                    binder().requestInjection(updateInterceptor);
                    binder().bindInterceptor(new AbstractMatcher<Class<?>>() {

                        @Override
                        public boolean matches(Class<?> arg0) {
                            if (arg0.isAnnotation() || arg0.isEnum()) {
                                return false;
                            }
                            return transactionClazzs.contains(arg0);
                        }
                    }, new AbstractMatcher<Method>() {

                        private final Filter<Method> filter = config.getTransactionMethodFilter();

                        @Override
                        public boolean matches(Method arg0) {
                            return (filter == null) || filter.filter(arg0);
                        }
                    }, updateInterceptor);
                }

                if (config.getDataSourceProviderType() != null) {
                    bindDataSourceProviderType(config.getDataSourceProviderType());
                } else {
                    bindDataSourceProviderType(BasicDataSourceProvider.class);
                }
                if (config.getTransactionFactoryType() != null) {
                    bindTransactionFactoryType(config.getTransactionFactoryType());
                } else {
                    bindTransactionFactoryType(JdbcTransactionFactory.class);
                }

                {
                    // properties
                    if ((config.getDbProperties() != null) && !config.getDbProperties().isEmpty()) {
                        Names.bindProperties(binder(), config.getDbProperties());
                    }
                    if (isNotEmpty(config.getEnvironmentId())) {
                        environmentId(config.getEnvironmentId());
                    }
                    if (config.getCacheEnabled() != null) {
                        useCacheEnabled(config.getCacheEnabled().booleanValue());
                    }
                    if (config.getLazyLoadingEnabled() != null) {
                        lazyLoadingEnabled(config.getLazyLoadingEnabled().booleanValue());
                    }
                    if (config.getAggressiveLazyLoading() != null) {
                        aggressiveLazyLoading(config.getAggressiveLazyLoading().booleanValue());
                    }
                    if (config.getMultipleResultSetsEnabled() != null) {
                        multipleResultSetsEnabled(config.getMultipleResultSetsEnabled()
                                .booleanValue());
                    }
                    if (config.getUseColumnLabel() != null) {
                        useColumnLabel(config.getUseColumnLabel().booleanValue());
                    }
                    if (config.getUseGeneratedKeys() != null) {
                        useGeneratedKeys(config.getUseGeneratedKeys().booleanValue());
                    }
                    if (config.getAutoMappingBehavior() != null) {
                        autoMappingBehavior(config.getAutoMappingBehavior());
                    }
                    if (config.getDefaultExecutorType() != null) {
                        executorType(config.getDefaultExecutorType());
                    }
                    if (config.getMapUnderscoreToCamelCase() != null) {
                        mapUnderscoreToCamelCase(config.getMapUnderscoreToCamelCase()
                                .booleanValue());
                    }
                    if (config.getLocalCacheScope() != null) {
                        localCacheScope(config.getLocalCacheScope());
                    }
                    if (config.getFailFast() != null) {
                        failFast(config.getFailFast().booleanValue());
                    }
                }

                {
                    Set<Class<?>> beanClazzs = getBeanClasses(config);
                    if (isNotNoSize(beanClazzs)) {
                        addSimpleAliases(beanClazzs);
                    }
                }

                {
                    Set<Class<?>> mapperClazzs = getMapperClasses(config);
                    if (isNotNoSize(mapperClazzs)) {
                        ImplResolver resolver = config.getMapperImplResolver();
                        if (resolver == null) {
                            addMapperClasses(mapperClazzs);
                        } else {
                            Map<Class<?>, Set<Class<?>>> implClazzMap = getImplClassMap(resolver,
                                    mapperClazzs);

                            addMapperClasses(implClazzMap.keySet());

                            for (Entry<Class<?>, Set<Class<?>>> e : implClazzMap.entrySet()) {
                                @SuppressWarnings("rawtypes")
                                Class implClazz = e.getKey();
                                Set<Class<?>> superClazzs = e.getValue();
                                for (Class<?> superClazz : superClazzs) {
                                    bind(superClazz).to(implClazz);
                                }
                            }
                        }
                    }
                }

                {
                    Collection<Class<? extends TypeHandler<?>>> typeHandlers = config
                            .getTypeHandlerClasses();
                    if (isNotNoSize(typeHandlers)) {
                        addTypeHandlersClasses(typeHandlers);
                    }
                }
            }
        }, new AbstractModule() {

            @Override
            protected void configure() {
                for (Class<?> transactionClazz : transactionClazzs) {
                    bind(transactionClazz).in(Scopes.SINGLETON);
                }
            }
        });
    }

    private static Set<Class<?>> getTransactionClasses(InjectorConfig config) {
        Set<Class<?>> ret = new HashSet<>();
        {
            Set<String> packages = getPackageNames(config.getTransactionPackages());
            if (isNotNoSize(packages)) {
                final Filter<Class<?>> filter = config.getTransactionClassFilter();
                for (String pkg : packages) {
                    final String regex = getPackageRegex(pkg);
                    ret.addAll(new ResolverUtil<>().find(new ResolverUtil.Test() {

                        @Override
                        public boolean matches(Class<?> arg0) {
                            if (arg0.isAnnotation() || arg0.isEnum()) {
                                return false;
                            }
                            if ((filter != null) && !filter.filter(arg0)) {
                                return false;
                            }
                            return arg0.getName().matches(regex);
                        }
                    }, pkg).getClasses());
                }
            }
        }
        return ret;
    }

    private static Set<Class<?>> getMapperClasses(InjectorConfig config) {
        Set<Class<?>> ret = new HashSet<>();
        {
            Set<String> packages = getPackageNames(config.getMapperPackages());
            if (isNotNoSize(packages)) {
                final Filter<Class<?>> filter = config.getMapperClassFilter();
                for (String pkg : packages) {
                    final String regex = getPackageRegex(pkg);
                    ret.addAll(new ResolverUtil<>().find(new ResolverUtil.Test() {

                        @Override
                        public boolean matches(Class<?> arg0) {
                            if (arg0.isAnnotation() || arg0.isEnum()) {
                                return false;
                            }
                            if ((filter != null) && !filter.filter(arg0)) {
                                return false;
                            }
                            return arg0.getName().matches(regex);
                        }
                    }, pkg).getClasses());
                }
            }
        }
        return ret;
    }

    private static Set<Class<?>> getBeanClasses(InjectorConfig config) {
        Set<Class<?>> ret = new HashSet<>();
        {
            Set<String> packages = getPackageNames(config.getBeanPackages());
            if (isNotNoSize(packages)) {
                final Filter<Class<?>> filter = config.getBeanClassFilter();
                for (String pkg : packages) {
                    final String regex = getPackageRegex(pkg);
                    ret.addAll(new ResolverUtil<>().find(new ResolverUtil.Test() {

                        @Override
                        public boolean matches(Class<?> arg0) {
                            if (arg0.isAnnotation() || arg0.isEnum() || arg0.isAnonymousClass()
                                    || !Modifier.isPublic(arg0.getModifiers())) {
                                return false;
                            }
                            if ((filter != null) && !filter.filter(arg0)) {
                                return false;
                            }
                            return arg0.getName().matches(regex);
                        }
                    }, pkg).getClasses());
                }
            }
        }
        return ret;
    }

    private static Map<Class<?>, Set<Class<?>>> getImplClassMap(ImplResolver resolver,
            Set<Class<?>> superClasses) {
        if (resolver == null) {
            return null;
        }
        Map<Class<?>, Set<Class<?>>> ret = new HashMap<>();
        if (isNotNoSize(superClasses)) {
            for (Class<?> superClass : superClasses) {
                Class<?> implClass = resolver.getImplClass(superClass);
                if (implClass == null) {
                    continue;
                }
                Set<Class<?>> ics = ret.get(implClass);
                if (ics == null) {
                    ics = new HashSet<>();
                    ret.put(implClass, ics);
                }
                ics.add(superClass);
            }
        }
        return ret;
    }

    private static Set<String> getPackageNames(Class<?>... packages) {
        Set<String> ret = new HashSet<>();
        if (isNotNoSize(packages)) {
            for (Class<?> pkg : packages) {
                Package p = pkg.getPackage();
                if (p == null) {
                    throw new IllegalStateException(String.format("Can not use Package %s", pkg));
                }
                String pName = p.getName();
                if (pName == null) {
                    throw new IllegalStateException(String.format("Can not use Package %s", pkg));
                }
                ret.add(pName);
            }
        }
        return ret;
    }

    private static String getPackageRegex(String pkg) {
        if (isEmpty(pkg)) {
            return null;
        }
        return getPackageRegex(Arrays.asList(pkg));
    }

    private static String getPackageRegex(Collection<String> packages) {
        if (isNoSize(packages)) {
            return null;
        }
        final String regex;
        {
            StringBuilder sb = new StringBuilder();
            sb.append("^(");
            boolean first = true;
            for (String pkg : packages) {
                if (isEmpty(pkg)) {
                    continue;
                }
                if (first) {
                    first = false;
                } else {
                    sb.append("|");
                }
                sb.append(pkg).append(".[^.]*");
            }
            sb.append(")$");
            regex = sb.toString();
        }
        return regex;
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.resolved()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.resolved()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (ToStringUtils.resolved()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }
}
