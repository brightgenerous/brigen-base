package com.brightgenerous.lang;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Test;

public class ArgsTest {

    @Test
    public void instanceOf() {
        Args.instanceOf("", String.class, "hoge");
        Args.instanceOf("", CharSequence.class, "hoge");
        Args.instanceOf("", Serializable.class, "hoge");
        try {
            Args.instanceOf("", Number.class, "hoge");
            fail();
        } catch (IllegalArgumentException e) {
        }
        Object obj = new Serializable() {

            private static final long serialVersionUID = 6668234371762322116L;
        };
        Args.instanceOf(obj, Serializable.class, "hoge");
        try {
            Args.instanceOf(obj, String.class, "hoge");
            fail();
        } catch (IllegalArgumentException e) {
        }
    }
}
