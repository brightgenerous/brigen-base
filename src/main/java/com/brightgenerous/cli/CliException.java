package com.brightgenerous.cli;

public class CliException extends Exception {

    private static final long serialVersionUID = -454118294293818123L;

    public CliException() {
    }

    public CliException(String message) {
        super(message);
    }

    public CliException(String message, Throwable cause) {
        super(message, cause);
    }

    public CliException(Throwable cause) {
        super(cause);
    }
}
