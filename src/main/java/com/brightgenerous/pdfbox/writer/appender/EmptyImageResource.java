package com.brightgenerous.pdfbox.writer.appender;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

class EmptyImageResource implements IImageResource {

    @Override
    public PDXObjectImage getImage() throws IOException {
        return null;
    }
}
