package com.brightgenerous.zxing.deleg.javase;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.zxing.DecodeArguments;
import com.brightgenerous.zxing.EncodeArguments;
import com.google.zxing.LuminanceSource;
import com.google.zxing.common.BitMatrix;

@Deprecated
public class ZxingJavaseUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    public static final boolean RESOLVED;

    private static final LuminanceSourceCreater luminanceSourceCreater;

    private static final RuntimeException luminanceSourceCreaterRex;

    private static final ImageCreater<Object> imageCreater;

    private static final RuntimeException imageCreaterRex;

    static {
        boolean resolvedLSC = false;
        boolean resolvedIC = false;
        {
            LuminanceSourceCreater tmp = null;
            RuntimeException ex = null;
            try {
                tmp = new LuminanceSourceCreaterImpl();
                resolvedLSC = true;
            } catch (NoClassDefFoundError | RuntimeException e) {

                if (log.isLoggable(Level.INFO)) {
                    log.log(Level.INFO, "does not resolve BufferedImageLuminanceSource");
                }

                if (e instanceof RuntimeException) {
                    Throwable th = e.getCause();
                    if ((th == null) || !(th instanceof ClassNotFoundException)) {
                        throw e;
                    }
                }
            }
            if (tmp == null) {
                try {
                    tmp = new LuminanceSourceCreaterSub();
                } catch (NoClassDefFoundError | RuntimeException e) {

                    if (log.isLoggable(Level.INFO)) {
                        log.log(Level.INFO,
                                "does not resolve javax.imageio.ImageIO, java.awt.image.BufferedImage");
                    }

                    if (e instanceof RuntimeException) {
                        Throwable th = e.getCause();
                        if ((th == null) || !(th instanceof ClassNotFoundException)) {
                            throw e;
                        }
                        ex = (RuntimeException) e;
                    } else {
                        ex = new RuntimeException(e);
                    }
                }
            }
            luminanceSourceCreater = tmp;
            luminanceSourceCreaterRex = ex;
        }
        {
            ImageCreater<Object> tmp = null;
            RuntimeException ex = null;
            try {
                tmp = new ImageCreaterImpl<>();
                resolvedIC = true;
            } catch (NoClassDefFoundError | RuntimeException e) {

                if (log.isLoggable(Level.INFO)) {
                    log.log(Level.INFO, "does not resolve MatrixToImageWriter");
                }

                if (e instanceof RuntimeException) {
                    Throwable th = e.getCause();
                    if ((th == null) || !(th instanceof ClassNotFoundException)) {
                        throw e;
                    }
                }
            }
            if (tmp == null) {
                try {
                    tmp = new ImageCreaterSub<>();
                } catch (NoClassDefFoundError | RuntimeException e) {

                    if (log.isLoggable(Level.INFO)) {
                        log.log(Level.INFO,
                                "does not resolve javax.imageio.ImageIO, java.awt.image.BufferedImage");
                    }

                    if (e instanceof RuntimeException) {
                        Throwable th = e.getCause();
                        if ((th == null) || !(th instanceof ClassNotFoundException)) {
                            throw e;
                        }
                        ex = (RuntimeException) e;
                    } else {
                        ex = new RuntimeException(e);
                    }
                }
            }
            imageCreater = tmp;
            imageCreaterRex = ex;
        }
        RESOLVED = resolvedLSC && resolvedIC;
        USEFUL = (luminanceSourceCreater != null) && (imageCreater != null);
    }

    private ZxingJavaseUtility() {
    }

    public static boolean useful(DecodeArguments args) {
        if (luminanceSourceCreater == null) {
            throw luminanceSourceCreaterRex;
        }
        return luminanceSourceCreater.useful(args);
    }

    public static LuminanceSource createLuminanceSource(DecodeArguments args) {
        if (luminanceSourceCreater == null) {
            throw luminanceSourceCreaterRex;
        }
        return luminanceSourceCreater.create(args);
    }

    public static boolean useful(EncodeArguments args) {
        if (imageCreater == null) {
            throw imageCreaterRex;
        }
        return imageCreater.useful(args);
    }

    public static <T> T toImage(BitMatrix bitMatrix) {
        if (imageCreater == null) {
            throw imageCreaterRex;
        }
        return (T) imageCreater.toImage(bitMatrix);
    }

    public static void writeToStream(BitMatrix bitMatrix, String format, OutputStream outputStream)
            throws IOException {
        if (imageCreater == null) {
            throw imageCreaterRex;
        }
        imageCreater.writeToStream(bitMatrix, format, outputStream);
    }
}
