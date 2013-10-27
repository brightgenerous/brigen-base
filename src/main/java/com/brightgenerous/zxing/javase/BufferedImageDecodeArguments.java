package com.brightgenerous.zxing.javase;

import java.awt.image.BufferedImage;

import com.brightgenerous.lang.Args;
import com.brightgenerous.zxing.DecodeArguments;

public class BufferedImageDecodeArguments extends DecodeArguments {

    private final BufferedImage bufferedImage;

    public BufferedImageDecodeArguments(BufferedImage bufferedImage) {
        Args.notNull(bufferedImage, "bufferedImage");

        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
