package com.brightgenerous.pdfbox.writer.appender;

import java.awt.print.PrinterException;
import java.io.IOException;

import org.apache.pdfbox.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageable;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

import com.brightgenerous.pdfbox.writer.IPagesAppender;

public abstract class PagesAppender implements IPagesAppender {

    private final AppenderFlag flag;

    public PagesAppender(AppenderFlag flag) {
        if (flag == null) {
            throw new IllegalArgumentException("The flag must not be null.");
        }

        this.flag = flag;
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
                appendUnderContent(end, start, pages, page, underStream, rect);
                appendOverContent(end, start, pages, page, overStream, rect);
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

    protected void appendUnderContent(int end, int start, int pages, int page,
            PDPageContentStream contentStream, PDRectangle rect) throws IOException {
        AppenderFlag flag = getFlag();
        if (flag.equals(AppenderFlag.UNDER) || flag.equals(AppenderFlag.BOTH)) {
            appendContent(end, start, pages, page, contentStream, rect);
        }
    }

    protected void appendOverContent(int end, int start, int pages, int page,
            PDPageContentStream contentStream, PDRectangle rect) throws IOException {
        AppenderFlag flag = getFlag();
        if (flag.equals(AppenderFlag.OVER) || flag.equals(AppenderFlag.BOTH)) {
            appendContent(end, start, pages, page, contentStream, rect);
        }
    }

    protected abstract void appendContent(int end, int start, int pages, int page,
            PDPageContentStream contentStream, PDRectangle rect) throws IOException;

    protected AppenderFlag getFlag() {
        return flag;
    }
}
