package com.brightgenerous.compress.zip.delegate;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import com.brightgenerous.compress.zip.DeflateLevel;
import com.brightgenerous.compress.zip.ZipException;

class ZipDelegaterZip4j implements ZipDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(ZipFile.class.getName());
            Class.forName(FileHeader.class.getName());
            Class.forName(ZipParameters.class.getName());
            Class.forName(Zip4jConstants.class.getName());
            Class.forName(net.lingala.zip4j.exception.ZipException.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addFile(File zip, File file) throws ZipException {
        addFile(zip, file, null, null);
    }

    @Override
    public void addFile(File zip, File file, DeflateLevel defLevel) throws ZipException {
        addFile(zip, file, null, defLevel);
    }

    @Override
    public void addFile(File zip, File file, String password) throws ZipException {
        addFile(zip, file, password, null);
    }

    @Override
    public void addFile(File zip, File file, String password, DeflateLevel defLevel)
            throws ZipException {
        List<File> files = new ArrayList<>();
        files.add(file);
        addFiles(zip, files, password, defLevel);
    }

    @Override
    public void addFiles(File zip, List<File> files) throws ZipException {
        addFiles(zip, files, null, null);
    }

    @Override
    public void addFiles(File zip, List<File> files, DeflateLevel defLevel) throws ZipException {
        addFiles(zip, files, null, defLevel);
    }

    @Override
    public void addFiles(File zip, List<File> files, String password) throws ZipException {
        addFiles(zip, files, password, null);
    }

    @Override
    public void addFiles(File zip, List<File> files, String password, DeflateLevel defLevel)
            throws ZipException {
        try {
            ZipFile zipFile = new ZipFile(zip);
            zipFile.addFiles(new ArrayList<>(files), getParams(password, null, defLevel));
        } catch (net.lingala.zip4j.exception.ZipException e) {
            throw new ZipException(e);
        }
    }

    @Override
    public void addFile(File zip, InputStream inputStream, String fileNameInZip)
            throws ZipException {
        addFile(zip, inputStream, fileNameInZip, null, null);
    }

    @Override
    public void addFile(File zip, InputStream inputStream, String fileNameInZip,
            DeflateLevel defLevel) throws ZipException {
        addFile(zip, inputStream, fileNameInZip, null, defLevel);
    }

    @Override
    public void addFile(File zip, InputStream inputStream, String fileNameInZip, String password)
            throws ZipException {
        addFile(zip, inputStream, fileNameInZip, password, null);
    }

    @Override
    public void addFile(File zip, InputStream inputStream, String fileNameInZip, String password,
            DeflateLevel defLevel) throws ZipException {
        try {
            ZipFile zipFile = new ZipFile(zip);
            zipFile.addStream(inputStream, getParams(password, fileNameInZip, defLevel));
        } catch (net.lingala.zip4j.exception.ZipException e) {
            throw new ZipException(e);
        }
    }

    @Override
    public void addFolder(File zip, File folder) throws ZipException {
        addFolder(zip, folder, null, null);
    }

    @Override
    public void addFolder(File zip, File folder, DeflateLevel defLevel) throws ZipException {
        addFolder(zip, folder, null, defLevel);
    }

    @Override
    public void addFolder(File zip, File folder, String password) throws ZipException {
        addFile(zip, folder, password, null);
    }

    @Override
    public void addFolder(File zip, File folder, String password, DeflateLevel defLevel)
            throws ZipException {
        try {
            ZipFile zipFile = new ZipFile(zip);
            zipFile.addFolder(folder, getParams(password, null, defLevel));
        } catch (net.lingala.zip4j.exception.ZipException e) {
            throw new ZipException(e);
        }
    }

    protected ZipParameters getParams(String password, String fileNameInZip, DeflateLevel defLevel) {
        ZipParameters params = new ZipParameters();
        {
            int dl;
            if (defLevel == null) {
                dl = Zip4jConstants.DEFLATE_LEVEL_NORMAL;
            } else {
                switch (defLevel) {
                    case FASTEST:
                        dl = Zip4jConstants.DEFLATE_LEVEL_FASTEST;
                        break;
                    case FAST:
                        dl = Zip4jConstants.DEFLATE_LEVEL_FAST;
                        break;
                    case NORMAL:
                        dl = Zip4jConstants.DEFLATE_LEVEL_NORMAL;
                        break;
                    case MAXIMUM:
                        dl = Zip4jConstants.DEFLATE_LEVEL_MAXIMUM;
                        break;
                    case ULTRA:
                        dl = Zip4jConstants.DEFLATE_LEVEL_ULTRA;
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
            params.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            params.setCompressionLevel(dl);
        }
        if (fileNameInZip != null) {
            params.setSourceExternalStream(true);
            params.setFileNameInZip(fileNameInZip);
        }
        if (password != null) {
            params.setEncryptFiles(true);
            params.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            params.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            params.setPassword(password);
        }
        return params;
    }

    @Override
    public void extractAll(File zip, File destination) throws ZipException {
        extractAll(zip, destination, null);
    }

    @Override
    public void extractAll(File zip, File destination, String password) throws ZipException {
        try {
            ZipFile zipFile = new ZipFile(zip);
            if ((password != null) && zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination.getAbsolutePath());
        } catch (net.lingala.zip4j.exception.ZipException e) {
            throw new ZipException(e);
        }
    }

    @Override
    public void extract(File zip, String fileName, File destination) throws ZipException {
        extract(zip, fileName, destination, null);
    }

    @Override
    public void extract(File zip, String fileName, File destination, String password)
            throws ZipException {
        try {
            ZipFile zipFile = new ZipFile(zip);
            if ((password != null) && zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractFile(fileName, destination.getAbsolutePath());
        } catch (net.lingala.zip4j.exception.ZipException e) {
            throw new ZipException(e);
        }
    }

    @Override
    public InputStream extract(File zip, String fileName) throws ZipException {
        return extract(zip, fileName, (String) null);
    }

    @Override
    public InputStream extract(File zip, String fileName, String password) throws ZipException {
        try {
            ZipFile zipFile = new ZipFile(zip);
            if ((password != null) && zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            FileHeader fh = zipFile.getFileHeader(fileName);
            return zipFile.getInputStream(fh);
        } catch (net.lingala.zip4j.exception.ZipException e) {
            throw new ZipException(e);
        }
    }

    @Override
    public void remove(File zip, String fileName) throws ZipException {
        try {
            ZipFile zipFile = new ZipFile(zip);
            zipFile.removeFile(fileName);
        } catch (net.lingala.zip4j.exception.ZipException e) {
            throw new ZipException(e);
        }
    }
}
