package com.brightgenerous.orm;

public interface IField<T> {

    T getValue();

    T getNotValue();

    Boolean getIsNull();

    Boolean getIsNotNull();

    String getPrefixValue();

    String getNotPrefixValue();

    String getSuffixValue();

    String getNotSuffixValue();

    String getBroadValue();

    String getNotBroadValue();

    T getGreaterThanValue();

    T getGreaterEqualValue();

    T getLowerThanValue();

    T getLowerEqualValue();
}
