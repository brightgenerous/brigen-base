package com.brightgenerous.commons.delegate;

import java.util.Collection;

import org.apache.commons.lang3.builder.HashCodeBuilder;

class HashCodeDelegaterImpl implements HashCodeDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(HashCodeBuilder.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber,
            Object object) {
        return HashCodeBuilder.reflectionHashCode(initialNonZeroOddNumber,
                multiplierNonZeroOddNumber, object);
    }

    @Override
    public int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber,
            Object object, boolean testTransients) {
        return HashCodeBuilder.reflectionHashCode(initialNonZeroOddNumber,
                multiplierNonZeroOddNumber, object, testTransients);
    }

    @Override
    public <T> int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber,
            T object, boolean testTransients, Class<? super T> reflectUpToClass,
            String... excludeFields) {
        return HashCodeBuilder
                .reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object,
                        testTransients, reflectUpToClass, excludeFields);
    }

    @Override
    public int reflectionHashCode(Object object, boolean testTransients) {
        return HashCodeBuilder.reflectionHashCode(object, testTransients);
    }

    @Override
    public int reflectionHashCode(Object object, Collection<String> excludeFields) {
        return HashCodeBuilder.reflectionHashCode(object, excludeFields);
    }

    @Override
    public int reflectionHashCode(Object object, String... excludeFields) {
        return HashCodeBuilder.reflectionHashCode(object, excludeFields);
    }
}
