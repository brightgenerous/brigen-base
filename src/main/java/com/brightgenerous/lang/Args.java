package com.brightgenerous.lang;

import java.io.Serializable;
import java.util.Collection;

public class Args {

    private Args() {
    }

    public static <T> T notNull(T argument, String name) {
        if (argument == null) {
            throw new IllegalArgumentException("Argument '" + name + "' may not be null.");
        }
        return argument;
    }

    public static <T extends CharSequence> T notEmpty(T argument, String name) {
        if ((argument == null) || (argument.length() < 1)) {
            throw new IllegalArgumentException("Argument '" + name + "' may not be null or empty.");
        }
        return argument;
    }

    public static <T extends Collection<?>> T notEmpty(T collection, String message,
            Object... params) {
        if ((collection == null) || collection.isEmpty()) {
            throw new IllegalArgumentException(Args.format(message, params));
        }
        return collection;
    }

    public static <T extends Collection<?>> T notEmpty(T collection, String name) {
        return notEmpty(collection, "Collection '%s' may not be null or empty.", name);
    }

    public static <T> T[] notEmpty(T[] array, String message, Object... params) {
        if ((array == null) || (array.length < 1)) {
            throw new IllegalArgumentException(Args.format(message, params));
        }
        return array;
    }

    public static <T> T[] notEmpty(T[] array, String name) {
        return notEmpty(array, "Array '%s' may not be null or empty.", name);
    }

    public static byte lowerThan(byte max, byte value, String name) {
        return lowerThan(Byte.valueOf(max), Byte.valueOf(value), name).byteValue();
    }

    public static short lowerThan(short max, short value, String name) {
        return lowerThan(Short.valueOf(max), Short.valueOf(value), name).shortValue();
    }

    public static int lowerThan(int max, int value, String name) {
        return lowerThan(Integer.valueOf(max), Integer.valueOf(value), name).intValue();
    }

    public static long lowerThan(long max, long value, String name) {
        return lowerThan(Long.valueOf(max), Long.valueOf(value), name).longValue();
    }

    public static float lowerThan(float max, float value, String name) {
        return lowerThan(Float.valueOf(max), Float.valueOf(value), name).floatValue();
    }

    public static double lowerThan(double max, double value, String name) {
        return lowerThan(Double.valueOf(max), Double.valueOf(value), name).doubleValue();
    }

    public static <T extends Comparable<T>> T lowerThan(T max, T value, String name) {
        notNull(max, name);
        if ((value.compareTo(max) >= 0)) {
            throw new IllegalArgumentException(
                    String.format("Argument '%s' must have a value lower than [%s], but was %s",
                            name, max, value));
        }
        return value;
    }

    public static byte lowerEqual(byte max, byte value, String name) {
        return lowerEqual(Byte.valueOf(max), Byte.valueOf(value), name).byteValue();
    }

    public static short lowerEqual(short max, short value, String name) {
        return lowerEqual(Short.valueOf(max), Short.valueOf(value), name).shortValue();
    }

    public static int lowerEqual(int max, int value, String name) {
        return lowerEqual(Integer.valueOf(max), Integer.valueOf(value), name).intValue();
    }

    public static long lowerEqual(long max, long value, String name) {
        return lowerEqual(Long.valueOf(max), Long.valueOf(value), name).longValue();
    }

    public static float lowerEqual(float max, float value, String name) {
        return lowerEqual(Float.valueOf(max), Float.valueOf(value), name).floatValue();
    }

    public static double lowerEqual(double max, double value, String name) {
        return lowerEqual(Double.valueOf(max), Double.valueOf(value), name).doubleValue();
    }

    public static <T extends Comparable<T>> T lowerEqual(T max, T value, String name) {
        notNull(max, name);
        if ((value.compareTo(max) > 0)) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s' must have a value lower equal [%s], but was %s", name, max,
                    value));
        }
        return value;
    }

    public static byte greaterThan(byte max, byte value, String name) {
        return greaterThan(Byte.valueOf(max), Byte.valueOf(value), name).byteValue();
    }

    public static short greaterThan(short max, short value, String name) {
        return greaterThan(Short.valueOf(max), Short.valueOf(value), name).shortValue();
    }

    public static int greaterThan(int max, int value, String name) {
        return greaterThan(Integer.valueOf(max), Integer.valueOf(value), name).intValue();
    }

    public static long greaterThan(long max, long value, String name) {
        return greaterThan(Long.valueOf(max), Long.valueOf(value), name).longValue();
    }

    public static float greaterThan(float max, float value, String name) {
        return greaterThan(Float.valueOf(max), Float.valueOf(value), name).floatValue();
    }

    public static double greaterThan(double max, double value, String name) {
        return greaterThan(Double.valueOf(max), Double.valueOf(value), name).doubleValue();
    }

    public static <T extends Comparable<T>> T greaterThan(T min, T value, String name) {
        notNull(min, name);
        if ((value.compareTo(min) <= 0)) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s' must have a value greater than [%s], but was %s", name, min,
                    value));
        }
        return value;
    }

    public static byte greaterEqual(byte max, byte value, String name) {
        return greaterEqual(Byte.valueOf(max), Byte.valueOf(value), name).byteValue();
    }

    public static short greaterEqual(short max, short value, String name) {
        return greaterEqual(Short.valueOf(max), Short.valueOf(value), name).shortValue();
    }

    public static int greaterEqual(int max, int value, String name) {
        return greaterEqual(Integer.valueOf(max), Integer.valueOf(value), name).intValue();
    }

    public static long greaterEqual(long max, long value, String name) {
        return greaterEqual(Long.valueOf(max), Long.valueOf(value), name).longValue();
    }

    public static float greaterEqual(float max, float value, String name) {
        return greaterEqual(Float.valueOf(max), Float.valueOf(value), name).floatValue();
    }

    public static double greaterEqual(double max, double value, String name) {
        return greaterEqual(Double.valueOf(max), Double.valueOf(value), name).doubleValue();
    }

    public static <T extends Comparable<T>> T greaterEqual(T min, T value, String name) {
        notNull(min, name);
        if ((value.compareTo(min) < 0)) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s' must have a value greater equal [%s], but was %s", name, min,
                    value));
        }
        return value;
    }

    public static byte withinRange(byte min, byte max, byte value, String name) {
        return withinRange(Byte.valueOf(min), Byte.valueOf(max), Byte.valueOf(value), name)
                .byteValue();
    }

    public static short withinRange(short min, short max, short value, String name) {
        return withinRange(Short.valueOf(min), Short.valueOf(max), Short.valueOf(value), name)
                .shortValue();
    }

    public static int withinRange(int min, int max, int value, String name) {
        return withinRange(Integer.valueOf(min), Integer.valueOf(max), Integer.valueOf(value), name)
                .intValue();
    }

    public static long withinRange(long min, long max, long value, String name) {
        return withinRange(Long.valueOf(min), Long.valueOf(max), Long.valueOf(value), name)
                .longValue();
    }

    public static float withinRange(float min, float max, float value, String name) {
        return withinRange(Float.valueOf(min), Float.valueOf(max), Float.valueOf(value), name)
                .floatValue();
    }

    public static double withinRange(double min, double max, double value, String name) {
        return withinRange(Double.valueOf(min), Double.valueOf(max), Double.valueOf(value), name)
                .doubleValue();
    }

    public static <T extends Comparable<T>> T withinRange(T min, T max, T value, String name) {
        notNull(min, name);
        notNull(max, name);
        if ((value.compareTo(min) < 0) || (value.compareTo(max) > 0)) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s' must have a value within [%s,%s], but was %s", name, min, max,
                    value));
        }
        return value;
    }

    public static boolean isTrue(boolean argument, String msg, Object... params) {
        if (!argument) {
            throw new IllegalArgumentException(format(msg, params));
        }
        return argument;
    }

    public static boolean isFalse(boolean argument, String msg, Object... params) {
        if (argument) {
            throw new IllegalArgumentException(format(msg, params));
        }
        return argument;
    }

    public static <T> T isSerializable(T argument, String name) {
        return instanceOf(argument, Serializable.class, name);
    }

    public static <T> T instanceOf(T argument, Class<?> clazz, String name) {
        notNull(argument, name);
        notNull(clazz, "clazz");
        if (!clazz.isAssignableFrom(argument.getClass())) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s' must instanceof %s, but argument was %s", name, clazz.getName(),
                    argument.getClass().getName()));
        }
        return argument;
    }

    static String format(String msg, Object... params) {
        msg = msg.replaceAll("\\{\\}", "%s");
        return String.format(msg, params);
    }
}
