package com.brightgenerous.pdfbox.writer.creater;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.brightgenerous.pdfbox.writer.IResourceLoader;

public abstract class AbstractResourceLoader<T> implements IResourceLoader<T> {

    @Override
    public PDDocument load(T data) throws IOException {
        PDDocument ret = new PDDocument();
        build(ret, data);
        return ret;
    }

    protected abstract void build(PDDocument doc, T data) throws IOException;
}
