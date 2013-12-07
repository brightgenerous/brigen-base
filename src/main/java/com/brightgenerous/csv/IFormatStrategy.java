package com.brightgenerous.csv;

import java.io.Serializable;

public interface IFormatStrategy<T> extends Serializable {

    IDataConverter<T> getConverter();

    char getSeparator();

    char getQuote();

    char getEscape();

    String getLineEnd();
}
