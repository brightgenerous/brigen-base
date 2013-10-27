package com.brightgenerous.zxing.android;

import android.graphics.Bitmap.Config;

import com.brightgenerous.zxing.EncodeArguments;

public class BitmapEncodeArguments extends EncodeArguments {

    private final Config config;

    private final Integer frontColor;

    private final Integer backColor;

    public BitmapEncodeArguments() {
        this(null);
    }

    public BitmapEncodeArguments(Config config) {
        this(config, null);
    }

    public BitmapEncodeArguments(Config config, Integer frontColor) {
        this(config, frontColor, null);
    }

    public BitmapEncodeArguments(Config config, Integer frontColor, Integer backColor) {
        this.config = config;
        this.frontColor = frontColor;
        this.backColor = backColor;
    }

    public Config getConfig() {
        return config;
    }

    public Integer getFrontColor() {
        return frontColor;
    }

    public Integer getBackColor() {
        return backColor;
    }
}
