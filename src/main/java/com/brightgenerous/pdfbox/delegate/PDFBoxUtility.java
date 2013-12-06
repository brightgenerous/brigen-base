package com.brightgenerous.pdfbox.delegate;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class PDFBoxUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final PDFBoxDelegater delegater;

    private static final RuntimeException rex;

    static {
        PDFBoxDelegater tmp = null;
        boolean useful = false;
        RuntimeException ex = null;
        try {
            tmp = new PDFBoxDelegaterImpl();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve jxl api");
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

    private PDFBoxUtility() {
    }

    public static boolean isPdf(InputStream inputStream) {
        if (delegater == null) {
            throw rex;
        }
        return delegater.isPdf(inputStream);
    }
}
