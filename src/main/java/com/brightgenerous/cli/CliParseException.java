package com.brightgenerous.cli;

public class CliParseException extends Exception {

    private static final long serialVersionUID = -454118294293818123L;

    public CliParseException() {
    }

    public CliParseException(String message) {
        super(message);
    }

    public CliParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CliParseException(Throwable cause) {
        super(cause);
    }
}
