package com.brightgenerous.compress.zip;

public class ZipException extends Exception {

    private static final long serialVersionUID = -454118294293818123L;

    public ZipException() {
    }

    public ZipException(String message) {
        super(message);
    }

    public ZipException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZipException(Throwable cause) {
        super(cause);
    }
}
