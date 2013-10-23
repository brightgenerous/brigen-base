package com.brightgenerous.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringConvertUtils {

    public static class Mode {

        public static final int SYMBOL = 0b0001;

        public static final int ALPHABET = 0b0010;

        public static final int NUMBER = 0b0100;

        public static final int KANA = 0b1000;

        public static final int ALL = SYMBOL | ALPHABET | NUMBER | KANA;

        private Mode() {
        }
    }

    private StringConvertUtils() {
    }

    public static String toSnakeCase(String str) {
        return toSnakeCase(str, false);
    }

    public static String toSnakeCase(String str, boolean separateNumber) {
        if (str == null) {
            return null;
        }
        String ret = str.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
        if (separateNumber) {
            ret = ret.replaceAll("([a-z])([0-9])", "$1_$2").replaceAll("([0-9])([a-z])", "$1_$2");
        }
        return ret;
    }

    public static String toCamelCase(String str) {
        return toCamelCase(str, false);
    }

    public static String toCamelCase(String str, boolean separateNumber) {
        if (str == null) {
            return null;
        }
        Pattern p;
        if (separateNumber) {
            p = Pattern.compile("_([0-9a-z])");
        } else {
            p = Pattern.compile("_([a-z])");
        }
        Matcher m = p.matcher(str.toLowerCase());
        StringBuffer sb = new StringBuffer(str.length());
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String toHalf(String str) {
        return toHalf(str, Mode.ALL);
    }

    public static String toHalf(String str, int mode) {
        String ret = str;
        if ((mode & Mode.ALL) == Mode.ALL) {
            ret = StringConvertFullToHalfUtils.convertToHalfCharacter(ret);
        } else {
            if ((mode & Mode.SYMBOL) == Mode.SYMBOL) {
                ret = StringConvertFullToHalfUtils.convertToHalfSymbol(ret);
            }
            if ((mode & Mode.NUMBER) == Mode.NUMBER) {
                ret = StringConvertFullToHalfUtils.convertToHalfNumber(ret);
            }
            if ((mode & Mode.ALPHABET) == Mode.ALPHABET) {
                ret = StringConvertFullToHalfUtils.convertToHalfAlphabet(ret);
            }
            if ((mode & Mode.KANA) == Mode.KANA) {
                ret = StringConvertFullToHalfUtils.convertToHalfKana(ret);
            }
        }
        return ret;
    }

    public static String toFull(String str) {
        return toFull(str, Mode.ALL);
    }

    public static String toFull(String str, int mode) {
        String ret = str;
        if ((mode & Mode.ALL) == Mode.ALL) {
            ret = StringConvertHalfToFullUtils.convertToFullCharacter(ret);
        } else {
            if ((mode & Mode.SYMBOL) == Mode.SYMBOL) {
                ret = StringConvertHalfToFullUtils.convertToFullSymbol(ret);
            }
            if ((mode & Mode.NUMBER) == Mode.NUMBER) {
                ret = StringConvertHalfToFullUtils.convertToFullNumber(ret);
            }
            if ((mode & Mode.ALPHABET) == Mode.ALPHABET) {
                ret = StringConvertHalfToFullUtils.convertToFullAlphabet(ret);
            }
            if ((mode & Mode.KANA) == Mode.KANA) {
                ret = StringConvertHalfToFullUtils.convertToFullKana(ret);
            }
        }
        return ret;
    }
}
