package com.brightgenerous.commons;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Collection;

import com.brightgenerous.commons.delegate.HashCodeUtility;

@SuppressWarnings("deprecation")
public class HashCodeUtils {

    public static boolean useful() {
        return HashCodeUtility.USEFUL;
    }

    private HashCodeUtils() {
    }

    public static int hashCodeAlt(Lookup lookup, Class<?> clazz, Object object,
            Collection<String> excludeFields) {
        return HashCodeUtility.reflectionHashCode(lookup, clazz, object, excludeFields);
    }

    public static int hashCodeAlt(Lookup lookup, Class<?> clazz, Object object,
            String... excludeFields) {
        return HashCodeUtility.reflectionHashCode(lookup, clazz, object, excludeFields);
    }

    public static int hashCodeAlt(MethodHandle mh, Object object, Collection<String> excludeFields) {
        return HashCodeUtility.reflectionHashCode(mh, object, excludeFields);
    }

    public static int hashCodeAlt(MethodHandle mh, Object object, String... excludeFields) {
        return HashCodeUtility.reflectionHashCode(mh, object, excludeFields);
    }
}
