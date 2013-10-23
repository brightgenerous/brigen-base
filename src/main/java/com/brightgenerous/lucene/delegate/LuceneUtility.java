package com.brightgenerous.lucene.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class LuceneUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final LuceneDelegater delegater;

    static {
        LuceneDelegater tmp = null;
        boolean useful = false;
        try {
            tmp = new LuceneDelegaterImpl();
            useful = true;
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
        USEFUL = useful;
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
