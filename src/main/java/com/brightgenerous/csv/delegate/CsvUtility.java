package com.brightgenerous.csv.delegate;

import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.csv.CsvFormatException;
import com.brightgenerous.csv.CsvParseException;
import com.brightgenerous.csv.IFormatStrategy;
import com.brightgenerous.csv.IParseStrategy;

@Deprecated
public class CsvUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean RESOLVED;

    private static final CsvDelegater delegater;

    static {
        CsvDelegater tmp = null;
        boolean resolved = false;
        try {
            tmp = new CsvDelegaterOpenCsv();
            resolved = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve open csv");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        if (tmp == null) {
            tmp = new CsvDelegaterSub();
        }
        RESOLVED = resolved;
        delegater = tmp;
    }

    private CsvUtility() {
    }

    public static <T> List<T> parse(String csv, IParseStrategy<T> strategy)
            throws CsvParseException {
        return delegater.parse(csv, strategy);
    }

    public static <T> List<T> parse(Reader csv, IParseStrategy<T> strategy)
            throws CsvParseException {
        return delegater.parse(csv, strategy);
    }

    public static <T> String format(List<T> datas, IFormatStrategy<T> strategy)
            throws CsvFormatException {
        return delegater.format(datas, strategy);
    }

    public static <T> void format(List<T> datas, IFormatStrategy<T> strategy, Writer out)
            throws CsvFormatException {
        delegater.format(datas, strategy, out);
    }
}
