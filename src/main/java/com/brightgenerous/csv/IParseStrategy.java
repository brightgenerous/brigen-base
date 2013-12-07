package com.brightgenerous.csv;

import java.io.Serializable;

public interface IParseStrategy<T> extends Serializable {

    IDataConverter<T> getConverter();

    char getSeparator();

    char getQuote();

    char getEscape();

    boolean getStrictQuotes();

    boolean getIgnoreLeadingWhiteSpace();

    int getSkipLines();
}
