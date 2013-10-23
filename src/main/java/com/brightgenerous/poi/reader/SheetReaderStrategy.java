package com.brightgenerous.poi.reader;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.brightgenerous.lang.Args;

public class SheetReaderStrategy implements ISheetReaderStrategy {

    private final ISheetSelector selector;

    private final ISheetReader reader;

    public SheetReaderStrategy(ISheetSelector selector, ISheetReader reader) {
        Args.notNull(selector, "selector");
        Args.notNull(reader, "reader");

        this.selector = selector;
        this.reader = reader;
    }

    @Override
    public Sheet getSheet(Workbook workbook) {
        return selector.select(workbook);
    }

    @Override
    public ISheetReader getReader() {
        return reader;
    }
}
