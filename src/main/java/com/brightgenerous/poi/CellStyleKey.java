package com.brightgenerous.poi;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.io.Serializable;

class CellStyleKey implements Serializable {

    private static final long serialVersionUID = 3383997735899607806L;

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

    private FontKey fontKey;

    private Boolean hidden;

    private Short indention;

    private Short leftBorderColor;

    private Boolean locked;

    private Short rightBorderColor;

    private Short rotation;

    private Short topBorderColor;

    private Short verticalAlignment;

    private Boolean wrapText;

    public Short getAlignment() {
        return alignment;
    }

    public void setAlignment(Short alignment) {
        this.alignment = alignment;
    }

    public Short getBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(Short borderBottom) {
        this.borderBottom = borderBottom;
    }

    public Short getBorderLeft() {
        return borderLeft;
    }

    public void setBorderLeft(Short borderLeft) {
        this.borderLeft = borderLeft;
    }

    public Short getBorderRight() {
        return borderRight;
    }

    public void setBorderRight(Short borderRight) {
        this.borderRight = borderRight;
    }

    public Short getBorderTop() {
        return borderTop;
    }

    public void setBorderTop(Short borderTop) {
        this.borderTop = borderTop;
    }

    public Short getBottomBorderColor() {
        return bottomBorderColor;
    }

    public void setBottomBorderColor(Short bottomBorderColor) {
        this.bottomBorderColor = bottomBorderColor;
    }

    public Short getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(Short dataFormat) {
        this.dataFormat = dataFormat;
    }

    public Short getFillBackgroundColor() {
        return fillBackgroundColor;
    }

    public void setFillBackgroundColor(Short fillBackgroundColor) {
        this.fillBackgroundColor = fillBackgroundColor;
    }

    public Short getFillForegroundColor() {
        return fillForegroundColor;
    }

    public void setFillForegroundColor(Short fillForegroundColor) {
        this.fillForegroundColor = fillForegroundColor;
    }

    public Short getFillPattern() {
        return fillPattern;
    }

    public void setFillPattern(Short fillPattern) {
        this.fillPattern = fillPattern;
    }

    public FontKey getFontKey() {
        return fontKey;
    }

    public void setFontKey(FontKey fontKey) {
        this.fontKey = fontKey;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Short getIndention() {
        return indention;
    }

    public void setIndention(Short indention) {
        this.indention = indention;
    }

    public Short getLeftBorderColor() {
        return leftBorderColor;
    }

    public void setLeftBorderColor(Short leftBorderColor) {
        this.leftBorderColor = leftBorderColor;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Short getRightBorderColor() {
        return rightBorderColor;
    }

    public void setRightBorderColor(Short rightBorderColor) {
        this.rightBorderColor = rightBorderColor;
    }

    public Short getRotation() {
        return rotation;
    }

    public void setRotation(Short rotation) {
        this.rotation = rotation;
    }

    public Short getTopBorderColor() {
        return topBorderColor;
    }

    public void setTopBorderColor(Short topBorderColor) {
        this.topBorderColor = topBorderColor;
    }

    public Short getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(Short verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public Boolean getWrapText() {
        return wrapText;
    }

    public void setWrapText(Boolean wrapText) {
        this.wrapText = wrapText;
    }

    @Override
    public int hashCode() {
        final int multiplier = 37;
        int result = 17;
        result = (multiplier * result) + hashCodeEscapeNull(alignment);
        result = (multiplier * result) + hashCodeEscapeNull(borderBottom);
        result = (multiplier * result) + hashCodeEscapeNull(borderLeft);
        result = (multiplier * result) + hashCodeEscapeNull(borderRight);
        result = (multiplier * result) + hashCodeEscapeNull(borderTop);
        result = (multiplier * result) + hashCodeEscapeNull(bottomBorderColor);
        result = (multiplier * result) + hashCodeEscapeNull(dataFormat);
        result = (multiplier * result) + hashCodeEscapeNull(fillBackgroundColor);
        result = (multiplier * result) + hashCodeEscapeNull(fillForegroundColor);
        result = (multiplier * result) + hashCodeEscapeNull(fillPattern);
        result = (multiplier * result) + hashCodeEscapeNull(fontKey);
        result = (multiplier * result) + hashCodeEscapeNull(hidden);
        result = (multiplier * result) + hashCodeEscapeNull(indention);
        result = (multiplier * result) + hashCodeEscapeNull(leftBorderColor);
        result = (multiplier * result) + hashCodeEscapeNull(locked);
        result = (multiplier * result) + hashCodeEscapeNull(rightBorderColor);
        result = (multiplier * result) + hashCodeEscapeNull(rotation);
        result = (multiplier * result) + hashCodeEscapeNull(topBorderColor);
        result = (multiplier * result) + hashCodeEscapeNull(verticalAlignment);
        result = (multiplier * result) + hashCodeEscapeNull(wrapText);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CellStyleKey)) {
            return false;
        }

        CellStyleKey other = (CellStyleKey) obj;

        if (!equalsEscapeNull(alignment, other.alignment)) {
            return false;
        }
        if (!equalsEscapeNull(borderBottom, other.borderBottom)) {
            return false;
        }
        if (!equalsEscapeNull(borderLeft, other.borderLeft)) {
            return false;
        }
        if (!equalsEscapeNull(borderRight, other.borderRight)) {
            return false;
        }
        if (!equalsEscapeNull(borderTop, other.borderTop)) {
            return false;
        }
        if (!equalsEscapeNull(bottomBorderColor, other.bottomBorderColor)) {
            return false;
        }
        if (!equalsEscapeNull(dataFormat, other.dataFormat)) {
            return false;
        }
        if (!equalsEscapeNull(fillBackgroundColor, other.fillBackgroundColor)) {
            return false;
        }
        if (!equalsEscapeNull(fillForegroundColor, other.fillForegroundColor)) {
            return false;
        }
        if (!equalsEscapeNull(fillPattern, other.fillPattern)) {
            return false;
        }
        if (!equalsEscapeNull(fontKey, other.fontKey)) {
            return false;
        }
        if (!equalsEscapeNull(hidden, other.hidden)) {
            return false;
        }
        if (!equalsEscapeNull(indention, other.indention)) {
            return false;
        }
        if (!equalsEscapeNull(leftBorderColor, other.leftBorderColor)) {
            return false;
        }
        if (!equalsEscapeNull(locked, other.locked)) {
            return false;
        }
        if (!equalsEscapeNull(rightBorderColor, other.rightBorderColor)) {
            return false;
        }
        if (!equalsEscapeNull(rotation, other.rotation)) {
            return false;
        }
        if (!equalsEscapeNull(topBorderColor, other.topBorderColor)) {
            return false;
        }
        if (!equalsEscapeNull(verticalAlignment, other.verticalAlignment)) {
            return false;
        }
        if (!equalsEscapeNull(wrapText, other.wrapText)) {
            return false;
        }
        return true;
    }
}
