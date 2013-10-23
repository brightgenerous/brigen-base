package com.brightgenerous.poi.writer;

import java.util.Iterator;
import java.util.List;

import com.brightgenerous.lang.Args;

public class WorkbookWriterStrategy implements IWorkbookWriterStrategy {

    private final List<ISheetWriterStrategy> sheetStrategys;

    private final ISheetWriterStrategy emptySheetStrategy;

    private final boolean xlsxFlag;

    public WorkbookWriterStrategy(List<ISheetWriterStrategy> sheetStrategys,
            ISheetWriterStrategy emptySheetStrategy, boolean xlsxFlag) {
        Args.notNull(sheetStrategys, "sheetStrategys");
        Args.notNull(emptySheetStrategy, "emptySheetStrategy");

        this.sheetStrategys = sheetStrategys;
        this.emptySheetStrategy = emptySheetStrategy;
        this.xlsxFlag = xlsxFlag;
    }

    @Override
    public Iterator<ISheetWriterStrategy> getSheetStrategys() {
        return sheetStrategys.iterator();
    }

    @Override
    public ISheetWriterStrategy getEmptySheetStrategy() {
        return emptySheetStrategy;
    }

    @Override
    public boolean getXlsxFlag() {
        return xlsxFlag;
    }
}
