package com.brightgenerous.xml;

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

import com.brightgenerous.xml.deleagate.XmlUtility;

@SuppressWarnings("deprecation")
public class XmlUtils implements Serializable {

    private static final long serialVersionUID = -8083708706919305178L;

    public static boolean useful() {
        return XmlUtility.USEFUL;
    }

    private XmlUtils() {
    }

    public static <T> T unmarshal(String xml, Class<T> clazz) throws XmlUnmarshalException {
        return XmlUtility.unmarshal(xml, clazz);
    }

    public static <T> T unmarshal(Reader xml, Class<T> clazz) throws XmlUnmarshalException {
        return XmlUtility.unmarshal(xml, clazz);
    }

    public static String marshal(Object obj) {
        return XmlUtility.marshal(obj);
    }

    public static void marshal(Object obj, Writer out) throws XmlMarshalException {
        XmlUtility.marshal(obj, out);
    }
}
