package com.brightgenerous.poi;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import com.brightgenerous.lang.Args;

/*
 *  this instance must be disposed at once.
 */
public class CellStyleRegister {

    private final Workbook workbook;

    private final Map<FontKey, Short> fontCache = new HashMap<>();

    private final Map<CellStyleKey, Short> cellStyleCache = new HashMap<>();

    public CellStyleRegister(Workbook workbook) {
        Args.notNull(workbook, "workbook");

        this.workbook = workbook;
    }

    public CellStyle getCellStyle(CellStyleKey key) {
        if (key == null) {
            return null;
        }
        CellStyle ret = null;
        {
            Short index = cellStyleCache.get(key);
            if (index != null) {
                ret = workbook.getCellStyleAt(index.shortValue());
            }
        }
        if (ret == null) {
            Font font = getFont(key.getFontKey());
            short index = createCellStyle(workbook, font, key);
            cellStyleCache.put(key, Short.valueOf(index));
            ret = workbook.getCellStyleAt(index);
        }
        return ret;
    }

    private Font getFont(FontKey key) {
        if (key == null) {
            return null;
        }
        Font ret = null;
        {
            Short index = fontCache.get(key);
            if (index != null) {
                ret = workbook.getFontAt(index.shortValue());
            }
        }
        if (ret == null) {
            short index = createFont(workbook, key);
            fontCache.put(key, Short.valueOf(index));
            ret = workbook.getFontAt(index);
        }
        return ret;
    }

    private static short createCellStyle(Workbook workbook, Font font, CellStyleKey key) {
        CellStyle ret = workbook.createCellStyle();
        if (key.getAlignment() != null) {
            ret.setAlignment(key.getAlignment().shortValue());
        }
        if (key.getBorderBottom() != null) {
            ret.setBorderBottom(key.getBorderBottom().shortValue());
        }
        if (key.getBorderLeft() != null) {
            ret.setBorderLeft(key.getBorderLeft().shortValue());
        }
        if (key.getBorderRight() != null) {
            ret.setBorderRight(key.getBorderRight().shortValue());
        }
        if (key.getBorderTop() != null) {
            ret.setBorderTop(key.getBorderTop().shortValue());
        }
        if (key.getBottomBorderColor() != null) {
            ret.setBottomBorderColor(key.getBottomBorderColor().shortValue());
        }
        if (key.getDataFormat() != null) {
            ret.setDataFormat(key.getDataFormat().shortValue());
        }
        if (key.getFillBackgroundColor() != null) {
            ret.setFillBackgroundColor(key.getFillBackgroundColor().shortValue());
        }
        if (key.getFillForegroundColor() != null) {
            ret.setFillForegroundColor(key.getFillForegroundColor().shortValue());
        }
        if (key.getFillPattern() != null) {
            ret.setFillPattern(key.getFillPattern().shortValue());
        }
        if (font != null) {
            ret.setFont(font);
        }
        if (key.getHidden() != null) {
            ret.setHidden(key.getHidden().booleanValue());
        }
        if (key.getIndention() != null) {
            ret.setIndention(key.getIndention().shortValue());
        }
        if (key.getLeftBorderColor() != null) {
            ret.setLeftBorderColor(key.getLeftBorderColor().shortValue());
        }
        if (key.getLocked() != null) {
            ret.setLocked(key.getLocked().booleanValue());
        }
        if (key.getRightBorderColor() != null) {
            ret.setRightBorderColor(key.getRightBorderColor().shortValue());
        }
        if (key.getRotation() != null) {
            ret.setRotation(key.getRotation().shortValue());
        }
        if (key.getTopBorderColor() != null) {
            ret.setTopBorderColor(key.getTopBorderColor().shortValue());
        }
        if (key.getVerticalAlignment() != null) {
            ret.setVerticalAlignment(key.getVerticalAlignment().shortValue());
        }
        if (key.getWrapText() != null) {
            ret.setWrapText(key.getWrapText().booleanValue());
        }
        return ret.getIndex();
    }

    private static short createFont(Workbook workbook, FontKey key) {
        Font ret = workbook.createFont();
        if (key.getBoldweight() != null) {
            ret.setBoldweight(key.getBoldweight().shortValue());
        }
        if (key.getCharset() != null) {
            ret.setCharSet(key.getCharset().shortValue());
        }
        if (key.getColor() != null) {
            ret.setColor(key.getColor().shortValue());
        }
        if (key.getFontHeight() != null) {
            ret.setFontHeight(key.getFontHeight().shortValue());
        }
        if (key.getFontHeightInPoints() != null) {
            ret.setFontHeightInPoints(key.getFontHeightInPoints().shortValue());
        }
        if (key.getFontName() != null) {
            ret.setFontName(key.getFontName());
        }
        if (key.getItalic() != null) {
            ret.setItalic(key.getItalic().booleanValue());
        }
        if (key.getStrikeout() != null) {
            ret.setStrikeout(key.getStrikeout().booleanValue());
        }
        if (key.getTypeOffset() != null) {
            ret.setTypeOffset(key.getTypeOffset().shortValue());
        }
        if (key.getUnderLine() != null) {
            ret.setUnderline(key.getUnderLine().byteValue());
        }
        return ret.getIndex();
    }

    public short getDataFormat(String format) {
        return workbook.createDataFormat().getFormat(format);
    }
}
