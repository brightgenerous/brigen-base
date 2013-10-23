package com.brightgenerous.commons;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

import com.brightgenerous.lang.Args;

public class MethodUtils {

    private MethodUtils() {
    }

    public static MethodHandle getSuperHashCode(Lookup lookup, Class<?> clazz)
            throws NoSuchMethodException, IllegalAccessException {
        Args.notNull(lookup, "lookup");
        Args.notNull(clazz, "clazz");

        return lookup.findSpecial(Object.class, "hashCode", MethodType.methodType(Integer.TYPE),
                clazz);
    }

    public static MethodHandle getSuperEquals(Lookup lookup, Class<?> clazz)
            throws NoSuchMethodException, IllegalAccessException {
        Args.notNull(lookup, "lookup");
        Args.notNull(clazz, "clazz");

        return lookup.findSpecial(Object.class, "equals",
                MethodType.methodType(Boolean.TYPE, Object.class), clazz);
    }

    public static MethodHandle getSuperToString(Lookup lookup, Class<?> clazz)
            throws NoSuchMethodException, IllegalAccessException {
        Args.notNull(lookup, "lookup");
        Args.notNull(clazz, "clazz");

        return lookup.findSpecial(Object.class, "toString", MethodType.methodType(String.class),
                clazz);
    }

    public static MethodHandle getSuperGetClass(Lookup lookup, Class<?> clazz)
            throws NoSuchMethodException, IllegalAccessException {
        Args.notNull(lookup, "lookup");
        Args.notNull(clazz, "clazz");

        return lookup.findSpecial(Object.class, "getClass", MethodType.methodType(Class.class),
                clazz);
    }
}
