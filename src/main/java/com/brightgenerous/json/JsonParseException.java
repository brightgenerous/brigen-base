package com.brightgenerous.json;

public class JsonParseException extends Exception {

    private static final long serialVersionUID = -454118294293818123L;

    public JsonParseException() {
    }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonParseException(Throwable cause) {
        super(cause);
    }
}
