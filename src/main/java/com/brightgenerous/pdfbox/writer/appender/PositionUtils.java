package com.brightgenerous.pdfbox.writer.appender;

class PositionUtils {

    private PositionUtils() {
    }

    public static float getCenterX(AppenderAlign align, float rectWidth, float contentWidth,
            float marginX, float paddingLeft) {
        float ret;
        switch (align) {
            case LEFT:
                ret = (contentWidth / 2) + marginX;
                break;
            case RIGHT:
                ret = rectWidth - (contentWidth / 2) - marginX;
                break;
            case CENTER:
                ret = rectWidth / 2;
                break;
            default:
                throw new IllegalStateException();
        }
        ret += paddingLeft;
        return ret;
    }

    public static float getCenterY(AppenderVertical vertical, float rectHeight,
            float contentHeight, float marginY, float paddingTop) {
        float ret;
        switch (vertical) {
            case TOP:
                ret = rectHeight - (contentHeight / 2) - marginY;
                break;
            case BOTTOM:
                ret = (contentHeight / 2) + marginY;
                break;
            case MIDDLE:
                ret = rectHeight / 2;
                break;
            default:
                throw new IllegalStateException();
        }
        ret -= paddingTop;
        return ret;
    }

    public static float getCornerX(AppenderAlign align, float rectWidth, float contentWidth,
            float marginX, float paddingLeft) {
        float ret;
        switch (align) {
            case LEFT:
                ret = marginX;
                break;
            case RIGHT:
                ret = rectWidth - contentWidth - marginX;
                break;
            case CENTER:
                ret = (rectWidth - contentWidth) / 2;
                break;
            default:
                throw new IllegalStateException();
        }
        ret += paddingLeft;
        return ret;
    }

    public static float getCornerY(AppenderVertical vertical, float rectHeight,
            float contentHeight, float marginY, float paddingTop) {
        float ret;
        switch (vertical) {
            case TOP:
                ret = rectHeight - contentHeight - marginY;
                break;
            case BOTTOM:
                ret = marginY;
                break;
            case MIDDLE:
                ret = (rectHeight - contentHeight) / 2;
                break;
            default:
                throw new IllegalStateException();
        }
        ret -= paddingTop;
        return ret;
    }
}
