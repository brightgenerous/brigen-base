package com.brightgenerous.poi.writer;

import java.io.IOException;
import java.io.InputStream;

public class DefaultTemplateLoader implements TemplateLoader {

    @Override
    public InputStream loadHSSF() throws IOException {
        return DefaultTemplateLoader.class.getResourceAsStream("template.xls");
    }

    @Override
    public InputStream loadXSSF() throws IOException {
        return DefaultTemplateLoader.class.getResourceAsStream("template.xlsx");
    }
}
