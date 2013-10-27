package com.brightgenerous.zxing.deleg.javase;

import java.io.IOException;
import java.io.OutputStream;

import com.brightgenerous.zxing.EncodeArguments;
import com.google.zxing.common.BitMatrix;

interface ImageCreater<T> {

    boolean useful(EncodeArguments args);

    T toImage(BitMatrix bitMatrix);

    void writeToStream(BitMatrix bitMatrix, String format, OutputStream outputStream)
            throws IOException;
}
