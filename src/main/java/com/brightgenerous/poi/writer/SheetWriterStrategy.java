package com.brightgenerous.poi.writer;

import org.apache.poi.ss.usermodel.Workbook;

import com.brightgenerous.lang.Args;
import com.brightgenerous.poi.FormatterRegister;

public class SheetWriterStrategy implements ISheetWriterStrategy {

    private final String sheetName;

    private final ISheetWriter sheetWriter;

    private final boolean convertToString;

    private final FormatterRegister formatterRegister;

    public SheetWriterStrategy(String sheetName, ISheetWriter sheetWriter) {
        this(sheetName, sheetWriter, false, null);
    }

    public SheetWriterStrategy(String sheetName, ISheetWriter sheetWriter, boolean convertToString,
            FormatterRegister formatterRegister) {
        Args.notNull(sheetName, "sheetName");
        Args.notNull(sheetWriter, "sheetWriter");

        this.sheetName = sheetName;
        this.sheetWriter = sheetWriter;
        this.convertToString = convertToString;
        this.formatterRegister = formatterRegister;
    }

    @Override
    public String getSheetName(Workbook workbook, int index) {
        return sheetName;
    }

    @Override
    public ISheetWriter getWriter() {
        return sheetWriter;
    }

    @Override
    public boolean getConvertToString() {
        return convertToString;
    }

    @Override
    public FormatterRegister getFormatterRegister() {
        return formatterRegister;
    }
}
