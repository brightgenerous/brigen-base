package com.brightgenerous.commons.delegate;

class StringDelegaterSub implements StringDelegater {

    @Override
    public String empty() {
        return StringUtils.EMPTY;
    }

    @Override
    public boolean isEmpty(CharSequence cs) {
        return StringUtils.isEmpty(cs);
    }

    @Override
    public boolean isNotEmpty(CharSequence cs) {
        return StringUtils.isNotEmpty(cs);
    }

    @Override
    public boolean isBlank(CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    @Override
    public boolean isNotBlank(CharSequence cs) {
        return StringUtils.isNotBlank(cs);
    }

    @Override
    public String trim(String str) {
        return StringUtils.trim(str);
    }

    @Override
    public String trimToNull(String str) {
        return StringUtils.trimToNull(str);
    }

    @Override
    public String trimToEmpty(String str) {
        return StringUtils.trimToEmpty(str);
    }

    @Override
    public String strip(String str) {
        return StringUtils.strip(str);
    }

    @Override
    public String stripToNull(String str) {
        return StringUtils.stripToNull(str);
    }

    @Override
    public String stripToEmpty(String str) {
        return StringUtils.stripToEmpty(str);
    }

    @Override
    public String strip(String str, String stripChars) {
        return StringUtils.strip(str, stripChars);
    }

    @Override
    public String stripStart(String str, String stripChars) {
        return StringUtils.stripStart(str, stripChars);
    }

    @Override
    public String stripEnd(String str, String stripChars) {
        return StringUtils.stripEnd(str, stripChars);
    }

    @Override
    public boolean isNumeric(CharSequence cs) {
        return StringUtils.isNumeric(cs);
    }

    @Override
    public int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return StringUtils.indexOfIgnoreCase(str, searchStr);
    }

    @Override
    public int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        return StringUtils.indexOfIgnoreCase(str, searchStr, startPos);
    }

    @Override
    public String removeStart(String str, String remove) {
        return StringUtils.removeStart(str, remove);
    }
}
