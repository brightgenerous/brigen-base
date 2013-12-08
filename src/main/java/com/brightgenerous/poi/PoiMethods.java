package com.brightgenerous.poi;

import static com.brightgenerous.commons.StringUtils.*;
import static org.apache.poi.ss.usermodel.Cell.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import com.brightgenerous.commons.DateUtils;
import com.brightgenerous.jxl.JxlUtils;
import com.brightgenerous.lang.Args;

@SuppressWarnings("deprecation")
public class PoiMethods {

    private PoiMethods() {
    }

    public static Workbook read(File file) throws InvalidFormatException, IOException {
        return read(file, false);
    }

    public static Workbook readIfWrap(File file) throws InvalidFormatException, IOException {
        return read(file, true);
    }

    private static Workbook read(File file, boolean ifWrap) throws InvalidFormatException,
            IOException {
        Args.notNull(file, "file");

        try {
            return WorkbookFactory.create(file);
        } catch (InvalidFormatException e) {
            if (!ifWrap || !JxlUtils.canWrap()) {
                throw e;
            }

            Logger.getAnonymousLogger().log(Level.INFO,
                    "can not create Workbook from File, using poi", e);

            // rarely, it may be possible to read through JXL.
            return WorkbookFactory.create(JxlUtils.wrap(file));
        }
    }

    public static Workbook read(InputStream inputStream) throws InvalidFormatException, IOException {
        return read(inputStream, false);
    }

    public static Workbook readIfWrap(InputStream inputStream) throws InvalidFormatException,
            IOException {
        return read(inputStream, true);
    }

    private static Workbook read(InputStream inputStream, boolean ifWrap)
            throws InvalidFormatException, IOException {
        Args.notNull(inputStream, "inputStream");

        try {
            return WorkbookFactory.create(inputStream);
        } catch (InvalidFormatException e) {
            if (!ifWrap || !JxlUtils.canWrap()) {
                throw e;
            }

            Logger.getAnonymousLogger().log(Level.INFO,
                    "can not create Workbook from InputStream, using poi api.", e);

            // rarely, it may be possible to read through JXL.
            return WorkbookFactory.create(JxlUtils.wrap(inputStream));
        } catch (IllegalArgumentException e) {

            Logger.getAnonymousLogger().log(Level.INFO,
                    "can not create Workbook from InputStream, using poi api.", e);

            throw new IOException(e);
        }
    }

    public static String escapeSheetName(String sheetName) {
        return escapeSheetName(sheetName, null);
    }

    public static String escapeSheetName(String sheetName, String replace) {
        if (sheetName == null) {
            return null;
        }
        String rep;
        if ((replace == null) || (replace.matches(".*[\\s　:\\\\?\\[\\]/*：￥？［］／＊]{1}.*"))) {
            rep = "_";
        } else {
            rep = replace;
        }
        return sheetName.replaceAll("[\\s　:\\\\?\\[\\]/*：￥？［］／＊]", rep);
    }

    public static String escapeString(String str) {
        if (str == null) {
            return null;
        }
        return str.replace("\"", "\"\"");
    }

    // TODO
    // vague calculation...
    public static int getColumnWidth(double halfSizeWidth, short fontHeight) {
        return (int) ((halfSizeWidth + 2.5) * 256);
    }

    public static short getLineHeight(short fontHeight) {
        return (short) (fontHeight * 1.275);
    }

    public static String getSelfCellFormula() {
        return getRelativeCellFormula(0, 0);
    }

    public static String getRelativeCellFormula(int col, int row) {
        return getRelativeCellFormula(col, row, null);
    }

    public static String getRelativeCellFormula(int col, int row, String sheetName) {
        if (isEmpty(sheetName)) {
            return String.format("INDIRECT(ADDRESS(%d,%d,4,0),FALSE)", Integer.valueOf(row),
                    Integer.valueOf(col));
        }
        return String.format("INDIRECT(ADDRESS(%d,%d,4,0,\"%s\"),FALSE)", Integer.valueOf(row),
                Integer.valueOf(col), escapeString(sheetName));
    }

    public static String getAbsoluteCellFormula(int col, int row) {
        return getAbsoluteCellFormula(col, row, null);
    }

    public static String getAbsoluteCellFormula(int col, int row, String sheetName) {
        if (isEmpty(sheetName)) {
            return String.format("INDIRECT(ADDRESS(%d,%d))", Integer.valueOf(row + 1),
                    Integer.valueOf(col + 1));
        }
        return String.format("INDIRECT(ADDRESS(%d,%d,,,\"%s\"))", Integer.valueOf(row + 1),
                Integer.valueOf(col + 1), escapeString(sheetName));
    }

    public static String getAbsoluteCellRangeFormula(int startCol, int startRow, int endCol,
            int endRow, String sheetName) {
        if (isEmpty(sheetName)) {
            return String.format("INDIRECT(ADDRESS(%d,%d)&\":\"&ADDRESS(%d,%d))",
                    Integer.valueOf(startRow + 1), Integer.valueOf(startCol + 1),
                    Integer.valueOf(endRow + 1), Integer.valueOf(endCol + 1));
        }
        return String.format("INDIRECT(ADDRESS(%d,%d,,,\"%s\")&\":\"&ADDRESS(%d,%d))",
                Integer.valueOf(startRow + 1), Integer.valueOf(startCol + 1),
                escapeString(sheetName), Integer.valueOf(endRow + 1), Integer.valueOf(endCol + 1));
    }

    public static String getColumnCharacter(int col) {
        Args.withinRange(0, 16383, col, "col");

        if (col < 26) {
            char c1 = (char) ('A' + col);
            return String.format("%c", Character.valueOf(c1));
        } else if (col < (26 * 27)) {
            char c1 = (char) (('A' + (col / 26)) - 1);
            char c2 = (char) ('A' + (col % 26));
            return String.format("%c%c", Character.valueOf(c1), Character.valueOf(c2));
        }
        char c1 = (char) ('A' + ((((col / 26) - 1) / 26) - 1));
        char c2 = (char) ('A' + (((col / 26) - 1) % 26));
        char c3 = (char) ('A' + (col % 26));
        return String.format("%c%c%c", Character.valueOf(c1), Character.valueOf(c2),
                Character.valueOf(c3));
    }

    public static String getRowString(int row) {
        Args.withinRange(0, 1048575, row, "row");

        return String.valueOf(row + 1);
    }

    public static String getCellString(int col, int row) {
        return getColumnCharacter(col) + getRowString(row);
    }

    public static double convertCmToInchi(double cm) {
        return cm * 0.3937;
    }

    public static double convertInchiToCm(double inchi) {
        return inchi / 0.3937;
    }

    public static Double convertTimeToDouble(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return convertTimeToDouble(calendar);
    }

    public static Double convertTimeToDouble(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return Double.valueOf((((double) hours * 60) + minutes) / (24 * 60));
    }

    public static Cell getCell(Row row, int col) {
        return getCell(row, col, Row.RETURN_NULL_AND_BLANK);
    }

    public static Cell getCell(Row row, int col, MissingCellPolicy mcp) {
        if (row == null) {
            return null;
        }
        return row.getCell(col, mcp);
    }

    public static void setRowHeight(Sheet sheet, int row, short height) {
        if (sheet == null) {
            return;
        }
        Row r = sheet.getRow(row);
        if (r == null) {
            r = sheet.createRow(row);
        }
        r.setHeight(height);
    }

    public static CellStyleRegister newCellStyleRegister(Workbook workbook) {
        return new CellStyleRegister(workbook);
    }

    public static int addBlankCell(Sheet sheet, int col, int row) {
        return addBlankCell(sheet, col, row, 1);
    }

    public static int addBlankCell(Sheet sheet, int col, int row, int width) {
        return addBlankCell(sheet, col, row, width, 1);
    }

    public static int addBlankCell(Sheet sheet, int col, int row, int width, int height) {
        return addBlankCell(sheet, col, row, width, height, null);
    }

    public static int addBlankCell(Sheet sheet, int col, int row, CellStyle cellStyle) {
        return addBlankCell(sheet, col, row, 1, cellStyle);
    }

    public static int addBlankCell(Sheet sheet, int col, int row, int width, CellStyle cellStyle) {
        return addBlankCell(sheet, col, row, width, 1, cellStyle);
    }

    public static int addBlankCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle) {
        return addBlankCell(sheet, col, row, width, height, cellStyle, null);
    }

    public static int addBlankCell(Sheet sheet, int col, int row, CellStyle cellStyle,
            Hyperlink link) {
        return addBlankCell(sheet, col, row, 1, cellStyle, link);
    }

    public static int addBlankCell(Sheet sheet, int col, int row, int width, CellStyle cellStyle,
            Hyperlink link) {
        return addBlankCell(sheet, col, row, width, 1, cellStyle, link);
    }

    public static int addBlankCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, Hyperlink link) {
        createCell(sheet, col, row, width, height, cellStyle, CELL_TYPE_BLANK, link);
        return width;
    }

    public static int addBooleanCell(Sheet sheet, int col, int row, Boolean value) {
        return addBooleanCell(sheet, col, row, 1, value);
    }

    public static int addBooleanCell(Sheet sheet, int col, int row, int width, Boolean value) {
        return addBooleanCell(sheet, col, row, width, 1, value);
    }

    public static int addBooleanCell(Sheet sheet, int col, int row, int width, int height,
            Boolean value) {
        return addBooleanCell(sheet, col, row, width, height, null, value);
    }

    public static int addBooleanCell(Sheet sheet, int col, int row, CellStyle cellStyle,
            Boolean value) {
        return addBooleanCell(sheet, col, row, 1, cellStyle, value);
    }

    public static int addBooleanCell(Sheet sheet, int col, int row, int width, CellStyle cellStyle,
            Boolean value) {
        return addBooleanCell(sheet, col, row, width, 1, cellStyle, value);
    }

    public static int addBooleanCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, Boolean value) {
        return addBooleanCell(sheet, col, row, width, height, cellStyle, value, null);
    }

    public static int addBooleanCell(Sheet sheet, int col, int row, CellStyle cellStyle,
            Boolean value, Hyperlink link) {
        return addBooleanCell(sheet, col, row, 1, cellStyle, value, link);
    }

    public static int addBooleanCell(Sheet sheet, int col, int row, int width, CellStyle cellStyle,
            Boolean value, Hyperlink link) {
        return addBooleanCell(sheet, col, row, width, 1, cellStyle, value, link);
    }

    public static int addBooleanCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, Boolean value, Hyperlink link) {
        if (value == null) {
            return addBlankCell(sheet, col, row, width, height, cellStyle, link);
        }
        Cell cell = createCell(sheet, col, row, width, height, cellStyle, CELL_TYPE_BOOLEAN, link);
        cell.setCellValue(value.booleanValue());
        return width;
    }

    public static int addLabelCell(Sheet sheet, int col, int row, String value) {
        return addLabelCell(sheet, col, row, 1, value);
    }

    public static int addLabelCell(Sheet sheet, int col, int row, int width, String value) {
        return addLabelCell(sheet, col, row, width, 1, value);
    }

    public static int addLabelCell(Sheet sheet, int col, int row, int width, int height,
            String value) {
        return addLabelCell(sheet, col, row, width, height, null, value);
    }

    public static int addLabelCell(Sheet sheet, int col, int row, CellStyle cellStyle, String value) {
        return addLabelCell(sheet, col, row, 1, cellStyle, value);
    }

    public static int addLabelCell(Sheet sheet, int col, int row, int width, CellStyle cellStyle,
            String value) {
        return addLabelCell(sheet, col, row, width, 1, cellStyle, value);
    }

    public static int addLabelCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, String value) {
        return addLabelCell(sheet, col, row, width, height, cellStyle, value, null);
    }

    public static int addLabelCell(Sheet sheet, int col, int row, CellStyle cellStyle,
            String value, Hyperlink link) {
        return addLabelCell(sheet, col, row, 1, cellStyle, value, link);
    }

    public static int addLabelCell(Sheet sheet, int col, int row, int width, CellStyle cellStyle,
            String value, Hyperlink link) {
        return addLabelCell(sheet, col, row, width, 1, cellStyle, value, link);
    }

    public static int addLabelCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, String value, Hyperlink link) {
        if (value == null) {
            return addBlankCell(sheet, col, row, width, height, cellStyle, link);
        }
        Cell cell = createCell(sheet, col, row, width, height, cellStyle, CELL_TYPE_STRING, link);
        cell.setCellValue(value);
        return width;
    }

    public static int addNumberCell(Sheet sheet, int col, int row, Number value) {
        return addNumberCell(sheet, col, row, 1, value);
    }

    public static int addNumberCell(Sheet sheet, int col, int row, int width, Number value) {
        return addNumberCell(sheet, col, row, width, 1, value);
    }

    public static int addNumberCell(Sheet sheet, int col, int row, int width, int height,
            Number value) {
        return addNumberCell(sheet, col, row, width, height, null, value);
    }

    public static int addNumberCell(Sheet sheet, int col, int row, CellStyle cellStyle, Number value) {
        return addNumberCell(sheet, col, row, 1, cellStyle, value);
    }

    public static int addNumberCell(Sheet sheet, int col, int row, int width, CellStyle cellStyle,
            Number value) {
        return addNumberCell(sheet, col, row, width, 1, cellStyle, value);
    }

    public static int addNumberCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, Number value) {
        return addNumberCell(sheet, col, row, width, height, cellStyle, value, null);
    }

    public static int addNumberCell(Sheet sheet, int col, int row, CellStyle cellStyle,
            Number value, Hyperlink link) {
        return addNumberCell(sheet, col, row, 1, cellStyle, value, link);
    }

    public static int addNumberCell(Sheet sheet, int col, int row, int width, CellStyle cellStyle,
            Number value, Hyperlink link) {
        return addNumberCell(sheet, col, row, width, 1, cellStyle, value, link);
    }

    public static int addNumberCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, Number value, Hyperlink link) {
        if (value == null) {
            return addBlankCell(sheet, col, row, width, height, cellStyle, link);
        }
        Cell cell = createCell(sheet, col, row, width, height, cellStyle, CELL_TYPE_NUMERIC, link);
        cell.setCellValue(value.doubleValue());
        return width;
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, Date value) {
        return addDateTimeCell(sheet, col, row, 1, value);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width, Date value) {
        return addDateTimeCell(sheet, col, row, width, 1, value);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width, int height,
            Date value) {
        return addDateTimeCell(sheet, col, row, width, height, null, value);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, CellStyle cellStyle, Date value) {
        return addDateTimeCell(sheet, col, row, 1, cellStyle, value);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width,
            CellStyle cellStyle, Date value) {
        return addDateTimeCell(sheet, col, row, width, 1, cellStyle, value);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, Date value) {
        return addDateTimeCell(sheet, col, row, width, height, cellStyle, value, null);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, CellStyle cellStyle,
            Date value, Hyperlink link) {
        return addDateTimeCell(sheet, col, row, 1, cellStyle, value, link);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width,
            CellStyle cellStyle, Date value, Hyperlink link) {
        return addDateTimeCell(sheet, col, row, width, 1, cellStyle, value, link);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, Date value, Hyperlink link) {
        if (value == null) {
            return addBlankCell(sheet, col, row, width, height, cellStyle, link);
        }
        Cell cell = createCell(sheet, col, row, width, height, cellStyle, CELL_TYPE_NUMERIC, link);
        cell.setCellValue(value);
        return width;
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, Calendar value) {
        return addDateTimeCell(sheet, col, row, 1, value);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width, Calendar value) {
        return addDateTimeCell(sheet, col, row, width, 1, value);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width, int height,
            Calendar value) {
        return addDateTimeCell(sheet, col, row, width, height, null, value);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, CellStyle cellStyle,
            Calendar value) {
        return addDateTimeCell(sheet, col, row, 1, cellStyle, value);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width,
            CellStyle cellStyle, Calendar value) {
        return addDateTimeCell(sheet, col, row, width, 1, cellStyle, value);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, Calendar value) {
        return addDateTimeCell(sheet, col, row, width, height, cellStyle, value, null);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, CellStyle cellStyle,
            Calendar value, Hyperlink link) {
        return addDateTimeCell(sheet, col, row, 1, cellStyle, value, link);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width,
            CellStyle cellStyle, Calendar value, Hyperlink link) {
        return addDateTimeCell(sheet, col, row, width, 1, cellStyle, value, link);
    }

    public static int addDateTimeCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, Calendar value, Hyperlink link) {
        if (value == null) {
            return addBlankCell(sheet, col, row, width, height, cellStyle, link);
        }
        Cell cell = createCell(sheet, col, row, width, height, cellStyle, CELL_TYPE_NUMERIC, link);
        cell.setCellValue(value);
        return width;
    }

    public static int addFormulaCell(Sheet sheet, int col, int row, String formula) {
        return addFormulaCell(sheet, col, row, 1, formula);
    }

    public static int addFormulaCell(Sheet sheet, int col, int row, int width, String formula) {
        return addFormulaCell(sheet, col, row, width, 1, formula);
    }

    public static int addFormulaCell(Sheet sheet, int col, int row, int width, int height,
            String formula) {
        return addFormulaCell(sheet, col, row, width, height, null, formula);
    }

    public static int addFormulaCell(Sheet sheet, int col, int row, CellStyle cellStyle,
            String formula) {
        return addFormulaCell(sheet, col, row, 1, cellStyle, formula);
    }

    public static int addFormulaCell(Sheet sheet, int col, int row, int width, CellStyle cellStyle,
            String formula) {
        return addFormulaCell(sheet, col, row, width, 1, cellStyle, formula);
    }

    public static int addFormulaCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, String formula) {
        return addFormulaCell(sheet, col, row, width, height, cellStyle, formula, null);
    }

    public static int addFormulaCell(Sheet sheet, int col, int row, CellStyle cellStyle,
            String formula, Hyperlink link) {
        return addFormulaCell(sheet, col, row, 1, cellStyle, formula, link);
    }

    public static int addFormulaCell(Sheet sheet, int col, int row, int width, CellStyle cellStyle,
            String formula, Hyperlink link) {
        return addFormulaCell(sheet, col, row, width, 1, cellStyle, formula, link);
    }

    public static int addFormulaCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, String formula, Hyperlink link) {
        if (formula == null) {
            return addBlankCell(sheet, col, row, width, height, cellStyle, link);
        }
        Cell cell = createCell(sheet, col, row, width, height, cellStyle, CELL_TYPE_FORMULA, link);
        cell.setCellFormula(formula);
        return width;
    }

    private static Cell createCell(Sheet sheet, int col, int row, int width, int height,
            CellStyle cellStyle, int cellType, Hyperlink link) {
        Args.notNull(sheet, "sheet");
        Args.greaterEqual(0, col, "col");
        Args.greaterEqual(0, row, "row");
        Args.greaterEqual(1, width, "width");
        Args.greaterEqual(1, height, "height");

        Cell ret = null;
        if (cellStyle != null) {
            for (int i = row; i < (row + height); i++) {
                Row r = sheet.getRow(i);
                if (r == null) {
                    r = sheet.createRow(i);
                }
                for (int j = col; j < (col + width); j++) {
                    Cell cell = r.getCell(j, Row.CREATE_NULL_AS_BLANK);
                    cell.setCellStyle(cellStyle);
                    if (ret == null) {
                        ret = cell;
                    }
                }
            }
        }
        if ((width != 1) || (height != 1)) {
            sheet.addMergedRegion(new CellRangeAddress(row, (row + height) - 1, col,
                    (col + width) - 1));
        }
        if (ret == null) {
            Row r = sheet.getRow(row);
            if (r == null) {
                r = sheet.createRow(row);
            }
            ret = r.getCell(col, Row.CREATE_NULL_AS_BLANK);
        }
        ret.setCellType(cellType);
        if (link != null) {
            ret.setHyperlink(link);
        }
        return ret;
    }

    public static Boolean getBooleanValue(Cell cell) {
        return getBooleanValue(cell, false);
    }

    public static Boolean getBooleanValue(Cell cell, boolean trim) {
        if ((cell == null) || isValueBlank(cell)) {
            return null;
        }
        Boolean b = null;
        boolean find = false;
        if (isValueBoolean(cell)) {
            try {
                b = Boolean.valueOf(cell.getBooleanCellValue());
                find = true;
            } catch (IllegalStateException e) {
            }
        }
        if (!find) {
            if (isValueString(cell)) {
                try {
                    String s = cell.getStringCellValue();
                    if (isNotEmpty(s)) {
                        if (trim) {
                            s = trim(s);
                        }
                        b = Boolean.valueOf(s);
                        find = true;
                    }
                } catch (IllegalStateException e) {
                }
            }
        }
        return b;
    }

    public static Double getNumericValue(Cell cell) {
        return getNumericValue(cell, false);
    }

    public static Double getNumericValue(Cell cell, boolean trim) {
        return getNumericValue(cell, trim, new NumberFormat[0]);
    }

    public static Double getNumericValue(Cell cell, NumberFormat... numberFormats) {
        return getNumericValue(cell, false, numberFormats);
    }

    public static Double getNumericValue(Cell cell, boolean trim, NumberFormat... numberFormats) {
        if ((cell == null) || isValueBlank(cell)) {
            return null;
        }
        Double d = null;
        boolean find = false;
        if (isValueNumeric(cell)) {
            try {
                d = Double.valueOf(cell.getNumericCellValue());
                find = true;
            } catch (IllegalStateException e) {
            }
        }
        if (!find) {
            if (isValueString(cell)) {
                String s = null;
                try {
                    s = cell.getStringCellValue();
                } catch (IllegalStateException e) {
                }
                if (isNotEmpty(s) && isNumeric(s)) {
                    if (trim) {
                        s = trim(s);
                    }
                    Number n = null;
                    if (numberFormats != null) {
                        for (NumberFormat numberFormat : numberFormats) {
                            try {
                                n = numberFormat.parse(s);
                                break;
                            } catch (ParseException e) {
                            }
                        }
                    }
                    if (n != null) {
                        d = Double.valueOf(n.doubleValue());
                        find = true;
                    } else {
                        try {
                            d = Double.valueOf(s);
                            find = true;
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        }
        return d;
    }

    public static Date getDateTimeValue(Cell cell) {
        return getDateTimeValue(cell, false);
    }

    public static Date getDateTimeValue(Cell cell, boolean trim) {
        return getDateTimeValue(cell, trim, new DateFormat[0]);
    }

    public static Date getDateTimeValue(Cell cell, DateFormat... dateFormats) {
        return getDateTimeValue(cell, false, dateFormats);
    }

    public static Date getDateTimeValue(Cell cell, boolean trim, DateFormat... dateFormats) {
        if ((cell == null) || isValueBlank(cell)) {
            return null;
        }
        Date date = null;
        boolean find = false;
        if (isValueNumeric(cell)) {
            try {
                date = cell.getDateCellValue();
                find = true;
            } catch (IllegalStateException e) {
            }
            if (!find) {
                try {
                    double d = cell.getNumericCellValue();
                    if (0d <= d) {
                        Date tmp = new Date();
                        tmp = DateUtils.setYears(tmp, 1900);
                        tmp = DateUtils.truncate(tmp, Calendar.YEAR);
                        BigDecimal seconds = BigDecimal.valueOf(d).multiply(BigDecimal.valueOf(24))
                                .multiply(BigDecimal.valueOf(60)).multiply(BigDecimal.valueOf(60));
                        long millis = seconds.multiply(BigDecimal.valueOf(1000)).longValue() % 1000;
                        tmp = DateUtils.addSeconds(tmp, seconds.intValue());
                        date = DateUtils.addMilliseconds(tmp, (int) millis);
                        find = true;
                    }
                } catch (IllegalStateException e) {
                }
            }
        }
        if (!find) {
            if ((dateFormats != null) && isValueString(cell)) {
                try {
                    String s = cell.getStringCellValue();
                    if (isNotEmpty(s)) {
                        if (trim) {
                            s = trim(s);
                        }
                        for (DateFormat dateFormat : dateFormats) {
                            try {
                                date = dateFormat.parse(s);
                                find = true;
                                break;
                            } catch (ParseException e) {
                            }
                        }
                    }
                } catch (IllegalStateException e) {
                }
            }
        }
        return date;
    }

    public static String getStringValue(Cell cell) {
        return getStringValue(cell, false);
    }

    public static String getStringValue(Cell cell, boolean trim) {
        if ((cell == null) || isValueBlank(cell)) {
            return null;
        }
        String s = null;
        boolean find = false;
        if (isValueString(cell)) {
            try {
                s = cell.getStringCellValue();
                if (trim) {
                    s = trim(s);
                }
                find = true;
            } catch (IllegalStateException e) {
            }
        }
        if (!find) {
            if (isValueNumeric(cell)) {
                try {
                    s = String.valueOf(cell.getNumericCellValue());
                    find = true;
                } catch (IllegalStateException e) {
                }
            }
        }
        if (!find) {
            if (isValueBoolean(cell)) {
                try {
                    s = String.valueOf(cell.getBooleanCellValue());
                    find = true;
                } catch (IllegalStateException e) {
                }
            }
        }
        return s;
    }

    public static boolean isValueBlank(Cell cell) {
        if (cell == null) {
            return false;
        }
        int type = cell.getCellType();
        if (type == CELL_TYPE_BLANK) {
            return true;
        }
        if (type == CELL_TYPE_FORMULA) {
            return cell.getCachedFormulaResultType() == CELL_TYPE_BLANK;
        }
        return false;
    }

    public static boolean isValueBoolean(Cell cell) {
        if (cell == null) {
            return false;
        }
        int type = cell.getCellType();
        if (type == CELL_TYPE_BOOLEAN) {
            return true;
        }
        if (type == CELL_TYPE_FORMULA) {
            return cell.getCachedFormulaResultType() == CELL_TYPE_BOOLEAN;
        }
        return false;
    }

    public static boolean isValueNumeric(Cell cell) {
        if (cell == null) {
            return false;
        }
        int type = cell.getCellType();
        if (type == CELL_TYPE_NUMERIC) {
            return true;
        }
        if (type == CELL_TYPE_FORMULA) {
            return cell.getCachedFormulaResultType() == CELL_TYPE_NUMERIC;
        }
        return false;
    }

    public static boolean isValueString(Cell cell) {
        if (cell == null) {
            return false;
        }
        int type = cell.getCellType();
        if (type == CELL_TYPE_STRING) {
            return true;
        }
        if (type == CELL_TYPE_FORMULA) {
            return cell.getCachedFormulaResultType() == CELL_TYPE_STRING;
        }
        return false;
    }

    public static void evaluateAllCell(FormulaEvaluator evaluator, Sheet sheet) {
        if ((evaluator == null) || (sheet == null)) {
            return;
        }
        for (int r = 0; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            for (int c = 0; c <= row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell == null) {
                    continue;
                }
                switch (cell.getCellType()) {
                    case CELL_TYPE_FORMULA: {
                        evaluator.evaluateInCell(cell);
                    }
                }
            }
        }
    }

    public static void convertAllCellToString(DataFormatter formatter, Sheet sheet,
            FormatterRegister register) {
        if (sheet == null) {
            return;
        }
        for (int r = 0; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            for (int c = 0; c <= row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell == null) {
                    continue;
                }
                switch (cell.getCellType()) {
                    case CELL_TYPE_BOOLEAN: {
                        String str = formatter.formatCellValue(cell);
                        cell.setCellType(CELL_TYPE_STRING);
                        cell.setCellValue(str);
                        break;
                    }
                    case CELL_TYPE_NUMERIC: {
                        String str;
                        String fmt = cell.getCellStyle().getDataFormatString();
                        if (register == null) {
                            str = formatter.formatCellValue(cell);
                        } else if (register.isNumberFormat(fmt)) {
                            double num = cell.getNumericCellValue();
                            str = register.getNumberFormat(fmt).format(num);
                        } else if (register.isDateFormat(fmt)) {
                            Date date = cell.getDateCellValue();
                            str = register.getDateFormat(fmt).format(date);
                        } else {
                            str = formatter.formatCellValue(cell);
                        }
                        cell.setCellType(CELL_TYPE_STRING);
                        cell.setCellValue(str);
                        break;
                    }
                    case CELL_TYPE_BLANK:
                    case CELL_TYPE_ERROR:
                    case CELL_TYPE_STRING: {
                        break;
                    }
                    case CELL_TYPE_FORMULA: {
                        throw new IllegalStateException();
                    }
                }
            }
        }
    }
}
