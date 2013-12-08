package com.brightgenerous.csv.delegate;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import com.brightgenerous.csv.CsvException;
import com.brightgenerous.csv.IDataConverter;
import com.brightgenerous.csv.IFormatStrategy;
import com.brightgenerous.csv.IParseStrategy;

class CsvDelegaterSub implements CsvDelegater {

    @Override
    public <T> List<T> parse(String csv, IParseStrategy<T> strategy) throws CsvException {
        return parse(new StringReader(csv), strategy);
    }

    @Override
    public <T> List<T> parse(Reader csv, IParseStrategy<T> strategy) throws CsvException {
        List<T> ret = new LinkedList<>();
        try (CSVReader reader = new CSVReader(csv, strategy.getSeparator(), strategy.getQuote(),
                strategy.getEscape(), strategy.getSkipLines(), strategy.getStrictQuotes(),
                strategy.getIgnoreLeadingWhiteSpace())) {
            IDataConverter<T> converter = strategy.getConverter();
            String[] line;
            while ((line = reader.readNext()) != null) {
                ret.add(converter.convertToData(line));
            }
        } catch (IOException e) {
            throw new CsvException(e);
        }
        return ret;
    }

    @Override
    public <T> String format(List<T> datas, IFormatStrategy<T> strategy) throws CsvException {
        StringWriter sw = new StringWriter();
        format(datas, strategy, sw);
        return sw.toString();
    }

    @Override
    public <T> void format(List<T> datas, IFormatStrategy<T> strategy, Writer out)
            throws CsvException {
        try (CSVWriter writer = new CSVWriter(out, strategy.getSeparator(), strategy.getQuote(),
                strategy.getEscape(), strategy.getLineEnd())) {
            IDataConverter<T> converter = strategy.getConverter();
            {
                String[] header = converter.header();
                if (header != null) {
                    writer.writeNext(header);
                }
            }
            for (T data : datas) {
                writer.writeNext(converter.convertToLine(data));
            }
        } catch (IOException e) {
            throw new CsvException(e);
        }
    }
}
