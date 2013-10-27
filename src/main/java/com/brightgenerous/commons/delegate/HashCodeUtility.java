package com.brightgenerous.commons.delegate;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.commons.MethodUtils;

@Deprecated
public class HashCodeUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean RESOLVED;

    private static final HashCodeDelegater delegater;

    static {
        HashCodeDelegater tmp = null;
        boolean resolved = false;
        try {
            tmp = new HashCodeDelegaterImpl();
            resolved = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve apache commons lang HashCodeBuilder");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        RESOLVED = resolved;
        delegater = tmp;
    }

    private HashCodeUtility() {
    }

    private static int methodHandle(Lookup lookup, Class<?> clazz, Object object) {
        if (object == null) {
            return 0;
        }
        MethodHandle mh = null;
        if ((lookup != null) && (clazz != null)) {
            try {
                mh = MethodUtils.getSuperHashCode(lookup, clazz);
            } catch (NoSuchMethodException | IllegalAccessException e) {

                if (log.isLoggable(Level.WARNING)) {
                    log.log(Level.WARNING, "does not get method handle", e);
                }

                throw new RuntimeException(e);
            }
        }
        return methodHandle(mh, object);
    }

    private static int methodHandle(MethodHandle mh, Object object) {
        if (object == null) {
            return 0;
        }
        if (mh == null) {
            return -1;
        }
        try {
            return ((Integer) mh.invoke(object)).intValue();
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    public static int reflectionHashCode(Lookup lookup, Class<?> clazz,
            int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object) {
        if (delegater == null) {
            return methodHandle(lookup, clazz, object);
        }
        return delegater.reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber,
                object);
    }

    public static int reflectionHashCode(Lookup lookup, Class<?> clazz,
            int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object,
            boolean testTransients) {
        if (delegater == null) {
            return methodHandle(lookup, clazz, object);
        }
        return delegater.reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber,
                object, testTransients);
    }

    public static <T> int reflectionHashCode(Lookup lookup, Class<?> clazz,
            int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, T object,
            boolean testTransients, Class<? super T> reflectUpToClass, String... excludeFields) {
        if (delegater == null) {
            return methodHandle(lookup, clazz, object);
        }
        return delegater.reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber,
                object, testTransients, reflectUpToClass, excludeFields);
    }

    public static int reflectionHashCode(Lookup lookup, Class<?> clazz, Object object,
            boolean testTransients) {
        if (delegater == null) {
            return methodHandle(lookup, clazz, object);
        }
        return delegater.reflectionHashCode(object, testTransients);
    }

    public static int reflectionHashCode(Lookup lookup, Class<?> clazz, Object object,
            Collection<String> excludeFields) {
        if (delegater == null) {
            return methodHandle(lookup, clazz, object);
        }
        return delegater.reflectionHashCode(object, excludeFields);
    }

    public static int reflectionHashCode(Lookup lookup, Class<?> clazz, Object object,
            String... excludeFields) {
        if (delegater == null) {
            return methodHandle(lookup, clazz, object);
        }
        return delegater.reflectionHashCode(object, excludeFields);
    }

    public static int reflectionHashCode(MethodHandle mh, int initialNonZeroOddNumber,
            int multiplierNonZeroOddNumber, Object object) {
        if (delegater == null) {
            return methodHandle(mh, object);
        }
        return delegater.reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber,
                object);
    }

    public static int reflectionHashCode(MethodHandle mh, int initialNonZeroOddNumber,
            int multiplierNonZeroOddNumber, Object object, boolean testTransients) {
        if (delegater == null) {
            return methodHandle(mh, object);
        }
        return delegater.reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber,
                object, testTransients);
    }

    public static <T> int reflectionHashCode(MethodHandle mh, int initialNonZeroOddNumber,
            int multiplierNonZeroOddNumber, T object, boolean testTransients,
            Class<? super T> reflectUpToClass, String... excludeFields) {
        if (delegater == null) {
            return methodHandle(mh, object);
        }
        return delegater.reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber,
                object, testTransients, reflectUpToClass, excludeFields);
    }

    public static int reflectionHashCode(MethodHandle mh, Object object, boolean testTransients) {
        if (delegater == null) {
            return methodHandle(mh, object);
        }
        return delegater.reflectionHashCode(object, testTransients);
    }

    public static int reflectionHashCode(MethodHandle mh, Object object,
            Collection<String> excludeFields) {
        if (delegater == null) {
            return methodHandle(mh, object);
        }
        return delegater.reflectionHashCode(object, excludeFields);
    }

    public static int reflectionHashCode(MethodHandle mh, Object object, String... excludeFields) {
        if (delegater == null) {
            return methodHandle(mh, object);
        }
        return delegater.reflectionHashCode(object, excludeFields);
    }
}
