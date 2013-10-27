package com.brightgenerous.zxing;

import java.io.IOException;
import java.io.OutputStream;

public interface ZxingEncoder {

    <T> T encode() throws IOException;

    <T> T encode(EncodeArguments args) throws IOException;

    void encode(OutputStream outputStream) throws IOException;

    void encode(OutputStream outputStream, EncodeArguments args) throws IOException;
}
