package com.brightgenerous.commons.delegate;

import static org.junit.Assert.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.junit.Test;

public class HashCodeUtilityTest {

    /*
     * must exist commons lang3 HashCodeBuilder in Class Path.
     */
    @Test
    public void hashCodeAlt() throws Exception {
        Object obj1 = new TestBeanA(0, null, null);
        Object obj2 = new TestBeanA(0, null, "bar");
        Object obj3 = new TestBeanA(10, "foo", null);
        Object obj4 = new TestBeanA(10, "foo", "bar");
        Object obj5 = new TestBeanB(10, "foo", "bar");
        Object obj6 = new TestBeanAA(10, "foo", "bar");

        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertEquals(obj3.hashCode(), obj3.hashCode());
        assertEquals(obj3.hashCode(), obj4.hashCode());
        assertEquals(obj4.hashCode(), obj5.hashCode());
        assertEquals(obj5.hashCode(), obj4.hashCode());
        assertEquals(obj4.hashCode(), obj6.hashCode());
        assertEquals(obj6.hashCode(), obj4.hashCode());
    }

    static class TestBeanA {

        private static final MethodHandle hashCode;

        static {
            try {
                hashCode = MethodHandles.lookup().findSpecial(Object.class, "hashCode",
                        MethodType.methodType(Integer.TYPE), TestBeanA.class);
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
            return HashCodeUtility.reflectionHashCode(hashCode, this, "field3");
        }
    }

    static class TestBeanAA extends TestBeanA {

        TestBeanAA(int field1, String field2, String field3) {
            super(field1, field2, field3);
        }
    }

    static class TestBeanB {

        private static final MethodHandle hashCode;

        static {
            try {
                hashCode = MethodHandles.lookup().findSpecial(Object.class, "hashCode",
                        MethodType.methodType(Integer.TYPE), TestBeanB.class);
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
            return HashCodeUtility.reflectionHashCode(hashCode, this, "field3");
        }
    }
}
