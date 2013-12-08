package com.brightgenerous.xml;

public class XmlException extends Exception {

    private static final long serialVersionUID = -454118294293818123L;

    public XmlException() {
    }

    public XmlException(String message) {
        super(message);
    }

    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlException(Throwable cause) {
        super(cause);
    }
}
