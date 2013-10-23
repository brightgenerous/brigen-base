package com.brightgenerous.poi.reader;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ISheetReader {

    void read(Workbook workbook, Sheet sheet);
}
