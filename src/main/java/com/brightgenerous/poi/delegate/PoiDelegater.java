package com.brightgenerous.poi.delegate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

interface PoiDelegater {

    boolean isExcel(File file) throws IOException;

    boolean isExcel(InputStream inputStream) throws IOException;

    boolean isXls(File file) throws IOException;

    boolean isXls(InputStream inputStream) throws IOException;

    boolean isXlsx(File file) throws IOException;

    boolean isXlsx(InputStream inputStream) throws IOException;
}
