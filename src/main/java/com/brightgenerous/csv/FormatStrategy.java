package com.brightgenerous.csv;

class FormatStrategy<T> implements IFormatStrategy<T> {

    private static final long serialVersionUID = 8398045530117843357L;

    private final IDataConverter<T> converter;

    private final char separator;

    private final char quote;

    private final char escape;

    private final String lineEnd;

    public FormatStrategy(IDataConverter<T> converter, char separator, char quote, char escape,
            String lineEnd) {
        this.converter = converter;
        this.separator = separator;
        this.quote = quote;
        this.escape = escape;
        this.lineEnd = lineEnd;
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
    public String getLineEnd() {
        return lineEnd;
    }
}
