package com.brightgenerous.compress.zip.delegate;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.compress.zip.ZipException;

@Deprecated
public class ZipUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final ZipDelegater delegater;

    private static final RuntimeException rex;

    static {
        ZipDelegater tmp = null;
        boolean useful = false;
        RuntimeException ex = null;
        try {
            tmp = new ZipDelegaterZip4j();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve zip4j");
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
        USEFUL = useful;
        delegater = tmp;
        rex = ex;
    }

    private ZipUtility() {
    }

    private static ZipDelegater getDelegater() {
        if (delegater == null) {
            throw rex;
        }
        return delegater;
    }

    public static void addFile(File zip, File file) throws ZipException {
        getDelegater().addFile(zip, file);
    }

    public static void addFile(File zip, File file, String password) throws ZipException {
        getDelegater().addFile(zip, file, password);
    }

    public static void addFiles(File zip, List<File> files) throws ZipException {
        getDelegater().addFiles(zip, files);
    }

    public static void addFiles(File zip, List<File> files, String password) throws ZipException {
        getDelegater().addFiles(zip, files, password);
    }

    public static void addFile(File zip, InputStream inputStream, String fileNameInZip)
            throws ZipException {
        getDelegater().addFile(zip, inputStream, fileNameInZip);
    }

    public static void addFile(File zip, InputStream inputStream, String fileNameInZip,
            String password) throws ZipException {
        getDelegater().addFile(zip, inputStream, fileNameInZip, password);
    }

    public static void addFolder(File zip, File folder) throws ZipException {
        getDelegater().addFolder(zip, folder);
    }

    public static void addFolder(File zip, File folder, String password) throws ZipException {
        getDelegater().addFolder(zip, folder, password);
    }

    public static void extractAll(File zip, File dest) throws ZipException {
        getDelegater().extractAll(zip, dest);
    }

    public static void extractAll(File zip, File dest, String password) throws ZipException {
        getDelegater().extractAll(zip, dest, password);
    }

    public static void extract(File zip, String fileName, File dest) throws ZipException {
        getDelegater().extract(zip, fileName, dest);
    }

    public static void extract(File zip, String fileName, File dest, String password)
            throws ZipException {
        getDelegater().extract(zip, fileName, dest, password);
    }

    public static InputStream extract(File zip, String fileName) throws ZipException {
        return getDelegater().extract(zip, fileName);
    }

    public static InputStream extract(File zip, String fileName, String password)
            throws ZipException {
        return getDelegater().extract(zip, fileName, password);
    }

    public static void remove(File zip, String fileName) throws ZipException {
        getDelegater().remove(zip, fileName);
    }
}
