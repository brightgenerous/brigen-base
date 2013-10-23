package com.brightgenerous.commons;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;

import com.brightgenerous.lang.Args;

public class ObjectUtils {

    private ObjectUtils() {
    }

    public static <T> T defaultIfNull(T obj, T def, T... defs) {
        if (obj != null) {
            return obj;
        }
        if (def != null) {
            return def;
        }
        if (defs != null) {
            for (T d : defs) {
                if (d != null) {
                    return d;
                }
            }
        }
        return null;
    }

    public static <T> boolean allNull(T obj, T... objs) {
        if (obj != null) {
            return false;
        }
        if ((objs != null) && (0 < objs.length)) {
            for (T o : objs) {
                if (o != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static <T> boolean allNotNull(T obj, T... objs) {
        if (obj == null) {
            return false;
        }
        if (objs == null) {
            return false;
        }
        if (0 < objs.length) {
            for (T o : objs) {
                if (o == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static <T> boolean anyNull(T obj, T... objs) {
        return !allNotNull(obj, objs);
    }

    public static <T> boolean anyNotNull(T obj, T... objs) {
        return !allNull(obj, objs);
    }

    public static <T> int compareNull(T obj0, T obj1) {
        return compareNull(obj0, obj1, false);
    }

    public static <T> int compareNull(T obj0, T obj1, boolean isNullLower) {
        if (obj0 == obj1) {
            return 0;
        }
        if (obj0 == null) {
            return isNullLower ? -1 : 1;
        }
        if (obj1 == null) {
            return isNullLower ? 1 : -1;
        }
        return 0;
    }

    public static <T> int compareEscapeNull(Comparable<T> obj0, T obj1) {
        return compareEscapeNull(obj0, obj1, false);
    }

    public static <T> int compareEscapeNull(Comparable<T> obj0, T obj1, boolean isNullLower) {
        if (obj0 == obj1) {
            return 0;
        }
        if (obj0 == null) {
            return isNullLower ? -1 : 1;
        }
        if (obj1 == null) {
            return isNullLower ? 1 : -1;
        }
        return obj0.compareTo(obj1);
    }

    public static <T> int compareEscapeNull(T obj0, T obj1, Comparator<T> comparator) {
        return compareEscapeNull(obj0, obj1, comparator, false);
    }

    public static <T> int compareEscapeNull(T obj0, T obj1, Comparator<T> comparator,
            boolean isNullLower) {
        if (obj0 == obj1) {
            return 0;
        }
        if (obj0 == null) {
            return isNullLower ? -1 : 1;
        }
        if (obj1 == null) {
            return isNullLower ? 1 : -1;
        }

        Args.notNull(comparator, "comparator");

        return comparator.compare(obj0, obj1);
    }

    public static boolean equalsValueOrNull(Boolean obj0, Boolean obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            }
            return false;
        }
        if (obj1 == null) {
            return false;
        }
        return obj0.booleanValue() == obj1.booleanValue();
    }

    public static boolean equalsValueOrNull(Byte obj0, Byte obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            }
            return false;
        }
        if (obj1 == null) {
            return false;
        }
        return obj0.byteValue() == obj1.byteValue();
    }

    public static boolean equalsValueOrNull(Short obj0, Short obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            }
            return false;
        }
        if (obj1 == null) {
            return false;
        }
        return obj0.shortValue() == obj1.shortValue();
    }

    public static boolean equalsValueOrNull(Character obj0, Character obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            }
            return false;
        }
        if (obj1 == null) {
            return false;
        }
        return obj0.charValue() == obj1.charValue();
    }

    public static boolean equalsValueOrNull(Integer obj0, Integer obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            }
            return false;
        }
        if (obj1 == null) {
            return false;
        }
        return obj0.intValue() == obj1.intValue();
    }

    public static boolean equalsValueOrNull(Long obj0, Long obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            }
            return false;
        }
        if (obj1 == null) {
            return false;
        }
        return obj0.longValue() == obj1.longValue();
    }

    public static boolean equalsValueOrNull(Float obj0, Float obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            }
            return false;
        }
        if (obj1 == null) {
            return false;
        }
        return obj0.floatValue() == obj1.floatValue();
    }

    public static boolean equalsValueOrNull(Double obj0, Double obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            }
            return false;
        }
        if (obj1 == null) {
            return false;
        }
        return obj0.doubleValue() == obj1.doubleValue();
    }

    public static boolean equalsValueOrNull(String obj0, String obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            }
            return false;
        }
        if (obj1 == null) {
            return false;
        }
        return obj0.equals(obj1);
    }

    public static <T> boolean equalsValueOrNull(T obj0, T obj1) {
        if (obj0 == null) {
            if (obj1 == null) {
                return true;
            }
            return false;
        }
        if (obj1 == null) {
            return false;
        }
        Class<?> clazz0 = obj0.getClass();
        Class<?> clazz1 = obj1.getClass();
        if (!clazz0.equals(clazz1)) {
            return false;
        }
        if (obj0 instanceof Boolean) {
            return equalsValueOrNull((Boolean) obj0, (Boolean) obj1);
        }
        if (obj0 instanceof Byte) {
            return equalsValueOrNull((Byte) obj0, (Byte) obj1);
        }
        if (obj0 instanceof Short) {
            return equalsValueOrNull((Short) obj0, (Short) obj1);
        }
        if (obj0 instanceof Character) {
            return equalsValueOrNull((Character) obj0, (Character) obj1);
        }
        if (obj0 instanceof Integer) {
            return equalsValueOrNull((Integer) obj0, (Integer) obj1);
        }
        if (obj0 instanceof Long) {
            return equalsValueOrNull((Long) obj0, (Long) obj1);
        }
        if (obj0 instanceof Float) {
            return equalsValueOrNull((Float) obj0, (Float) obj1);
        }
        if (obj0 instanceof Double) {
            return equalsValueOrNull((Double) obj0, (Double) obj1);
        }
        if (obj0 instanceof String) {
            return equalsValueOrNull((String) obj0, (String) obj1);
        }
        return obj0 == obj1;
    }

    public static int hashCodeEscapeNull(Object obj) {
        return hashCodeEscapeNull(obj, 0);
    }

    public static int hashCodeEscapeNull(Object obj, int nullValue) {
        if (obj == null) {
            return nullValue;
        }
        return obj.hashCode();
    }

    public static boolean equalsEscapeNull(Object obj0, Object obj1) {
        if (obj0 == obj1) {
            return true;
        }
        if ((obj0 == null) || (obj1 == null)) {
            return false;
        }
        return obj0.equals(obj1);
    }

    public static <S, T extends S> T cast(S obj, Class<? extends T> clazz) {
        return cast(obj, null, clazz);
    }

    public static <S, T extends S> T cast(S obj, T def, Class<? extends T> clazz) {
        if (obj == null) {
            return null;
        }
        if (clazz != null) {
            try {
                return clazz.cast(obj);
            } catch (ClassCastException e) {
            }
        }
        return def;
    }

    public static boolean isNoSize(boolean[] obj) {
        return (obj == null) || (obj.length < 1);
    }

    public static boolean isNotNoSize(boolean[] obj) {
        return !isNoSize(obj);
    }

    public static boolean isNoSize(byte[] obj) {
        return (obj == null) || (obj.length < 1);
    }

    public static boolean isNotNoSize(byte[] obj) {
        return !isNoSize(obj);
    }

    public static boolean isNoSize(short[] obj) {
        return (obj == null) || (obj.length < 1);
    }

    public static boolean isNotNoSize(short[] obj) {
        return !isNoSize(obj);
    }

    public static boolean isNoSize(char[] obj) {
        return (obj == null) || (obj.length < 1);
    }

    public static boolean isNotNoSize(char[] obj) {
        return !isNoSize(obj);
    }

    public static boolean isNoSize(int[] obj) {
        return (obj == null) || (obj.length < 1);
    }

    public static boolean isNotNoSize(int[] obj) {
        return !isNoSize(obj);
    }

    public static boolean isNoSize(long[] obj) {
        return (obj == null) || (obj.length < 1);
    }

    public static boolean isNotNoSize(long[] obj) {
        return !isNoSize(obj);
    }

    public static boolean isNoSize(float[] obj) {
        return (obj == null) || (obj.length < 1);
    }

    public static boolean isNotNoSize(float[] obj) {
        return !isNoSize(obj);
    }

    public static boolean isNoSize(double[] obj) {
        return (obj == null) || (obj.length < 1);
    }

    public static boolean isNotNoSize(double[] obj) {
        return !isNoSize(obj);
    }

    public static <T> boolean isNoSize(T[] obj) {
        return (obj == null) || (obj.length < 1);
    }

    public static <T> boolean isNotNoSize(T[] obj) {
        return !isNoSize(obj);
    }

    public static boolean isNoSize(Collection<?> obj) {
        return (obj == null) || obj.isEmpty();
    }

    public static boolean isNotNoSize(Collection<?> obj) {
        return !isNoSize(obj);
    }

    public static boolean getSingleOrException(boolean[] datas, boolean defaultValue) {
        return getSingleOrException(datas, defaultValue, false);
    }

    public static boolean getSingleOrException(boolean[] datas, boolean defaultValue,
            boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        if (1 < datas.length) {
            throw new IllegalArgumentException();
        }
        return datas[0];
    }

    public static byte getSingleOrException(byte[] datas, byte defaultValue) {
        return getSingleOrException(datas, defaultValue, false);
    }

    public static byte getSingleOrException(byte[] datas, byte defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        if (1 < datas.length) {
            throw new IllegalArgumentException();
        }
        return datas[0];
    }

    public static short getSingleOrException(short[] datas, short defaultValue) {
        return getSingleOrException(datas, defaultValue, false);
    }

    public static short getSingleOrException(short[] datas, short defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        if (1 < datas.length) {
            throw new IllegalArgumentException();
        }
        return datas[0];
    }

    public static char getSingleOrException(char[] datas, char defaultValue) {
        return getSingleOrException(datas, defaultValue, false);
    }

    public static char getSingleOrException(char[] datas, char defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        if (1 < datas.length) {
            throw new IllegalArgumentException();
        }
        return datas[0];
    }

    public static int getSingleOrException(int[] datas, int defaultValue) {
        return getSingleOrException(datas, defaultValue, false);
    }

    public static int getSingleOrException(int[] datas, int defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        if (1 < datas.length) {
            throw new IllegalArgumentException();
        }
        return datas[0];
    }

    public static long getSingleOrException(long[] datas, long defaultValue) {
        return getSingleOrException(datas, defaultValue, false);
    }

    public static long getSingleOrException(long[] datas, long defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        if (1 < datas.length) {
            throw new IllegalArgumentException();
        }
        return datas[0];
    }

    public static float getSingleOrException(float[] datas, float defaultValue) {
        return getSingleOrException(datas, defaultValue, false);
    }

    public static float getSingleOrException(float[] datas, float defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        if (1 < datas.length) {
            throw new IllegalArgumentException();
        }
        return datas[0];
    }

    public static double getSingleOrException(double[] datas, double defaultValue) {
        return getSingleOrException(datas, defaultValue, false);
    }

    public static double getSingleOrException(double[] datas, double defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        if (1 < datas.length) {
            throw new IllegalArgumentException();
        }
        return datas[0];
    }

    public static <T> T getSingleOrException(T[] datas) {
        return getSingleOrException(datas, false);
    }

    public static <T> T getSingleOrException(T[] datas, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return null;
        }
        if (1 < datas.length) {
            throw new IllegalArgumentException();
        }
        return datas[0];
    }

    public static <T> T getSingleOrException(Collection<T> datas) {
        return getSingleOrException(datas, false);
    }

    public static <T> T getSingleOrException(Collection<T> datas, boolean notEmpty) {
        if ((datas == null) || datas.isEmpty()) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return null;
        }
        if (1 < datas.size()) {
            throw new IllegalArgumentException();
        }
        return datas.iterator().next();
    }

    public static boolean getFirst(boolean[] datas, boolean defaultValue) {
        return getFirst(datas, defaultValue, false);
    }

    public static boolean getFirst(boolean[] datas, boolean defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[0];
    }

    public static boolean getLast(boolean[] datas, boolean defaultValue) {
        return getLast(datas, defaultValue, false);
    }

    public static boolean getLast(boolean[] datas, boolean defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[datas.length - 1];
    }

    public static byte getFirst(byte[] datas, byte defaultValue) {
        return getFirst(datas, defaultValue, false);
    }

    public static byte getFirst(byte[] datas, byte defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[0];
    }

    public static byte getLast(byte[] datas, byte defaultValue) {
        return getLast(datas, defaultValue, false);
    }

    public static byte getLast(byte[] datas, byte defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[datas.length - 1];
    }

    public static short getFirst(short[] datas, short defaultValue) {
        return getFirst(datas, defaultValue, false);
    }

    public static short getFirst(short[] datas, short defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[0];
    }

    public static short getLast(short[] datas, short defaultValue) {
        return getLast(datas, defaultValue, false);
    }

    public static short getLast(short[] datas, short defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[datas.length - 1];
    }

    public static char getFirst(char[] datas, char defaultValue) {
        return getFirst(datas, defaultValue, false);
    }

    public static char getFirst(char[] datas, char defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[0];
    }

    public static char getLast(char[] datas, char defaultValue) {
        return getLast(datas, defaultValue, false);
    }

    public static char getLast(char[] datas, char defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[datas.length - 1];
    }

    public static int getFirst(int[] datas, int defaultValue) {
        return getFirst(datas, defaultValue, false);
    }

    public static int getFirst(int[] datas, int defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[0];
    }

    public static int getLast(int[] datas, int defaultValue) {
        return getLast(datas, defaultValue, false);
    }

    public static int getLast(int[] datas, int defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[datas.length - 1];
    }

    public static long getFirst(long[] datas, long defaultValue) {
        return getFirst(datas, defaultValue, false);
    }

    public static long getFirst(long[] datas, long defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[0];
    }

    public static long getLast(long[] datas, long defaultValue) {
        return getLast(datas, defaultValue, false);
    }

    public static long getLast(long[] datas, long defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[datas.length - 1];
    }

    public static float getFirst(float[] datas, float defaultValue) {
        return getFirst(datas, defaultValue, false);
    }

    public static float getFirst(float[] datas, float defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[0];
    }

    public static float getLast(float[] datas, float defaultValue) {
        return getLast(datas, defaultValue, false);
    }

    public static float getLast(float[] datas, float defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[datas.length - 1];
    }

    public static double getFirst(double[] datas, double defaultValue) {
        return getFirst(datas, defaultValue, false);
    }

    public static double getFirst(double[] datas, double defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[0];
    }

    public static double getLast(double[] datas, double defaultValue) {
        return getLast(datas, defaultValue, false);
    }

    public static double getLast(double[] datas, double defaultValue, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return defaultValue;
        }
        return datas[datas.length - 1];
    }

    public static <T> T getFirst(T[] datas) {
        return getFirst(datas, false);
    }

    public static <T> T getFirst(T[] datas, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return null;
        }
        return datas[0];
    }

    public static <T> T getLast(T[] datas) {
        return getLast(datas, false);
    }

    public static <T> T getLast(T[] datas, boolean notEmpty) {
        if ((datas == null) || (datas.length < 1)) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return null;
        }
        return datas[datas.length - 1];
    }

    public static <T> T getFirst(List<T> datas) {
        return getFirst(datas, false);
    }

    public static <T> T getFirst(List<T> datas, boolean notEmpty) {
        if ((datas == null) || datas.isEmpty()) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return null;
        }
        return datas.get(0);
    }

    public static <T> T getLast(List<T> datas) {
        return getLast(datas, false);
    }

    public static <T> T getLast(List<T> datas, boolean notEmpty) {
        if ((datas == null) || datas.isEmpty()) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return null;
        }
        return datas.get(datas.size() - 1);
    }

    public static <T> T getFirst(SortedSet<T> datas) {
        return getFirst(datas, false);
    }

    public static <T> T getFirst(SortedSet<T> datas, boolean notEmpty) {
        if ((datas == null) || datas.isEmpty()) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return null;
        }
        return datas.first();
    }

    public static <T> T getLast(SortedSet<T> datas) {
        return getLast(datas, false);
    }

    public static <T> T getLast(SortedSet<T> datas, boolean notEmpty) {
        if ((datas == null) || datas.isEmpty()) {
            if (notEmpty) {
                throw new IllegalArgumentException();
            }
            return null;
        }
        return datas.last();
    }

    public static String convertBytesToHexString(byte[] bytes) {
        return convertBytesToHexString(bytes, false);
    }

    public static String convertBytesToHexString(byte[] bytes, boolean upperCase) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String s = Integer.toHexString(0xff & b);
            if (s.length() < 2) {
                sb.append("0");
            }
            sb.append(s);
        }
        // Integer.toHexString() method returns lower-case characters.
        return upperCase ? sb.toString().toUpperCase() : sb.toString();
    }

    /**
     * Convert Hex String to Byte Array.
     * 
     * @param hex
     * @return
     * @throws NumberFormatException
     */
    public static byte[] convertHexStringToBytes(String hex) {
        if (hex == null) {
            return null;
        }
        byte[] bytes = new byte[hex.length() / 2];
        for (int index = 0; index < bytes.length; index++) {
            bytes[index] = (byte) Integer.parseInt(hex.substring(index * 2, (index + 1) * 2), 16);
        }
        return bytes;
    }
}
