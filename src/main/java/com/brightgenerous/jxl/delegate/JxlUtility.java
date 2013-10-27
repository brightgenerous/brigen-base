package com.brightgenerous.jxl.delegate;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class JxlUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean RESOLVED;

    private static final JxlDelegater delegater;

    static {
        JxlDelegater tmp = null;
        boolean resolved = false;
        try {
            tmp = new JxlDelegaterImpl();
            resolved = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve jxl api");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        if (tmp == null) {
            tmp = new JxlDelegaterSub();
        }
        RESOLVED = resolved;
        delegater = tmp;
    }

    private JxlUtility() {
    }

    public static InputStream wrap(InputStream inputStream) throws IOException {
        return delegater.wrap(inputStream);
    }
}
