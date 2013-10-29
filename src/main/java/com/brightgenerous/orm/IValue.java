package com.brightgenerous.orm;

public interface IValue<T> {

    T getValue();

    String getJdbcType();
}
