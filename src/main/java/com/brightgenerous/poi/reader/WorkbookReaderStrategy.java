package com.brightgenerous.poi.reader;

import java.util.Iterator;
import java.util.List;

import com.brightgenerous.lang.Args;

public class WorkbookReaderStrategy implements IWorkbookReaderStrategy {

    private final List<ISheetReaderStrategy> sheetStrategys;

    public WorkbookReaderStrategy(List<ISheetReaderStrategy> sheetStrategys) {
        Args.notNull(sheetStrategys, "sheetStrategys");

        this.sheetStrategys = sheetStrategys;
    }

    @Override
    public Iterator<ISheetReaderStrategy> getSheetStrategys() {
        return sheetStrategys.iterator();
    }
}
