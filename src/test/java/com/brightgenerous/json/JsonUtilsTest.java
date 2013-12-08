package com.brightgenerous.json;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

public class JsonUtilsTest {

    @Test
    public void formatJson() throws JsonException {

        assertTrue(JsonUtils.gson());
        assertFalse(JsonUtils.jsonic());
        assertFalse(JsonUtils.jackson());

        JsonUtils util = JsonUtils.get();

        Foo<Bar> obj1 = new Foo<>();
        Foo<Bar> obj2;
        {
            obj1.setObj(new Bar());
            obj1.getObj().setStr("bar.str");
            obj1.setHoge(new Hoge());
            obj1.getHoge().setObj(Integer.valueOf(100));
            String json = util.formatJson(obj1, new TypeToken<Foo<Bar>>() {
            }.getType());
            obj2 = util.parseJson(new StringReader(json), new TypeToken<Foo<Bar>>() {
            }.getType());
        }

        assertEquals(obj1.getObj().getClass(), obj2.getObj().getClass());
        assertEquals(obj1.getObj().getStr(), "bar.str");
        assertEquals(obj1.getObj().getStr(), obj2.getObj().getStr());
        assertEquals(obj1.getHoge().getClass(), obj2.getHoge().getClass());
        assertEquals(obj1.getHoge().getObj(), obj2.getHoge().getObj());
    }

    static class Foo<T> {

        private T obj;

        private Hoge hoge;

        public T getObj() {
            return obj;
        }

        public void setObj(T obj) {
            this.obj = obj;
        }

        public Hoge getHoge() {
            return hoge;
        }

        public void setHoge(Hoge hoge) {
            this.hoge = hoge;
        }
    }

    static class Bar {

        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

    static class Hoge {

        private Integer obj;

        public Integer getObj() {
            return obj;
        }

        public void setObj(Integer obj) {
            this.obj = obj;
        }
    }
}
