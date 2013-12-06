package com.brightgenerous.xml.deleagate;

import java.io.Reader;
import java.io.Writer;

import com.brightgenerous.xml.XmlMarshalException;
import com.brightgenerous.xml.XmlUnmarshalException;

interface XmlDelegater {

    <T> T unmarshal(String xml, Class<T> clazz) throws XmlUnmarshalException;

    <T> T unmarshal(Reader xml, Class<T> clazz) throws XmlUnmarshalException;

    String marshal(Object obj);

    void marshal(Object obj, Writer out) throws XmlMarshalException;
}
