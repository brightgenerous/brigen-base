package com.brightgenerous.datasource;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamException;

import org.apache.ibatis.session.AutoMappingBehavior;

import com.brightgenerous.datasource.mybatis.guice.bean._bean_;
import com.brightgenerous.datasource.mybatis.guice.mapper._mapper_;
import com.brightgenerous.datasource.mybatis.guice.mapper.derby._mapper_derby_;
import com.brightgenerous.datasource.mybatis.guice.transaction.QueryTransaction;
import com.brightgenerous.datasource.mybatis.guice.transaction._transaction_;
import com.brightgenerous.injection.PackageGenerateImplResolver;
import com.brightgenerous.injection.PatternClassFilter;
import com.brightgenerous.injection.PatternMemberFilter;
import com.brightgenerous.injection.mybatis.guice.InjectorConfig;
import com.brightgenerous.injection.mybatis.guice.InjectorFactory;
import com.brightgenerous.orm.mapper.MapperMethods;
import com.brightgenerous.orm.mapper.TypeComparator;
import com.google.inject.Injector;

class DerbyDataSource extends DataSource {

    @Override
    protected InjectorFactory createInjectorFactory() {
        return new InjectorFactoryImpl();
    }

    private List<String> createTables;

    private List<String> dropTables;

    private List<String> initSqls;

    private List<String> dummySqls;

    {
        Class<?> mapperPath = _mapper_derby_.class;
        {
            URL res = mapperPath.getResource("_create_tables.xml");
            if (res != null) {
                try (InputStream stream = res.openStream()) {
                    createTables = Loader.loadCreateTables(stream);
                } catch (IOException | XMLStreamException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        {
            URL res = mapperPath.getResource("_create_tables.xml");
            if (res != null) {
                try (InputStream stream = res.openStream()) {
                    dropTables = Loader.loadDropTables(stream);
                    Collections.reverse(dropTables);
                } catch (IOException | XMLStreamException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        {
            URL res = mapperPath.getResource("_init_sqls.xml");
            if (res != null) {
                try (InputStream stream = res.openStream()) {
                    initSqls = Loader.loadInitSqls(stream);
                } catch (IOException | XMLStreamException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        {
            URL res = mapperPath.getResource("_dummy_sqls.xml");
            if (res != null) {
                try (InputStream stream = res.openStream()) {
                    dummySqls = Loader.loadDummySqls(stream);
                } catch (IOException | XMLStreamException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    protected Initializer createInitializer() {
        return new CompositeInitializer(super.createInitializer(), new Initializer() {

            @Override
            public void initialize(Injector injector) {
                try {
                    Class.forName(MapperMethods.class.getName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                List<String> sqls = new ArrayList<>();
                if (isNotNoSize(createTables)) {
                    sqls.addAll(createTables);
                }
                if (isNotNoSize(initSqls)) {
                    sqls.addAll(initSqls);
                }
                if (isNotNoSize(dummySqls)) {
                    sqls.addAll(dummySqls);
                }
                if (isNotNoSize(sqls)) {
                    injector.getInstance(QueryTransaction.class).edit(sqls);
                }
            }
        });
    }

    @Override
    protected Destroyer createDestroyer() {
        return new CompositeDestroyer(new Destroyer() {

            @Override
            public void destroy(Injector injector) {
                if (isNotNoSize(dropTables)) {
                    injector.getInstance(QueryTransaction.class).edit(dropTables);
                }
            }
        }, super.createDestroyer());
    }
}

class InjectorFactoryImpl extends InjectorFactory {

    private static final long serialVersionUID = -8248458184709739906L;

    @Override
    protected InjectorConfig createConfig() {
        InjectorConfig config = new InjectorConfig();
        {
            Map<String, String> dbProperties = new HashMap<>();
            {
                dbProperties.put("JDBC.driver", "org.apache.derby.jdbc.EmbeddedDriver");
                dbProperties.put("JDBC.url", "jdbc:derby:memory:patient;create=true");
                dbProperties.put("JDBC.autoCommit", "false");
                dbProperties.put("DBCP.defaultReadOnly", "true");
            }
            config.setDbProperties(dbProperties);

            config.setEnvironmentId("test");
            config.setUseGeneratedKeys(true);
            config.setLazyLoadingEnabled(true);
            config.setAutoMappingBehavior(AutoMappingBehavior.FULL);
            config.setMapUnderscoreToCamelCase(true);
            config.setFailFast(true);
        }
        config.setTransactionPackages(_transaction_.class);
        config.setTransactionClassFilter(new PatternClassFilter("^.*Transaction$"));
        config.setTransactionMethodFilter(new PatternMemberFilter<Method>(
                "^(add|edit|remove|initialize|destroy).*$"));
        config.setMapperPackages(_mapper_.class);
        config.setMapperClassFilter(new PatternClassFilter("^.*Mapper$"));
        config.setMapperImplResolver(new PackageGenerateImplResolver(_mapper_derby_.class));
        config.setBeanPackages(_bean_.class);
        return config;
    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        MapperMethods.set(com.brightgenerous.datasource.mybatis.guice.mapper.MapperMethods.class);
    }

    @Override
    protected boolean compareBeanToTable(DatabaseMetaData dmd, Set<Class<?>> beanClasses)
            throws SQLException {
        MapperMethods mapperMethods = MapperMethods.get();
        Map<Class<?>, String> targetTables = mapperMethods.getTargetTables();
        for (Class<?> beanClass : beanClasses) {
            String table = targetTables.get(beanClass);
            if (table != null) {
                Map<String, String> columnTypes = new TreeMap<>(new Comparator<String>() {

                    @Override
                    public int compare(String o1, String o2) {
                        return o1.toLowerCase().compareTo(o2.toLowerCase());
                    }
                });
                try (ResultSet rs = dmd.getColumns(null, "APP", table.toUpperCase(), "%")) {
                    while (rs.next()) {
                        String name = rs.getString("COLUMN_NAME");
                        String type = rs.getString("TYPE_NAME");
                        columnTypes.put(name, type);
                    }
                }
                mapperMethods.verify(table, columnTypes, TypeComparator.DERBY);
            }
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (Exception ex) {
        }

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
            }
        }
    }
}
