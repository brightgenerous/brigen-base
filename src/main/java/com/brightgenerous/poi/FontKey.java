package com.brightgenerous.poi;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.io.Serializable;

class FontKey implements Serializable {

    private static final long serialVersionUID = 5270447498321020016L;

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

    public Short getBoldweight() {
        return boldweight;
    }

    public void setBoldweight(Short boldweight) {
        this.boldweight = boldweight;
    }

    public Short getCharset() {
        return charset;
    }

    public void setCharset(Short charset) {
        this.charset = charset;
    }

    public Short getColor() {
        return color;
    }

    public void setColor(Short color) {
        this.color = color;
    }

    public Short getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(Short fontHeight) {
        this.fontHeight = fontHeight;
    }

    public Short getFontHeightInPoints() {
        return fontHeightInPoints;
    }

    public void setFontHeightInPoints(Short fontHeightInPoints) {
        this.fontHeightInPoints = fontHeightInPoints;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Boolean getItalic() {
        return italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public Boolean getStrikeout() {
        return strikeout;
    }

    public void setStrikeout(Boolean strikeout) {
        this.strikeout = strikeout;
    }

    public Short getTypeOffset() {
        return typeOffset;
    }

    public void setTypeOffset(Short typeOffset) {
        this.typeOffset = typeOffset;
    }

    public Byte getUnderLine() {
        return underLine;
    }

    public void setUnderLine(Byte underLine) {
        this.underLine = underLine;
    }

    @Override
    public int hashCode() {
        final int multiplier = 37;
        int result = 17;
        result = (multiplier * result) + hashCodeEscapeNull(boldweight);
        result = (multiplier * result) + hashCodeEscapeNull(charset);
        result = (multiplier * result) + hashCodeEscapeNull(color);
        result = (multiplier * result) + hashCodeEscapeNull(fontHeight);
        result = (multiplier * result) + hashCodeEscapeNull(fontHeightInPoints);
        result = (multiplier * result) + hashCodeEscapeNull(fontName);
        result = (multiplier * result) + hashCodeEscapeNull(italic);
        result = (multiplier * result) + hashCodeEscapeNull(strikeout);
        result = (multiplier * result) + hashCodeEscapeNull(typeOffset);
        result = (multiplier * result) + hashCodeEscapeNull(underLine);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FontKey)) {
            return false;
        }

        FontKey other = (FontKey) obj;

        if (!equalsEscapeNull(boldweight, other.boldweight)) {
            return false;
        }
        if (!equalsEscapeNull(charset, other.charset)) {
            return false;
        }
        if (!equalsEscapeNull(color, other.color)) {
            return false;
        }
        if (!equalsEscapeNull(fontHeight, other.fontHeight)) {
            return false;
        }
        if (!equalsEscapeNull(fontHeightInPoints, other.fontHeightInPoints)) {
            return false;
        }
        if (!equalsEscapeNull(fontName, other.fontName)) {
            return false;
        }
        if (!equalsEscapeNull(italic, other.italic)) {
            return false;
        }
        if (!equalsEscapeNull(strikeout, other.strikeout)) {
            return false;
        }
        if (!equalsEscapeNull(typeOffset, other.typeOffset)) {
            return false;
        }
        if (!equalsEscapeNull(underLine, other.underLine)) {
            return false;
        }
        return true;
    }
}
