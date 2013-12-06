package com.brightgenerous.pdfbox.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFMergerUtility;

public class PdfWriter<T> implements IPdfWriter<T> {

    private final IPdfWriterStrategy<T> strategy;

    public PdfWriter(IPdfWriterStrategy<T> strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("The strategy must not be null.");
        }

        this.strategy = strategy;
    }

    @Override
    public void write(OutputStream outputStream, T data) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("The outputStream must not be null.");
        }

        List<IDocumentCreater<T>> creaters;
        IPagesAppender appender;
        {
            IDocumentCreaters<T> cs = strategy.getDocumentCreaters();
            creaters = cs.getDocumentCreaters();
            appender = cs.getPagesAppender();
        }
        if ((creaters == null) || creaters.isEmpty()) {
            creaters = Arrays.asList(strategy.getEmptyDocumentCreater());
        }
        List<PDDocument> documents = new ArrayList<>();
        try {
            {
                int last = 0;
                {
                    for (IDocumentCreater<T> creater : creaters) {
                        PDDocument document = creater.create(last, data);
                        last += document.getNumberOfPages();
                        documents.add(document);
                    }
                }
                {
                    int start = 0;
                    List<PDDocument> tmp = new ArrayList<>();
                    for (PDDocument document : documents) {
                        if (appender != null) {
                            document = appender.append(last, start, document);
                        }
                        start += document.getNumberOfPages();
                        tmp.add(document);
                    }
                    documents.clear();
                    documents.addAll(tmp);
                }
            }
            {
                PDDocument dest;
                if (documents.size() == 1) {
                    dest = documents.get(0);
                } else {
                    dest = new PDDocument();
                    if (!documents.isEmpty()) {
                        PDFMergerUtility merger = new PDFMergerUtility();
                        for (PDDocument document : documents) {
                            merger.appendDocument(dest, document);
                        }
                    }
                }
                dest.save(outputStream);
                dest.close();
            }
        } catch (COSVisitorException e) {
            throw new IOException(e);
        }
    }
}
