package com.brightgenerous.poi.reader;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ISheetCollector<T> {

    void bind(Workbook workbook, Sheet sheet);

    Workbook getWorkbook();

    Sheet getSheet();

    T get();
}
