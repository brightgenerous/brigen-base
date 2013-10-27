package com.brightgenerous.lucene.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class LuceneUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean RESOLVED;

    private static final LuceneDelegater delegater;

    static {
        LuceneDelegater tmp = null;
        boolean resolved = false;
        try {
            tmp = new LuceneDelegaterImpl();
            resolved = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve apache lucene");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        if (tmp == null) {
            tmp = new LuceneDelegaterSub();
        }
        RESOLVED = resolved;
        delegater = tmp;
    }

    private LuceneUtility() {
    }

    public static StringDistanceDelegater createLevensteinDistance() {
        return delegater.createLevensteinDistance();
    }

    public static StringDistanceDelegater createJaroWinklerDistance() {
        return delegater.createJaroWinklerDistance();
    }
}
