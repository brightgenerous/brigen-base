package com.brightgenerous.pdfbox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.brightgenerous.lang.Args;
import com.brightgenerous.pdfbox.deleg.PDFBoxUtility;

@SuppressWarnings("deprecation")
public class PDFBoxUtils {

    public static boolean useful() {
        return PDFBoxUtility.USEFUL;
    }

    private PDFBoxUtils() {
    }

    public static boolean isPdf(byte[] bytes) {
        Args.notNull(bytes, "bytes");

        return isPdf(new ByteArrayInputStream(bytes));
    }

    public static boolean isPdf(String fileName) {
        Args.notNull(fileName, "fileName");

        return isPdf(new File(fileName));
    }

    public static boolean isPdf(File file) {
        Args.notNull(file, "file");

        if (!file.exists()) {
            return false;
        }
        if (!file.canRead()) {
            return false;
        }

        boolean ret = false;
        try {
            ret = isPdf(new FileInputStream(file));
        } catch (FileNotFoundException e) {
        }
        return ret;
    }

    public static boolean isPdf(URL url) {
        Args.notNull(url, "url");

        boolean ret = false;
        try (InputStream inputStream = url.openStream()) {
            ret = isPdf(inputStream);
        } catch (IOException e) {
        }
        return ret;
    }

    public static boolean isPdf(InputStream inputStream) {
        Args.notNull(inputStream, "inputStream");

        return PDFBoxUtility.isPdf(inputStream);
    }
}
