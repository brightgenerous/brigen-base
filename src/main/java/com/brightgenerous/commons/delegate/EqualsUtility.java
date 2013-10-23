package com.brightgenerous.commons.delegate;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.commons.MethodUtils;

@Deprecated
public class EqualsUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final EqualsDelegater delegater;

    static {
        EqualsDelegater tmp = null;
        boolean useful = false;
        try {
            tmp = new EqualsDelegaterImpl();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve apache commons lang EqualsBuilder");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        USEFUL = useful;
        delegater = tmp;
    }

    private EqualsUtility() {
    }

    private static boolean methodHandle(Lookup lookup, Class<?> clazz, Object lhs, Object rhs) {
        if (lhs == rhs) {
            return true;
        }
        if ((lhs == null) || (rhs == null)) {
            return false;
        }
        MethodHandle mh = null;
        if ((lookup != null) && (clazz != null)) {
            try {
                mh = MethodUtils.getSuperEquals(lookup, clazz);
            } catch (NoSuchMethodException | IllegalAccessException e) {

                if (log.isLoggable(Level.WARNING)) {
                    log.log(Level.WARNING, "does not get method handle", e);
                }

                throw new RuntimeException(e);
            }
        }
        return methodHandle(mh, lhs, rhs);
    }

    private static boolean methodHandle(MethodHandle mh, Object lhs, Object rhs) {
        if (lhs == rhs) {
            return true;
        }
        if ((lhs == null) || (rhs == null)) {
            return false;
        }
        if (mh == null) {
            return lhs == rhs;
        }
        try {
            return ((Boolean) mh.invoke(lhs, rhs)).booleanValue();
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    public static boolean reflectionEquals(Lookup lookup, Class<?> clazz, Object lhs, Object rhs,
            boolean testTransients) {
        if (delegater == null) {
            return methodHandle(lookup, clazz, lhs, rhs);
        }
        return delegater.reflectionEquals(lhs, rhs, testTransients);
    }

    public static boolean reflectionEquals(Lookup lookup, Class<?> clazz, Object lhs, Object rhs,
            Collection<String> excludeFields) {
        if (delegater == null) {
            return methodHandle(lookup, clazz, lhs, rhs);
        }
        return delegater.reflectionEquals(lhs, rhs, excludeFields);
    }

    public static boolean reflectionEquals(Lookup lookup, Class<?> clazz, Object lhs, Object rhs,
            String... excludeFields) {
        if (delegater == null) {
            return methodHandle(lookup, clazz, lhs, rhs);
        }
        return delegater.reflectionEquals(lhs, rhs, excludeFields);
    }

    public static boolean reflectionEquals(Lookup lookup, Class<?> clazz, Object lhs, Object rhs,
            boolean testTransients, Class<?> reflectUpToClass, String... excludeFields) {
        if (delegater == null) {
            return methodHandle(lookup, clazz, lhs, rhs);
        }
        return delegater
                .reflectionEquals(lhs, rhs, testTransients, reflectUpToClass, excludeFields);
    }

    public static boolean reflectionEquals(MethodHandle mh, Object lhs, Object rhs,
            boolean testTransients) {
        if (delegater == null) {
            return methodHandle(mh, lhs, rhs);
        }
        return delegater.reflectionEquals(lhs, rhs, testTransients);
    }

    public static boolean reflectionEquals(MethodHandle mh, Object lhs, Object rhs,
            Collection<String> excludeFields) {
        if (delegater == null) {
            return methodHandle(mh, lhs, rhs);
        }
        return delegater.reflectionEquals(lhs, rhs, excludeFields);
    }

    public static boolean reflectionEquals(MethodHandle mh, Object lhs, Object rhs,
            String... excludeFields) {
        if (delegater == null) {
            return methodHandle(mh, lhs, rhs);
        }
        return delegater.reflectionEquals(lhs, rhs, excludeFields);
    }

    public static boolean reflectionEquals(MethodHandle mh, Object lhs, Object rhs,
            boolean testTransients, Class<?> reflectUpToClass, String... excludeFields) {
        if (delegater == null) {
            return methodHandle(mh, lhs, rhs);
        }
        return delegater
                .reflectionEquals(lhs, rhs, testTransients, reflectUpToClass, excludeFields);
    }
}
