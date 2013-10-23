package com.brightgenerous.commons.delegate;

import java.util.Collection;

import org.apache.commons.lang3.builder.EqualsBuilder;

class EqualsDelegaterImpl implements EqualsDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(EqualsBuilder.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients) {
        return EqualsBuilder.reflectionEquals(lhs, rhs, testTransients);
    }

    @Override
    public boolean reflectionEquals(Object lhs, Object rhs, Collection<String> excludeFields) {
        return EqualsBuilder.reflectionEquals(lhs, rhs, excludeFields);
    }

    @Override
    public boolean reflectionEquals(Object lhs, Object rhs, String... excludeFields) {
        return EqualsBuilder.reflectionEquals(lhs, rhs, excludeFields);
    }

    @Override
    public boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients,
            Class<?> reflectUpToClass, String... excludeFields) {
        return EqualsBuilder.reflectionEquals(lhs, rhs, testTransients, reflectUpToClass,
                excludeFields);
    }
}
