package com.brightgenerous.pdfbox.deleg;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdfparser.PDFParser;

class PDFBoxDelegaterImpl implements PDFBoxDelegater {

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
