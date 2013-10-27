package com.brightgenerous.commons;

import static org.junit.Assert.*;

import org.junit.Test;

public class ToStringUtilsTest {

    /*
     * must exist commons lang3 EqualsBuilder in Class Path.
     */
    @Test
    public void tos() throws Exception {
        Object obj1 = new Hoge();
        Object obj2 = new Hoge();

        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1.toString(), obj2.toString());
        assertEquals(_tos(obj1), obj1.toString());
        assertEquals(_tos(obj2), obj2.toString());
        assertNotEquals(obj1.toString(), ToStringUtils.toStringAlt(obj1));
        assertNotEquals(obj2.toString(), ToStringUtils.toStringAlt(obj2));
    }

    private String _tos(Object obj) {
        return String
                .format("%s@%s", obj.getClass().getName(), Integer.toHexString(obj.hashCode()));
    }

    static class Hoge {

        int i = 1;
    }
}
