package com.brightgenerous.commons.delegate;

interface StringDelegater {

    String empty();

    boolean isEmpty(CharSequence cs);

    boolean isNotEmpty(CharSequence cs);

    boolean isBlank(CharSequence cs);

    boolean isNotBlank(CharSequence cs);

    String trim(String str);

    String trimToNull(String str);

    String trimToEmpty(String str);

    String strip(String str);

    String stripToNull(String str);

    String stripToEmpty(String str);

    String strip(String str, String stripChars);

    String stripStart(String str, String stripChars);

    String stripEnd(String str, String stripChars);

    boolean isNumeric(CharSequence cs);

    int indexOfIgnoreCase(CharSequence str, CharSequence searchStr);

    int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos);

    String removeStart(String str, String remove);
}
