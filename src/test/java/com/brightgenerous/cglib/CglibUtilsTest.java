package com.brightgenerous.cglib;

import static org.junit.Assert.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import org.junit.Test;

public class CglibUtilsTest {

    @Test
    public void createInterface() throws Exception {

        Class<?> subClass = CglibUtils.defineInterface(Foo.class.getName().replace("Foo", "Gen"),
                Foo.class, Bar.class, Hoge.class);

        assertTrue(Foo.class.isAssignableFrom(subClass));
        assertTrue(Bar.class.isAssignableFrom(subClass));
        assertEquals(Foo.class.getName(), subClass.getName().replace("Gen", "Foo"));

        {
            Method method = If.class.getMethod("function");
            assertNull(method.getAnnotation(Ant1.class));
            assertNull(method.getAnnotation(Ant2.class));
            assertNotNull(method.getAnnotation(Ant3.class));
        }
        {
            Method method = subClass.getMethod("function");
            assertNotNull(method.getAnnotation(Ant1.class));
            assertNull(method.getAnnotation(Ant2.class));
            assertNull(method.getAnnotation(Ant3.class));
        }

    }

    public interface Foo {

        @Ant1
        void function();
    }

    public interface Bar {

        @Ant2
        void function();
    }

    public interface Hoge {

        @Ant3
        void function();
    }

    public interface If extends Hoge, Foo, Bar {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    protected static @interface Ant1 {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    protected static @interface Ant2 {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    protected static @interface Ant3 {
    }
}
