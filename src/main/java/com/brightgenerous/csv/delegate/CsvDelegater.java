package com.brightgenerous.csv.delegate;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

import com.brightgenerous.csv.CsvException;
import com.brightgenerous.csv.IFormatStrategy;
import com.brightgenerous.csv.IParseStrategy;

interface CsvDelegater {

    <T> List<T> parse(String csv, IParseStrategy<T> strategy) throws CsvException;

    <T> List<T> parse(Reader csv, IParseStrategy<T> strategy) throws CsvException;

    <T> String format(List<T> datas, IFormatStrategy<T> strategy) throws CsvException;

    <T> void format(List<T> datas, IFormatStrategy<T> strategy, Writer out) throws CsvException;
}
