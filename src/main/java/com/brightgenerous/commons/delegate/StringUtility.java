package com.brightgenerous.commons.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class StringUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final StringDelegater delegater;

    static {
        StringDelegater tmp = null;
        boolean useful = false;
        try {
            tmp = new StringDelegaterImpl();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve apache commons lang StringUtils");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        if (tmp == null) {
            tmp = new StringDelegaterSub();
        }
        USEFUL = useful;
        delegater = tmp;
    }

    public static final String EMPTY = delegater.empty();

    private StringUtility() {
    }

    public static boolean isEmpty(CharSequence cs) {
        return delegater.isEmpty(cs);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return delegater.isNotEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        return delegater.isBlank(cs);
    }

    public static boolean isNotBlank(CharSequence cs) {
        return delegater.isNotBlank(cs);
    }

    public static String trim(String str) {
        return delegater.trim(str);
    }

    public static String trimToNull(String str) {
        return delegater.trimToNull(str);
    }

    public static String trimToEmpty(String str) {
        return delegater.trimToEmpty(str);
    }

    public static String strip(String str) {
        return delegater.strip(str);
    }

    public static String stripToNull(String str) {
        return delegater.stripToNull(str);
    }

    public static String stripToEmpty(String str) {
        return delegater.stripToEmpty(str);
    }

    public static String strip(String str, String stripChars) {
        return delegater.strip(str, stripChars);
    }

    public static String stripStart(String str, String stripChars) {
        return delegater.stripStart(str, stripChars);
    }

    public static String stripEnd(String str, String stripChars) {
        return delegater.stripEnd(str, stripChars);
    }

    public static boolean isNumeric(CharSequence cs) {
        return delegater.isNumeric(cs);
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return delegater.indexOfIgnoreCase(str, searchStr);
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return delegater.indexOfIgnoreCase(str, searchStr, startPos);
    }

    public static String removeStart(String str, String remove) {
        return delegater.removeStart(str, remove);
    }
}
