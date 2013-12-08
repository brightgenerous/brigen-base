package com.brightgenerous.csv;

public class CsvException extends Exception {

    private static final long serialVersionUID = -454118294293818123L;

    public CsvException() {
    }

    public CsvException(String message) {
        super(message);
    }

    public CsvException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvException(Throwable cause) {
        super(cause);
    }
}
