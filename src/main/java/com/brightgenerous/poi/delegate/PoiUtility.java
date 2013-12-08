package com.brightgenerous.poi.delegate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class PoiUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final PoiDelegater delegater;

    private static final RuntimeException rex;

    static {
        PoiDelegater tmp = null;
        boolean useful = false;
        RuntimeException ex = null;
        try {
            tmp = new PoiDelegaterImpl();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve poi");
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

    private PoiUtility() {
    }

    private static PoiDelegater getDelegater() {
        if (delegater == null) {
            throw rex;
        }
        return delegater;
    }

    public static boolean isExcel(File file) throws IOException {
        return getDelegater().isExcel(file);
    }

    public static boolean isExcel(InputStream inputStream) throws IOException {
        return getDelegater().isExcel(inputStream);
    }

    public static boolean isXls(File file) throws IOException {
        return getDelegater().isXls(file);
    }

    public static boolean isXls(InputStream inputStream) throws IOException {
        return getDelegater().isXls(inputStream);
    }

    public static boolean isXlsx(File file) throws IOException {
        return getDelegater().isXlsx(file);
    }

    public static boolean isXlsx(InputStream inputStream) throws IOException {
        return getDelegater().isXlsx(inputStream);
    }
}
