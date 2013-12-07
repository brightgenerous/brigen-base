package com.brightgenerous.csv.delegate;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

import com.brightgenerous.csv.CsvFormatException;
import com.brightgenerous.csv.CsvParseException;
import com.brightgenerous.csv.IFormatStrategy;
import com.brightgenerous.csv.IParseStrategy;

interface CsvDelegater {

    <T> List<T> parse(String csv, IParseStrategy<T> strategy) throws CsvParseException;

    <T> List<T> parse(Reader csv, IParseStrategy<T> strategy) throws CsvParseException;

    <T> String format(List<T> datas, IFormatStrategy<T> strategy) throws CsvFormatException;

    <T> void format(List<T> datas, IFormatStrategy<T> strategy, Writer out)
            throws CsvFormatException;
}
