package com.brightgenerous.commons.delegate;

import java.lang.invoke.MethodHandle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class ToStringUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean RESOLVED;

    private static final ToStringDelegater delegater;

    static {
        ToStringDelegater tmp = null;
        boolean resolved = false;
        try {
            tmp = new ToStringDelegaterImpl();
            resolved = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve apache commons lang ToStringBuilder");
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

    private ToStringUtility() {
    }

    private static String methodHandle(MethodHandle mh, Object obj) {
        if (obj == null) {
            return null;
        }
        if (mh == null) {
            return String.format("%s@%s", obj.getClass().getName(),
                    Integer.toHexString(obj.hashCode()));
        }
        try {
            return (String) mh.invoke(obj);
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    public static String reflectionToString(Object obj) {
        return reflectionToString(null, obj);
    }

    public static String reflectionToString(MethodHandle mh, Object obj) {
        if (delegater == null) {
            return methodHandle(mh, obj);
        }
        return delegater.reflectionToString(obj);
    }
}
