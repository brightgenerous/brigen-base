package com.brightgenerous.poi.writer;

import java.io.IOException;
import java.io.OutputStream;

public interface IWorkbookWriter {

    void write(OutputStream os) throws IOException;
}
