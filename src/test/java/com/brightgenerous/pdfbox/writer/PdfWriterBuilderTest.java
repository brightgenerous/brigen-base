package com.brightgenerous.pdfbox.writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;

import com.brightgenerous.pdfbox.writer.appender.AppenderAlign;
import com.brightgenerous.pdfbox.writer.appender.AppenderBuilder;
import com.brightgenerous.pdfbox.writer.appender.AppenderUtils;
import com.brightgenerous.pdfbox.writer.creater.AbstractResourceLoader;

public class PdfWriterBuilderTest {

    @Test
    public void build() throws FileNotFoundException, IOException {
        PdfWriterBuilder<Void> builder = PdfWriterBuilder.create();
        builder.pagesAppender(AppenderUtils.composite(
                AppenderBuilder.create().buildPagesFooter(),
                AppenderBuilder.create().align(AppenderAlign.LEFT).format("{0} of {1}")
                        .buildPagesFooter(),
                AppenderBuilder.create().format("- - - - - - - - - -SAMPLE- - - - - - - - - -")
                        .rotate(45).buildPagesText(),
                AppenderBuilder.create()
                        .imageResource("C:/Users/master/Desktop/11922299_907749692_normal.png")
                        .rotate(45).buildPagesBackground()));
        builder.addCreater(new AbstractResourceLoader<Void>() {

            @Override
            protected void build(PDDocument doc, Void data) throws IOException {
                for (int i = 0; i < 3; i++) {
                    PDPage page = new PDPage();
                    doc.addPage(page);
                    PDPageContentStream contentStream = new PDPageContentStream(doc, page, true,
                            true);
                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(300, 100);
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                    contentStream.drawString("1 - " + i);
                    contentStream.endText();
                    contentStream.close();
                }
            }
        });
        builder.addCreater(new AbstractResourceLoader<Void>() {

            @Override
            protected void build(PDDocument doc, Void data) throws IOException {
                for (int i = 0; i < 3; i++) {
                    PDPage page = new PDPage();
                    doc.addPage(page);
                    PDPageContentStream contentStream = new PDPageContentStream(doc, page, true,
                            true);
                    contentStream.beginText();
                    contentStream.moveTextPositionByAmount(100, 300);
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                    contentStream.drawString("2 - " + i);
                    contentStream.endText();
                    contentStream.close();
                }
            }
        });
        try (OutputStream os = new FileOutputStream(
                "C:/Users/master/Desktop/PdfWriterBuilderTest_pdfbox.pdf")) {
            builder.build().write(os, null);
        }
    }
}
