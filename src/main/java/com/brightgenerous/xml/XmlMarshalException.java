package com.brightgenerous.xml;

public class XmlMarshalException extends Exception {

    private static final long serialVersionUID = -454118294293818123L;

    public XmlMarshalException() {
    }

    public XmlMarshalException(String message) {
        super(message);
    }

    public XmlMarshalException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlMarshalException(Throwable cause) {
        super(cause);
    }
}
