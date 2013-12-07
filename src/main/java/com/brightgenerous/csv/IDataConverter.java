package com.brightgenerous.csv;

import java.io.Serializable;

public interface IDataConverter<T> extends Serializable {

    T convertToData(String[] strs);

    String[] convertToLine(T data);

    String[] header();
}
