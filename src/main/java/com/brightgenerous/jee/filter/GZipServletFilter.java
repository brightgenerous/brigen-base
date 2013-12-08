package com.brightgenerous.jee.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPOutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GZipServletFilter extends SimpleFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (acceptsGZipEncoding(httpRequest)) {
            httpResponse.addHeader("Content-Encoding", "gzip");
            GZipServletResponseWrapper gzipResponse = new GZipServletResponseWrapper(httpResponse);
            chain.doFilter(request, gzipResponse);
            gzipResponse.close();
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean acceptsGZipEncoding(HttpServletRequest httpRequest) {
        String acceptEncoding = httpRequest.getHeader("Accept-Encoding");
        return (acceptEncoding != null) && (acceptEncoding.indexOf("gzip") != -1);
    }
}

class GZipServletResponseWrapper extends HttpServletResponseWrapper {

    private GZipServletOutputStream gzipOutputStream;

    private PrintWriter printWriter;

    public GZipServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void close() throws IOException {

        //PrintWriter.close does not throw exceptions. Thus, the call does not need
        //be inside a try-catch block.
        if (printWriter != null) {
            printWriter.close();
        }

        if (gzipOutputStream != null) {
            gzipOutputStream.close();
        }
    }

    @Override
    public void flushBuffer() throws IOException {

        //PrintWriter.flush() does not throw exception
        if (printWriter != null) {
            printWriter.flush();
        }

        IOException exception1 = null;
        try {
            if (gzipOutputStream != null) {
                gzipOutputStream.flush();
            }
        } catch (IOException e) {
            exception1 = e;
        }

        IOException exception2 = null;
        try {
            super.flushBuffer();
        } catch (IOException e) {
            exception2 = e;
        }

        if (exception1 != null) {
            throw exception1;
        }
        if (exception2 != null) {
            throw exception2;
        }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (printWriter != null) {
            throw new IllegalStateException(
                    "PrintWriter obtained already - cannot get OutputStream");
        }
        if (gzipOutputStream == null) {
            gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
        }
        return gzipOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if ((printWriter == null) && (gzipOutputStream != null)) {
            throw new IllegalStateException(
                    "OutputStream obtained already - cannot get PrintWriter");
        }
        if (printWriter == null) {
            gzipOutputStream = new GZipServletOutputStream(getResponse().getOutputStream());
            printWriter = new PrintWriter(new OutputStreamWriter(gzipOutputStream, getResponse()
                    .getCharacterEncoding()));
        }
        return printWriter;
    }

    @Override
    public void setContentLength(int len) {
        //ignore, since content length of zipped content
        //does not match content length of unzipped content.
    }
}

class GZipServletOutputStream extends ServletOutputStream {

    private final GZIPOutputStream gzipStream;

    private final AtomicBoolean open = new AtomicBoolean(true);

    public GZipServletOutputStream(OutputStream output) throws IOException {
        gzipStream = new GZIPOutputStream(output);
    }

    @Override
    public void close() throws IOException {
        if (open.compareAndSet(true, false)) {
            gzipStream.close();
        }
    }

    @Override
    public void flush() throws IOException {
        gzipStream.flush();
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (!open.get()) {
            throw new IOException("Stream closed!");
        }
        gzipStream.write(b, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        if (!open.get()) {
            throw new IOException("Stream closed!");
        }
        gzipStream.write(b);
    }
}
