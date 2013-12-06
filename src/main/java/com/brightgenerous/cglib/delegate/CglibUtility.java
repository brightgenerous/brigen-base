package com.brightgenerous.cglib.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class CglibUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final CglibDelegater delegater;

    private static final RuntimeException rex;

    static {
        CglibDelegater tmp = null;
        RuntimeException ex = null;
        boolean useful = false;
        try {
            tmp = new CglibDelegaterImpl();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve javax mail");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
                ex = (RuntimeException) e;
            } else {
                ex = new RuntimeException(e);
            }
        }
        USEFUL = useful;
        delegater = tmp;
        rex = ex;
    }

    private CglibUtility() {
    }

    public static Class<?> defineInterface(String name, Class<?>... supers) {
        if (delegater == null) {
            throw rex;
        }
        return delegater.defineInterface(name, supers);
    }
}
