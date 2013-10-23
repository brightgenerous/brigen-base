package com.brightgenerous.poi.writer;

import org.apache.poi.ss.usermodel.Workbook;

import com.brightgenerous.poi.FormatterRegister;

public interface ISheetWriterStrategy {

    String getSheetName(Workbook workbook, int index);

    ISheetWriter getWriter();

    boolean getConvertToString();

    FormatterRegister getFormatterRegister();
}
