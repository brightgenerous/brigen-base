//# -*- encoding: utf-8 -*-
package com.brightgenerous.lucene;

import static org.junit.Assert.*;

import org.junit.Test;

public class LuceneUtilsTest {

    @Test
    public void near() {
        LuceneUtils utils = LuceneUtils.get();

        String[] strs = new String[] { "牛乳", "おむらいす", "open", "いっぱい" };

        assertEquals("いっぱい", utils.near("おっぱい", strs));
        assertNull(utils.near("PEN", strs));
        assertEquals("open", utils.near("oppai", strs));
        assertEquals("牛乳", utils.near("巨乳", strs));
    }
}
