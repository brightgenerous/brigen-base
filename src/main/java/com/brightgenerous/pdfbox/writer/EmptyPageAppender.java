package com.brightgenerous.pdfbox.writer;

import java.io.IOException;
import java.io.Serializable;

import org.apache.pdfbox.pdmodel.PDDocument;

class EmptyPageAppender implements IPageAppender, Serializable {

    private static final long serialVersionUID = -6550790798300472228L;

    @Override
    public PDDocument append(int start, PDDocument document) throws IOException {
        return document;
    }
}
