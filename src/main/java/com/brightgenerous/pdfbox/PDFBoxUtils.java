package com.brightgenerous.pdfbox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdfparser.PDFParser;

import com.brightgenerous.lang.Args;

public class PDFBoxUtils {

    private PDFBoxUtils() {
    }

    public static boolean isPdf(byte[] bytes) {
        if (bytes == null) {
            return false;
        }

        return isPdf(new ByteArrayInputStream(bytes));
    }

    public static boolean isPdf(File file) {
        if (file == null) {
            return false;
        }
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

    public static boolean isPdf(InputStream inputStream) {
        Args.notNull(inputStream, "inputStream");

        boolean ret = false;
        try (InputStream is = inputStream) {
            new PDFParser(is).parse();
            ret = true;
        } catch (IOException e) {
        }
        return ret;
    }
}
