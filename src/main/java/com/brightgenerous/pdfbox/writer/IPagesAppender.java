package com.brightgenerous.pdfbox.writer;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface IPagesAppender {

    PDDocument append(int end, int start, PDDocument document) throws IOException;
}
