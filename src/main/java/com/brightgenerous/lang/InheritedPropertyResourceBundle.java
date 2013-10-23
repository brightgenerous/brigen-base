package com.brightgenerous.lang;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.PropertyResourceBundle;

class InheritedPropertyResourceBundle extends PropertyResourceBundle {

    public InheritedPropertyResourceBundle(InputStream stream, InputStream... parents)
            throws IOException {
        super(stream);

        if ((parents != null) && (0 < parents.length)) {
            if (parents.length == 1) {
                setParent(new InheritedPropertyResourceBundle(parents[0]));
            } else {
                InputStream[] ps = new InputStream[parents.length - 1];
                for (int i = 0; i < ps.length; i++) {
                    ps[i] = parents[i + 1];
                }
                setParent(new InheritedPropertyResourceBundle(parents[0], ps));
            }
        }
    }

    public InheritedPropertyResourceBundle(Reader reader, Reader... parents) throws IOException {
        super(reader);

        if ((parents != null) && (0 < parents.length)) {
            if (parents.length == 1) {
                setParent(new InheritedPropertyResourceBundle(parents[0]));
            } else {
                Reader[] ps = new Reader[parents.length - 1];
                for (int i = 0; i < ps.length; i++) {
                    ps[i] = parents[i + 1];
                }
                setParent(new InheritedPropertyResourceBundle(parents[0], ps));
            }
        }
    }
}
