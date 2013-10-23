package com.brightgenerous.poi.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.brightgenerous.lang.Args;
import com.brightgenerous.poi.PoiUtils;

public class WorkbookReader implements IWorkbookReader {

    private final IWorkbookReaderStrategy workbookStrategy;

    public WorkbookReader(IWorkbookReaderStrategy workbookStrategy) {
        Args.notNull(workbookStrategy, "workbookStrategy");

        this.workbookStrategy = workbookStrategy;
    }

    protected IWorkbookReaderStrategy getWorkbookStrategy() {
        return workbookStrategy;
    }

    @Override
    public void read(InputStream is) throws IOException, InvalidFormatException {
        Workbook workbook;
        try (InputStream stream = is) {
            workbook = PoiUtils.readIfWrap(is);
        }
        IWorkbookReaderStrategy strategy = getWorkbookStrategy();
        Iterator<ISheetReaderStrategy> itr = strategy.getSheetStrategys();
        while (itr.hasNext()) {
            ISheetReaderStrategy sheetStrategy = itr.next();
            readSheet(workbook, sheetStrategy);
        }
    }

    protected void readSheet(Workbook workbook, ISheetReaderStrategy sheetStrategy) {
        Sheet sheet = sheetStrategy.getSheet(workbook);
        sheetStrategy.getReader().read(workbook, sheet);
    }
}
