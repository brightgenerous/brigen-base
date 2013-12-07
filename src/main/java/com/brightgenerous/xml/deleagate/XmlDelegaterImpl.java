package com.brightgenerous.xml.deleagate;

import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;

import com.brightgenerous.xml.XmlMarshalException;
import com.brightgenerous.xml.XmlUnmarshalException;

class XmlDelegaterImpl implements XmlDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(DataBindingException.class.getName());
            Class.forName(JAXB.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T unmarshal(String xml, Class<T> clazz) throws XmlUnmarshalException {
        try {
            return JAXB.unmarshal(xml, clazz);
        } catch (DataBindingException e) {
            throw new XmlUnmarshalException(e);
        }
    }

    @Override
    public <T> T unmarshal(Reader xml, Class<T> clazz) throws XmlUnmarshalException {
        try {
            return JAXB.unmarshal(xml, clazz);
        } catch (DataBindingException e) {
            throw new XmlUnmarshalException(e);
        }
    }

    @Override
    public String marshal(Object obj) {
        StringWriter sw = new StringWriter();
        JAXB.marshal(obj, sw);
        return sw.toString();
    }

    @Override
    public void marshal(Object obj, Writer out) throws XmlMarshalException {
        try {
            JAXB.marshal(obj, out);
        } catch (DataBindingException e) {
            throw new XmlMarshalException(e);
        }
    }
}
