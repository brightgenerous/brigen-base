package com.brightgenerous.csv;

public class CsvUtilsBuilder {

    private static final IDataConverter<String[]> emptyConverter = new EmptyDataConverter();

    private static final Character DEFAULT_SEPARATOR = Character.valueOf(',');

    private static final Character DEFAULT_QUOTE = Character.valueOf('"');

    private static final Character DEFAULT_ESCAPE = Character.valueOf('\\');

    private static final Boolean DEFAULT_STRICT_QUOTES = Boolean.FALSE;

    private static final Boolean DEFAULT_IGNORE_LEADING_WHITESPACE = Boolean.TRUE;

    private static final Integer DEFAULT_SKIP_LINES = Integer.valueOf(0);

    private static final String DEFAULT_LINE_END = "\n";

    private Character separator;

    private Character quote;

    private Character escape;

    private Boolean strictQuotes;

    private Boolean ignoreLeadingWhiteSpace;

    private String lineEnd;

    private Integer skipLines;

    protected CsvUtilsBuilder() {
    }

    public static CsvUtilsBuilder create() {
        return new CsvUtilsBuilder();
    }

    public CsvUtilsBuilder clear() {
        separator = null;
        quote = null;
        escape = null;
        strictQuotes = null;
        ignoreLeadingWhiteSpace = null;
        skipLines = null;
        lineEnd = null;
        return this;
    }

    public Character separator() {
        return separator;
    }

    public CsvUtilsBuilder separator(Character separator) {
        this.separator = separator;
        return this;
    }

    public CsvUtilsBuilder separator(char separator) {
        return separator(Character.valueOf(separator));
    }

    public Character quote() {
        return quote;
    }

    public CsvUtilsBuilder quote(Character quote) {
        this.quote = quote;
        return this;
    }

    public CsvUtilsBuilder quote(char quote) {
        return quote(Character.valueOf(quote));
    }

    public Character escape() {
        return escape;
    }

    public CsvUtilsBuilder escape(Character escape) {
        this.escape = escape;
        return this;
    }

    public CsvUtilsBuilder escape(char escape) {
        return escape(Character.valueOf(escape));
    }

    public Boolean strictQuotes() {
        return strictQuotes;
    }

    public CsvUtilsBuilder strictQuotes(Boolean strictQuotes) {
        this.strictQuotes = strictQuotes;
        return this;
    }

    public CsvUtilsBuilder strictQuotes(boolean strictQuotes) {
        return strictQuotes(strictQuotes ? Boolean.TRUE : Boolean.FALSE);
    }

    public Boolean ignoreLeadingWhiteSpace() {
        return ignoreLeadingWhiteSpace;
    }

    public CsvUtilsBuilder ignoreLeadingWhiteSpace(Boolean ignoreLeadingWhiteSpace) {
        this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
        return this;
    }

    public CsvUtilsBuilder ignoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
        return ignoreLeadingWhiteSpace(ignoreLeadingWhiteSpace ? Boolean.TRUE : Boolean.FALSE);
    }

    public String lineEnd() {
        return lineEnd;
    }

    public CsvUtilsBuilder lineEnd(String lineEnd) {
        this.lineEnd = lineEnd;
        return this;
    }

    public Integer skipLines() {
        return skipLines;
    }

    public CsvUtilsBuilder skipLines(Integer skipLines) {
        this.skipLines = skipLines;
        return this;
    }

    public CsvUtilsBuilder skipLines(int skipLines) {
        return skipLines(Integer.valueOf(skipLines));
    }

    public CsvUtils<String[]> build() {
        return CsvUtils.get(getParseStrategy(emptyConverter), getFormatStrategy(emptyConverter));
    }

    public <T> CsvUtils<T> build(IDataConverter<T> converter) {
        return CsvUtils.get(getParseStrategy(converter), getFormatStrategy(converter));
    }

    protected <T> IParseStrategy<T> getParseStrategy(IDataConverter<T> converter) {
        return new ParseStrategy<>(converter, getSeparator(), getQuote(), getEscape(),
                getStrictQuotes(), getIgnoreLeadingWhiteSpace(), getSkipLines());
    }

    protected <T> IFormatStrategy<T> getFormatStrategy(IDataConverter<T> converter) {
        return new FormatStrategy<>(converter, getSeparator(), getQuote(), getEscape(),
                getLineEnd());
    }

    protected char getSeparator() {
        Character ret = separator;
        if (ret == null) {
            ret = DEFAULT_SEPARATOR;
        }
        return ret.charValue();
    }

    protected char getQuote() {
        Character ret = quote;
        if (ret == null) {
            ret = DEFAULT_QUOTE;
        }
        return ret.charValue();
    }

    protected char getEscape() {
        Character ret = escape;
        if (ret == null) {
            ret = DEFAULT_ESCAPE;
        }
        return ret.charValue();
    }

    protected boolean getStrictQuotes() {
        Boolean ret = strictQuotes;
        if (ret == null) {
            ret = DEFAULT_STRICT_QUOTES;
        }
        return ret.booleanValue();
    }

    protected boolean getIgnoreLeadingWhiteSpace() {
        Boolean ret = ignoreLeadingWhiteSpace;
        if (ret == null) {
            ret = DEFAULT_IGNORE_LEADING_WHITESPACE;
        }
        return ret.booleanValue();
    }

    protected int getSkipLines() {
        Integer ret = skipLines;
        if (ret == null) {
            ret = DEFAULT_SKIP_LINES;
        }
        return ret.intValue();
    }

    protected String getLineEnd() {
        String ret = lineEnd;
        if (ret == null) {
            ret = DEFAULT_LINE_END;
        }
        return ret;
    }

}
