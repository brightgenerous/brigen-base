package com.brightgenerous.commons.delegate;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

class DateDelegaterImpl implements DateDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(DateUtils.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isSameDay(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    @Override
    public boolean isSameDay(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameDay(cal1, cal2);
    }

    @Override
    public boolean isSameInstant(Date date1, Date date2) {
        return DateUtils.isSameInstant(date1, date2);
    }

    @Override
    public boolean isSameInstant(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameInstant(cal1, cal2);
    }

    @Override
    public boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameLocalTime(cal1, cal2);
    }

    @Override
    public Date parseDate(String str, String... parsePatterns) throws ParseException {
        return DateUtils.parseDate(str, parsePatterns);
    }

    @Override
    public Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
        return DateUtils.parseDateStrictly(str, parsePatterns);
    }

    @Override
    public Date addYears(Date date, int amount) {
        return DateUtils.addYears(date, amount);
    }

    @Override
    public Date addMonths(Date date, int amount) {
        return DateUtils.addMonths(date, amount);
    }

    @Override
    public Date addWeeks(Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    @Override
    public Date addDays(Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    @Override
    public Date addHours(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    @Override
    public Date addMinutes(Date date, int amount) {
        return DateUtils.addMinutes(date, amount);
    }

    @Override
    public Date addSeconds(Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    @Override
    public Date addMilliseconds(Date date, int amount) {
        return DateUtils.addMilliseconds(date, amount);
    }

    @Override
    public Date setYears(Date date, int amount) {
        return DateUtils.setYears(date, amount);
    }

    @Override
    public Date setMonths(Date date, int amount) {
        return DateUtils.setMonths(date, amount);
    }

    @Override
    public Date setDays(Date date, int amount) {
        return DateUtils.setDays(date, amount);
    }

    @Override
    public Date setHours(Date date, int amount) {
        return DateUtils.setHours(date, amount);
    }

    @Override
    public Date setMinutes(Date date, int amount) {
        return DateUtils.setMinutes(date, amount);
    }

    @Override
    public Date truncate(Date date, int field) {
        return DateUtils.truncate(date, field);
    }

    @Override
    public Calendar truncate(Calendar date, int field) {
        return DateUtils.truncate(date, field);
    }

    @Override
    public long getFragmentInMilliseconds(Date date, int fragment) {
        return DateUtils.getFragmentInMilliseconds(date, fragment);
    }

    @Override
    public long getFragmentInSeconds(Date date, int fragment) {
        return DateUtils.getFragmentInSeconds(date, fragment);
    }

    @Override
    public long getFragmentInMinutes(Date date, int fragment) {
        return DateUtils.getFragmentInMinutes(date, fragment);
    }

    @Override
    public long getFragmentInHours(Date date, int fragment) {
        return DateUtils.getFragmentInHours(date, fragment);
    }

    @Override
    public long getFragmentInDays(Date date, int fragment) {
        return DateUtils.getFragmentInDays(date, fragment);
    }

    @Override
    public long getFragmentInMilliseconds(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInMilliseconds(calendar, fragment);
    }

    @Override
    public long getFragmentInSeconds(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInSeconds(calendar, fragment);
    }

    @Override
    public long getFragmentInMinutes(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInMinutes(calendar, fragment);
    }

    @Override
    public long getFragmentInHours(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInHours(calendar, fragment);
    }

    @Override
    public long getFragmentInDays(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInDays(calendar, fragment);
    }

    @Override
    public boolean truncatedEquals(Calendar cal1, Calendar cal2, int field) {
        return DateUtils.truncatedEquals(cal1, cal2, field);
    }

    @Override
    public boolean truncatedEquals(Date date1, Date date2, int field) {
        return DateUtils.truncatedEquals(date1, date2, field);
    }

    @Override
    public int truncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
        return DateUtils.truncatedCompareTo(cal1, cal2, field);
    }

    @Override
    public int truncatedCompareTo(Date date1, Date date2, int field) {
        return DateUtils.truncatedCompareTo(date1, date2, field);
    }
}
