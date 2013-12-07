package com.brightgenerous.csv;

class ParseStrategy<T> implements IParseStrategy<T> {

    private static final long serialVersionUID = -2604463756157177228L;

    private final IDataConverter<T> converter;

    private final char separator;

    private final char quote;

    private final char escape;

    private final boolean strictQuotes;

    private final boolean ignoreLeadingWhiteSpace;

    private final int skipLines;

    public ParseStrategy(IDataConverter<T> converter, char separator, char quote, char escape,
            boolean strictQuotes, boolean ignoreLeadingWhiteSpace, int skipLines) {
        this.converter = converter;
        this.separator = separator;
        this.quote = quote;
        this.escape = escape;
        this.strictQuotes = strictQuotes;
        this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
        this.skipLines = skipLines;
    }

    @Override
    public IDataConverter<T> getConverter() {
        return converter;
    }

    @Override
    public char getSeparator() {
        return separator;
    }

    @Override
    public char getQuote() {
        return quote;
    }

    @Override
    public char getEscape() {
        return escape;
    }

    @Override
    public boolean getStrictQuotes() {
        return strictQuotes;
    }

    @Override
    public boolean getIgnoreLeadingWhiteSpace() {
        return ignoreLeadingWhiteSpace;
    }

    @Override
    public int getSkipLines() {
        return skipLines;
    }
}
