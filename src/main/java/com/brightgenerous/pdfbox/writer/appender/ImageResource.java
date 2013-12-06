package com.brightgenerous.pdfbox.writer.appender;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class ImageResource implements IImageResource {

    private final String fileName;

    private final URL url;

    private final byte[] bytes;

    public ImageResource(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("The fileName must not be null.");
        }

        this.fileName = fileName;
        url = null;
        bytes = null;
    }

    public ImageResource(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("The url must not be null.");
        }

        fileName = null;
        this.url = url;
        bytes = null;
    }

    public ImageResource(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("The bytes must not be null.");
        }

        fileName = null;
        url = null;
        this.bytes = bytes;
    }

    @Override
    public PDXObjectImage getImage() throws IOException {
        BufferedImage image;
        if (fileName != null) {
            image = ImageIO.read(new File(fileName));
        } else if (url != null) {
            image = ImageIO.read(url);
        } else if (bytes != null) {
            image = ImageIO.read(new ByteArrayInputStream(bytes));
        } else {
            throw new IllegalStateException();
        }
        return new PDJpeg(new PDDocument(), image, 1.0f);
    }
}
