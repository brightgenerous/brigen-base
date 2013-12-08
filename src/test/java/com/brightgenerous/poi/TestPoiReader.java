package com.brightgenerous.poi;

import static com.brightgenerous.commons.StringUtils.*;
import static com.brightgenerous.poi.PoiMethods.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.brightgenerous.poi.reader.ISheetCollector;
import com.brightgenerous.poi.reader.IterableSheetCollector;
import com.brightgenerous.poi.reader.WorkbookReaderBuilder;

public class TestPoiReader {

    private ISheetCollector<Iterable<RowData>> rowCollector;

    public TestPoiReader(InputStream inputStream) throws IOException, InvalidFormatException {
        WorkbookReaderBuilder wrb = WorkbookReaderBuilder.create();
        rowCollector = new RowCollector();
        wrb.addSheet(rowCollector);
        wrb.build().read(inputStream);
    }

    public Iterable<RowData> getLines() {
        return rowCollector.get();
    }

    private static class RowCollector extends IterableSheetCollector<RowData> {

        @Override
        protected RowData rowToData(int r, int index, Row row) {
            if (!isDataRow(row)) {
                return null;
            }
            RowData ret = new RowData();
            ret.setValue1(stripToEmpty(getStringValue(getCell(row, 0))));
            ret.setValue2(Long.valueOf(getNumericValue(getCell(row, 1)).longValue()));
            ret.setValue3(getDateTimeValue(getCell(row, 2)));
            return ret;
        }
    }

    private static boolean isDataRow(Row row) {
        if (row == null) {
            return false;
        }
        Cell cell = row.getCell(0, Row.RETURN_BLANK_AS_NULL);
        if (cell == null) {
            return false;
        }
        return true;
    }
}
