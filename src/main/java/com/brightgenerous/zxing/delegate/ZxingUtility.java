package com.brightgenerous.zxing.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.zxing.ZxingBuilder;
import com.brightgenerous.zxing.deleg.android.ZxingAndroidUtility;
import com.brightgenerous.zxing.deleg.javase.ZxingJavaseUtility;

@Deprecated
public class ZxingUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final RuntimeException rex;

    static {
        RuntimeException ex = null;
        boolean useful = false;
        try {
            Class.forName(ZxingBuilderImpl.class.getName());
            useful = true;
        } catch (ClassNotFoundException | NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve com.google.zxing core");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw (RuntimeException) e;
                }
                ex = (RuntimeException) e;
            } else {
                ex = new RuntimeException(e);
            }
        }
        USEFUL = useful;
        rex = ex;
    }

    public static boolean useful() {
        return USEFUL && (ZxingJavaseUtility.USEFUL || ZxingAndroidUtility.USEFUL);
    }

    public static boolean resolved() {
        return USEFUL && (ZxingJavaseUtility.RESOLVED || ZxingAndroidUtility.RESOLVED);
    }

    public static boolean resolvedJavase() {
        return USEFUL && ZxingJavaseUtility.RESOLVED;
    }

    public static boolean resolvedAndroid() {
        return USEFUL && ZxingAndroidUtility.RESOLVED;
    }

    private ZxingUtility() {
    }

    public static <B, E, R, S, D, C, DS> ZxingBuilder<B, E, R, S, D, C, DS> builder() {
        if (rex != null) {
            throw rex;
        }
        return (ZxingBuilder<B, E, R, S, D, C, DS>) ZxingBuilderImpl.create();
    }
}
