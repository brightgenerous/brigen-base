package com.brightgenerous.commons;

import com.brightgenerous.commons.delegate.StringUtility;

@SuppressWarnings("deprecation")
public class StringUtils {

    public static boolean useful() {
        return StringUtility.USEFUL;
    }

    public static final String EMPTY = StringUtility.EMPTY;

    private StringUtils() {
    }

    public static boolean isEmptyNotNull(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        return isEmpty(cs);
    }

    public static boolean isEmpty(CharSequence cs) {
        return StringUtility.isEmpty(cs);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return StringUtility.isNotEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        return StringUtility.isBlank(cs);
    }

    public static boolean isNotBlank(CharSequence cs) {
        return StringUtility.isNotBlank(cs);
    }

    public static String trim(String str) {
        return StringUtility.trim(str);
    }

    public static String trimToNull(String str) {
        return StringUtility.trimToNull(str);
    }

    public static String trimToEmpty(String str) {
        return StringUtility.trimToEmpty(str);
    }

    public static String strip(String str) {
        return StringUtility.strip(str);
    }

    public static String stripToNull(String str) {
        return StringUtility.stripToNull(str);
    }

    public static String stripToEmpty(String str) {
        return StringUtility.stripToEmpty(str);
    }

    public static String strip(String str, String stripChars) {
        return StringUtility.strip(str, stripChars);
    }

    public static String stripStart(String str, String stripChars) {
        return StringUtility.stripStart(str, stripChars);
    }

    public static String stripEnd(String str, String stripChars) {
        return StringUtility.stripEnd(str, stripChars);
    }

    public static boolean isNumeric(CharSequence cs) {
        return StringUtility.isNumeric(cs);
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return StringUtility.indexOfIgnoreCase(str, searchStr);
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return StringUtility.indexOfIgnoreCase(str, searchStr, startPos);
    }

    public static String removeStart(String str, String remove) {
        return StringUtility.removeStart(str, remove);
    }
}
