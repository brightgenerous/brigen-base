//# -*- encoding: utf-8 -*-
package com.brightgenerous.commons;

class StringConvertFullToHalfUtils {

    private StringConvertFullToHalfUtils() {
    }

    private static char convertToHalfNumber(char ch) {
        char ret;
        if ((ch >= '０') && (ch <= '９')) {
            ret = (char) ((ch - '０') + '0');
        } else {
            ret = ch;
        }
        return ret;
    }

    public static String convertToHalfNumber(String str) {
        String ret;
        if ((str == null) || str.isEmpty()) {
            ret = str;
        } else if (str.length() == 1) {
            ret = String.valueOf(convertToHalfNumber(str.charAt(0)));
        } else {
            StringBuilder sb = new StringBuilder(str);
            for (int i = 0; i < str.length(); ++i) {
                char originalChar = sb.charAt(i);
                char convedChar = convertToHalfNumber(originalChar);
                if (originalChar != convedChar) {
                    sb.setCharAt(i, convedChar);
                }
            }
            ret = sb.toString();
        }
        return ret;
    }

    private static char convertToHalfAlphabet(char ch) {
        char ret;
        if ((ch >= 'ａ') && (ch <= 'ｚ')) {
            ret = (char) ((ch - 'ａ') + 'a');
        } else if ((ch >= 'Ａ') && (ch <= 'Ｚ')) {
            ret = (char) ((ch - 'Ａ') + 'A');
        } else {
            ret = ch;
        }
        return ret;
    }

    public static String convertToHalfAlphabet(String str) {
        String ret;
        if ((str == null) || str.isEmpty()) {
            ret = str;
        } else if (str.length() == 1) {
            ret = String.valueOf(convertToHalfAlphabet(str.charAt(0)));
        } else {
            StringBuilder sb = new StringBuilder(str);
            for (int i = 0; i < str.length(); ++i) {
                char originalChar = sb.charAt(i);
                char convedChar = convertToHalfAlphabet(originalChar);
                if (originalChar != convedChar) {
                    sb.setCharAt(i, convedChar);
                }
            }
            ret = sb.toString();
        }
        return ret;
    }

    // @see StringConvertHalfToFullUtils.convertToFullKana
    private static String convertToHalfKana(char ch) {
        String ret;
        switch (ch) {
            case 'ヲ':
                ret = "ｦ";
                break;
            case 'ァ':
                ret = "ｧ";
                break;
            case 'ィ':
                ret = "ｨ";
                break;
            case 'ゥ':
                ret = "ｩ";
                break;
            case 'ェ':
                ret = "ｪ";
                break;
            case 'ォ':
                ret = "ｫ";
                break;
            case 'ャ':
                ret = "ｬ";
                break;
            case 'ュ':
                ret = "ｭ";
                break;
            case 'ョ':
                ret = "ｮ";
                break;
            case 'ッ':
                ret = "ｯ";
                break;
            case 'ー':
                ret = "ｰ";
                break;
            case 'ア':
                ret = "ｱ";
                break;
            case 'イ':
                ret = "ｲ";
                break;
            case 'ウ':
                ret = "ｳ";
                break;
            case 'エ':
                ret = "ｴ";
                break;
            case 'オ':
                ret = "ｵ";
                break;
            case 'カ':
                ret = "ｶ";
                break;
            case 'キ':
                ret = "ｷ";
                break;
            case 'ク':
                ret = "ｸ";
                break;
            case 'ケ':
                ret = "ｹ";
                break;
            case 'コ':
                ret = "ｺ";
                break;
            case 'サ':
                ret = "ｻ";
                break;
            case 'シ':
                ret = "ｼ";
                break;
            case 'ス':
                ret = "ｽ";
                break;
            case 'セ':
                ret = "ｾ";
                break;
            case 'ソ':
                ret = "ｿ";
                break;
            case 'タ':
                ret = "ﾀ";
                break;
            case 'チ':
                ret = "ﾁ";
                break;
            case 'ツ':
                ret = "ﾂ";
                break;
            case 'テ':
                ret = "ﾃ";
                break;
            case 'ト':
                ret = "ﾄ";
                break;
            case 'ナ':
                ret = "ﾅ";
                break;
            case 'ニ':
                ret = "ﾆ";
                break;
            case 'ヌ':
                ret = "ﾇ";
                break;
            case 'ネ':
                ret = "ﾈ";
                break;
            case 'ノ':
                ret = "ﾉ";
                break;
            case 'ハ':
                ret = "ﾊ";
                break;
            case 'ヒ':
                ret = "ﾋ";
                break;
            case 'フ':
                ret = "ﾌ";
                break;
            case 'ヘ':
                ret = "ﾍ";
                break;
            case 'ホ':
                ret = "ﾎ";
                break;
            case 'マ':
                ret = "ﾏ";
                break;
            case 'ミ':
                ret = "ﾐ";
                break;
            case 'ム':
                ret = "ﾑ";
                break;
            case 'メ':
                ret = "ﾒ";
                break;
            case 'モ':
                ret = "ﾓ";
                break;
            case 'ヤ':
                ret = "ﾔ";
                break;
            case 'ユ':
                ret = "ﾕ";
                break;
            case 'ヨ':
                ret = "ﾖ";
                break;
            case 'ラ':
                ret = "ﾗ";
                break;
            case 'リ':
                ret = "ﾘ";
                break;
            case 'ル':
                ret = "ﾙ";
                break;
            case 'レ':
                ret = "ﾚ";
                break;
            case 'ロ':
                ret = "ﾛ";
                break;
            case 'ワ':
                ret = "ﾜ";
                break;
            case 'ン':
                ret = "ﾝ";
                break;
            case '゛':
                ret = "ﾞ";
                break;
            case '゜':
                ret = "ﾟ";
                break;
            case 'ガ':
                ret = "ｶﾞ";
                break;
            case 'ギ':
                ret = "ｷﾞ";
                break;
            case 'グ':
                ret = "ｸﾞ";
                break;
            case 'ゲ':
                ret = "ｹﾞ";
                break;
            case 'ゴ':
                ret = "ｺﾞ";
                break;
            case 'ザ':
                ret = "ｻﾞ";
                break;
            case 'ジ':
                ret = "ｼﾞ";
                break;
            case 'ズ':
                ret = "ｽﾞ";
                break;
            case 'ゼ':
                ret = "ｾﾞ";
                break;
            case 'ゾ':
                ret = "ｿﾞ";
                break;
            case 'ダ':
                ret = "ﾀﾞ";
                break;
            case 'ヂ':
                ret = "ﾁﾞ";
                break;
            case 'ヅ':
                ret = "ﾂﾞ";
                break;
            case 'デ':
                ret = "ﾃﾞ";
                break;
            case 'ド':
                ret = "ﾄﾞ";
                break;
            case 'バ':
                ret = "ﾊﾞ";
                break;
            case 'ビ':
                ret = "ﾋﾞ";
                break;
            case 'ブ':
                ret = "ﾌﾞ";
                break;
            case 'ベ':
                ret = "ﾍﾞ";
                break;
            case 'ボ':
                ret = "ﾎﾞ";
                break;
            case 'パ':
                ret = "ﾊﾟ";
                break;
            case 'ピ':
                ret = "ﾋﾟ";
                break;
            case 'プ':
                ret = "ﾌﾟ";
                break;
            case 'ペ':
                ret = "ﾍﾟ";
                break;
            case 'ポ':
                ret = "ﾎﾟ";
                break;
            case 'ヴ':
                ret = "ｳﾞ";
                break;

            default:
                ret = String.valueOf(ch);
        }
        return ret;
    }

    public static String convertToHalfKana(String str) {
        String ret;
        if ((str == null) || str.isEmpty()) {
            ret = str;
        } else if (str.length() == 1) {
            ret = convertToHalfKana(str.charAt(0));
        } else {
            StringBuilder sb = new StringBuilder(str.length());
            for (int i = 0; i < str.length(); ++i) {
                sb.append(convertToHalfKana(str.charAt(i)));
            }
            ret = sb.toString();
        }
        return ret;
    }

    // @see StringConvertHalfToFullUtils.convertToFullSymbol
    private static char convertToHalfSymbol(char ch) {
        char ret;
        switch (ch) {
            case '　':
                ret = ' ';
                break;
            case '！':
                ret = '!';
                break;
            case '”':
                ret = '"';
                break;
            case '＃':
                ret = '#';
                break;
            case '＄':
                ret = '$';
                break;
            case '％':
                ret = '%';
                break;
            case '＆':
                ret = '&';
                break;
            case '’':
                ret = '\'';
                break;
            case '（':
                ret = '(';
                break;
            case '）':
                ret = ')';
                break;
            case '＊':
                ret = '*';
                break;
            case '＋':
                ret = '+';
                break;
            case '，':
                ret = ',';
                break;
            case '－':
                ret = '-';
                break;
            case '．':
                ret = '.';
                break;
            case '／':
                ret = '/';
                break;
            case '：':
                ret = ':';
                break;
            case '；':
                ret = ';';
                break;
            case '＜':
                ret = '<';
                break;
            case '＝':
                ret = '=';
                break;
            case '＞':
                ret = '>';
                break;
            case '？':
                ret = '?';
                break;
            case '＠':
                ret = '@';
                break;
            case '［':
                ret = '[';
                break;
            case '￥':
                ret = '\\';
                break;
            case '］':
                ret = ']';
                break;
            case '＾':
                ret = '^';
                break;
            case '＿':
                ret = '_';
                break;
            case '‘':
                ret = '`';
                break;
            case '｛':
                ret = '{';
                break;
            case '｜':
                ret = '|';
                break;
            case '｝':
                ret = '}';
                break;
            case '～':
                ret = '~';
                break;
            case '。':
                ret = '｡';
                break;
            case '「':
                ret = '｢';
                break;
            case '」':
                ret = '｣';
                break;
            case '、':
                ret = '､';
                break;
            case '・':
                ret = '･';
                break;

            // 追加
            case '＂':
                ret = '"';
                break;
            case '＇':
                ret = '\'';
                break;
            case '＼':
                ret = '\\';
                break;
            case '｀':
                ret = '`';
                break;

            // コード変換
            case 0x00a5:
                ret = 0x005c;
                break; // 「\」 165 → 92
            case 0xffe0:
                ret = 0x00a2;
                break; // 「￠」65504 → 162
            case 0xffe1:
                ret = 0x00a3;
                break; // 「￡」65505 → 163
            case 0xffe2:
                ret = 0x00ac;
                break; // 「￢」65506 → 172
            case 0xffe3:
                ret = 0x00af;
                break; // 「￣」65507 → 175

            // おまけ
            case '‐':
                ret = '-';
                break;

            default:
                ret = ch;
        }
        return ret;
    }

    public static String convertToHalfSymbol(String str) {
        String ret;
        if ((str == null) || str.isEmpty()) {
            ret = str;
        } else if (str.length() == 1) {
            ret = String.valueOf(convertToHalfSymbol(str.charAt(0)));
        } else {
            StringBuilder sb = new StringBuilder(str);
            int i = 0;
            for (i = 0; i < sb.length(); ++i) {
                char originalChar = sb.charAt(i);
                char convedChar = convertToHalfSymbol(originalChar);
                if (originalChar != convedChar) {
                    sb.setCharAt(i, convedChar);
                }
            }
            ret = sb.toString();
        }
        return ret;
    }

    private static String convertToHalfCharacter(char ch) {
        {
            {
                char c = convertToHalfNumber(ch);
                if (ch != c) {
                    return String.valueOf(c);
                }
            }
            {
                char c = convertToHalfAlphabet(ch);
                if (ch != c) {
                    return String.valueOf(c);
                }
            }
            {
                char c = convertToHalfSymbol(ch);
                if (ch != c) {
                    return String.valueOf(c);
                }
            }
        }
        {
            String str = convertToHalfKana(ch);
            if ((1 < str.length()) || (str.charAt(0) != ch)) {
                return str;
            }
        }
        return String.valueOf(ch);
    }

    public static String convertToHalfCharacter(String str) {
        String ret;
        if ((str == null) || str.isEmpty()) {
            ret = str;
        } else if (str.length() == 1) {
            ret = convertToHalfCharacter(str.charAt(0));
        } else {
            StringBuilder sb = new StringBuilder(str.length());
            for (int i = 0; i < str.length(); ++i) {
                sb.append(convertToHalfCharacter(str.charAt(i)));
            }
            ret = sb.toString();
        }
        return ret;
    }
}
