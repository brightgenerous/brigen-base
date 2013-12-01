package com.brightgenerous.pdfbox.deleg;

import java.io.InputStream;

interface PDFBoxDelegater {

    boolean isPdf(InputStream inputStream);
}
