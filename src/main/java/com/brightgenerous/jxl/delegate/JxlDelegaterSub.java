package com.brightgenerous.jxl.delegate;

import java.io.InputStream;

class JxlDelegaterSub implements JxlDelegater {

    @Override
    public InputStream wrap(InputStream inputStream) {
        return inputStream;
    }
}
