package com.brightgenerous.poi.delegate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.brightgenerous.poi.PoiMethods;

class PoiDelegaterImpl implements PoiDelegater {

    @Override
    public boolean isExcel(File file) throws IOException {
        try {
            PoiMethods.readIfWrap(file);
            return true;
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }

    }

    @Override
    public boolean isExcel(InputStream inputStream) throws IOException {
        try {
            PoiMethods.readIfWrap(inputStream);
            return true;
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean isXls(File file) throws IOException {
        try {
            return PoiMethods.readIfWrap(file) instanceof HSSFWorkbook;
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean isXls(InputStream inputStream) throws IOException {
        try {
            return PoiMethods.readIfWrap(inputStream) instanceof HSSFWorkbook;
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean isXlsx(File file) throws IOException {
        try {
            return PoiMethods.readIfWrap(file) instanceof XSSFWorkbook;
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean isXlsx(InputStream inputStream) throws IOException {
        try {
            return PoiMethods.readIfWrap(inputStream) instanceof XSSFWorkbook;
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }
    }
}
