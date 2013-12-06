package com.brightgenerous.pdfbox.delegate;

import java.io.InputStream;

interface PDFBoxDelegater {

    boolean isPdf(InputStream inputStream);
}
