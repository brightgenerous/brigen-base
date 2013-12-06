package com.brightgenerous.zxing;

import com.brightgenerous.zxing.delegate.ZxingUtility;

@SuppressWarnings("deprecation")
public class ZxingUtils {

    public static boolean useful() {
        return ZxingUtility.useful();
    }

    public static boolean resolvedJavase() {
        return ZxingUtility.resolvedJavase();
    }

    public static boolean resolvedAndroid() {
        return ZxingUtility.resolvedAndroid();
    }

    private ZxingUtils() {
    }

    public static <B, E, R, S, D, C, DS> ZxingBuilder<B, E, R, S, D, C, DS> builder() {
        return ZxingUtility.builder();
    }
}
