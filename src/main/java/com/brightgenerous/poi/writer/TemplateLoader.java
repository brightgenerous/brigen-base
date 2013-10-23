package com.brightgenerous.poi.writer;

import java.io.IOException;
import java.io.InputStream;

public interface TemplateLoader {

    InputStream loadHSSF() throws IOException;

    InputStream loadXSSF() throws IOException;
}
