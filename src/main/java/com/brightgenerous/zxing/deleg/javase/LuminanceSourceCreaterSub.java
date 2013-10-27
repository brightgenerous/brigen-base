package com.brightgenerous.zxing.deleg.javase;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.brightgenerous.zxing.DecodeArguments;
import com.brightgenerous.zxing.javase.BufferedImageDecodeArguments;
import com.google.zxing.LuminanceSource;

class LuminanceSourceCreaterSub implements LuminanceSourceCreater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(ImageIO.class.getName());
            Class.forName(BufferedImage.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean useful(DecodeArguments args) {
        if (args == null) {
            return false;
        }
        if (args instanceof BufferedImageDecodeArguments) {
            return true;
        }
        return false;
    }

    @Override
    public LuminanceSource create(DecodeArguments args) {
        if ((args != null) && (args instanceof BufferedImageDecodeArguments)) {
            return new BufferedImageLuminanceSource(
                    ((BufferedImageDecodeArguments) args).getBufferedImage());
        }
        return null;

    }
}
