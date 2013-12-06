package com.brightgenerous.pdfbox.writer;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface IPageAppender {

    PDDocument append(int start, PDDocument document) throws IOException;
}
