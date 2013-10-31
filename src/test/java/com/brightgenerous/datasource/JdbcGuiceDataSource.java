package com.brightgenerous.datasource;

import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.derby.jdbc.EmbeddedConnectionPoolDataSource;
import org.apache.derby.jdbc.EmbeddedDataSourceInterface;

import com.brightgenerous.datasource.jdbc.guice.mapper._mapper_;
import com.brightgenerous.datasource.jdbc.guice.mapper.derby._mapper_derby_;
import com.brightgenerous.datasource.jdbc.guice.transaction._transaction_;
import com.brightgenerous.injection.PackageImplResolver;
import com.brightgenerous.injection.PatternClassFilter;
import com.brightgenerous.injection.PatternMemberFilter;
import com.brightgenerous.injection.jdbc.guice.InjectorConfig;
import com.brightgenerous.injection.jdbc.guice.InjectorFactory;
import com.brightgenerous.orm.mapper.MapperMethods;
import com.brightgenerous.orm.mapper.TypeComparator;

public class JdbcGuiceDataSource extends DataSource {

    @Override
    protected InjectorFactory createInjectorFactory() {
        return new InjectorFactoryImpl();
    }

    static class InjectorFactoryImpl extends InjectorFactory {

        private static final long serialVersionUID = -8248458184709739906L;

        private static final String DATASOURCE_NAME = "jdbc/derby";

        @Override
        protected InjectorConfig createConfig() {
            InjectorConfig config = new InjectorConfig();

            config.setDataSourceName(DATASOURCE_NAME);

            config.setTransactionPackages(_transaction_.class);
            config.setTransactionClassFilter(new PatternClassFilter("^.*Transaction$"));
            config.setTransactionMethodFilter(new PatternMemberFilter<Method>(
                    "^(add|edit|remove|initialize|destroy).*$"));
            config.setMapperPackages(_mapper_.class);
            config.setMapperClassFilter(new PatternClassFilter("^.*Mapper$"));
            config.setMapperImplResolver(new PackageImplResolver(_mapper_derby_.class));
            return config;
        }

        @Override
        public void onInitialize() {
            super.onInitialize();

            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();

                EmbeddedDataSourceInterface dataSource = new EmbeddedConnectionPoolDataSource();
                dataSource.setDatabaseName("test");
                dataSource.setCreateDatabase("create");
                System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                        "org.apache.naming.java.javaURLContextFactory");
                // System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
                Context context = new InitialContext();
                {
                    {
                        int index = 0;
                        if ((index = DATASOURCE_NAME.indexOf(":")) != -1) {
                            context.createSubcontext(DATASOURCE_NAME.substring(0, index + 1));
                        }
                    }
                    {
                        int index = 0;
                        while ((index = DATASOURCE_NAME.indexOf("/", index)) != -1) {
                            context.createSubcontext(DATASOURCE_NAME.substring(0, index));
                            index += 1;
                        }
                    }
                }
                context.bind(DATASOURCE_NAME, dataSource);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | NamingException e) {
                throw new RuntimeException(e);
            }

            MapperMethods
                    .set(com.brightgenerous.datasource.mybatis.guice.mapper.MapperMethods.class);
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
}
