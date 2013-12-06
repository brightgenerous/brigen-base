package com.brightgenerous.pdfbox.writer.appender;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageable;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

class CompositePagesAppender extends PagesAppender {

    private final List<PagesAppender> delegs;

    public CompositePagesAppender(List<PagesAppender> delegs) {
        super(AppenderFlag.BOTH);

        this.delegs = (delegs == null) ? new ArrayList<PagesAppender>() : delegs;
    }

    public CompositePagesAppender(PagesAppender... delegs) {
        this(Arrays.asList(delegs));
    }

    public CompositePagesAppender add(PagesAppender deleg, PagesAppender... delegs) {
        this.delegs.add(deleg);
        for (PagesAppender d : delegs) {
            this.delegs.add(d);
        }
        return this;
    }

    public CompositePagesAppender clear() {
        delegs.clear();
        return this;
    }

    @Override
    public PDDocument append(int end, int start, PDDocument document) throws IOException {
        PDPageable pageable;
        try {
            pageable = new PDPageable(document);
        } catch (IllegalArgumentException | PrinterException e) {
            throw new IOException(e);
        }
        int pages = pageable.getNumberOfPages();
        if (0 < pages) {
            PDDocument overDoc = new PDDocument();
            PDDocument underDoc = new PDDocument();
            for (int i = 0; i < pages; i++) {
                int page = i + 1;
                PDPageContentStream overStream;
                {
                    PDPage overPage = new PDPage();
                    overDoc.addPage(overPage);
                    overStream = new PDPageContentStream(overDoc, overPage, true, true);
                }
                PDPageContentStream underStream;
                {
                    PDPage underPage = new PDPage();
                    underDoc.addPage(underPage);
                    underStream = new PDPageContentStream(underDoc, underPage, true, true);
                }
                PDRectangle rect;
                {
                    PDPage pdPage = (PDPage) document.getDocumentCatalog().getAllPages().get(i);
                    rect = pdPage.getMediaBox();
                }
                for (PagesAppender deleg : delegs) {
                    deleg.appendUnderContent(end, start, pages, page, underStream, rect);
                    deleg.appendOverContent(end, start, pages, page, overStream, rect);
                }
                underStream.close();
                overStream.close();
            }
            {
                Overlay overlay = new Overlay();
                document = overlay.overlay(document, underDoc);
            }
            {
                Overlay overlay = new Overlay();
                document = overlay.overlay(overDoc, document);
            }
        }
        return document;
    }

    @Override
    protected void appendContent(int end, int start, int pages, int page,
            PDPageContentStream contentStream, PDRectangle rect) throws IOException {
        throw new UnsupportedOperationException();
    }
}
