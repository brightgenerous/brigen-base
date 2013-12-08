package com.brightgenerous.poi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.brightgenerous.poi.delegate.PoiUtility;

@SuppressWarnings("deprecation")
public class PoiUtils {

    public static boolean useful() {
        return PoiUtility.USEFUL;
    }

    private PoiUtils() {
    }

    public static boolean isExcel(File file) throws IOException {
        return PoiUtility.isExcel(file);
    }

    public static boolean isExcel(InputStream inputStream) throws IOException {
        return PoiUtility.isExcel(inputStream);
    }

    public static boolean isXls(File file) throws IOException {
        return PoiUtility.isXls(file);
    }

    public static boolean isXls(InputStream inputStream) throws IOException {
        return PoiUtility.isXls(inputStream);
    }

    public static boolean isXlsx(File file) throws IOException {
        return PoiUtility.isXlsx(file);
    }

    public static boolean isXlsx(InputStream inputStream) throws IOException {
        return PoiUtility.isXlsx(inputStream);
    }
}
