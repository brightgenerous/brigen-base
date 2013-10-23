package com.brightgenerous.jxl.delegate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.FormulaCell;
import jxl.NumberFormulaCell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.formula.FormulaException;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

class JxlDelegaterImpl implements JxlDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(jxl.Cell.class.getName());
            Class.forName(jxl.FormulaCell.class.getName());
            Class.forName(jxl.NumberFormulaCell.class.getName());
            Class.forName(jxl.Workbook.class.getName());
            Class.forName(jxl.WorkbookSettings.class.getName());
            Class.forName(jxl.biff.formula.FormulaException.class.getName());
            Class.forName(jxl.read.biff.BiffException.class.getName());
            Class.forName(jxl.write.Label.class.getName());
            Class.forName(jxl.write.WritableSheet.class.getName());
            Class.forName(jxl.write.WritableWorkbook.class.getName());
            Class.forName(jxl.write.WriteException.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream wrap(InputStream inputStream) throws IOException {
        return wrap(inputStream, true);
    }

    public InputStream wrap(InputStream inputStream, boolean defaultIsEmpty) throws IOException {
        ByteArrayInputStream ret;
        Workbook workbook = null;
        try {
            workbook = jxl.Workbook.getWorkbook(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            WritableWorkbook writableWorkbook = null;
            try {
                WorkbookSettings settings = new WorkbookSettings();
                settings.setWriteAccess(null);
                writableWorkbook = Workbook.createWorkbook(baos, workbook, settings);
                for (WritableSheet sheet : writableWorkbook.getSheets()) {
                    final int rows = sheet.getRows();
                    for (int r = 0; r < rows; r++) {
                        Cell[] cells = sheet.getRow(r);
                        final int cols = cells.length;
                        for (int c = 0; c < cols; c++) {
                            Cell cell = cells[c];
                            String contents = cell.getContents();
                            if (cell instanceof FormulaCell) {
                                boolean empty = false;
                                if (cell instanceof NumberFormulaCell) {
                                    if ((contents == null) || (contents.length() == 0)
                                            || (defaultIsEmpty && contents.equals("0"))) {
                                        empty = true;
                                    }
                                } else {
                                    if ((contents == null) || (contents.length() == 0)) {
                                        empty = true;
                                    }
                                }
                                if (empty) {
                                    try {
                                        contents = ((FormulaCell) cell).getFormula();
                                    } catch (FormulaException e) {
                                    }
                                }
                                if ((contents != null) && (0 < contents.length())) {
                                    try {
                                        sheet.addCell(new Label(c, r, contents));
                                    } catch (WriteException e) {
                                    }
                                }
                            }
                        }
                    }
                }
                writableWorkbook.write();
            } finally {
                if (writableWorkbook != null) {
                    try {
                        writableWorkbook.close();
                    } catch (WriteException e) {
                    }
                }
            }
            ret = new ByteArrayInputStream(baos.toByteArray());
        } catch (BiffException e) {
            throw new IOException(e);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return ret;
    }
}
