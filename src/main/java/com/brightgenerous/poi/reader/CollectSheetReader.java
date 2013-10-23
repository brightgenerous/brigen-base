package com.brightgenerous.poi.reader;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class CollectSheetReader implements ISheetReader {

    private final ISheetCollector<?>[] collectors;

    public CollectSheetReader(ISheetCollector<?>... collectors) {
        this.collectors = collectors;
    }

    @Override
    public void read(Workbook workbook, Sheet sheet) {
        if ((collectors != null) && (0 < collectors.length)) {
            for (ISheetCollector<?> collector : collectors) {
                if (collector != null) {
                    collector.bind(workbook, sheet);
                }
            }
        }
    }
}
