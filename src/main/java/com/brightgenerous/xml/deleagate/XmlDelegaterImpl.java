package com.brightgenerous.xml.deleagate;

import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;

import com.brightgenerous.xml.XmlException;

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
    public <T> T unmarshal(String xml, Class<T> clazz) throws XmlException {
        try {
            return JAXB.unmarshal(xml, clazz);
        } catch (DataBindingException e) {
            throw new XmlException(e);
        }
    }

    @Override
    public <T> T unmarshal(Reader xml, Class<T> clazz) throws XmlException {
        try {
            return JAXB.unmarshal(xml, clazz);
        } catch (DataBindingException e) {
            throw new XmlException(e);
        }
    }

    @Override
    public String marshal(Object obj) throws XmlException {
        StringWriter sw = new StringWriter();
        try {
            JAXB.marshal(obj, sw);
        } catch (DataBindingException e) {
            throw new XmlException(e);
        }
        return sw.toString();
    }

    @Override
    public void marshal(Object obj, Writer out) throws XmlException {
        try {
            JAXB.marshal(obj, out);
        } catch (DataBindingException e) {
            throw new XmlException(e);
        }
    }
}
