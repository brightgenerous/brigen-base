package com.brightgenerous.poi;

import static com.brightgenerous.poi.PoiMethods.*;
import static org.apache.poi.ss.usermodel.CellStyle.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.brightgenerous.lang.Args;
import com.brightgenerous.poi.writer.AbstractSheetWriter;
import com.brightgenerous.poi.writer.WorkbookWriterBuilder;

public class TestPoiWriter {

    public static class SheetData implements Serializable {

        private static final long serialVersionUID = 1L;

        private List<RowData> rows;

        public SheetData(List<RowData> rows) {
            this.rows = rows;
        }

        public List<RowData> getRows() {
            return rows;
        }

        public void setRows(List<RowData> rows) {
            this.rows = rows;
        }
    }

    private final SheetData data;

    public TestPoiWriter(SheetData data) {
        Args.notNull(data, "data");

        this.data = data;
    }

    public void write(OutputStream os) throws IOException {
        WorkbookWriterBuilder wwb = WorkbookWriterBuilder.create();
        wwb.addSheet("sheet_hoge", new SheetWriter(data));
        wwb.build().write(os);
    }

    private static class SheetWriter extends AbstractSheetWriter {

        private final SheetData data;

        public SheetWriter(SheetData data) {
            this.data = data;
        }

        @Override
        public void write(Workbook workbook, int index, Sheet sheet, CellStyleRegister reg) {

            final int START_ROW = 0;
            final int START_COL = 0;

            int row = START_ROW;
            int col = START_COL;

            {
                for (int i = 0; i < data.getRows().size(); i++) {

                    col = START_COL;
                    row += 1;

                    RowData rd = data.getRows().get(i);

                    col += addLabelCell(
                            sheet,
                            col,
                            row,
                            reg.getCellStyle(builder().borderBottom(BORDER_THIN)
                                    .borderLeft(BORDER_THIN).borderRight(BORDER_THIN).build()),
                            rd.getValue1());

                    col += addNumberCell(
                            sheet,
                            col,
                            row,
                            reg.getCellStyle(builder().alignment(ALIGN_LEFT)
                                    .borderBottom(BORDER_THIN).borderLeft(BORDER_THIN)
                                    .borderRight(BORDER_THIN).build()), rd.getValue2());

                    col += addDateTimeCell(
                            sheet,
                            col,
                            row,
                            2,
                            reg.getCellStyle(builder().alignment(ALIGN_LEFT)
                                    .borderBottom(BORDER_THIN).borderLeft(BORDER_THIN)
                                    .borderRight(BORDER_THIN).build()), rd.getValue3());
                }
            }
        }

        private CellStyleKeyBuilder builder() {
            CellStyleKeyBuilder ret = new CellStyleKeyBuilder();
            return ret.alignment(ALIGN_CENTER).verticalAlignment(VERTICAL_CENTER)
                    .fontHeight((short) (11 * 20)).fontName("ＭＳ 明朝");
        }
    }
}
