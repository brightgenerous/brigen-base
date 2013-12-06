package com.brightgenerous.pdfbox.writer.appender;

public abstract class PositionPageAppender extends PageAppender {

    private final AppenderAlign align;

    private final AppenderVertical vertical;

    private final float marginX;

    private final float marginY;

    private final float paddingLeft;

    private final float paddingTop;

    private final float rotate;

    public PositionPageAppender(AppenderFlag flag, AppenderAlign align, AppenderVertical vertical,
            float marginX, float marginY, float paddingLeft, float paddingTop, float rotate) {
        super(flag);

        if (align == null) {
            throw new IllegalArgumentException("The align must not be null.");
        }
        if (vertical == null) {
            throw new IllegalArgumentException("The vertical must not be null.");
        }

        this.align = align;
        this.vertical = vertical;
        this.marginX = marginX;
        this.marginY = marginY;
        this.paddingLeft = paddingLeft;
        this.paddingTop = paddingTop;
        this.rotate = rotate;
    }

    private float getCenterX(float rectWidth, float contentWidth) {
        return PositionUtils.getCenterX(getAlign(), rectWidth, contentWidth, getMarginX(),
                getPaddingLeft());
    }

    private float getCenterY(float rectHeight, float contentHeight) {
        return PositionUtils.getCenterY(getVertical(), rectHeight, contentHeight, getMarginY(),
                getPaddingTop());
    }

    protected float getCornerX(float rectWidth, float contentWidth) {
        return PositionUtils.getCornerX(getAlign(), rectWidth, contentWidth, getMarginX(),
                getPaddingLeft());
    }

    protected float getCornerY(float rectHeight, float contentHeight) {
        return PositionUtils.getCornerY(getVertical(), rectHeight, contentHeight, getMarginY(),
                getPaddingTop());
    }

    protected float[] getRotatePoint(double angle, float rectWidth, float rectHeight,
            float contentWidth, float contentHeight) {
        float centerX = getCenterX(rectWidth, contentWidth);
        float centerY = getCenterY(rectHeight, contentHeight);
        float cornerX = getCornerX(rectWidth, contentWidth);
        float cornerY = getCornerY(rectHeight, contentHeight);
        float dx = cornerX - centerX;
        float dy = cornerY - centerY;
        return new float[] { (float) ((Math.cos(angle) * dx) - (Math.sin(angle) * dy)) + centerX,
                (float) ((Math.cos(angle) * dy) + (Math.sin(angle) * dx)) + centerY };
    }

    protected AppenderAlign getAlign() {
        return align;
    }

    protected AppenderVertical getVertical() {
        return vertical;
    }

    protected float getMarginX() {
        return marginX;
    }

    protected float getMarginY() {
        return marginY;
    }

    protected float getPaddingLeft() {
        return paddingLeft;
    }

    protected float getPaddingTop() {
        return paddingTop;
    }

    protected float getRotate() {
        return rotate;
    }
}
