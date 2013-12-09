package com.brightgenerous.csv;

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.csv.delegate.CsvUtility;
import com.brightgenerous.lang.Args;

@SuppressWarnings("deprecation")
public class CsvUtils<T> implements Serializable {

    private static final long serialVersionUID = -2226338883387789321L;

    public static boolean resolved() {
        return CsvUtility.RESOLVED;
    }

    private final IParseStrategy<T> parseStrategy;

    private final IFormatStrategy<T> formatStrategy;

    protected CsvUtils(IParseStrategy<T> parseStrategy, IFormatStrategy<T> formatStrategy) {
        Args.notNull(parseStrategy, "parseStrategy");
        Args.notNull(formatStrategy, "formatStrategy");

        this.parseStrategy = parseStrategy;
        this.formatStrategy = formatStrategy;
    }

    public static <T> CsvUtils<T> get(IParseStrategy<T> parseStrategy,
            IFormatStrategy<T> formatStrategy) {
        return getInstance(parseStrategy, formatStrategy);
    }

    protected static <T> CsvUtils<T> getInstance(IParseStrategy<T> parseStrategy,
            IFormatStrategy<T> formatStrategy) {
        return new CsvUtils<>(parseStrategy, formatStrategy);
    }

    public List<T> parse(String csv) throws CsvException {
        return CsvUtility.parse(csv, parseStrategy);
    }

    public List<T> parse(Reader csv) throws CsvException {
        return CsvUtility.parse(csv, parseStrategy);
    }

    public String format(List<T> datas) throws CsvException {
        return CsvUtility.format(datas, formatStrategy);
    }

    public void format(List<T> datas, Writer out) throws CsvException {
        CsvUtility.format(datas, formatStrategy, out);
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.resolved()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.resolved()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (ToStringUtils.resolved()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }
}
