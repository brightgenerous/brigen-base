package com.brightgenerous.datasource;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.brightgenerous.datasource.jdbc.guice.transaction.BrigenTransaction;

public class JdbcGuiceDataSourceTest {

    @BeforeClass
    public static void before() throws Exception {
        DataSource.set(JdbcGuiceDataSource.class);
        DataSource.get().initialize();
    }

    @AfterClass
    public static void after() throws Exception {
        DataSource.get().destroy();
    }

    @Test
    public void test() throws Exception {
        BrigenTransaction transaction = DataSource.get().instance(BrigenTransaction.class);

        assertTrue(transaction.test());
        assertTrue(transaction.edit());
    }
}
