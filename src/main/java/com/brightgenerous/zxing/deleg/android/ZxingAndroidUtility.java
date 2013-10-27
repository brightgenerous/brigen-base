package com.brightgenerous.zxing.deleg.android;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.zxing.DecodeArguments;
import com.brightgenerous.zxing.EncodeArguments;
import com.google.zxing.LuminanceSource;
import com.google.zxing.common.BitMatrix;

@Deprecated
public class ZxingAndroidUtility {

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
                    log.log(Level.INFO, "does not resolve android.graphics.Bitmap");
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
                    log.log(Level.INFO,
                            "does not resolve android.graphics.Bitmap, android.graphics.Color");
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
            imageCreater = tmp;
            imageCreaterRex = ex;
        }
        RESOLVED = resolvedLSC && resolvedIC;
        USEFUL = (luminanceSourceCreater != null) && (imageCreater != null);
    }

    private ZxingAndroidUtility() {
    }

    public static boolean useful(DecodeArguments args) {
        if (luminanceSourceCreater == null) {
            throw luminanceSourceCreaterRex;
        }
        return luminanceSourceCreater.useful(args);
    }

    public static <T extends DecodeArguments> LuminanceSource createLuminanceSource(T args) {
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

    public static <T> T toImage(BitMatrix bitMatrix, EncodeArguments args) {
        if (imageCreater == null) {
            throw imageCreaterRex;
        }
        return (T) imageCreater.toImage(bitMatrix, args);
    }

    public static void writeToStream(BitMatrix bitMatrix, String format, int quality,
            OutputStream outputStream, EncodeArguments args) throws IOException {
        if (imageCreater == null) {
            throw imageCreaterRex;
        }
        imageCreater.writeToStream(bitMatrix, format, quality, outputStream, args);
    }
}
