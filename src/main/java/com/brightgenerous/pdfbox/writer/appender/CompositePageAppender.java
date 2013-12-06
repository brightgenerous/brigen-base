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

class CompositePageAppender extends PageAppender {

    private final List<PageAppender> delegs;

    public CompositePageAppender(List<PageAppender> delegs) {
        super(AppenderFlag.BOTH);

        this.delegs = (delegs == null) ? new ArrayList<PageAppender>() : delegs;
    }

    public CompositePageAppender(PageAppender... delegs) {
        this(Arrays.asList(delegs));
    }

    public CompositePageAppender add(PageAppender deleg, PageAppender... delegs) {
        this.delegs.add(deleg);
        for (PageAppender d : delegs) {
            this.delegs.add(d);
        }
        return this;
    }

    public CompositePageAppender clear() {
        delegs.clear();
        return this;
    }

    @Override
    public PDDocument append(int start, PDDocument document) throws IOException {
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
                for (PageAppender deleg : delegs) {
                    deleg.appendUnderContent(start, pages, page, underStream, rect);
                    deleg.appendOverContent(start, pages, page, overStream, rect);
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
    protected void appendContent(int start, int pages, int page, PDPageContentStream contentStream,
            PDRectangle rect) throws IOException {
        throw new UnsupportedOperationException();
    }
}
