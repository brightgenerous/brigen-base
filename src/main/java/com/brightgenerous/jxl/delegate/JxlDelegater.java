package com.brightgenerous.jxl.delegate;

import java.io.IOException;
import java.io.InputStream;

interface JxlDelegater {

    InputStream wrap(InputStream inputStream) throws IOException;
}
