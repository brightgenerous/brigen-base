package com.brightgenerous.zxing.deleg.android;

import java.io.IOException;
import java.io.OutputStream;

import com.brightgenerous.zxing.EncodeArguments;
import com.google.zxing.common.BitMatrix;

interface ImageCreater<T> {

    boolean useful(EncodeArguments args);

    T toImage(BitMatrix data, EncodeArguments args);

    void writeToStream(BitMatrix data, String format, int quality, OutputStream outputStream,
            EncodeArguments args) throws IOException;
}
