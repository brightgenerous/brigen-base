package com.brightgenerous.jxl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.brightgenerous.jxl.delegate.JxlUtility;
import com.brightgenerous.lang.Args;

@Deprecated
public class JxlUtils {

    public static boolean useful() {
        return JxlUtility.USEFUL;
    }

    private JxlUtils() {
    }

    public static boolean canWrap() {
        return useful();
    }

    public static InputStream wrap(byte[] bytes) throws IOException {
        Args.notNull(bytes, "bytes");

        return wrap(new ByteArrayInputStream(bytes));
    }

    public static InputStream wrap(File file) throws IOException {
        Args.notNull(file, "file");

        InputStream ret = null;
        try {
            ret = wrap(new FileInputStream(file));
        } catch (FileNotFoundException e) {
        }
        return ret;
    }

    public static InputStream wrap(InputStream inputStream) throws IOException {
        return JxlUtility.wrap(inputStream);
    }
}
