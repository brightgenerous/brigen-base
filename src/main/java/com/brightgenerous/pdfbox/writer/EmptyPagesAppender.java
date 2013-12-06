package com.brightgenerous.pdfbox.writer;

import java.io.IOException;
import java.io.Serializable;

import org.apache.pdfbox.pdmodel.PDDocument;

class EmptyPagesAppender implements IPagesAppender, Serializable {

    private static final long serialVersionUID = -5768543467458069306L;

    @Override
    public PDDocument append(int end, int start, PDDocument document) throws IOException {
        return document;
    }
}
