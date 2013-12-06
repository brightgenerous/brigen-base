package com.brightgenerous.xml;

public class XmlUnmarshalException extends Exception {

    private static final long serialVersionUID = -454118294293818123L;

    public XmlUnmarshalException() {
    }

    public XmlUnmarshalException(String message) {
        super(message);
    }

    public XmlUnmarshalException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlUnmarshalException(Throwable cause) {
        super(cause);
    }
}
