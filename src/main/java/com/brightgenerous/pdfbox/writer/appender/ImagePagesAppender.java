package com.brightgenerous.pdfbox.writer.appender;

import java.awt.geom.AffineTransform;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class ImagePagesAppender extends PositionPagesAppender {

    private final IImageResource resource;

    private final boolean inline;

    public ImagePagesAppender(AppenderFlag flag, AppenderAlign align, AppenderVertical vertical,
            float marginX, float marginY, float paddingLeft, float paddingTop, float rotate,
            IImageResource resource, boolean inline) {
        super(flag, align, vertical, marginX, marginY, paddingLeft, paddingTop, rotate);

        if (resource == null) {
            throw new IllegalArgumentException("The resource must not be null.");
        }

        this.resource = resource;
        this.inline = inline;
    }

    @Override
    protected void appendContent(int end, int start, int pages, int page,
            PDPageContentStream contentStream, PDRectangle rect) throws IOException {
        PDXObjectImage image = getResource().getImage();
        if (image != null) {
            float width = image.getWidth();
            float height = image.getHeight();
            AffineTransform transform = new AffineTransform(width, 0, 0, height, getCornerX(
                    rect.getWidth(), width), getCornerY(rect.getHeight(), height));
            transform.rotate((float) ((getRotate() * Math.PI) / 180), 0.5, 0.5);
            contentStream.drawXObject(image, transform);
        }
    }

    protected IImageResource getResource() {
        return resource;
    }

    protected boolean getInline() {
        return inline;
    }
}
