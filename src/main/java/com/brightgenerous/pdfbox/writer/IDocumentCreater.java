package com.brightgenerous.pdfbox.writer;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface IDocumentCreater<T> {

    PDDocument create(int start, T data) throws IOException;
}
