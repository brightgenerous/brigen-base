package com.brightgenerous.datasource;

import static com.brightgenerous.commons.StringUtils.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

class Loader {

    private Loader() {
    }

    public static List<String> loadCreateTables(InputStream stream) throws XMLStreamException {
        return load(stream, "create");
    }

    public static List<String> loadDropTables(InputStream stream) throws XMLStreamException {
        return load(stream, "drop");
    }

    public static List<String> loadInitSqls(InputStream stream) throws XMLStreamException {
        return load(stream, "sql");
    }

    public static List<String> loadDummySqls(InputStream stream) throws XMLStreamException {
        return load(stream, "sql");
    }

    private static List<String> load(InputStream stream, String elem) throws XMLStreamException {
        List<String> ret = new ArrayList<>();
        XMLStreamReader reader = null;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            reader = factory.createXMLStreamReader(stream);
            String localName = null;
            StringBuilder text = null;
            for (; reader.hasNext(); reader.next()) {
                int eventType = reader.getEventType();
                if (eventType == XMLStreamConstants.START_ELEMENT) {
                    localName = reader.getLocalName();
                    if (localName.equals(elem)) {
                        text = new StringBuilder();
                    }
                } else if (eventType == XMLStreamConstants.CHARACTERS) {
                    if ((localName != null) && (text != null)) {
                        if (localName.equals(elem)) {
                            text.append(reader.getText());
                        }
                    }
                } else if (eventType == XMLStreamConstants.END_ELEMENT) {
                    if (text != null) {
                        localName = reader.getLocalName();
                        if (localName.equals(elem)) {
                            String sql = text.toString().trim();
                            if (isNotEmpty(sql)) {
                                ret.add(sql);
                            }
                        }
                    }
                }
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException e) {
                }
            }
        }
        return ret;
    }
}
