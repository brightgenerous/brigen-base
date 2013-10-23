package com.brightgenerous.poi.reader;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ISheetReaderStrategy {

    Sheet getSheet(Workbook workbook);

    ISheetReader getReader();
}
