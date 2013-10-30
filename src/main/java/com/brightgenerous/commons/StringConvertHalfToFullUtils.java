//# -*- encoding: utf-8 -*-
package com.brightgenerous.commons;

class StringConvertHalfToFullUtils {

    private StringConvertHalfToFullUtils() {
    }

    private static char convertToFullNumber(char ch) {
        char ret;
        if ((ch >= '0') && (ch <= '9')) {
            ret = (char) ((ch - '0') + '０');
        } else {
            ret = ch;
        }
        return ret;
    }

    public static String convertToFullNumber(String str) {
        String ret;
        if ((str == null) || str.isEmpty()) {
            ret = str;
        } else if (str.length() == 1) {
            ret = String.valueOf(convertToFullNumber(str.charAt(0)));
        } else {
            StringBuilder sb = new StringBuilder(str);
            for (int i = 0; i < str.length(); ++i) {
                char originalChar = sb.charAt(i);
                char convedChar = convertToFullNumber(originalChar);
                if (originalChar != convedChar) {
                    sb.setCharAt(i, convedChar);
                }
            }
            ret = sb.toString();
        }
        return ret;
    }

    private static char convertToFullAlphabet(char ch) {
        char ret;
        if ((ch >= 'a') && (ch <= 'z')) {
            ret = (char) ((ch - 'a') + 'ａ');
        } else if ((ch >= 'A') && (ch <= 'Z')) {
            ret = (char) ((ch - 'A') + 'Ａ');
        } else {
            ret = ch;
        }
        return ret;
    }

    public static String convertToFullAlphabet(String str) {
        String ret;
        if ((str == null) || str.isEmpty()) {
            ret = str;
        } else if (str.length() == 1) {
            ret = String.valueOf(convertToFullAlphabet(str.charAt(0)));
        } else {
            StringBuilder sb = new StringBuilder(str);
            for (int i = 0; i < str.length(); ++i) {
                char originalChar = sb.charAt(i);
                char convedChar = convertToFullAlphabet(originalChar);
                if (originalChar != convedChar) {
                    sb.setCharAt(i, convedChar);
                }
            }
            ret = sb.toString();
        }
        return ret;
    }

    // @see StringConvertFullToHalfUtils#convertToHalfKatakana
    private static char convertToFullKatakana(char ch) {
        char ret;
        switch (ch) {
            case 'ｦ':
                ret = 'ヲ';
                break;
            case 'ｧ':
                ret = 'ァ';
                break;
            case 'ｨ':
                ret = 'ィ';
                break;
            case 'ｩ':
                ret = 'ゥ';
                break;
            case 'ｪ':
                ret = 'ェ';
                break;
            case 'ｫ':
                ret = 'ォ';
                break;
            case 'ｬ':
                ret = 'ャ';
                break;
            case 'ｭ':
                ret = 'ュ';
                break;
            case 'ｮ':
                ret = 'ョ';
                break;
            case 'ｯ':
                ret = 'ッ';
                break;
            case 'ｰ':
                ret = 'ー';
                break;
            case 'ｱ':
                ret = 'ア';
                break;
            case 'ｲ':
                ret = 'イ';
                break;
            case 'ｳ':
                ret = 'ウ';
                break;
            case 'ｴ':
                ret = 'エ';
                break;
            case 'ｵ':
                ret = 'オ';
                break;
            case 'ｶ':
                ret = 'カ';
                break;
            case 'ｷ':
                ret = 'キ';
                break;
            case 'ｸ':
                ret = 'ク';
                break;
            case 'ｹ':
                ret = 'ケ';
                break;
            case 'ｺ':
                ret = 'コ';
                break;
            case 'ｻ':
                ret = 'サ';
                break;
            case 'ｼ':
                ret = 'シ';
                break;
            case 'ｽ':
                ret = 'ス';
                break;
            case 'ｾ':
                ret = 'セ';
                break;
            case 'ｿ':
                ret = 'ソ';
                break;
            case 'ﾀ':
                ret = 'タ';
                break;
            case 'ﾁ':
                ret = 'チ';
                break;
            case 'ﾂ':
                ret = 'ツ';
                break;
            case 'ﾃ':
                ret = 'テ';
                break;
            case 'ﾄ':
                ret = 'ト';
                break;
            case 'ﾅ':
                ret = 'ナ';
                break;
            case 'ﾆ':
                ret = 'ニ';
                break;
            case 'ﾇ':
                ret = 'ヌ';
                break;
            case 'ﾈ':
                ret = 'ネ';
                break;
            case 'ﾉ':
                ret = 'ノ';
                break;
            case 'ﾊ':
                ret = 'ハ';
                break;
            case 'ﾋ':
                ret = 'ヒ';
                break;
            case 'ﾌ':
                ret = 'フ';
                break;
            case 'ﾍ':
                ret = 'ヘ';
                break;
            case 'ﾎ':
                ret = 'ホ';
                break;
            case 'ﾏ':
                ret = 'マ';
                break;
            case 'ﾐ':
                ret = 'ミ';
                break;
            case 'ﾑ':
                ret = 'ム';
                break;
            case 'ﾒ':
                ret = 'メ';
                break;
            case 'ﾓ':
                ret = 'モ';
                break;
            case 'ﾔ':
                ret = 'ヤ';
                break;
            case 'ﾕ':
                ret = 'ユ';
                break;
            case 'ﾖ':
                ret = 'ヨ';
                break;
            case 'ﾗ':
                ret = 'ラ';
                break;
            case 'ﾘ':
                ret = 'リ';
                break;
            case 'ﾙ':
                ret = 'ル';
                break;
            case 'ﾚ':
                ret = 'レ';
                break;
            case 'ﾛ':
                ret = 'ロ';
                break;
            case 'ﾜ':
                ret = 'ワ';
                break;
            case 'ﾝ':
                ret = 'ン';
                break;
            case 'ﾞ':
                ret = '゛';
                break;
            case 'ﾟ':
                ret = '゜';
                break;

            default:
                ret = ch;
        }
        return ret;
    }

    private static char mergeToFullKatakana(char ch1, char ch2) {
        if (ch2 == 'ﾞ') {
            if ("ｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾊﾋﾌﾍﾎｳ".indexOf(ch1) != -1) {
                switch (ch1) {
                    case 'ｶ':
                        return 'ガ';
                    case 'ｷ':
                        return 'ギ';
                    case 'ｸ':
                        return 'グ';
                    case 'ｹ':
                        return 'ゲ';
                    case 'ｺ':
                        return 'ゴ';
                    case 'ｻ':
                        return 'ザ';
                    case 'ｼ':
                        return 'ジ';
                    case 'ｽ':
                        return 'ズ';
                    case 'ｾ':
                        return 'ゼ';
                    case 'ｿ':
                        return 'ゾ';
                    case 'ﾀ':
                        return 'ダ';
                    case 'ﾁ':
                        return 'ヂ';
                    case 'ﾂ':
                        return 'ヅ';
                    case 'ﾃ':
                        return 'デ';
                    case 'ﾄ':
                        return 'ド';
                    case 'ﾊ':
                        return 'バ';
                    case 'ﾋ':
                        return 'ビ';
                    case 'ﾌ':
                        return 'ブ';
                    case 'ﾍ':
                        return 'ベ';
                    case 'ﾎ':
                        return 'ボ';
                    case 'ｳ':
                        return 'ヴ';
                }
            }
        } else if (ch2 == 'ﾟ') {
            if ("ﾊﾋﾌﾍﾎ".indexOf(ch1) != -1) {
                switch (ch1) {
                    case 'ﾊ':
                        return 'パ';
                    case 'ﾋ':
                        return 'ピ';
                    case 'ﾌ':
                        return 'プ';
                    case 'ﾍ':
                        return 'ペ';
                    case 'ﾎ':
                        return 'ポ';
                }
            }
        }
        return ch1;
    }

    public static String convertToFullKatakana(String str) {
        if ((str == null) || str.isEmpty()) {
            return str;
        } else if (str.length() == 1) {
            return String.valueOf(convertToFullKatakana(str.charAt(0)));
        } else {
            StringBuilder sb = new StringBuilder(str);
            int i = 0;
            for (i = 0; i < (sb.length() - 1); ++i) {
                char originalChar1 = sb.charAt(i);
                char originalChar2 = sb.charAt(i + 1);
                char mergedChar = mergeToFullKatakana(originalChar1, originalChar2);
                if (mergedChar != originalChar1) {
                    sb.setCharAt(i, mergedChar);
                    sb.deleteCharAt(i + 1);
                } else {
                    char convertedChar = convertToFullKatakana(originalChar1);
                    if (convertedChar != originalChar1) {
                        sb.setCharAt(i, convertedChar);
                    }
                }
            }
            if (i < sb.length()) {
                char originalChar1 = sb.charAt(i);
                char convertedChar = convertToFullKatakana(originalChar1);
                if (convertedChar != originalChar1) {
                    sb.setCharAt(i, convertedChar);
                }
            }
            return sb.toString();
        }
    }

    private static final char FULL_BACK_SLASH;

    static {
        String osName = System.getProperties().getProperty("os.name");
        if ((osName == null) || (osName.toLowerCase().indexOf("windows") == -1)) {
            FULL_BACK_SLASH = '＼';
        } else {
            FULL_BACK_SLASH = '￥';
        }
    }

    // @see StringConvertFullToHalfUtils#convertToHalfSymbol
    private static char convertToFullSymbol(char ch) {
        char ret;
        switch (ch) {
            case ' ':
                ret = '　';
                break;
            case '!':
                ret = '！';
                break;
            case '"':
                ret = '”';
                break;
            case '#':
                ret = '＃';
                break;
            case '$':
                ret = '＄';
                break;
            case '%':
                ret = '％';
                break;
            case '&':
                ret = '＆';
                break;
            case '\'':
                ret = '’';
                break;
            case '(':
                ret = '（';
                break;
            case ')':
                ret = '）';
                break;
            case '*':
                ret = '＊';
                break;
            case '+':
                ret = '＋';
                break;
            case ',':
                ret = '，';
                break;
            case '-':
                ret = '－';
                break;
            case '.':
                ret = '．';
                break;
            case '/':
                ret = '／';
                break;
            case ':':
                ret = '：';
                break;
            case ';':
                ret = '；';
                break;
            case '<':
                ret = '＜';
                break;
            case '=':
                ret = '＝';
                break;
            case '>':
                ret = '＞';
                break;
            case '?':
                ret = '？';
                break;
            case '@':
                ret = '＠';
                break;
            case '[':
                ret = '［';
                break;
            case '\\':
                ret = FULL_BACK_SLASH;
                break;
            case ']':
                ret = '］';
                break;
            case '^':
                ret = '＾';
                break;
            case '_':
                ret = '＿';
                break;
            case '`':
                ret = '‘';
                break;
            case '{':
                ret = '｛';
                break;
            case '|':
                ret = '｜';
                break;
            case '}':
                ret = '｝';
                break;
            case '~':
                ret = '～';
                break;
            case '｡':
                ret = '。';
                break;
            case '｢':
                ret = '「';
                break;
            case '｣':
                ret = '」';
                break;
            case '､':
                ret = '、';
                break;
            case '･':
                ret = '・';
                break;

            default:
                ret = ch;
        }
        return ret;
    }

    public static String convertToFullSymbol(String str) {
        if ((str == null) || str.isEmpty()) {
            return str;
        } else if (str.length() == 1) {
            return String.valueOf(convertToFullSymbol(str.charAt(0)));
        } else {
            StringBuilder sb = new StringBuilder(str);
            int i = 0;
            for (i = 0; i < sb.length(); ++i) {
                char originalChar = sb.charAt(i);
                char convedChar = convertToFullSymbol(originalChar);
                sb.setCharAt(i, convedChar);
            }
            return sb.toString();
        }
    }

    private static char mergeToFullCharacter(char ch1, char ch2) {
        {
            char c = ch1;
            c = mergeToFullKatakana(ch1, ch2);
            if (ch1 != c) {
                return c;
            }
        }
        return ch1;
    }

    private static char convertToFullCharacter(char ch) {
        {
            char c = convertToFullNumber(ch);
            if (ch != c) {
                return c;
            }
        }
        {
            char c = convertToFullAlphabet(ch);
            if (ch != c) {
                return c;
            }
        }
        {
            char c = convertToFullSymbol(ch);
            if (ch != c) {
                return c;
            }
        }
        {
            char c = convertToFullKatakana(ch);
            if (ch != c) {
                return c;
            }
        }
        return ch;
    }

    public static String convertToFullCharacter(String str) {
        if ((str == null) || str.isEmpty()) {
            return str;
        } else if (str.length() == 1) {
            return String.valueOf(convertToFullCharacter(str.charAt(0)));
        } else {
            StringBuilder sb = new StringBuilder(str);
            int i = 0;
            for (i = 0; i < (sb.length() - 1); ++i) {
                char originalChar1 = sb.charAt(i);
                char originalChar2 = sb.charAt(i + 1);
                char mergedChar = mergeToFullCharacter(originalChar1, originalChar2);
                if (mergedChar != originalChar1) {
                    sb.setCharAt(i, mergedChar);
                    sb.deleteCharAt(i + 1);
                } else {
                    char convertedChar = convertToFullCharacter(originalChar1);
                    if (convertedChar != originalChar1) {
                        sb.setCharAt(i, convertedChar);
                    }
                }
            }
            if (i < sb.length()) {
                char originalChar1 = sb.charAt(i);
                char convertedChar = convertToFullCharacter(originalChar1);
                if (convertedChar != originalChar1) {
                    sb.setCharAt(i, convertedChar);
                }
            }
            return sb.toString();
        }
    }
}
