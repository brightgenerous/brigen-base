package com.brightgenerous.zxing.deleg.javase;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.brightgenerous.zxing.EncodeArguments;
import com.brightgenerous.zxing.javase.BufferedImageEncodeArguments;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

class ImageCreaterImpl<T> implements ImageCreater<T> {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(ImageIO.class.getName());
            Class.forName(BufferedImage.class.getName());
            Class.forName(MatrixToImageWriter.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean useful(EncodeArguments args) {
        if (args == null) {
            return true;
        }
        if (args instanceof BufferedImageEncodeArguments) {
            return true;
        }
        return false;
    }

    @Override
    public T toImage(BitMatrix bitMatrix) {
        return (T) MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @Override
    public void writeToStream(BitMatrix bitMatrix, String format, OutputStream outputStream)
            throws IOException {
        MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
    }
}
