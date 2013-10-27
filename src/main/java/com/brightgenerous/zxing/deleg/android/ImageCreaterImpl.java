package com.brightgenerous.zxing.deleg.android;

import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Color;

import com.brightgenerous.zxing.EncodeArguments;
import com.brightgenerous.zxing.android.BitmapEncodeArguments;
import com.google.zxing.common.BitMatrix;

class ImageCreaterImpl<T> implements ImageCreater<T> {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(Bitmap.class.getName());
            Class.forName(CompressFormat.class.getName());
            Class.forName(Config.class.getName());
            Class.forName(Color.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean useful(EncodeArguments args) {
        if (args == null) {
            return true;
        }
        if (args instanceof BitmapEncodeArguments) {
            return true;
        }
        return false;
    }

    @Override
    public T toImage(BitMatrix bitMatrix, EncodeArguments args) {
        return (T) createBitmap(bitMatrix, args);
    }

    @Override
    public void writeToStream(BitMatrix bitMatrix, String format, int quality,
            OutputStream outputStream, EncodeArguments args) throws IOException {
        Bitmap bmp = createBitmap(bitMatrix, args);
        CompressFormat cf = null;
        if (format != null) {
            for (CompressFormat e : CompressFormat.values()) {
                if (e.toString().equals(format)) {
                    cf = e;
                    break;
                }
            }
            if (cf == null) {
                if (format.toLowerCase().startsWith("jpeg")) {
                    cf = CompressFormat.JPEG;
                } else if (format.toLowerCase().startsWith("png")) {
                    cf = CompressFormat.PNG;
                } else if (format.toLowerCase().startsWith("webp")) {
                    cf = CompressFormat.WEBP;
                }
            }
            if (cf == null) {
                throw new IllegalArgumentException(String.format("not found format by name %s",
                        format));
            }
        } else {
            cf = CompressFormat.PNG;
        }
        bmp.compress(cf, quality, outputStream);
    }

    protected Bitmap createBitmap(BitMatrix bitMatrix, EncodeArguments args) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];

        Config config = null;
        int frontColor;
        int backColor;
        {
            Integer fc = null;
            Integer bc = null;
            if ((args != null) && (args instanceof BitmapEncodeArguments)) {
                BitmapEncodeArguments ba = (BitmapEncodeArguments) args;
                config = ba.getConfig();
                fc = ba.getFrontColor();
                bc = ba.getBackColor();
            }
            if (config == null) {
                config = Config.ARGB_8888;
            }
            if (fc == null) {
                frontColor = Color.BLACK;
            } else {
                frontColor = fc.intValue();
            }
            if (bc == null) {
                backColor = Color.WHITE;
            } else {
                backColor = bc.intValue();
            }
        }

        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? frontColor : backColor;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
