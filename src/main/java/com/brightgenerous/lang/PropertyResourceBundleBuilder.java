package com.brightgenerous.lang;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.util.PropertyResourceBundle;

public class PropertyResourceBundleBuilder implements Serializable {

    private static final long serialVersionUID = 4952182820600583655L;

    private String parentKey;

    protected PropertyResourceBundleBuilder() {
    }

    public static PropertyResourceBundleBuilder create() {
        return new PropertyResourceBundleBuilder();
    }

    public PropertyResourceBundleBuilder clear() {
        parentKey = null;
        return this;
    }

    public String parentKey() {
        return parentKey;
    }

    public PropertyResourceBundleBuilder parentKey(String parentKey) {
        this.parentKey = parentKey;
        return this;
    }

    public PropertyResourceBundle build(InputStream stream, InputStream... parents)
            throws IOException {
        if ((parents != null) && (0 < parents.length)) {
            return new InheritedPropertyResourceBundle(stream, parents);
        }
        if (parentKey == null) {
            return new PropertyResourceBundle(stream);
        }
        return new ChainPropertyResourceBundle(stream, parentKey);
    }

    public PropertyResourceBundle build(Reader reader, Reader... parents) throws IOException {
        if ((parents != null) && (0 < parents.length)) {
            return new InheritedPropertyResourceBundle(reader, parents);
        }
        if (parentKey == null) {
            return new PropertyResourceBundle(reader);
        }
        return new ChainPropertyResourceBundle(reader, parentKey);
    }
}
