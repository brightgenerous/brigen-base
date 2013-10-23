//# -*- encoding: utf-8 -*-
package com.brightgenerous.commons;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUnicodeUtilsTest {

    @Test
    public void length() {
        {
            String str = "𠮷";
            assertEquals(2, str.length());
            assertEquals(1, StringUnicodeUtils.length(str));
        }
    }

    @Test
    public void cutoff() {
        {
            String str = "abc";
            assertEquals("ab", StringUnicodeUtils.cutoff(str, 2));
            assertEquals("a", StringUnicodeUtils.cutoff(str, 1));
        }
        {
            String str = "𠮷𠮷𠮷";
            assertEquals("𠮷𠮷", StringUnicodeUtils.cutoff(str, 2));
            assertEquals("𠮷", StringUnicodeUtils.cutoff(str, 1));
        }
    }

    @Test
    public void getHalfSizeWidth() {
        assertEquals(3, StringUnicodeUtils.getHalfSizeWidth("abc"));
        assertEquals(2, StringUnicodeUtils.getHalfSizeWidth("あ"));
        assertEquals(2, StringUnicodeUtils.getHalfSizeWidth("𠮷"));
        assertEquals(8, StringUnicodeUtils.getHalfSizeWidth("か\u3099か\u3099", false));
        assertEquals(4, StringUnicodeUtils.getHalfSizeWidth("か\u3099か\u3099", true));
    }

    @Test
    public void cutoffHalfSizeWidth() {
        assertEquals("ab", StringUnicodeUtils.cutoffHalfSizeWidth("abc", 2));
        {
            String str = "𠮷𠮷𠮷";
            assertEquals("𠮷", StringUnicodeUtils.cutoffHalfSizeWidth(str, 2));
            assertEquals("𠮷", StringUnicodeUtils.cutoffHalfSizeWidth(str, 3));
            assertEquals("𠮷𠮷", StringUnicodeUtils.cutoffHalfSizeWidth(str, 4));
            assertEquals("𠮷𠮷", StringUnicodeUtils.cutoffHalfSizeWidth(str, 5));
        }
        assertEquals("あa", StringUnicodeUtils.cutoffHalfSizeWidth("あaいうえ", 3));
        assertEquals("あa", StringUnicodeUtils.cutoffHalfSizeWidth("あaいうえ", 4));
        assertEquals("が", StringUnicodeUtils.cutoffHalfSizeWidth("か\u3099か\u3099", 2, true));
    }
}
