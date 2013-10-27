package com.brightgenerous.zxing.android;

import android.graphics.Bitmap;

import com.brightgenerous.lang.Args;
import com.brightgenerous.zxing.DecodeArguments;

public class BitmapDecodeArguments extends DecodeArguments {

    private final Bitmap bitmap;

    public BitmapDecodeArguments(Bitmap bitmap) {
        Args.notNull(bitmap, "bitmap");

        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
