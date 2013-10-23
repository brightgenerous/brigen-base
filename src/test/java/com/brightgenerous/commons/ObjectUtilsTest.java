package com.brightgenerous.commons;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import org.junit.Test;

public class ObjectUtilsTest {

    @Test
    public void hashCodeEscapeNull() throws Exception {
        Object obj = new Object();
        assertEquals(obj.hashCode(), ObjectUtils.hashCodeEscapeNull(obj));
        assertEquals(0, ObjectUtils.hashCodeEscapeNull(null));
    }

    @Test
    public void equalsEscapeNull() throws Exception {
        Object obj1 = new Object();
        Object obj2 = new Object();
        assertEquals(obj1.equals(null), ObjectUtils.equalsEscapeNull(obj1, null));
        assertEquals(obj1.equals(obj1), ObjectUtils.equalsEscapeNull(obj1, obj1));
        assertEquals(obj1.equals(obj2), ObjectUtils.equalsEscapeNull(obj1, obj2));
    }

    @Test
    public void cast() throws Exception {
        Object str1 = "foo";
        Object str2 = "bar";
        Object lng1 = Integer.valueOf(100);
        assertEquals(str1, ObjectUtils.cast(str1, Object.class));
        assertEquals(str1, ObjectUtils.cast(str1, String.class));
        assertEquals(str2, ObjectUtils.cast(lng1, str2, String.class));
        assertNull(ObjectUtils.cast(str1, Integer.class));
        assertEquals(lng1, ObjectUtils.cast(str1, lng1, Integer.class));
    }

    @Test
    public void isNoSize() throws Exception {
        assertTrue(ObjectUtils.isNoSize((boolean[]) null));
        assertTrue(ObjectUtils.isNoSize(new boolean[] {}));
        assertTrue(ObjectUtils.isNotNoSize(new boolean[] { true }));

        assertTrue(ObjectUtils.isNoSize((byte[]) null));
        assertTrue(ObjectUtils.isNoSize(new byte[] {}));
        assertTrue(ObjectUtils.isNotNoSize(new byte[] { (byte) 1 }));

        assertTrue(ObjectUtils.isNoSize((short[]) null));
        assertTrue(ObjectUtils.isNoSize(new short[] {}));
        assertTrue(ObjectUtils.isNotNoSize(new short[] { (short) 1 }));

        assertTrue(ObjectUtils.isNoSize((char[]) null));
        assertTrue(ObjectUtils.isNoSize(new char[] {}));
        assertTrue(ObjectUtils.isNotNoSize(new char[] { 'a' }));

        assertTrue(ObjectUtils.isNoSize((int[]) null));
        assertTrue(ObjectUtils.isNoSize(new int[] {}));
        assertTrue(ObjectUtils.isNotNoSize(new int[] { 1 }));

        assertTrue(ObjectUtils.isNoSize((long[]) null));
        assertTrue(ObjectUtils.isNoSize(new long[] {}));
        assertTrue(ObjectUtils.isNotNoSize(new long[] { 1L }));

        assertTrue(ObjectUtils.isNoSize((float[]) null));
        assertTrue(ObjectUtils.isNoSize(new float[] {}));
        assertTrue(ObjectUtils.isNotNoSize(new float[] { 1F }));

        assertTrue(ObjectUtils.isNoSize((double[]) null));
        assertTrue(ObjectUtils.isNoSize(new double[] {}));
        assertTrue(ObjectUtils.isNotNoSize(new double[] { 1D }));

        assertTrue(ObjectUtils.isNoSize((Object[]) null));
        assertTrue(ObjectUtils.isNoSize(new Object[] {}));
        assertTrue(ObjectUtils.isNotNoSize(new Object[] { null }));

        assertTrue(ObjectUtils.isNoSize((Collection<?>) null));
        assertTrue(ObjectUtils.isNoSize(Collections.emptyList()));
        assertTrue(ObjectUtils.isNotNoSize(Arrays.asList("foo")));
    }

    @Test
    public void defaultIfNull() throws Exception {
        Object obj1 = null;
        Object obj2 = "foo";
        Object obj3 = "bar";
        assertEquals(obj2, ObjectUtils.defaultIfNull(obj1, obj2, obj3));
        assertEquals(obj3, ObjectUtils.defaultIfNull(obj1, null, obj3));
        assertNull(ObjectUtils.defaultIfNull(obj1, null, obj1));
    }

    @Test
    public void getSingleOrException() {
        assertTrue(ObjectUtils.getSingleOrException((boolean[]) null, true));
        assertTrue(ObjectUtils.getSingleOrException((boolean[]) null, true, false));
        try {
            ObjectUtils.getSingleOrException((boolean[]) null, true, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getSingleOrException(new boolean[] {}, true));
        assertTrue(ObjectUtils.getSingleOrException(new boolean[] {}, true, false));
        try {
            ObjectUtils.getSingleOrException(new boolean[] {}, true, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertFalse(ObjectUtils.getSingleOrException(new boolean[] { false }, true));
        assertFalse(ObjectUtils.getSingleOrException(new boolean[] { false }, true, false));
        assertFalse(ObjectUtils.getSingleOrException(new boolean[] { false }, true, true));
        try {
            ObjectUtils.getSingleOrException(new boolean[] { true, true }, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new boolean[] { true, true }, true, false);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new boolean[] { true, true }, true, true);
            fail();
        } catch (IllegalArgumentException e) {
        }

        assertEquals((byte) 1, ObjectUtils.getSingleOrException((byte[]) null, (byte) 1));
        assertEquals((byte) 1, ObjectUtils.getSingleOrException((byte[]) null, (byte) 1, false));
        try {
            ObjectUtils.getSingleOrException((byte[]) null, (byte) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((byte) 1, ObjectUtils.getSingleOrException(new byte[] {}, (byte) 1));
        assertEquals((byte) 1, ObjectUtils.getSingleOrException(new byte[] {}, (byte) 1, false));
        try {
            ObjectUtils.getSingleOrException(new byte[] {}, (byte) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((byte) 2, ObjectUtils.getSingleOrException(new byte[] { (byte) 2 }, (byte) 1));
        assertEquals((byte) 2,
                ObjectUtils.getSingleOrException(new byte[] { (byte) 2 }, (byte) 1, false));
        assertEquals((byte) 2,
                ObjectUtils.getSingleOrException(new byte[] { (byte) 2 }, (byte) 1, true));
        try {
            ObjectUtils.getSingleOrException(new byte[] { (byte) 1, (byte) 1 }, (byte) 1);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new byte[] { (byte) 1, (byte) 1 }, (byte) 1, false);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new byte[] { (byte) 1, (byte) 1 }, (byte) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }

        assertEquals((short) 1, ObjectUtils.getSingleOrException((short[]) null, (short) 1));
        assertEquals((short) 1, ObjectUtils.getSingleOrException((short[]) null, (short) 1, false));
        try {
            ObjectUtils.getSingleOrException((short[]) null, (short) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((short) 1, ObjectUtils.getSingleOrException(new short[] {}, (short) 1));
        assertEquals((short) 1, ObjectUtils.getSingleOrException(new short[] {}, (short) 1, false));
        try {
            ObjectUtils.getSingleOrException(new short[] {}, (short) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((short) 2,
                ObjectUtils.getSingleOrException(new short[] { (short) 2 }, (short) 1));
        assertEquals((short) 2,
                ObjectUtils.getSingleOrException(new short[] { (short) 2 }, (short) 1, false));
        assertEquals((short) 2,
                ObjectUtils.getSingleOrException(new short[] { (short) 2 }, (short) 1, true));
        try {
            ObjectUtils.getSingleOrException(new short[] { (short) 1, (short) 2 }, (short) 1);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils
                    .getSingleOrException(new short[] { (short) 1, (short) 2 }, (short) 1, false);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new short[] { (short) 1, (short) 2 }, (short) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }

        assertEquals('a', ObjectUtils.getSingleOrException((char[]) null, 'a'));
        assertEquals('a', ObjectUtils.getSingleOrException((char[]) null, 'a', false));
        try {
            ObjectUtils.getSingleOrException((char[]) null, 'a', true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals('a', ObjectUtils.getSingleOrException(new char[] {}, 'a'));
        assertEquals('a', ObjectUtils.getSingleOrException(new char[] {}, 'a', false));
        try {
            ObjectUtils.getSingleOrException(new char[] {}, 'a', true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals('b', ObjectUtils.getSingleOrException(new char[] { 'b' }, 'a'));
        assertEquals('b', ObjectUtils.getSingleOrException(new char[] { 'b' }, 'a', false));
        assertEquals('b', ObjectUtils.getSingleOrException(new char[] { 'b' }, 'a', true));
        try {
            ObjectUtils.getSingleOrException(new char[] { 'a', 'a' }, 'a');
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new char[] { 'a', 'a' }, 'a', false);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new char[] { 'a', 'a' }, 'a', true);
            fail();
        } catch (IllegalArgumentException e) {
        }

        assertEquals(1, ObjectUtils.getSingleOrException((int[]) null, 1));
        assertEquals(1, ObjectUtils.getSingleOrException((int[]) null, 1, false));
        try {
            ObjectUtils.getSingleOrException((int[]) null, 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(1, ObjectUtils.getSingleOrException(new int[] {}, 1));
        assertEquals(1, ObjectUtils.getSingleOrException(new int[] {}, 1, false));
        try {
            ObjectUtils.getSingleOrException(new int[] {}, 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(2, ObjectUtils.getSingleOrException(new int[] { 2 }, 1));
        assertEquals(2, ObjectUtils.getSingleOrException(new int[] { 2 }, 1, false));
        assertEquals(2, ObjectUtils.getSingleOrException(new int[] { 2 }, 1, true));
        try {
            ObjectUtils.getSingleOrException(new int[] { 1, 1 }, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new int[] { 1, 1 }, 1, false);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new int[] { 1, 1 }, 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }

        assertEquals(1L, ObjectUtils.getSingleOrException((long[]) null, 1L));
        assertEquals(1L, ObjectUtils.getSingleOrException((long[]) null, 1L, false));
        try {
            ObjectUtils.getSingleOrException((long[]) null, 1L, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(1L, ObjectUtils.getSingleOrException(new long[] {}, 1L));
        assertEquals(1L, ObjectUtils.getSingleOrException(new long[] {}, 1L, false));
        try {
            ObjectUtils.getSingleOrException(new long[] {}, 1L, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(2L, ObjectUtils.getSingleOrException(new long[] { 2L }, 1L));
        assertEquals(2, ObjectUtils.getSingleOrException(new long[] { 2L }, 1L, false));
        assertEquals(2L, ObjectUtils.getSingleOrException(new long[] { 2L }, 1L, true));
        try {
            ObjectUtils.getSingleOrException(new long[] { 1L, 1L }, 1L);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new long[] { 1L, 1L }, 1L, false);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new long[] { 1L, 1L }, 1L, true);
            fail();
        } catch (IllegalArgumentException e) {
        }

        assertTrue(ObjectUtils.getSingleOrException((float[]) null, 1F) == 1F);
        assertTrue(ObjectUtils.getSingleOrException((float[]) null, 1F, false) == 1F);
        try {
            ObjectUtils.getSingleOrException((float[]) null, 1F, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getSingleOrException(new float[] {}, 1F) == 1F);
        assertTrue(ObjectUtils.getSingleOrException(new float[] {}, 1F, false) == 1F);
        try {
            ObjectUtils.getSingleOrException(new float[] {}, 1F, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getSingleOrException(new float[] { 2F }, 1F) == 2F);
        assertTrue(ObjectUtils.getSingleOrException(new float[] { 2F }, 1F, false) == 2F);
        assertTrue(ObjectUtils.getSingleOrException(new float[] { 2F }, 1F, true) == 2F);
        try {
            ObjectUtils.getSingleOrException(new float[] { 1F, 1F }, 1F);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new float[] { 1F, 1F }, 1F, false);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new float[] { 1F, 1F }, 1F, true);
            fail();
        } catch (IllegalArgumentException e) {
        }

        assertTrue(ObjectUtils.getSingleOrException((double[]) null, 1D) == 1D);
        assertTrue(ObjectUtils.getSingleOrException((double[]) null, 1D, false) == 1D);
        try {
            ObjectUtils.getSingleOrException((double[]) null, 1D, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getSingleOrException(new double[] {}, 1D) == 1D);
        assertTrue(ObjectUtils.getSingleOrException(new double[] {}, 1D, false) == 1D);
        try {
            ObjectUtils.getSingleOrException(new double[] {}, 1D, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getSingleOrException(new double[] { 2D }, 1D) == 2D);
        assertTrue(ObjectUtils.getSingleOrException(new double[] { 2D }, 1D, false) == 2D);
        assertTrue(ObjectUtils.getSingleOrException(new double[] { 2D }, 1D, true) == 2D);
        try {
            ObjectUtils.getSingleOrException(new double[] { 1D, 1D }, 1D);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new double[] { 1D, 1D }, 1D, false);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new double[] { 1D, 1D }, 1D, true);
            fail();
        } catch (IllegalArgumentException e) {
        }

        Object obj = Integer.valueOf(100);

        assertNull(ObjectUtils.getSingleOrException((Object[]) null));
        assertNull(ObjectUtils.getSingleOrException((Object[]) null, false));
        try {
            ObjectUtils.getSingleOrException((Object[]) null, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertNull(ObjectUtils.getSingleOrException(new Object[] {}));
        assertNull(ObjectUtils.getSingleOrException(new Object[] {}, false));
        try {
            ObjectUtils.getSingleOrException(new Object[] {}, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(obj, ObjectUtils.getSingleOrException(new Object[] { obj }));
        assertEquals(obj, ObjectUtils.getSingleOrException(new Object[] { obj }, false));
        assertEquals(obj, ObjectUtils.getSingleOrException(new Object[] { obj }, true));
        try {
            ObjectUtils.getSingleOrException(new Object[] { obj, obj });
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new Object[] { obj, obj }, false);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(new Object[] { obj, obj }, true);
            fail();
        } catch (IllegalArgumentException e) {
        }

        assertNull(ObjectUtils.getSingleOrException((Collection<?>) null));
        assertNull(ObjectUtils.getSingleOrException((Collection<?>) null, false));
        try {
            ObjectUtils.getSingleOrException((Collection<?>) null, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertNull(ObjectUtils.getSingleOrException(Collections.emptyList()));
        assertNull(ObjectUtils.getSingleOrException(Collections.emptyList(), false));
        try {
            ObjectUtils.getSingleOrException(Collections.emptyList(), true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(obj, ObjectUtils.getSingleOrException(Arrays.asList(obj)));
        assertEquals(obj, ObjectUtils.getSingleOrException(Arrays.asList(obj), false));
        assertEquals(obj, ObjectUtils.getSingleOrException(Arrays.asList(obj), true));
        try {
            ObjectUtils.getSingleOrException(Arrays.asList(1, 2));
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(Arrays.asList(1, 2), false);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            ObjectUtils.getSingleOrException(Arrays.asList(1, 2), true);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void getFirstLast() throws Exception {
        assertTrue(ObjectUtils.getFirst((boolean[]) null, true));
        assertTrue(ObjectUtils.getFirst((boolean[]) null, true, false));
        try {
            ObjectUtils.getFirst((boolean[]) null, true, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getFirst(new boolean[] {}, true));
        assertTrue(ObjectUtils.getFirst(new boolean[] {}, true, false));
        try {
            ObjectUtils.getFirst(new boolean[] {}, true, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertFalse(ObjectUtils.getFirst(new boolean[] { false, true }, true));
        assertFalse(ObjectUtils.getFirst(new boolean[] { false, true }, true, false));
        assertFalse(ObjectUtils.getFirst(new boolean[] { false, true }, true, true));

        assertTrue(ObjectUtils.getLast((boolean[]) null, true));
        assertTrue(ObjectUtils.getLast((boolean[]) null, true, false));
        try {
            ObjectUtils.getLast((boolean[]) null, true, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getLast(new boolean[] {}, true));
        assertTrue(ObjectUtils.getLast(new boolean[] {}, true, false));
        try {
            ObjectUtils.getLast(new boolean[] {}, true, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertFalse(ObjectUtils.getLast(new boolean[] { true, false }, true));
        assertFalse(ObjectUtils.getLast(new boolean[] { true, false }, true, false));
        assertFalse(ObjectUtils.getLast(new boolean[] { true, false }, true, true));

        assertEquals((byte) 1, ObjectUtils.getFirst((byte[]) null, (byte) 1));
        assertEquals((byte) 1, ObjectUtils.getFirst((byte[]) null, (byte) 1, false));
        try {
            ObjectUtils.getFirst((byte[]) null, (byte) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((byte) 1, ObjectUtils.getFirst(new byte[] {}, (byte) 1));
        assertEquals((byte) 1, ObjectUtils.getFirst(new byte[] {}, (byte) 1, false));
        try {
            ObjectUtils.getFirst(new byte[] {}, (byte) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((byte) 2, ObjectUtils.getFirst(new byte[] { (byte) 2, (byte) 1 }, (byte) 1));
        assertEquals((byte) 2,
                ObjectUtils.getFirst(new byte[] { (byte) 2, (byte) 1 }, (byte) 1, false));
        assertEquals((byte) 2,
                ObjectUtils.getFirst(new byte[] { (byte) 2, (byte) 1 }, (byte) 1, true));

        assertEquals((byte) 1, ObjectUtils.getLast((byte[]) null, (byte) 1));
        assertEquals((byte) 1, ObjectUtils.getLast((byte[]) null, (byte) 1, false));
        try {
            ObjectUtils.getLast((byte[]) null, (byte) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((byte) 1, ObjectUtils.getLast(new byte[] {}, (byte) 1));
        assertEquals((byte) 1, ObjectUtils.getLast(new byte[] {}, (byte) 1, false));
        try {
            ObjectUtils.getLast(new byte[] {}, (byte) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((byte) 2, ObjectUtils.getLast(new byte[] { (byte) 1, (byte) 2 }, (byte) 1));
        assertEquals((byte) 2,
                ObjectUtils.getLast(new byte[] { (byte) 1, (byte) 2 }, (byte) 1, false));
        assertEquals((byte) 2,
                ObjectUtils.getLast(new byte[] { (byte) 1, (byte) 2 }, (byte) 1, true));

        assertEquals((short) 1, ObjectUtils.getFirst((short[]) null, (short) 1));
        assertEquals((short) 1, ObjectUtils.getFirst((short[]) null, (short) 1, false));
        try {
            ObjectUtils.getFirst((short[]) null, (short) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((short) 1, ObjectUtils.getFirst(new short[] {}, (short) 1));
        assertEquals((short) 1, ObjectUtils.getFirst(new short[] {}, (short) 1, false));
        try {
            ObjectUtils.getFirst(new short[] {}, (short) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((short) 2,
                ObjectUtils.getFirst(new short[] { (short) 2, (short) 1 }, (short) 1));
        assertEquals((short) 2,
                ObjectUtils.getFirst(new short[] { (short) 2, (short) 1 }, (short) 1, false));
        assertEquals((short) 2,
                ObjectUtils.getFirst(new short[] { (short) 2, (short) 1 }, (short) 1, true));

        assertEquals((short) 1, ObjectUtils.getLast((short[]) null, (short) 1));
        assertEquals((short) 1, ObjectUtils.getLast((short[]) null, (short) 1, false));
        try {
            ObjectUtils.getLast((short[]) null, (short) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((short) 1, ObjectUtils.getLast(new short[] {}, (short) 1));
        assertEquals((short) 1, ObjectUtils.getLast(new short[] {}, (short) 1, false));
        try {
            ObjectUtils.getLast(new short[] {}, (short) 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals((short) 2,
                ObjectUtils.getLast(new short[] { (short) 1, (short) 2 }, (short) 1));
        assertEquals((short) 2,
                ObjectUtils.getLast(new short[] { (short) 1, (short) 2 }, (short) 1, false));
        assertEquals((short) 2,
                ObjectUtils.getLast(new short[] { (short) 1, (short) 2 }, (short) 1, true));

        assertEquals('a', ObjectUtils.getFirst((char[]) null, 'a'));
        assertEquals('a', ObjectUtils.getFirst((char[]) null, 'a', false));
        try {
            ObjectUtils.getFirst((char[]) null, 'a', true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals('a', ObjectUtils.getFirst(new char[] {}, 'a'));
        assertEquals('a', ObjectUtils.getFirst(new char[] {}, 'a', false));
        try {
            ObjectUtils.getFirst(new char[] {}, 'a', true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals('b', ObjectUtils.getFirst(new char[] { 'b', 'a' }, 'a'));
        assertEquals('b', ObjectUtils.getFirst(new char[] { 'b', 'a' }, 'a', false));
        assertEquals('b', ObjectUtils.getFirst(new char[] { 'b', 'a' }, 'a', true));

        assertEquals('a', ObjectUtils.getLast((char[]) null, 'a'));
        assertEquals('a', ObjectUtils.getLast((char[]) null, 'a', false));
        try {
            ObjectUtils.getLast((char[]) null, 'a', true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals('a', ObjectUtils.getLast(new char[] {}, 'a'));
        assertEquals('a', ObjectUtils.getLast(new char[] {}, 'a', false));
        try {
            ObjectUtils.getLast(new char[] {}, 'a', true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals('b', ObjectUtils.getLast(new char[] { 'a', 'b' }, 'a'));
        assertEquals('b', ObjectUtils.getLast(new char[] { 'a', 'b' }, 'a', false));
        assertEquals('b', ObjectUtils.getLast(new char[] { 'a', 'b' }, 'a', true));

        assertEquals(1, ObjectUtils.getFirst((int[]) null, 1));
        assertEquals(1, ObjectUtils.getFirst((int[]) null, 1, false));
        try {
            ObjectUtils.getFirst((int[]) null, 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(1, ObjectUtils.getFirst(new int[] {}, 1));
        assertEquals(1, ObjectUtils.getFirst(new int[] {}, 1, false));
        try {
            ObjectUtils.getFirst(new int[] {}, 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(2, ObjectUtils.getFirst(new int[] { 2, 1 }, 1));
        assertEquals(2, ObjectUtils.getFirst(new int[] { 2, 1 }, 1, false));
        assertEquals(2, ObjectUtils.getFirst(new int[] { 2, 1 }, 1, true));

        assertEquals(1, ObjectUtils.getLast((int[]) null, 1));
        assertEquals(1, ObjectUtils.getLast((int[]) null, 1, false));
        try {
            ObjectUtils.getLast((int[]) null, 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(1, ObjectUtils.getLast(new int[] {}, 1));
        assertEquals(1, ObjectUtils.getLast(new int[] {}, 1, false));
        try {
            ObjectUtils.getLast(new int[] {}, 1, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(2, ObjectUtils.getLast(new int[] { 1, 2 }, 1));
        assertEquals(2, ObjectUtils.getLast(new int[] { 1, 2 }, 1, false));
        assertEquals(2, ObjectUtils.getLast(new int[] { 1, 2 }, 1, true));

        assertEquals(1L, ObjectUtils.getFirst((long[]) null, 1L));
        assertEquals(1L, ObjectUtils.getFirst((long[]) null, 1L, false));
        try {
            ObjectUtils.getFirst((long[]) null, 1L, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(1L, ObjectUtils.getFirst(new long[] {}, 1L));
        assertEquals(1L, ObjectUtils.getFirst(new long[] {}, 1L, false));
        try {
            ObjectUtils.getFirst(new long[] {}, 1L, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(2L, ObjectUtils.getFirst(new long[] { 2L, 1L }, 1L));
        assertEquals(2L, ObjectUtils.getFirst(new long[] { 2L, 1L }, 1L, false));
        assertEquals(2L, ObjectUtils.getFirst(new long[] { 2L, 1L }, 1L, true));

        assertEquals(1, ObjectUtils.getLast((long[]) null, 1L));
        assertEquals(1, ObjectUtils.getLast((long[]) null, 1L, false));
        try {
            ObjectUtils.getLast((long[]) null, 1L, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(1L, ObjectUtils.getLast(new long[] {}, 1L));
        assertEquals(1L, ObjectUtils.getLast(new long[] {}, 1L, false));
        try {
            ObjectUtils.getLast(new long[] {}, 1L, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(2L, ObjectUtils.getLast(new long[] { 1L, 2L }, 1L));
        assertEquals(2L, ObjectUtils.getLast(new long[] { 1L, 2L }, 1L, false));
        assertEquals(2L, ObjectUtils.getLast(new long[] { 1L, 2L }, 1L, true));

        assertTrue(ObjectUtils.getFirst((float[]) null, 1F) == 1F);
        assertTrue(ObjectUtils.getFirst((float[]) null, 1F, false) == 1F);
        try {
            ObjectUtils.getFirst((float[]) null, 1F, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getFirst(new float[] {}, 1F) == 1F);
        assertTrue(ObjectUtils.getFirst(new float[] {}, 1F, false) == 1F);
        try {
            ObjectUtils.getFirst(new float[] {}, 1F, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getFirst(new float[] { 2F, 1F }, 1F) == 2F);
        assertTrue(ObjectUtils.getFirst(new float[] { 2F, 1F }, 1F, false) == 2F);
        assertTrue(ObjectUtils.getFirst(new float[] { 2F, 1F }, 1F, true) == 2F);

        assertTrue(ObjectUtils.getLast((float[]) null, 1F) == 1F);
        assertTrue(ObjectUtils.getLast((float[]) null, 1F, false) == 1F);
        try {
            ObjectUtils.getLast((float[]) null, 1F, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getLast(new float[] {}, 1F) == 1F);
        assertTrue(ObjectUtils.getLast(new float[] {}, 1F, false) == 1F);
        try {
            ObjectUtils.getLast(new float[] {}, 1F, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getLast(new float[] { 1F, 2F }, 1F) == 2F);
        assertTrue(ObjectUtils.getLast(new float[] { 1F, 2F }, 1F, false) == 2F);
        assertTrue(ObjectUtils.getLast(new float[] { 1F, 2F }, 1F, true) == 2F);

        assertTrue(ObjectUtils.getFirst((double[]) null, 1D) == 1D);
        assertTrue(ObjectUtils.getFirst((double[]) null, 1D, false) == 1D);
        try {
            ObjectUtils.getFirst((double[]) null, 1D, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getFirst(new double[] {}, 1D) == 1D);
        assertTrue(ObjectUtils.getFirst(new double[] {}, 1D, false) == 1D);
        try {
            ObjectUtils.getFirst(new double[] {}, 1D, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getFirst(new double[] { 2D, 1D }, 1D) == 2D);
        assertTrue(ObjectUtils.getFirst(new double[] { 2D, 1D }, 1D, false) == 2D);
        assertTrue(ObjectUtils.getFirst(new double[] { 2D, 1D }, 1D, true) == 2D);

        assertTrue(ObjectUtils.getLast((double[]) null, 1D) == 1D);
        assertTrue(ObjectUtils.getLast((double[]) null, 1D, false) == 1D);
        try {
            ObjectUtils.getLast((double[]) null, 1D, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getLast(new double[] {}, 1D) == 1D);
        assertTrue(ObjectUtils.getLast(new double[] {}, 1D, false) == 1D);
        try {
            ObjectUtils.getLast(new double[] {}, 1D, true);
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertTrue(ObjectUtils.getLast(new double[] { 1D, 2D }, 1D) == 2D);
        assertTrue(ObjectUtils.getLast(new double[] { 1D, 2D }, 1D, false) == 2D);
        assertTrue(ObjectUtils.getLast(new double[] { 1D, 2D }, 1D, true) == 2D);

        Object obj1 = "foo";
        Object obj2 = "bar";

        assertNull(ObjectUtils.getFirst((Object[]) null));
        assertNull(ObjectUtils.getFirst((Object[]) null, false));
        try {
            assertNull(ObjectUtils.getFirst((Object[]) null, true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertNull(ObjectUtils.getFirst(new Object[] {}));
        assertNull(ObjectUtils.getFirst(new Object[] {}, false));
        try {
            assertNull(ObjectUtils.getFirst(new Object[] {}, true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(obj1, ObjectUtils.getFirst(new Object[] { obj1, null }));
        assertEquals(obj1, ObjectUtils.getFirst(new Object[] { obj1, null }, false));
        assertEquals(obj1, ObjectUtils.getFirst(new Object[] { obj1, null }, true));

        assertNull(ObjectUtils.getLast((Object[]) null));
        assertNull(ObjectUtils.getLast((Object[]) null, false));
        try {
            assertNull(ObjectUtils.getLast((Object[]) null, true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertNull(ObjectUtils.getLast(new Object[] {}));
        assertNull(ObjectUtils.getLast(new Object[] {}, false));
        try {
            assertNull(ObjectUtils.getLast(new Object[] {}, true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(obj1, ObjectUtils.getLast(new Object[] { null, obj1 }));
        assertEquals(obj1, ObjectUtils.getLast(new Object[] { null, obj1 }, false));
        assertEquals(obj1, ObjectUtils.getLast(new Object[] { null, obj1 }, true));

        assertNull(ObjectUtils.getFirst((List<?>) null));
        assertNull(ObjectUtils.getFirst((List<?>) null, false));
        try {
            assertNull(ObjectUtils.getFirst((List<?>) null, true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertNull(ObjectUtils.getFirst(Collections.emptyList()));
        assertNull(ObjectUtils.getFirst(Collections.emptyList(), false));
        try {
            assertNull(ObjectUtils.getFirst(Collections.emptyList(), true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(obj1, ObjectUtils.getFirst(Arrays.asList(obj1, null)));
        assertEquals(obj1, ObjectUtils.getFirst(Arrays.asList(obj1, null), false));
        assertEquals(obj1, ObjectUtils.getFirst(Arrays.asList(obj1, null), true));

        assertNull(ObjectUtils.getLast((List<?>) null));
        assertNull(ObjectUtils.getLast((List<?>) null, false));
        try {
            assertNull(ObjectUtils.getLast((List<?>) null, true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertNull(ObjectUtils.getLast(Collections.emptyList()));
        assertNull(ObjectUtils.getLast(Collections.emptyList(), false));
        try {
            assertNull(ObjectUtils.getLast(Collections.emptyList(), true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(obj1, ObjectUtils.getLast(Arrays.asList(null, obj1)));
        assertEquals(obj1, ObjectUtils.getLast(Arrays.asList(null, obj1), false));
        assertEquals(obj1, ObjectUtils.getLast(Arrays.asList(null, obj1), true));

        assertNull(ObjectUtils.getFirst((SortedSet<?>) null));
        assertNull(ObjectUtils.getFirst((SortedSet<?>) null, false));
        try {
            assertNull(ObjectUtils.getFirst((SortedSet<?>) null, true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertNull(ObjectUtils.getFirst(new TreeSet<>()));
        assertNull(ObjectUtils.getFirst(new TreeSet<>(), false));
        try {
            assertNull(ObjectUtils.getFirst(new TreeSet<>(), true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(obj2, ObjectUtils.getFirst(new TreeSet<>(Arrays.asList(obj1, obj2))));
        assertEquals(obj2, ObjectUtils.getFirst(new TreeSet<>(Arrays.asList(obj1, obj2)), false));
        assertEquals(obj2, ObjectUtils.getFirst(new TreeSet<>(Arrays.asList(obj1, obj2)), true));

        assertNull(ObjectUtils.getLast((SortedSet<?>) null));
        assertNull(ObjectUtils.getLast((SortedSet<?>) null, false));
        try {
            assertNull(ObjectUtils.getLast((SortedSet<?>) null, true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertNull(ObjectUtils.getLast(new TreeSet<>()));
        assertNull(ObjectUtils.getLast(new TreeSet<>(), false));
        try {
            assertNull(ObjectUtils.getLast(new TreeSet<>(), true));
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals(obj1, ObjectUtils.getLast(new TreeSet<>(Arrays.asList(obj2, obj1))));
        assertEquals(obj1, ObjectUtils.getLast(new TreeSet<>(Arrays.asList(obj2, obj1)), false));
        assertEquals(obj1, ObjectUtils.getLast(new TreeSet<>(Arrays.asList(obj2, obj1)), true));
    }

    @Test
    public void convertBytesToHexString() throws Exception {
        for (int i = 0; i < 5; i++) {
            String plainStr = UUID.randomUUID().toString();
            byte[] plainBytes = plainStr.getBytes();
            String bytesStr = ObjectUtils.convertBytesToHexString(plainBytes);
            String bytesStrUp = ObjectUtils.convertBytesToHexString(plainBytes, true);
            assertTrue(bytesStr.matches("^[a-z0-9]*$"));
            assertTrue(bytesStrUp.matches("^[A-Z0-9]*$"));
            assertTrue(bytesStr.equalsIgnoreCase(bytesStrUp));
            byte[] restoreBytes = ObjectUtils.convertHexStringToBytes(bytesStr);
            assertArrayEquals(plainBytes, restoreBytes);
            assertEquals(plainStr, new String(restoreBytes));
        }
    }
}
