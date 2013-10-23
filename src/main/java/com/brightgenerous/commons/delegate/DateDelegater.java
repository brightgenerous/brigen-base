package com.brightgenerous.commons.delegate;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

interface DateDelegater {

    boolean isSameDay(Date date1, Date date2);

    boolean isSameDay(Calendar cal1, Calendar cal2);

    boolean isSameInstant(Date date1, Date date2);

    boolean isSameInstant(Calendar cal1, Calendar cal2);

    boolean isSameLocalTime(Calendar cal1, Calendar cal2);

    Date parseDate(String str, String... parsePatterns) throws ParseException;

    Date parseDateStrictly(String str, String... parsePatterns) throws ParseException;

    Date addYears(Date date, int amount);

    Date addMonths(Date date, int amount);

    Date addWeeks(Date date, int amount);

    Date addDays(Date date, int amount);

    Date addHours(Date date, int amount);

    Date addMinutes(Date date, int amount);

    Date addSeconds(Date date, int amount);

    Date addMilliseconds(Date date, int amount);

    Date setYears(Date date, int amount);

    Date setMonths(Date date, int amount);

    Date setDays(Date date, int amount);

    Date setHours(Date date, int amount);

    Date setMinutes(Date date, int amount);

    Date truncate(Date date, int field);

    Calendar truncate(Calendar date, int field);

    long getFragmentInMilliseconds(Date date, int fragment);

    long getFragmentInSeconds(Date date, int fragment);

    long getFragmentInMinutes(Date date, int fragment);

    long getFragmentInHours(Date date, int fragment);

    long getFragmentInDays(Date date, int fragment);

    long getFragmentInMilliseconds(Calendar calendar, int fragment);

    long getFragmentInSeconds(Calendar calendar, int fragment);

    long getFragmentInMinutes(Calendar calendar, int fragment);

    long getFragmentInHours(Calendar calendar, int fragment);

    long getFragmentInDays(Calendar calendar, int fragment);

    boolean truncatedEquals(Calendar cal1, Calendar cal2, int field);

    boolean truncatedEquals(Date date1, Date date2, int field);

    int truncatedCompareTo(Calendar cal1, Calendar cal2, int field);

    int truncatedCompareTo(Date date1, Date date2, int field);
}
