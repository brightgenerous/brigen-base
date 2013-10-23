package com.brightgenerous.commons.delegate;

import java.util.Collection;

interface HashCodeDelegater {

    int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber,
            Object object);

    int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber,
            Object object, boolean testTransients);

    <T> int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber,
            T object, boolean testTransients, Class<? super T> reflectUpToClass,
            String... excludeFields);

    int reflectionHashCode(Object object, boolean testTransients);

    int reflectionHashCode(Object object, Collection<String> excludeFields);

    int reflectionHashCode(Object object, String... excludeFields);
}
