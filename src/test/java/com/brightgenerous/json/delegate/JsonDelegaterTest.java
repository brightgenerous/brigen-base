package com.brightgenerous.json.delegate;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.brightgenerous.json.JsonParseException;
import com.brightgenerous.json.TypeToken;

public class JsonDelegaterTest {

    @Test
    public void formatJson() throws JsonParseException, ParseException {
        String dateFormat = "yyyy-MM-dd";
        JsonDelegater[] delegs = new JsonDelegater[] { new JsonDelegaterGson(),
                new JsonDelegaterJackson(), new JsonDelegaterJsonic() };

        for (JsonDelegater deleg : delegs) {
            Foo<Bar> obj1 = new Foo<>();
            Foo<Bar> obj2;
            {
                obj1.setObj(new Bar());
                obj1.getObj().setStr("bar.str");
                obj1.setHoge(new Hoge());
                obj1.getHoge().setObj(Integer.valueOf(100));
                obj1.setDate(new SimpleDateFormat(dateFormat).parse("2013-01-02"));
                String json = deleg.formatJson(obj1, new TypeToken<Foo<Bar>>() {
                }.getType(), dateFormat, false);
                obj2 = deleg.parseJson(new StringReader(json), new TypeToken<Foo<Bar>>() {
                }.getType(), dateFormat, false);
            }

            assertEquals(obj1.getObj().getClass(), obj2.getObj().getClass());
            assertEquals(obj1.getObj().getStr(), "bar.str");
            assertEquals(obj1.getObj().getStr(), obj2.getObj().getStr());
            assertEquals(obj1.getHoge().getClass(), obj2.getHoge().getClass());
            assertEquals(obj1.getHoge().getObj(), obj2.getHoge().getObj());
            assertEquals(obj1.getDate(), obj2.getDate());
        }
    }

    static class Foo<T> {

        private T obj;

        private Hoge hoge;

        private Date date;

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

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
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
