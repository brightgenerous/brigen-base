package com.brightgenerous.pdfbox.writer.appender;

import java.awt.Color;
import java.net.URL;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class AppenderBuilder {

    private static final AppenderFlag DEFAULT_FLAG = AppenderFlag.OVER;

    private static final AppenderAlign DEFAULT_ALIGN = AppenderAlign.CENTER;

    private static final AppenderVertical DEFAULT_VERTICAL = AppenderVertical.MIDDLE;

    private static final Float DEFAULT_MARGIN_X = Float.valueOf(10);

    private static final Float DEFAULT_MARGIN_Y = Float.valueOf(10);

    private static final Float DEFAULT_PADDING_LEFT = Float.valueOf(0);

    private static final Float DEFAULT_PADDING_TOP = Float.valueOf(0);

    private static final String DEFAULT_READER_FORMAT = "{0}";

    private static final String DEFAULT_READERS_FORMAT = "{0} / {1}";

    private static final PDFont DEFAULT_FONT = PDType1Font.HELVETICA;

    private static final Float DEFAULT_FONT_SIZE = Float.valueOf(12);

    private static final Boolean DEFAULT_KERNED = Boolean.TRUE;

    private static final Color DEFAULT_COLOR = Color.BLACK;

    private static final Float DEFAULT_ROTATE = Float.valueOf(0);

    private static final IImageResource DEFAULT_IMAGE_RESOURCE = new EmptyImageResource();

    private static final Boolean DEFAULT_INLINE = Boolean.FALSE;

    private AppenderFlag flag;

    private AppenderAlign align;

    private AppenderVertical vertical;

    private Float marginX;

    private Float marginY;

    private Float paddingLeft;

    private Float paddingTop;

    private String format;

    private PDFont font;

    private Float fontSize;

    private Boolean kerned;

    private Color color;

    private Float rotate;

    private IImageResource imageResource;

    private Boolean inline;

    protected AppenderBuilder() {
    }

    public static AppenderBuilder create() {
        return new AppenderBuilder();
    }

    public AppenderBuilder clear() {
        flag = null;
        align = null;
        vertical = null;
        marginX = null;
        marginY = null;
        paddingLeft = null;
        paddingTop = null;
        format = null;
        font = null;
        fontSize = null;
        kerned = null;
        color = null;
        rotate = null;
        imageResource = null;
        inline = null;
        return this;
    }

    public AppenderFlag flag() {
        return flag;
    }

    public AppenderBuilder flag(AppenderFlag flag) {
        this.flag = flag;
        return this;
    }

    public AppenderAlign align() {
        return align;
    }

    public AppenderBuilder align(AppenderAlign align) {
        this.align = align;
        return this;
    }

    public AppenderVertical vertical() {
        return vertical;
    }

    public AppenderBuilder vertical(AppenderVertical vertical) {
        this.vertical = vertical;
        return this;
    }

    public Float marginX() {
        return marginX;
    }

    public AppenderBuilder marginX(Float marginX) {
        this.marginX = marginX;
        return this;
    }

    public AppenderBuilder marginX(float marginX) {
        return marginX(Float.valueOf(marginX));
    }

    public Float marginY() {
        return marginY;
    }

    public AppenderBuilder marginY(Float marginY) {
        this.marginY = marginY;
        return this;
    }

    public AppenderBuilder marginY(float marginY) {
        return marginY(Float.valueOf(marginY));
    }

    public Float paddingLeft() {
        return paddingLeft;
    }

    public AppenderBuilder paddingLeft(Float paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public AppenderBuilder paddingLeft(float paddingLeft) {
        return paddingLeft(Float.valueOf(paddingLeft));
    }

    public Float paddingTop() {
        return paddingTop;
    }

    public AppenderBuilder paddingTop(Float paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public AppenderBuilder paddingTop(float paddingTop) {
        return paddingTop(Float.valueOf(paddingTop));
    }

    public String format() {
        return format;
    }

    public AppenderBuilder format(String format) {
        this.format = format;
        return this;
    }

    public PDFont font() {
        return font;
    }

    public AppenderBuilder font(PDFont font) {
        this.font = font;
        return this;
    }

    public Float fontSize() {
        return fontSize;
    }

    public AppenderBuilder fontSize(Float fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public AppenderBuilder fontSize(float fontSize) {
        return fontSize(Float.valueOf(fontSize));
    }

    @Deprecated
    public Boolean kerned() {
        return kerned;
    }

    @Deprecated
    public AppenderBuilder kerned(Boolean kerned) {
        this.kerned = kerned;
        return this;
    }

    @Deprecated
    public AppenderBuilder kerned(boolean kerned) {
        return kerned(kerned ? Boolean.TRUE : Boolean.FALSE);
    }

    public Color color() {
        return color;
    }

    public AppenderBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public Float rotate() {
        return rotate;
    }

    public AppenderBuilder rotate(Float rotate) {
        this.rotate = rotate;
        return this;
    }

    public AppenderBuilder rotate(float rotate) {
        return rotate(Float.valueOf(rotate));
    }

    public IImageResource imageResource() {
        return imageResource;
    }

    public AppenderBuilder imageResource(IImageResource imageResource) {
        this.imageResource = imageResource;
        return this;
    }

    public AppenderBuilder imageResource(String fileName) {
        return imageResource(new ImageResource(fileName));
    }

    public AppenderBuilder imageResource(URL url) {
        return imageResource(new ImageResource(url));
    }

    public AppenderBuilder imageResource(byte[] bytes) {
        return imageResource(new ImageResource(bytes));
    }

    @Deprecated
    public Boolean inline() {
        return inline;
    }

    @Deprecated
    public AppenderBuilder inline(Boolean inline) {
        this.inline = inline;
        return this;
    }

    @Deprecated
    public AppenderBuilder inline(boolean inline) {
        return inline(inline ? Boolean.TRUE : Boolean.FALSE);
    }

    public TextPagesAppender buildPagesText() {
        return new TextPagesAppender(getFlag(), getAlign(), getVertical(), getMarginX(),
                getMarginY(), getPaddingLeft(), getPaddingTop(), getRotate(), getReadersFormat(),
                getFont(), getFontSize(), getKerned(), getColor());
    }

    public TextPagesAppender buildPagesHeader() {
        return new TextPagesAppender(AppenderFlag.OVER, getAlign(), AppenderVertical.TOP,
                getMarginX(), getMarginY(), getPaddingLeft(), getPaddingTop(), getRotate(),
                getReadersFormat(), getFont(), getFontSize(), getKerned(), getColor());
    }

    public TextPagesAppender buildPagesFooter() {
        return new TextPagesAppender(AppenderFlag.OVER, getAlign(), AppenderVertical.BOTTOM,
                getMarginX(), getMarginY(), getPaddingLeft(), getPaddingTop(), getRotate(),
                getReadersFormat(), getFont(), getFontSize(), getKerned(), getColor());
    }

    public TextPageAppender buildPageText() {
        return new TextPageAppender(getFlag(), getAlign(), getVertical(), getMarginX(),
                getMarginY(), getPaddingLeft(), getPaddingTop(), getRotate(), getReadersFormat(),
                getFont(), getFontSize(), getKerned(), getColor());
    }

    public TextPageAppender buildPageHeader() {
        return new TextPageAppender(AppenderFlag.OVER, getAlign(), AppenderVertical.TOP,
                getMarginX(), getMarginY(), getPaddingLeft(), getPaddingTop(), getRotate(),
                getReaderFormat(), getFont(), getFontSize(), getKerned(), getColor());
    }

    public TextPageAppender buildPageFooter() {
        return new TextPageAppender(AppenderFlag.OVER, getAlign(), AppenderVertical.BOTTOM,
                getMarginX(), getMarginY(), getPaddingLeft(), getPaddingTop(), getRotate(),
                getReaderFormat(), getFont(), getFontSize(), getKerned(), getColor());
    }

    public ImagePagesAppender buildPagesImage() {
        return new ImagePagesAppender(getFlag(), getAlign(), getVertical(), getMarginX(),
                getMarginY(), getPaddingLeft(), getPaddingTop(), getRotate(), getImageResource(),
                getInline());
    }

    public ImagePagesAppender buildPagesBackground() {
        return new ImagePagesAppender(AppenderFlag.UNDER, getAlign(), getVertical(), getMarginX(),
                getMarginY(), getPaddingLeft(), getPaddingTop(), getRotate(), getImageResource(),
                getInline());
    }

    public ImagePageAppender buildPageImage() {
        return new ImagePageAppender(getFlag(), getAlign(), getVertical(), getMarginX(),
                getMarginY(), getPaddingLeft(), getPaddingTop(), getRotate(), getImageResource(),
                getInline());
    }

    public ImagePageAppender buildPageBackground() {
        return new ImagePageAppender(AppenderFlag.UNDER, getAlign(), getVertical(), getMarginX(),
                getMarginY(), getPaddingLeft(), getPaddingTop(), getRotate(), getImageResource(),
                getInline());
    }

    protected AppenderFlag getFlag() {
        AppenderFlag ret = flag;
        if (ret == null) {
            ret = DEFAULT_FLAG;
        }
        return ret;
    }

    protected AppenderAlign getAlign() {
        AppenderAlign ret = align;
        if (ret == null) {
            ret = DEFAULT_ALIGN;
        }
        return ret;
    }

    protected AppenderVertical getVertical() {
        AppenderVertical ret = vertical;
        if (ret == null) {
            ret = DEFAULT_VERTICAL;
        }
        return ret;
    }

    protected float getMarginX() {
        Float ret = marginX;
        if (ret == null) {
            ret = DEFAULT_MARGIN_X;
        }
        return ret.floatValue();
    }

    protected float getMarginY() {
        Float ret = marginY;
        if (ret == null) {
            ret = DEFAULT_MARGIN_Y;
        }
        return ret.floatValue();
    }

    protected float getPaddingLeft() {
        Float ret = paddingLeft;
        if (ret == null) {
            ret = DEFAULT_PADDING_LEFT;
        }
        return ret.floatValue();
    }

    protected float getPaddingTop() {
        Float ret = paddingTop;
        if (ret == null) {
            ret = DEFAULT_PADDING_TOP;
        }
        return ret.floatValue();
    }

    protected String getReaderFormat() {
        String ret = format;
        if (ret == null) {
            ret = DEFAULT_READER_FORMAT;
        }
        return ret;
    }

    protected String getReadersFormat() {
        String ret = format;
        if (ret == null) {
            ret = DEFAULT_READERS_FORMAT;
        }
        return ret;
    }

    protected PDFont getFont() {
        PDFont ret = font;
        if (ret == null) {
            ret = DEFAULT_FONT;
        }
        return ret;
    }

    protected float getFontSize() {
        Float ret = fontSize;
        if (ret == null) {
            ret = DEFAULT_FONT_SIZE;
        }
        return ret.floatValue();
    }

    protected boolean getKerned() {
        Boolean ret = kerned;
        if (ret == null) {
            ret = DEFAULT_KERNED;
        }
        return ret.booleanValue();
    }

    protected Color getColor() {
        Color ret = color;
        if (ret == null) {
            ret = DEFAULT_COLOR;
        }
        return ret;
    }

    protected float getRotate() {
        Float ret = rotate;
        if (ret == null) {
            ret = DEFAULT_ROTATE;
        }
        return ret.floatValue();
    }

    protected IImageResource getImageResource() {
        IImageResource ret = imageResource;
        if (ret == null) {
            ret = DEFAULT_IMAGE_RESOURCE;
        }
        return ret;
    }

    protected boolean getInline() {
        Boolean ret = inline;
        if (ret == null) {
            ret = DEFAULT_INLINE;
        }
        return ret.booleanValue();
    }
}
