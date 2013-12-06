package com.brightgenerous.pdfbox.writer.creater;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.brightgenerous.pdfbox.writer.IResourceLoader;

public class ResourceLoader<T> implements IResourceLoader<T>, Serializable {

    private static final long serialVersionUID = 319356142238190647L;

    private final String fileName;

    private final URL url;

    private final byte[] bytes;

    public ResourceLoader(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("The fileName must not be null.");
        }

        this.fileName = fileName;
        url = null;
        bytes = null;
    }

    public ResourceLoader(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("The url must not be null.");
        }

        fileName = null;
        this.url = url;
        bytes = null;
    }

    public ResourceLoader(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("The bytes must not be null.");
        }

        fileName = null;
        url = null;
        this.bytes = bytes;
    }

    @Override
    public PDDocument load(T data) throws IOException {
        PDDocument ret;
        if (fileName != null) {
            ret = PDDocument.load(fileName);
        } else if (url != null) {
            ret = PDDocument.load(url);
        } else if (bytes != null) {
            ret = PDDocument.load(new ByteArrayInputStream(bytes));
        } else {
            throw new IllegalStateException();
        }
        return ret;
    }
}
