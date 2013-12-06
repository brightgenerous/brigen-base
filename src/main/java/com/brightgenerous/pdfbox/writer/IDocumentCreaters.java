package com.brightgenerous.pdfbox.writer;

import java.util.List;

public interface IDocumentCreaters<T> {

    IPagesAppender getPagesAppender();

    List<IDocumentCreater<T>> getDocumentCreaters();
}
