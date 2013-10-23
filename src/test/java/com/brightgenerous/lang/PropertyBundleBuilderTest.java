package com.brightgenerous.lang;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Test;

public class PropertyBundleBuilderTest {

    @Test
    public void test() throws IOException {
        PropertyResourceBundleBuilder builder = PropertyResourceBundleBuilder.create();
        builder.parentKey("extends");

        {
            InputStream is = PropertyBundleBuilderTest.class.getResourceAsStream("C.properties");
            assertNotNull(is);
            ResourceBundle rb = builder.build(is);

            assertEquals("hoge-c", rb.getString("hoge"));
            assertEquals("foo-b", rb.getString("foo"));
            assertEquals("bar-a", rb.getString("bar"));

            try {
                rb.getString("hello");
                fail();
            } catch (MissingResourceException e) {
            }
        }

        {
            InputStream isA = PropertyBundleBuilderTest.class.getResourceAsStream("A.properties");
            InputStream isB = PropertyBundleBuilderTest.class.getResourceAsStream("B.properties");
            InputStream isC = PropertyBundleBuilderTest.class.getResourceAsStream("C.properties");
            assertNotNull(isA);
            assertNotNull(isB);
            assertNotNull(isC);

            ResourceBundle rb = builder.build(isB, isC, isA);

            assertEquals("", rb.getString("hoge"));
            assertEquals("foo-b", rb.getString("foo"));
            assertEquals("bar-a", rb.getString("bar"));

            try {
                rb.getString("hello");
                fail();
            } catch (MissingResourceException e) {
            }
        }
    }
}
