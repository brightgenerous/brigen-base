package com.brightgenerous.poi.reader;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public interface IWorkbookReader {

    void read(InputStream is) throws IOException, InvalidFormatException;
}
