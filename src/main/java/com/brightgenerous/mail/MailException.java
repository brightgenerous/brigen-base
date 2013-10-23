package com.brightgenerous.mail;

public class MailException extends Exception {

    private static final long serialVersionUID = -874776040157691046L;

    public MailException() {
    }

    public MailException(Throwable cause) {
        super(cause);
    }

    public MailException(String message) {
        super(message);
    }

    public MailException(String message, Throwable cause) {
        super(message, cause);
    }
}
