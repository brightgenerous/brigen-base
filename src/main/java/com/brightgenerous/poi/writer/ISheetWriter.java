package com.brightgenerous.poi.writer;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.brightgenerous.poi.CellStyleRegister;

public interface ISheetWriter {

    void write(Workbook workbook, int index, Sheet sheet, CellStyleRegister register);

    int getIndex();
}
