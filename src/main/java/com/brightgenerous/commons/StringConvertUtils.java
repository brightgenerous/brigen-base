package com.brightgenerous.commons;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringConvertUtils {

    public static enum Mode {
        ALL, SYMBOL, ALPHABET, NUMBER, KATAKANA;
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

    public static String toHalf(String str, Mode mode, Mode... modes) {
        if (str == null) {
            return null;
        }
        String ret = str;
        for (Mode m : compressModes(mode, modes)) {
            switch (m) {
                case ALL:
                    ret = StringConvertFullToHalfUtils.convertToHalfCharacter(ret);
                    break;
                case SYMBOL:
                    ret = StringConvertFullToHalfUtils.convertToHalfSymbol(ret);
                    break;
                case NUMBER:
                    ret = StringConvertFullToHalfUtils.convertToHalfNumber(ret);
                    break;
                case ALPHABET:
                    ret = StringConvertFullToHalfUtils.convertToHalfAlphabet(ret);
                    break;
                case KATAKANA:
                    ret = StringConvertFullToHalfUtils.convertToHalfKatakana(ret);
                    break;
            }
        }
        return ret;
    }

    public static String toFull(String str) {
        return toFull(str, Mode.ALL);
    }

    public static String toFull(String str, Mode mode, Mode... modes) {
        if (str == null) {
            return null;
        }
        String ret = str;
        for (Mode m : compressModes(mode, modes)) {
            switch (m) {
                case ALL:
                    ret = StringConvertHalfToFullUtils.convertToFullCharacter(ret);
                    break;
                case SYMBOL:
                    ret = StringConvertHalfToFullUtils.convertToFullSymbol(ret);
                    break;
                case NUMBER:
                    ret = StringConvertHalfToFullUtils.convertToFullNumber(ret);
                    break;
                case ALPHABET:
                    ret = StringConvertHalfToFullUtils.convertToFullAlphabet(ret);
                    break;
                case KATAKANA:
                    ret = StringConvertHalfToFullUtils.convertToFullKatakana(ret);
                    break;
            }
        }
        return ret;
    }

    private static Set<Mode> compressModes(Mode mode, Mode... modes) {
        Set<Mode> ret = new HashSet<>();
        loop: {
            if (mode != null) {
                ret.add(mode);
                if (mode.equals(Mode.ALL)) {
                    break loop;
                }
            }
            if ((modes != null) && (0 < modes.length)) {
                for (Mode m : modes) {
                    if ((m != null) && !ret.contains(m)) {
                        ret.add(m);
                        if (m.equals(Mode.ALL)) {
                            break loop;
                        }
                    }
                }
            }
        }
        if ((ret.contains(Mode.ALL) && (1 < ret.size()))
                || (ret.contains(Mode.SYMBOL) && ret.contains(Mode.ALPHABET)
                        && ret.contains(Mode.NUMBER) && ret.contains(Mode.KATAKANA))) {
            ret.clear();
            ret.add(Mode.ALL);
        }
        return ret;
    }
}
