package com.brightgenerous.zxing;

import java.io.IOException;

public interface ZxingDecoder {

    String decode(int[] pixels, int width, int height) throws IOException;

    String decode(byte[] yuvData, int dataWidth, int dataHeight, int left, int top, int width,
            int height, boolean reverseHorizontal) throws IOException;

    String decode(DecodeArguments args) throws IOException;
}
