package com.brightgenerous.poi;

import java.io.Serializable;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public class CellStyleKeyBuilder implements Serializable {

    private static final long serialVersionUID = -6569363308972428391L;

    private Short alignment;

    private Short borderBottom;

    private Short borderLeft;

    private Short borderRight;

    private Short borderTop;

    private Short bottomBorderColor;

    private Short dataFormat;

    private Short fillBackgroundColor;

    private Short fillForegroundColor;

    private Short fillPattern;

    private Boolean hidden;

    private Short indention;

    private Short leftBorderColor;

    private Boolean locked;

    private Short rightBorderColor;

    private Short rotation;

    private Short topBorderColor;

    private Short verticalAlignment;

    private Boolean wrapText;

    private Short boldweight;

    private Short charset;

    private Short color;

    private Short fontHeight;

    private Short fontHeightInPoints;

    private String fontName;

    private Boolean italic;

    private Boolean strikeout;

    private Short typeOffset;

    private Byte underLine;

    protected CellStyleKeyBuilder() {
    }

    public static CellStyleKeyBuilder create() {
        return new CellStyleKeyBuilder();
    }

    public CellStyleKeyBuilder clear() {
        alignment = null;
        borderBottom = null;
        borderLeft = null;
        borderRight = null;
        borderTop = null;
        bottomBorderColor = null;
        dataFormat = null;
        fillBackgroundColor = null;
        fillForegroundColor = null;
        fillPattern = null;
        hidden = null;
        indention = null;
        leftBorderColor = null;
        locked = null;
        rightBorderColor = null;
        rotation = null;
        topBorderColor = null;
        verticalAlignment = null;
        wrapText = null;
        boldweight = null;
        charset = null;
        color = null;
        fontHeight = null;
        fontHeightInPoints = null;
        fontName = null;
        italic = null;
        strikeout = null;
        typeOffset = null;
        underLine = null;
        return this;
    }

    public Short alignment() {
        return alignment;
    }

    public CellStyleKeyBuilder alignment(Short alignment) {
        this.alignment = alignment;
        return this;
    }

    public CellStyleKeyBuilder alignment(short alignment) {
        return alignment(Short.valueOf(alignment));
    }

    public Short borderBottom() {
        return borderBottom;
    }

    public CellStyleKeyBuilder borderBottom(Short borderBottom) {
        this.borderBottom = borderBottom;
        return this;
    }

    public CellStyleKeyBuilder borderBottom(short borderBottom) {
        return borderBottom(Short.valueOf(borderBottom));
    }

    public Short borderLeft() {
        return borderLeft;
    }

    public CellStyleKeyBuilder borderLeft(Short borderLeft) {
        this.borderLeft = borderLeft;
        return this;
    }

    public CellStyleKeyBuilder borderLeft(short borderLeft) {
        return borderLeft(Short.valueOf(borderLeft));
    }

    public Short borderRight() {
        return borderRight;
    }

    public CellStyleKeyBuilder borderRight(Short borderRight) {
        this.borderRight = borderRight;
        return this;
    }

    public CellStyleKeyBuilder borderRight(short borderRight) {
        return borderRight(Short.valueOf(borderRight));
    }

    public Short borderTop() {
        return borderTop;
    }

    public CellStyleKeyBuilder borderTop(Short borderTop) {
        this.borderTop = borderTop;
        return this;
    }

    public CellStyleKeyBuilder borderTop(short borderTop) {
        return borderTop(Short.valueOf(borderTop));
    }

    public Short bottomBorderColor() {
        return bottomBorderColor;
    }

    public CellStyleKeyBuilder bottomBorderColor(Short bottomBorderColor) {
        this.bottomBorderColor = bottomBorderColor;
        return this;
    }

    public CellStyleKeyBuilder bottomBorderColor(short bottomBorderColor) {
        return bottomBorderColor(Short.valueOf(bottomBorderColor));
    }

    public Short dataFormat() {
        return dataFormat;
    }

    public CellStyleKeyBuilder dataFormat(Short dataFormat) {
        this.dataFormat = dataFormat;
        return this;
    }

    public CellStyleKeyBuilder dataFormat(short dataFormat) {
        return dataFormat(Short.valueOf(dataFormat));
    }

    public Short fillBackgroundColor() {
        return fillBackgroundColor;
    }

    public CellStyleKeyBuilder fillBackgroundColor(Short fillBackgroundColor) {
        this.fillBackgroundColor = fillBackgroundColor;
        return this;
    }

    public CellStyleKeyBuilder fillBackgroundColor(short fillBackgroundColor) {
        return fillBackgroundColor(Short.valueOf(fillBackgroundColor));
    }

    public Short fillForegroundColor() {
        return fillForegroundColor;
    }

    public CellStyleKeyBuilder fillForegroundColor(Short fillForegroundColor) {
        this.fillForegroundColor = fillForegroundColor;
        return this;
    }

    public CellStyleKeyBuilder fillForegroundColor(short fillForegroundColor) {
        return fillForegroundColor(Short.valueOf(fillForegroundColor));
    }

    public Short fillPattern() {
        return fillPattern;
    }

    public CellStyleKeyBuilder fillPattern(Short fillPattern) {
        this.fillPattern = fillPattern;
        return this;
    }

    public CellStyleKeyBuilder fillPattern(short fillPattern) {
        return fillPattern(Short.valueOf(fillPattern));
    }

    public Boolean hidden() {
        return hidden;
    }

    public CellStyleKeyBuilder hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public CellStyleKeyBuilder hidden(boolean hidden) {
        return hidden(Boolean.valueOf(hidden));
    }

    public Short indention() {
        return indention;
    }

    public CellStyleKeyBuilder indention(Short indention) {
        this.indention = indention;
        return this;
    }

    public CellStyleKeyBuilder indention(short indention) {
        return indention(Short.valueOf(indention));
    }

    public Short leftBorderColor() {
        return leftBorderColor;
    }

    public CellStyleKeyBuilder leftBorderColor(Short leftBorderColor) {
        this.leftBorderColor = leftBorderColor;
        return this;
    }

    public CellStyleKeyBuilder leftBorderColor(short leftBorderColor) {
        return leftBorderColor(Short.valueOf(leftBorderColor));
    }

    public Boolean locked() {
        return locked;
    }

    public CellStyleKeyBuilder locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public CellStyleKeyBuilder locked(boolean locked) {
        return locked(Boolean.valueOf(locked));
    }

    public Short rightBorderColor() {
        return rightBorderColor;
    }

    public CellStyleKeyBuilder rightBorderColor(Short rightBorderColor) {
        this.rightBorderColor = rightBorderColor;
        return this;
    }

    public CellStyleKeyBuilder rightBorderColor(short rightBorderColor) {
        return rightBorderColor(Short.valueOf(rightBorderColor));
    }

    public Short rotation() {
        return rotation;
    }

    public CellStyleKeyBuilder rotation(Short rotation) {
        this.rotation = rotation;
        return this;
    }

    public CellStyleKeyBuilder rotation(short rotation) {
        return rotation(Short.valueOf(rotation));
    }

    public CellStyleKeyBuilder rotationVertical() {
        rotation = Short.valueOf((short) 0xff);
        return this;
    }

    public Short topBorderColor() {
        return topBorderColor;
    }

    public CellStyleKeyBuilder topBorderColor(Short topBorderColor) {
        this.topBorderColor = topBorderColor;
        return this;
    }

    public CellStyleKeyBuilder topBorderColor(short topBorderColor) {
        return topBorderColor(Short.valueOf(topBorderColor));
    }

    public Short verticalAlignment() {
        return verticalAlignment;
    }

    public CellStyleKeyBuilder verticalAlignment(Short verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        return this;
    }

    public CellStyleKeyBuilder verticalAlignment(short verticalAlignment) {
        return verticalAlignment(Short.valueOf(verticalAlignment));
    }

    public Boolean wrapText() {
        return wrapText;
    }

    public CellStyleKeyBuilder wrapText(Boolean wrapText) {
        this.wrapText = wrapText;
        return this;
    }

    public CellStyleKeyBuilder wrapText(boolean wrapText) {
        return wrapText(Boolean.valueOf(wrapText));
    }

    public Short boldweight() {
        return boldweight;
    }

    public CellStyleKeyBuilder boldweight(Short boldweight) {
        this.boldweight = boldweight;
        return this;
    }

    public CellStyleKeyBuilder boldweight(short boldweight) {
        return boldweight(Short.valueOf(boldweight));
    }

    public Short charset() {
        return charset;
    }

    public CellStyleKeyBuilder charset(Short charset) {
        this.charset = charset;
        return this;
    }

    public CellStyleKeyBuilder charset(short charset) {
        return charset(Short.valueOf(charset));
    }

    public Short color() {
        return color;
    }

    public CellStyleKeyBuilder color(Short color) {
        this.color = color;
        return this;
    }

    public CellStyleKeyBuilder color(short color) {
        return color(Short.valueOf(color));
    }

    public Short fontHeight() {
        return fontHeight;
    }

    public CellStyleKeyBuilder fontHeight(Short fontHeight) {
        this.fontHeight = fontHeight;
        return this;
    }

    public CellStyleKeyBuilder fontHeight(short fontHeight) {
        return fontHeight(Short.valueOf(fontHeight));
    }

    public Short fontHeightInPoints() {
        return fontHeightInPoints;
    }

    public CellStyleKeyBuilder fontHeightInPoints(Short fontHeightInPoints) {
        this.fontHeightInPoints = fontHeightInPoints;
        return this;
    }

    public CellStyleKeyBuilder fontHeightInPoints(short fontHeightInPoints) {
        return fontHeightInPoints(Short.valueOf(fontHeightInPoints));
    }

    public String fontName() {
        return fontName;
    }

    public CellStyleKeyBuilder fontName(String fontName) {
        this.fontName = fontName;
        return this;
    }

    public Boolean italic() {
        return italic;
    }

    public CellStyleKeyBuilder italic(Boolean italic) {
        this.italic = italic;
        return this;
    }

    public CellStyleKeyBuilder italic(boolean italic) {
        return italic(Boolean.valueOf(italic));
    }

    public Boolean strikeout() {
        return strikeout;
    }

    public CellStyleKeyBuilder strikeout(Boolean strikeout) {
        this.strikeout = strikeout;
        return this;
    }

    public CellStyleKeyBuilder strikeout(boolean strikeout) {
        return strikeout(Boolean.valueOf(strikeout));
    }

    public Short typeOffset() {
        return typeOffset;
    }

    public CellStyleKeyBuilder typeOffset(Short typeOffset) {
        this.typeOffset = typeOffset;
        return this;
    }

    public CellStyleKeyBuilder typeOffset(short typeOffset) {
        return typeOffset(Short.valueOf(typeOffset));
    }

    public Byte underLine() {
        return underLine;
    }

    public CellStyleKeyBuilder underLine(Byte underLine) {
        this.underLine = underLine;
        return this;
    }

    public CellStyleKeyBuilder underLine(byte underLine) {
        return underLine(Byte.valueOf(underLine));
    }

    public CellStyleKey build() {
        CellStyleKey ret = new CellStyleKey();
        ret.setAlignment(alignment);
        ret.setBorderBottom(borderBottom);
        ret.setBorderLeft(borderLeft);
        ret.setBorderRight(borderRight);
        ret.setBorderTop(borderTop);
        ret.setBottomBorderColor(bottomBorderColor);
        ret.setDataFormat(dataFormat);
        ret.setFillBackgroundColor(fillBackgroundColor);
        ret.setFillForegroundColor(fillForegroundColor);
        ret.setFillPattern(fillPattern);
        ret.setHidden(hidden);
        ret.setIndention(indention);
        ret.setLeftBorderColor(leftBorderColor);
        ret.setLocked(locked);
        ret.setRightBorderColor(rightBorderColor);
        ret.setRotation(rotation);
        ret.setTopBorderColor(topBorderColor);
        ret.setVerticalAlignment(verticalAlignment);
        ret.setWrapText(wrapText);
        FontKey font = new FontKey();
        font.setBoldweight(boldweight);
        font.setCharset(charset);
        font.setColor(color);
        font.setFontHeight(fontHeight);
        font.setFontHeightInPoints(fontHeightInPoints);
        font.setFontName(fontName);
        font.setItalic(italic);
        font.setStrikeout(strikeout);
        font.setTypeOffset(typeOffset);
        font.setUnderLine(underLine);
        ret.setFontKey(font);
        return ret;
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.useful()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.useful()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (ToStringUtils.useful()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }
}
