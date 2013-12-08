package com.brightgenerous.xml.deleagate;

import java.io.Reader;
import java.io.Writer;

import com.brightgenerous.xml.XmlException;

interface XmlDelegater {

    <T> T unmarshal(String xml, Class<T> clazz) throws XmlException;

    <T> T unmarshal(Reader xml, Class<T> clazz) throws XmlException;

    String marshal(Object obj) throws XmlException;

    void marshal(Object obj, Writer out) throws XmlException;
}
