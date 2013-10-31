package com.brightgenerous.poi;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.brightgenerous.poi.TestPoiWriter.SheetData;

public class PoiUtilsTest {

    @Test
    public void test() throws Exception {

        final int SIZE = 10;

        List<RowData> rds = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            RowData rd = new RowData();
            rd.setValue1(String.format("row-data-%02d", i));
            rd.setValue2(Long.valueOf(i));
            rd.setValue3(new SimpleDateFormat("yyyy-MM-dd").parse(String.format("2013-01-%02d", i)));
            rds.add(rd);
        }

        // write
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        new TestPoiWriter(new SheetData(rds)).write(bos);

        // read
        List<RowData> loads = new ArrayList<>();
        for (RowData rd : new TestPoiReader(new ByteArrayInputStream(bos.toByteArray())).getLines()) {
            loads.add(rd);
        }

        // compare
        assertEquals(SIZE, rds.size());
        assertEquals(rds.size(), loads.size());

        for (int i = 0; i < rds.size(); i++) {
            assertEquals(rds.get(i).getValue1(), loads.get(i).getValue1());
            assertEquals(rds.get(i).getValue2(), loads.get(i).getValue2());
            assertEquals(rds.get(i).getValue3(), loads.get(i).getValue3());
        }
    }
}
