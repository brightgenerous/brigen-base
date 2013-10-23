package com.brightgenerous.commons.delegate;

import java.util.Collection;

interface EqualsDelegater {

    boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients);

    boolean reflectionEquals(Object lhs, Object rhs, Collection<String> excludeFields);

    boolean reflectionEquals(Object lhs, Object rhs, String... excludeFields);

    boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients,
            Class<?> reflectUpToClass, String... excludeFields);
}
