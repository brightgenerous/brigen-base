package com.brightgenerous.orm.mapper;

public interface TypeComparator {

    TypeComparator DERBY = new DerbyTypeComparator();

    TypeComparator H2 = new H2TypeComparator();

    TypeComparator MYSQL = new MysqlTypeComparator();

    TypeComparator POSTGRESQL = new PostgresqlTypeComparator();

    boolean compare(Class<?> fieldType, String columnType);
}
