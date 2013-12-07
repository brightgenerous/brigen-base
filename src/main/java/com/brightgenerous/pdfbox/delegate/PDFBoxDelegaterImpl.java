package com.brightgenerous.pdfbox.delegate;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdfparser.PDFParser;

class PDFBoxDelegaterImpl implements PDFBoxDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(PDFParser.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isPdf(InputStream inputStream) {
        boolean ret = false;
        try (InputStream is = inputStream) {
            new PDFParser(is).parse();
            ret = true;
        } catch (IOException e) {
        }
        return ret;
    }
}
