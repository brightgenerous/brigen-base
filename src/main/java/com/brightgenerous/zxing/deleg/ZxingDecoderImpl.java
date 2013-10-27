package com.brightgenerous.zxing.deleg;

import java.io.IOException;
import java.util.Map;

import com.brightgenerous.zxing.DecodeArguments;
import com.brightgenerous.zxing.ZxingDecoder;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

class ZxingDecoderImpl implements ZxingDecoder {

    private final Map<DecodeHintType, Object> hints;

    public ZxingDecoderImpl(Map<DecodeHintType, Object> hints) {
        this.hints = hints;
    }

    @Override
    public String decode(int[] pixels, int width, int height) throws IOException {
        LuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        try {
            result = new MultiFormatReader().decode(bitmap, hints);
        } catch (NotFoundException e) {
            throw new IOException(e);
        }
        return result.getText();
    }

    @Override
    public String decode(byte[] yuvData, int dataWidth, int dataHeight, int left, int top,
            int width, int height, boolean reverseHorizontal) throws IOException {
        LuminanceSource source = new PlanarYUVLuminanceSource(yuvData, dataWidth, dataHeight, left,
                top, width, height, reverseHorizontal);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        try {
            result = new MultiFormatReader().decode(bitmap, hints);
        } catch (NotFoundException e) {
            throw new IOException(e);
        }
        return result.getText();
    }

    @Override
    public String decode(DecodeArguments args) throws IOException {
        LuminanceSource source = ZxingBuilderImpl.createLuminanceSource(args);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        try {
            result = new MultiFormatReader().decode(bitmap, hints);
        } catch (NotFoundException e) {
            throw new IOException(e);
        }
        return result.getText();
    }
}
