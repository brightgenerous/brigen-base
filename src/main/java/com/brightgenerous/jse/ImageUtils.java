package com.brightgenerous.jse;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

@Deprecated
public class ImageUtils {

    private ImageUtils() {
    }

    public static BufferedImage rescale(BufferedImage srcImage, int argw, int argh) {
        BufferedImage dstImage = null;

        int nw = argw;
        int nh = argh;
        double rate;
        {
            double width = srcImage.getWidth();
            double height = srcImage.getHeight();
            double sx = argw / width;
            double sy = argh / height;
            if (sx < sy) {
                nh = (int) (height * sx);
            } else {
                nw = (int) (width * sy);
            }
            rate = Math.min(sx, sy);
        }

        if (srcImage.getColorModel() instanceof IndexColorModel) {
            dstImage = new BufferedImage(nw, nh, srcImage.getType(),
                    (IndexColorModel) srcImage.getColorModel());
        } else {
            if (srcImage.getType() == 0) {
                dstImage = new BufferedImage(nw, nh, BufferedImage.TYPE_4BYTE_ABGR_PRE);
            } else {
                dstImage = new BufferedImage(nw, nh, srcImage.getType());
            }
        }

        AffineTransform trans = AffineTransform.getScaleInstance(rate, rate);

        if (dstImage.getColorModel().hasAlpha()
                && (dstImage.getColorModel() instanceof IndexColorModel)) {
            int transparentPixel = ((IndexColorModel) dstImage.getColorModel())
                    .getTransparentPixel();
            for (int i = 0; i < dstImage.getWidth(); ++i) {
                for (int j = 0; j < dstImage.getHeight(); ++j) {
                    dstImage.setRGB(i, j, transparentPixel);
                }
            }
        }

        Graphics2D g2 = dstImage.createGraphics();
        g2.drawImage(srcImage, trans, null);
        g2.dispose();

        return dstImage;
    }
}
