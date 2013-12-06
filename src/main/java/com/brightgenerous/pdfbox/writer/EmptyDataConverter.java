package com.brightgenerous.pdfbox.writer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class EmptyDataConverter<T> implements IDataConverter<T>, Serializable {

    private static final long serialVersionUID = 4300010553503889209L;

    @Override
    public Map<String, String> convert(T data) {
        return new HashMap<>();
    }
}
