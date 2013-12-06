package com.brightgenerous.pdfbox.writer.creater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.brightgenerous.pdfbox.writer.IDocumentCreater;
import com.brightgenerous.pdfbox.writer.IDocumentCreaters;
import com.brightgenerous.pdfbox.writer.IPagesAppender;

public class DocumentCreaters<T> implements IDocumentCreaters<T> {

    private final IPagesAppender appender;

    private final List<IDocumentCreater<T>> creaters;

    public DocumentCreaters(List<IDocumentCreater<T>> creaters) {
        this(null, creaters);
    }

    public DocumentCreaters(IPagesAppender appender, List<IDocumentCreater<T>> creaters) {
        this.appender = appender;
        this.creaters = (creaters == null) ? new ArrayList<IDocumentCreater<T>>() : creaters;
    }

    public DocumentCreaters(IDocumentCreater<T>... creaters) {
        this(null, creaters);
    }

    public DocumentCreaters(IPagesAppender appender, IDocumentCreater<T>... creaters) {
        this(appender, Arrays.asList(creaters));
    }

    @Override
    public IPagesAppender getPagesAppender() {
        return appender;
    }

    @Override
    public List<IDocumentCreater<T>> getDocumentCreaters() {
        return creaters;
    }
}
