package com.brightgenerous.xml.deleagate;

import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.xml.XmlMarshalException;
import com.brightgenerous.xml.XmlUnmarshalException;

@Deprecated
public class XmlUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final XmlDelegater delegater;

    private static final RuntimeException rex;

    static {
        XmlDelegater tmp = null;
        boolean useful = false;
        RuntimeException ex = null;
        try {
            tmp = new XmlDelegaterImpl();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve jaxb");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        USEFUL = useful;
        delegater = tmp;
        rex = ex;
    }

    private XmlUtility() {
    }

    private static XmlDelegater getDelegater() {
        if (delegater == null) {
            throw rex;
        }
        return delegater;
    }

    public static <T> T unmarshal(String xml, Class<T> clazz) throws XmlUnmarshalException {
        return getDelegater().unmarshal(xml, clazz);
    }

    public static <T> T unmarshal(Reader xml, Class<T> clazz) throws XmlUnmarshalException {
        return getDelegater().unmarshal(xml, clazz);
    }

    public static String marshal(Object obj) {
        return getDelegater().marshal(obj);
    }

    public static void marshal(Object obj, Writer out) throws XmlMarshalException {
        getDelegater().marshal(obj, out);
    }
}
