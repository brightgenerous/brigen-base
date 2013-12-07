package com.brightgenerous.csv;

public class CsvFormatException extends Exception {

    private static final long serialVersionUID = -454118294293818123L;

    public CsvFormatException() {
    }

    public CsvFormatException(String message) {
        super(message);
    }

    public CsvFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvFormatException(Throwable cause) {
        super(cause);
    }
}
