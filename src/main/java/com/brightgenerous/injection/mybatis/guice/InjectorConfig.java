package com.brightgenerous.injection.mybatis.guice;

import java.util.Arrays;
import java.util.Collection;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public class InjectorConfig extends com.brightgenerous.injection.InjectorConfig {

    private static final long serialVersionUID = -7277196035686329629L;

    private Class<? extends TransactionFactory> transactionFactoryType;

    private String environmentId;

    private Boolean cacheEnabled;

    private Boolean lazyLoadingEnabled;

    private Boolean aggressiveLazyLoading;

    private Boolean multipleResultSetsEnabled;

    private Boolean useColumnLabel;

    private Boolean useGeneratedKeys;

    private AutoMappingBehavior autoMappingBehavior;

    private ExecutorType defaultExecutorType;

    private Boolean mapUnderscoreToCamelCase;

    private LocalCacheScope localCacheScope;

    private Boolean failFast;

    private Collection<Class<? extends TypeHandler<?>>> typeHandlerClasses;

    public Class<? extends TransactionFactory> getTransactionFactoryType() {
        return transactionFactoryType;
    }

    public void setTransactionFactoryType(Class<? extends TransactionFactory> transactionFactoryType) {
        this.transactionFactoryType = transactionFactoryType;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public Boolean getCacheEnabled() {
        return cacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        setCacheEnabled(cacheEnabled ? Boolean.TRUE : Boolean.FALSE);
    }

    public void setCacheEnabled(Boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public Boolean getLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled) {
        setLazyLoadingEnabled(lazyLoadingEnabled ? Boolean.TRUE : Boolean.FALSE);
    }

    public void setLazyLoadingEnabled(Boolean lazyLoadingEnabled) {
        this.lazyLoadingEnabled = lazyLoadingEnabled;
    }

    public Boolean getAggressiveLazyLoading() {
        return aggressiveLazyLoading;
    }

    public void setAggressiveLazyLoading(boolean aggressiveLazyLoading) {
        setAggressiveLazyLoading(aggressiveLazyLoading ? Boolean.TRUE : Boolean.FALSE);
    }

    public void setAggressiveLazyLoading(Boolean aggressiveLazyLoading) {
        this.aggressiveLazyLoading = aggressiveLazyLoading;
    }

    public Boolean getMultipleResultSetsEnabled() {
        return multipleResultSetsEnabled;
    }

    public void setMultipleResultSetsEnabled(boolean multipleResultSetsEnabled) {
        setMultipleResultSetsEnabled(multipleResultSetsEnabled ? Boolean.TRUE : Boolean.FALSE);
    }

    public void setMultipleResultSetsEnabled(Boolean multipleResultSetsEnabled) {
        this.multipleResultSetsEnabled = multipleResultSetsEnabled;
    }

    public Boolean getUseColumnLabel() {
        return useColumnLabel;
    }

    public void setUseColumnLabel(boolean useColumnLabel) {
        setUseColumnLabel(useColumnLabel ? Boolean.TRUE : Boolean.FALSE);
    }

    public void setUseColumnLabel(Boolean useColumnLabel) {
        this.useColumnLabel = useColumnLabel;
    }

    public Boolean getUseGeneratedKeys() {
        return useGeneratedKeys;
    }

    public void setUseGeneratedKeys(boolean useGeneratedKeys) {
        setUseGeneratedKeys(useGeneratedKeys ? Boolean.TRUE : Boolean.FALSE);
    }

    public void setUseGeneratedKeys(Boolean useGeneratedKeys) {
        this.useGeneratedKeys = useGeneratedKeys;
    }

    public AutoMappingBehavior getAutoMappingBehavior() {
        return autoMappingBehavior;
    }

    public void setAutoMappingBehavior(AutoMappingBehavior autoMappingBehavior) {
        this.autoMappingBehavior = autoMappingBehavior;
    }

    public ExecutorType getDefaultExecutorType() {
        return defaultExecutorType;
    }

    public void setDefaultExecutorType(ExecutorType defaultExecutorType) {
        this.defaultExecutorType = defaultExecutorType;
    }

    public Boolean getMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase ? Boolean.TRUE : Boolean.FALSE);
    }

    public void setMapUnderscoreToCamelCase(Boolean mapUnderscoreToCamelCase) {
        this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
    }

    public LocalCacheScope getLocalCacheScope() {
        return localCacheScope;
    }

    public void setLocalCacheScope(LocalCacheScope localCacheScope) {
        this.localCacheScope = localCacheScope;
    }

    public Boolean getFailFast() {
        return failFast;
    }

    public void setFailFast(boolean failFast) {
        setFailFast(failFast ? Boolean.TRUE : Boolean.FALSE);
    }

    public void setFailFast(Boolean failFast) {
        this.failFast = failFast;
    }

    public Collection<Class<? extends TypeHandler<?>>> getTypeHandlerClasses() {
        return typeHandlerClasses;
    }

    public void setTypeHandlerClasses(Class<? extends TypeHandler<?>>... typeHandlerClasses) {
        this.typeHandlerClasses = Arrays.asList(typeHandlerClasses);
    }

    public void setTypeHandlerClasses(Collection<Class<? extends TypeHandler<?>>> typeHandlerClasses) {
        this.typeHandlerClasses = typeHandlerClasses;
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
