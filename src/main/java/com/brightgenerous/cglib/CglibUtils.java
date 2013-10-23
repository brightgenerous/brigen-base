package com.brightgenerous.cglib;

import com.brightgenerous.cglib.deleg.CglibUtility;

@SuppressWarnings("deprecation")
public class CglibUtils {

    public static boolean useful() {
        return CglibUtility.USEFUL;
    }

    private CglibUtils() {
    }

    public static Class<?> defineInterface(String name, Class<?>... supers) {
        return CglibUtility.defineInterface(name, supers);
    }
}
