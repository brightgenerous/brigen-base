package com.brightgenerous.pdfbox.writer;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface IResourceLoader<T> {

    PDDocument load(T data) throws IOException;
}
