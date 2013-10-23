package com.brightgenerous.commons;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.brightgenerous.commons.delegate.DateUtility;

@SuppressWarnings("deprecation")
public class DateUtils {

    public static boolean useful() {
        return DateUtility.USEFUL;
    }

    private DateUtils() {
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return DateUtility.isSameDay(date1, date2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return DateUtility.isSameDay(cal1, cal2);
    }

    public static boolean isSameInstant(Date date1, Date date2) {
        return DateUtility.isSameInstant(date1, date2);
    }

    public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
        return DateUtility.isSameInstant(cal1, cal2);
    }

    public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
        return DateUtility.isSameLocalTime(cal1, cal2);
    }

    public static Date parseDate(String str, String... parsePatterns) throws ParseException {
        return DateUtility.parseDate(str, parsePatterns);
    }

    public static Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
        return DateUtility.parseDateStrictly(str, parsePatterns);
    }

    public static Date addYears(Date date, int amount) {
        return DateUtility.addYears(date, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return DateUtility.addMonths(date, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return DateUtility.addWeeks(date, amount);
    }

    public static Date addDays(Date date, int amount) {
        return DateUtility.addDays(date, amount);
    }

    public static Date addHours(Date date, int amount) {
        return DateUtility.addHours(date, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return DateUtility.addMinutes(date, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return DateUtility.addSeconds(date, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return DateUtility.addMilliseconds(date, amount);
    }

    public static Date setYears(Date date, int amount) {
        return DateUtility.setYears(date, amount);
    }

    public static Date setMonths(Date date, int amount) {
        return DateUtility.setMonths(date, amount);
    }

    public static Date setDays(Date date, int amount) {
        return DateUtility.setDays(date, amount);
    }

    public static Date setHours(Date date, int amount) {
        return DateUtility.setHours(date, amount);
    }

    public static Date setMinutes(Date date, int amount) {
        return DateUtility.setMinutes(date, amount);
    }

    public static Date truncate(Date date, int field) {
        return DateUtility.truncate(date, field);
    }

    public static Calendar truncate(Calendar date, int field) {
        return DateUtility.truncate(date, field);
    }

    public static long getFragmentInMilliseconds(Date date, int fragment) {
        return DateUtility.getFragmentInMilliseconds(date, fragment);
    }

    public static long getFragmentInSeconds(Date date, int fragment) {
        return DateUtility.getFragmentInSeconds(date, fragment);
    }

    public static long getFragmentInMinutes(Date date, int fragment) {
        return DateUtility.getFragmentInMinutes(date, fragment);
    }

    public static long getFragmentInHours(Date date, int fragment) {
        return DateUtility.getFragmentInHours(date, fragment);
    }

    public static long getFragmentInDays(Date date, int fragment) {
        return DateUtility.getFragmentInDays(date, fragment);
    }

    public static long getFragmentInMilliseconds(Calendar calendar, int fragment) {
        return DateUtility.getFragmentInMilliseconds(calendar, fragment);
    }

    public static long getFragmentInSeconds(Calendar calendar, int fragment) {
        return DateUtility.getFragmentInSeconds(calendar, fragment);
    }

    public static long getFragmentInMinutes(Calendar calendar, int fragment) {
        return DateUtility.getFragmentInMinutes(calendar, fragment);
    }

    public static long getFragmentInHours(Calendar calendar, int fragment) {
        return DateUtility.getFragmentInHours(calendar, fragment);
    }

    public static long getFragmentInDays(Calendar calendar, int fragment) {
        return DateUtility.getFragmentInDays(calendar, fragment);
    }

    public static boolean truncatedEquals(Calendar cal1, Calendar cal2, int field) {
        return DateUtility.truncatedEquals(cal1, cal2, field);
    }

    public static boolean truncatedEquals(Date date1, Date date2, int field) {
        return DateUtility.truncatedEquals(date1, date2, field);
    }

    public static int truncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
        return DateUtility.truncatedCompareTo(cal1, cal2, field);
    }

    public static int truncatedCompareTo(Date date1, Date date2, int field) {
        return DateUtility.truncatedCompareTo(date1, date2, field);
    }
}
