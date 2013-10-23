package com.brightgenerous.poi.reader;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class IndexSheetSelector implements ISheetSelector {

    private final int index;

    public IndexSheetSelector(int index) {
        this.index = index;
    }

    @Override
    public Sheet select(Workbook workbook) {
        return workbook.getSheetAt(index);
    }
}
