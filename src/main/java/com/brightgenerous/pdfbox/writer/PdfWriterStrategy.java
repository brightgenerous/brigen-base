package com.brightgenerous.pdfbox.writer;

public class PdfWriterStrategy<T> implements IPdfWriterStrategy<T> {

    private final IDocumentCreaters<T> documentCreaters;

    private final IDocumentCreater<T> emptyDocumentCreater;

    public PdfWriterStrategy(IDocumentCreaters<T> documentCreaters,
            IDocumentCreater<T> emptyDocumentCreater) {
        if (documentCreaters == null) {
            throw new IllegalArgumentException("The documentCreaters must not be null.");
        }
        if (emptyDocumentCreater == null) {
            throw new IllegalArgumentException("The emptyDocumentCreater must not be null.");
        }

        this.documentCreaters = documentCreaters;
        this.emptyDocumentCreater = emptyDocumentCreater;
    }

    @Override
    public IDocumentCreaters<T> getDocumentCreaters() {
        return documentCreaters;
    }

    @Override
    public IDocumentCreater<T> getEmptyDocumentCreater() {
        return emptyDocumentCreater;
    }
}
