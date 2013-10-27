package com.brightgenerous.commons;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Collection;

import com.brightgenerous.commons.delegate.EqualsUtility;

@SuppressWarnings("deprecation")
public class EqualsUtils {

    public static boolean resolved() {
        return EqualsUtility.RESOLVED;
    }

    private EqualsUtils() {
    }

    public static boolean equalsAlt(Lookup lookup, Class<?> clazz, Object lhs, Object rhs,
            Collection<String> excludeFields) {
        return EqualsUtility.reflectionEquals(lookup, clazz, lhs, rhs, excludeFields);
    }

    public static boolean equalsAlt(Lookup lookup, Class<?> clazz, Object lhs, Object rhs,
            String... excludeFields) {
        return EqualsUtility.reflectionEquals(lookup, clazz, lhs, rhs, excludeFields);
    }

    public static boolean equalsAlt(MethodHandle mh, Object lhs, Object rhs,
            Collection<String> excludeFields) {
        return EqualsUtility.reflectionEquals(mh, lhs, rhs, excludeFields);
    }

    public static boolean equalsAlt(MethodHandle mh, Object lhs, Object rhs,
            String... excludeFields) {
        return EqualsUtility.reflectionEquals(mh, lhs, rhs, excludeFields);
    }
}
