package com.brightgenerous.csv;

public class CsvParseException extends Exception {

    private static final long serialVersionUID = -454118294293818123L;

    public CsvParseException() {
    }

    public CsvParseException(String message) {
        super(message);
    }

    public CsvParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvParseException(Throwable cause) {
        super(cause);
    }
}
