package com.brightgenerous.commons.delegate;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class DateUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final DateDelegater delegater;

    static {
        DateDelegater tmp = null;
        boolean useful = false;
        try {
            tmp = new DateDelegaterImpl();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve apache commons lang DateUtils");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        if (tmp == null) {
            tmp = new DateDelegaterSub();
        }
        USEFUL = useful;
        delegater = tmp;
    }

    private DateUtility() {
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return delegater.isSameDay(date1, date2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return delegater.isSameDay(cal1, cal2);
    }

    public static boolean isSameInstant(Date date1, Date date2) {
        return delegater.isSameInstant(date1, date2);
    }

    public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
        return delegater.isSameInstant(cal1, cal2);
    }

    public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
        return delegater.isSameLocalTime(cal1, cal2);
    }

    public static Date parseDate(String str, String... parsePatterns) throws ParseException {
        return delegater.parseDate(str, parsePatterns);
    }

    public static Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
        return delegater.parseDateStrictly(str, parsePatterns);
    }

    public static Date addYears(Date date, int amount) {
        return delegater.addYears(date, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return delegater.addMonths(date, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return delegater.addWeeks(date, amount);
    }

    public static Date addDays(Date date, int amount) {
        return delegater.addDays(date, amount);
    }

    public static Date addHours(Date date, int amount) {
        return delegater.addHours(date, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return delegater.addMinutes(date, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return delegater.addSeconds(date, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return delegater.addMilliseconds(date, amount);
    }

    public static Date setYears(Date date, int amount) {
        return delegater.setYears(date, amount);
    }

    public static Date setMonths(Date date, int amount) {
        return delegater.setMonths(date, amount);
    }

    public static Date setDays(Date date, int amount) {
        return delegater.setDays(date, amount);
    }

    public static Date setHours(Date date, int amount) {
        return delegater.setHours(date, amount);
    }

    public static Date setMinutes(Date date, int amount) {
        return delegater.setMinutes(date, amount);
    }

    public static Date truncate(Date date, int field) {
        return delegater.truncate(date, field);
    }

    public static Calendar truncate(Calendar date, int field) {
        return delegater.truncate(date, field);
    }

    public static long getFragmentInMilliseconds(Date date, int fragment) {
        return delegater.getFragmentInMilliseconds(date, fragment);
    }

    public static long getFragmentInSeconds(Date date, int fragment) {
        return delegater.getFragmentInSeconds(date, fragment);
    }

    public static long getFragmentInMinutes(Date date, int fragment) {
        return delegater.getFragmentInMinutes(date, fragment);
    }

    public static long getFragmentInHours(Date date, int fragment) {
        return delegater.getFragmentInHours(date, fragment);
    }

    public static long getFragmentInDays(Date date, int fragment) {
        return delegater.getFragmentInDays(date, fragment);
    }

    public static long getFragmentInMilliseconds(Calendar calendar, int fragment) {
        return delegater.getFragmentInMilliseconds(calendar, fragment);
    }

    public static long getFragmentInSeconds(Calendar calendar, int fragment) {
        return delegater.getFragmentInSeconds(calendar, fragment);
    }

    public static long getFragmentInMinutes(Calendar calendar, int fragment) {
        return delegater.getFragmentInMinutes(calendar, fragment);
    }

    public static long getFragmentInHours(Calendar calendar, int fragment) {
        return delegater.getFragmentInHours(calendar, fragment);
    }

    public static long getFragmentInDays(Calendar calendar, int fragment) {
        return delegater.getFragmentInDays(calendar, fragment);
    }

    public static boolean truncatedEquals(Calendar cal1, Calendar cal2, int field) {
        return delegater.truncatedEquals(cal1, cal2, field);
    }

    public static boolean truncatedEquals(Date date1, Date date2, int field) {
        return delegater.truncatedEquals(date1, date2, field);
    }

    public static int truncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
        return delegater.truncatedCompareTo(cal1, cal2, field);
    }

    public static int truncatedCompareTo(Date date1, Date date2, int field) {
        return delegater.truncatedCompareTo(date1, date2, field);
    }
}
