package com.brightgenerous.pdfbox.writer;

public interface IPdfWriterStrategy<T> {

    IDocumentCreaters<T> getDocumentCreaters();

    IDocumentCreater<T> getEmptyDocumentCreater();
}
