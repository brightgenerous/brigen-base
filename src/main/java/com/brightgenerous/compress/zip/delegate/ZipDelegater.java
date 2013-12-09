package com.brightgenerous.compress.zip.delegate;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.brightgenerous.compress.zip.DeflateLevel;
import com.brightgenerous.compress.zip.ZipException;

interface ZipDelegater {

    void addFile(File zip, File file) throws ZipException;

    void addFile(File zip, File file, DeflateLevel defLevel) throws ZipException;

    void addFile(File zip, File file, String password) throws ZipException;

    void addFile(File zip, File file, String password, DeflateLevel defLevel) throws ZipException;

    void addFiles(File zip, List<File> files) throws ZipException;

    void addFiles(File zip, List<File> files, DeflateLevel defLevel) throws ZipException;

    void addFiles(File zip, List<File> files, String password) throws ZipException;

    void addFiles(File zip, List<File> files, String password, DeflateLevel defLevel)
            throws ZipException;

    void addFile(File zip, InputStream inputStream, String fileNameInZip) throws ZipException;

    void addFile(File zip, InputStream inputStream, String fileNameInZip, DeflateLevel defLevel)
            throws ZipException;

    void addFile(File zip, InputStream inputStream, String fileNameInZip, String password)
            throws ZipException;

    void addFile(File zip, InputStream inputStream, String fileNameInZip, String password,
            DeflateLevel defLevel) throws ZipException;

    void addFolder(File zip, File folder) throws ZipException;

    void addFolder(File zip, File folder, DeflateLevel defLevel) throws ZipException;

    void addFolder(File zip, File folder, String password) throws ZipException;

    void addFolder(File zip, File folder, String password, DeflateLevel defLevel)
            throws ZipException;

    void extractAll(File zip, File dest) throws ZipException;

    void extractAll(File zip, File dest, String password) throws ZipException;

    void extract(File zip, String fileName, File dest) throws ZipException;

    void extract(File zip, String fileName, File dest, String password) throws ZipException;

    InputStream extract(File zip, String fileName) throws ZipException;

    InputStream extract(File zip, String fileName, String password) throws ZipException;

    void remove(File zip, String fileName) throws ZipException;
}
