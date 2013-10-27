package com.brightgenerous.commons;

import java.lang.invoke.MethodHandle;

import com.brightgenerous.commons.delegate.ToStringUtility;

@SuppressWarnings("deprecation")
public class ToStringUtils {

    public static boolean resolved() {
        return ToStringUtility.RESOLVED;
    }

    private ToStringUtils() {
    }

    public static String toStringAlt(Object obj) {
        if (obj == null) {
            return null;
        }
        return ToStringUtility.reflectionToString(obj);
    }

    public static String toStringAlt(MethodHandle mh, Object obj) {
        if (obj == null) {
            return null;
        }
        return ToStringUtility.reflectionToString(mh, obj);
    }
}
