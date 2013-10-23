package com.brightgenerous.commons.delegate;

import static org.junit.Assert.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.junit.Test;

public class EqualsUtilityTest {

    /*
     * must exist commons lang3 EqualsBuilder in Class Path.
     */
    @Test
    public void equalsAlt() throws Exception {
        Object obj1 = new TestBeanA(0, null, null);
        Object obj2 = new TestBeanA(0, null, "bar");
        Object obj3 = new TestBeanA(10, "foo", null);
        Object obj4 = new TestBeanA(10, "foo", "bar");
        Object obj5 = new TestBeanB(10, "foo", "bar");
        Object obj6 = new TestBeanAA(10, "foo", "bar");

        assertEquals(obj1, obj1);
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
        assertEquals(obj3, obj3);
        assertEquals(obj3, obj4);
        assertNotEquals(obj4, obj5);
        assertNotEquals(obj5, obj4);
        assertEquals(obj4, obj6);
        assertEquals(obj6, obj4);
    }

    static class TestBeanA {

        private static final MethodHandle equals;

        static {
            try {
                equals = MethodHandles.lookup().findSpecial(Object.class, "equals",
                        MethodType.methodType(Boolean.TYPE, Object.class), TestBeanA.class);
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new InternalError(e.getMessage());
            }
        }

        int field1;

        String field2;

        String field3;

        TestBeanA(int field1, String field2, String field3) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsUtility.reflectionEquals(equals, this, obj, "field3");
        }
    }

    static class TestBeanAA extends TestBeanA {

        TestBeanAA(int field1, String field2, String field3) {
            super(field1, field2, field3);
        }
    }

    static class TestBeanB {

        private static final MethodHandle equals;

        static {
            try {
                equals = MethodHandles.lookup().findSpecial(Object.class, "equals",
                        MethodType.methodType(Boolean.TYPE, Object.class), TestBeanB.class);
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new InternalError(e.getMessage());
            }
        }

        int field1;

        String field2;

        String field3;

        TestBeanB(int field1, String field2, String field3) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsUtility.reflectionEquals(equals, this, obj, "field3");
        }
    }
}
