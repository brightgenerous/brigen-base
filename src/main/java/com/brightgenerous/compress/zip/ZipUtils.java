package com.brightgenerous.compress.zip;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.brightgenerous.compress.zip.delegate.ZipUtility;

@SuppressWarnings("deprecation")
public class ZipUtils {

    public static boolean useful() {
        return ZipUtility.USEFUL;
    }

    private ZipUtils() {
    }

    public static void addFile(File zip, File file) throws ZipException {
        ZipUtility.addFile(zip, file);
    }

    public static void addFile(File zip, File file, String password) throws ZipException {
        ZipUtility.addFile(zip, file, password);
    }

    public static void addFiles(File zip, List<File> files) throws ZipException {
        ZipUtility.addFiles(zip, files);
    }

    public static void addFiles(File zip, List<File> files, String password) throws ZipException {
        ZipUtility.addFiles(zip, files, password);
    }

    public static void addFile(File zip, InputStream inputStream, String fileNameInZip)
            throws ZipException {
        ZipUtility.addFile(zip, inputStream, fileNameInZip);
    }

    public static void addFile(File zip, InputStream inputStream, String fileNameInZip,
            String password) throws ZipException {
        ZipUtility.addFile(zip, inputStream, fileNameInZip, password);
    }

    public static void addFolder(File zip, File folder) throws ZipException {
        ZipUtility.addFolder(zip, folder);
    }

    public static void addFolder(File zip, File folder, String password) throws ZipException {
        ZipUtility.addFolder(zip, folder, password);
    }

    public static void extractAll(File zip, File dest) throws ZipException {
        ZipUtility.extractAll(zip, dest);
    }

    public static void extractAll(File zip, File dest, String password) throws ZipException {
        ZipUtility.extractAll(zip, dest, password);
    }

    public static void extract(File zip, String fileName, File dest) throws ZipException {
        ZipUtility.extract(zip, fileName, dest);
    }

    public static void extract(File zip, String fileName, File dest, String password)
            throws ZipException {
        ZipUtility.extract(zip, fileName, dest, password);
    }

    public static InputStream extract(File zip, String fileName) throws ZipException {
        return ZipUtility.extract(zip, fileName);
    }

    public static InputStream extract(File zip, String fileName, String password)
            throws ZipException {
        return ZipUtility.extract(zip, fileName, password);
    }

    public static void remove(File zip, String fileName) throws ZipException {
        ZipUtility.remove(zip, fileName);
    }
}
