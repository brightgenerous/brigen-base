package com.brightgenerous.pdfbox.writer.appender;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public interface IImageResource {

    PDXObjectImage getImage() throws IOException;
}
