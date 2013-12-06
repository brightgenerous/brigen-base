package com.brightgenerous.pdfbox.writer.appender;

import java.awt.Color;
import java.io.IOException;
import java.text.MessageFormat;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class TextPagesAppender extends PositionPagesAppender {

    private final String format;

    private final PDFont font;

    private final float fontSize;

    private final boolean kerned;

    private final Color color;

    public TextPagesAppender(AppenderFlag flag, AppenderAlign align, AppenderVertical vertical,
            float marginX, float marginY, float paddingLeft, float paddingTop, float rotate,
            String format, PDFont font, float fontSize, boolean kerned, Color color) {
        super(flag, align, vertical, marginX, marginY, paddingLeft, paddingTop, rotate);

        if (format == null) {
            throw new IllegalArgumentException("The format must not be null.");
        }
        if (font == null) {
            throw new IllegalArgumentException("The font must not be null.");
        }
        if (fontSize <= 0) {
            throw new IllegalArgumentException("The fontSize must not be lower equal 0.");
        }
        if (color == null) {
            throw new IllegalArgumentException("The color must not be null.");
        }

        this.format = format;
        this.font = font;
        this.fontSize = fontSize;
        this.kerned = kerned;
        this.color = color;
    }

    @Override
    protected void appendContent(int end, int start, int pages, int page,
            PDPageContentStream contentStream, PDRectangle rect) throws IOException {
        PDFont font = getFont();
        float fontSize = getFontSize();
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(getColor());
        String str = getText(end, start, pages, page);
        float contentWidth;
        float contentHeight;
        {
            contentWidth = (font.getStringWidth(str) / 1000) * fontSize;
            contentHeight = (font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000)
                    * fontSize;
        }
        double angle = (getRotate() * Math.PI) / 180.0;
        float[] point = getRotatePoint(angle, rect.getWidth(), rect.getHeight(), contentWidth,
                contentHeight);
        contentStream.setTextRotation(angle, point[0], point[1]);
        contentStream.drawString(str);
        contentStream.endText();
    }

    protected String getFormat() {
        return format;
    }

    protected String getText(int end, int start, int pages, int page) {
        return MessageFormat.format(getFormat(), Integer.valueOf(start + page),
                Integer.valueOf(end), Integer.valueOf(start), Integer.valueOf(pages),
                Integer.valueOf(page));
    }

    protected PDFont getFont() {
        return font;
    }

    protected float getFontSize() {
        return fontSize;
    }

    protected boolean getKerned() {
        return kerned;
    }

    protected Color getColor() {
        return color;
    }
}
