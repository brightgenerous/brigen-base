//# -*- encoding: utf-8 -*-
package com.brightgenerous.commons;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringConvertUtilsTest {

    @Test
    public void toSnakeCase() {
        assertEquals("a_b", StringConvertUtils.toSnakeCase("aB"));
        assertEquals("a_bc", StringConvertUtils.toSnakeCase("aBc"));
        assertEquals("abc", StringConvertUtils.toSnakeCase("ABC"));
        assertEquals("ab_c", StringConvertUtils.toSnakeCase("AbC"));
        assertEquals("a100", StringConvertUtils.toSnakeCase("a100"));
        assertEquals("a100", StringConvertUtils.toSnakeCase("A100"));
        assertEquals("a100b", StringConvertUtils.toSnakeCase("A100B"));
        assertEquals("a_100", StringConvertUtils.toSnakeCase("a100", true));
        assertEquals("a_100", StringConvertUtils.toSnakeCase("A100", true));
        assertEquals("a_100_b", StringConvertUtils.toSnakeCase("A100B", true));
    }

    @Test
    public void toCamelCase() {
        assertEquals("aB", StringConvertUtils.toCamelCase("a_b"));
        assertEquals("aBc", StringConvertUtils.toCamelCase("a_bc"));
        assertEquals("abc", StringConvertUtils.toCamelCase("abc"));
        assertEquals("abC", StringConvertUtils.toCamelCase("ab_c"));
        assertEquals("a100", StringConvertUtils.toCamelCase("a100"));
        assertEquals("a100", StringConvertUtils.toCamelCase("a100"));
        assertEquals("a100b", StringConvertUtils.toCamelCase("a100b"));
        assertEquals("a100", StringConvertUtils.toCamelCase("a_100", true));
        assertEquals("a100", StringConvertUtils.toCamelCase("a100", true));
        assertEquals("a100B", StringConvertUtils.toCamelCase("a_100_b", true));
    }

    @Test
    public void toHalf() {
        String strWin = "ＡＢブライジェン１２３￥";
        String strOth = "ＡＢブライジェン１２３＼";
        String str1 = "ABﾌﾞﾗｲｼﾞｪﾝ123\\";

        assertEquals(str1, StringConvertUtils.toHalf(strWin));
        assertEquals(str1, StringConvertUtils.toHalf(strOth));
        assertTrue(strWin.equals(StringConvertUtils.toFull(str1))
                || strOth.equals(StringConvertUtils.toFull(str1)));
    }
}
