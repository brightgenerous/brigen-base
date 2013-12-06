package com.brightgenerous.pdfbox.writer;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.brightgenerous.pdfbox.writer.creater.AbstractResourceLoader;

class EmptyResourceLoader<T> extends AbstractResourceLoader<T> {

    @Override
    protected void build(PDDocument doc, T data) throws IOException {
    }
}
