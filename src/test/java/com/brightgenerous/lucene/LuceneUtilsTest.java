//# -*- encoding: utf-8 -*-
package com.brightgenerous.lucene;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.TreeSet;

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

    @Test
    public void comparator() {
        LuceneUtils utils = LuceneUtils.get();

        Set<String> set = new TreeSet<>();
        for (String str : new String[] { "牛乳", "おむらいす", "open", "いっぱい" }) {
            set.add(str);
        }

        {
            TreeSet<String> sorted = new TreeSet<>(utils.comparator("おっぱい"));
            sorted.addAll(set);
            assertEquals("いっぱい", sorted.first());
        }

        {
            TreeSet<String> sorted = new TreeSet<>(utils.comparator("巨乳"));
            sorted.addAll(set);
            assertEquals("牛乳", sorted.first());
        }

        {
            TreeSet<String> sorted = new TreeSet<>(utils.comparator("おむぱいす"));
            sorted.addAll(set);
            assertEquals("おむらいす", sorted.pollFirst());
            assertEquals("いっぱい", sorted.pollFirst());
        }
    }
}
