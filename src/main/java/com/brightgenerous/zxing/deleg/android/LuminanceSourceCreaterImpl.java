package com.brightgenerous.zxing.deleg.android;

import android.graphics.Bitmap;

import com.brightgenerous.lang.Args;
import com.brightgenerous.zxing.DecodeArguments;
import com.brightgenerous.zxing.android.BitmapDecodeArguments;
import com.google.zxing.LuminanceSource;

class LuminanceSourceCreaterImpl implements LuminanceSourceCreater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(Bitmap.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean useful(DecodeArguments args) {
        if (args == null) {
            return false;
        }
        if (args instanceof BitmapDecodeArguments) {
            return true;
        }
        return false;
    }

    @Override
    public LuminanceSource create(DecodeArguments args) {
        if ((args != null) && (args instanceof BitmapDecodeArguments)) {
            return new BitmapLuminanceSource(((BitmapDecodeArguments) args).getBitmap());
        }
        return null;
    }

    static class BitmapLuminanceSource extends LuminanceSource {

        private final byte[] matrix;

        protected BitmapLuminanceSource(Bitmap bitmap) {
            this(bitmap, bitmap.getWidth(), bitmap.getHeight());
        }

        protected BitmapLuminanceSource(Bitmap bitmap, int width, int height) {
            super(width, height);

            int size = width * height;
            matrix = new byte[size];
            int[] rgbs = new int[size];
            bitmap.getPixels(rgbs, 0, width, 0, 0, width, height);
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    int rgb = rgbs[offset + x];
                    matrix[offset + x] = toLuminance((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF,
                            rgb & 0xFF);
                }
            }
        }

        protected byte toLuminance(int r, int g, int b) {
            //int ret = ((306 * r) + (601 * g) + (117 * b)) >> 10;
            //double ret = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
            double ret = (0.298912 * r) + (0.586611 * g) + (0.114478 * b);
            //double ret = Math.sqrt((0.241 * r * r) + (0.691 * g * g) + (0.068 * b * b));
            return (byte) ret;
        }

        @Override
        public byte[] getMatrix() {
            return matrix;
        }

        @Override
        public byte[] getRow(int y, byte[] row) {
            Args.greaterEqual(0, y, "y");
            Args.lowerThan(getHeight(), y, "y");

            int width = getWidth();
            if ((row == null) || (row.length < width)) {
                row = new byte[width];
            }

            int offset = y * width;
            System.arraycopy(matrix, offset, row, 0, width);

            return row;
        }
    }
}
