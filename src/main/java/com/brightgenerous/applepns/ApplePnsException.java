package com.brightgenerous.applepns;

public class ApplePnsException extends Exception {

    private static final long serialVersionUID = -874776040157691046L;

    public ApplePnsException() {
    }

    public ApplePnsException(Throwable cause) {
        super(cause);
    }

    public ApplePnsException(String message) {
        super(message);
    }

    public ApplePnsException(String message, Throwable cause) {
        super(message, cause);
    }
}
