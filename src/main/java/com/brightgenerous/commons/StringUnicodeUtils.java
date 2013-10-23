package com.brightgenerous.commons;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import com.brightgenerous.lang.Args;

public class StringUnicodeUtils {

    private StringUnicodeUtils() {
    }

    public static int[] toCodePointArray(String str) {
        if (str == null) {
            return null;
        }
        int len = str.length(); // the length of str
        int[] acp = new int[str.codePointCount(0, len)];

        for (int i = 0, j = 0; i < len; i = str.offsetByCodePoints(i, 1)) {
            acp[j++] = str.codePointAt(i);
        }
        return acp;
    }

    public static String[] toCodePointStringArray(String str) {
        if (str == null) {
            return null;
        }
        int len = str.length(); // the length of str
        String[] acp = new String[str.codePointCount(0, len)];

        for (int i = 0, j = 0; i < len; i = str.offsetByCodePoints(i, 1)) {
            acp[j++] = String.valueOf(Character.toChars(str.codePointAt(i)));
        }
        return acp;
    }

    public static String normalize(String str) {
        if (str == null) {
            return null;
        }
        if (StringUtils.isEmptyNotNull(str)) {
            return StringUtils.EMPTY;
        }
        return Normalizer.normalize(str, Form.NFC);
    }

    public static int length(String str) {
        return length(str, false);
    }

    public static int length(String str, boolean normalize) {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }
        String s = normalize ? normalize(str) : str;
        return s.codePointCount(0, s.length());
    }

    public static String cutoff(String str, int width) {
        return cutoff(str, width, false);
    }

    public static String cutoff(String str, int width, boolean normalize) {
        Args.greaterEqual(0, width, "width");

        if (str == null) {
            return null;
        }
        if ((width < 1) || StringUtils.isEmptyNotNull(str)) {
            return StringUtils.EMPTY;
        }
        String s = normalize ? normalize(str) : str;
        int length = s.codePointCount(0, s.length());
        if (length <= width) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; (count < width) && (i < s.length()); i++) {
            char c = s.charAt(i);
            sb.append(c);
            if (((i + 1) < s.length()) && Character.isSurrogatePair(c, s.charAt(i + 1))) {
                sb.append(s.charAt(++i));
            }
            count++;
        }
        return sb.toString();
    }

    public static int getHalfSizeWidth(String str) {
        return getHalfSizeWidth(str, false);
    }

    public static int getHalfSizeWidth(String str, boolean normalize) {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }
        String s = normalize ? normalize(str) : str;
        int half = 0;
        int full = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if ((0x0 <= c) && (c <= 0x7F)) {
                /* ASCII */
                ++half;
            } else if ((0xFF61 <= c) && (c <= 0xFF9F)) {
                /* half KANA */
                ++half;
            } else {
                if (((i + 1) < s.length()) && Character.isSurrogatePair(c, s.charAt(i + 1))) {
                    /* surrogate high */
                    ++full;
                    ++i;
                } else {
                    /* other */
                    ++full;
                }
            }
        }
        return (full * 2) + half;
    }

    public static String cutoffHalfSizeWidth(String str, int width) {
        return cutoffHalfSizeWidth(str, width, false);
    }

    public static String cutoffHalfSizeWidth(String str, int width, boolean normalize) {
        Args.greaterEqual(0, width, "width");

        if (str == null) {
            return null;
        }
        if ((width < 1) || StringUtils.isEmptyNotNull(str)) {
            return StringUtils.EMPTY;
        }
        String s = normalize ? normalize(str) : str;
        int length = s.codePointCount(0, s.length());
        if ((length * 2) <= width) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; (count < width) && (i < s.length()); i++) {
            char c = s.charAt(i);
            boolean isSurrogateHigh = ((i + 1) < s.length())
                    && Character.isSurrogatePair(c, s.charAt(i + 1));
            if ((0x0 <= c) && (c <= 0x7F)) {
                /* ASCII */
                assert true;
            } else if ((0xFF61 <= c) && (c <= 0xFF9F)) {
                /* half KANA */
                assert true;
            } else {
                if (isSurrogateHigh) {
                    /* surrogate high */
                    count++;
                } else {
                    /* other */
                    count++;
                }
            }
            if (width <= count) {
                break;
            }
            sb.append(c);
            if (isSurrogateHigh) {
                sb.append(s.charAt(++i));
            }
            count++;
        }
        return sb.toString();
    }
}
