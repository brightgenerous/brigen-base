package com.brightgenerous.zxing.delegate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.brightgenerous.lang.Args;
import com.brightgenerous.zxing.EncodeArguments;
import com.brightgenerous.zxing.ZxingEncoder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

class ZxingEncoderImpl implements ZxingEncoder {

    private final String contents;

    private final BarcodeFormat barcodeFormat;

    private final int width;

    private final int height;

    private final String format;

    private final int quality;

    private final Map<EncodeHintType, Object> hints;

    public ZxingEncoderImpl(String contents, BarcodeFormat barcodeFormat, int width, int height,
            String format, int quality, Map<EncodeHintType, Object> hints) {
        Args.notEmpty(contents, "contents");
        Args.notNull(barcodeFormat, "barcodeFormat");
        Args.greaterThan(0, width, "width");
        Args.greaterThan(0, height, "height");
        Args.notNull(format, "format");
        Args.greaterThan(0, quality, "quality");

        this.contents = contents;
        this.barcodeFormat = barcodeFormat;
        this.width = width;
        this.height = height;
        this.format = format;
        this.quality = quality;
        this.hints = hints;
    }

    @Override
    public <T> T encode() throws IOException {
        return encode((EncodeArguments) null);
    }

    @Override
    public <T> T encode(EncodeArguments args) throws IOException {
        BitMatrix bitData;
        try {
            bitData = new MultiFormatWriter().encode(contents, barcodeFormat, width, height, hints);
        } catch (WriterException e) {
            throw new IOException(e);
        }
        return (T) ZxingBuilderImpl.toImage(bitData, args);
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        encode(outputStream, null);
    }

    @Override
    public void encode(OutputStream outputStream, EncodeArguments args) throws IOException {
        BitMatrix bitData;
        try {
            bitData = new MultiFormatWriter().encode(contents, barcodeFormat, width, height, hints);
        } catch (WriterException e) {
            throw new IOException(e);
        }
        ZxingBuilderImpl.writeToStream(bitData, format, quality, outputStream, args);
    }
}
