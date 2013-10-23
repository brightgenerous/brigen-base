package com.brightgenerous.bean;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class BeanUtilsTest {

    @Test
    public void primaryComparator() {
        Comparator<OuterBean> c1 = BeanUtils.<OuterBean> primaryComparator();
        Comparator<OuterBean2> c2 = BeanUtils.<OuterBean2> primaryComparator();
        Comparator<AbstractBean> c3 = BeanUtils.primaryComparator();
        c1.compare(new OuterBean(), new OuterBean());
        c2.compare(new OuterBean2(), new OuterBean2());
        try {
            c3.compare(new OuterBean(), new OuterBean2());
            fail();
        } catch (ClassCastException e) {
        }
        c3.compare(new OuterBean(), new OuterBean() {

            private static final long serialVersionUID = -2551841812680981271L;

        });
        c3.compare(new OuterBean() {

            private static final long serialVersionUID = -8849888534954766184L;

        }, new OuterBean());
    }

    @SuppressWarnings("boxing")
    @Test
    public void comparePrimary() {
        final OuterBean bean1;
        {
            OuterBean tmp = new OuterBean();
            tmp.setInner(new InnerBean());
            tmp.getInner().setKey1("key1");
            tmp.getInner().setKey2(100);
            tmp.getInner().setValue1(500L);
            bean1 = tmp;
        }
        final OuterBean bean2;
        {
            OuterBean tmp = new OuterBean();
            tmp.setInner(new InnerBean());
            tmp.getInner().setKey1("key1");
            tmp.getInner().setKey2(100);
            tmp.getInner().setValue1(300L);
            bean2 = tmp;
        }
        final OuterBean bean3;
        {
            OuterBean tmp = new OuterBean();
            tmp.setInner(new InnerBean());
            tmp.getInner().setKey1("key1");
            tmp.getInner().setKey2(200);
            tmp.getInner().setValue1(300L);
            bean3 = tmp;
        }
        final OuterBean2 bean4;
        {
            OuterBean2 tmp = new OuterBean2();
            tmp.setInner(new InnerBean());
            tmp.getInner().setKey1("key1");
            tmp.getInner().setKey2(100);
            tmp.getInner().setValue1(300L);
            bean4 = tmp;
        }

        assertEquals(LinkedList.class, bean1.getList().getClass());
        assertEquals(ArrayList.class, bean1.getList2().getClass());
        assertEquals(0, BeanUtils.comparePrimary(bean1, bean2));
        assertTrue(BeanUtils.comparePrimary(bean1, bean3) < 0);
        try {
            BeanUtils.comparePrimary(bean1, bean4);
            fail();
        } catch (ClassCastException e) {
        }
        assertTrue(BeanUtils.equalsPrimary(bean1, bean2));
        assertFalse(BeanUtils.equalsPrimary(bean1, bean3));
        assertFalse(BeanUtils.equalsPrimary(bean1, bean4));
    }

    static class OuterBean extends AbstractBean {

        private static final long serialVersionUID = -3840933230500207350L;

        @Primary
        private InnerBean inner;

        @Fill
        private List<Integer> list;

        @Fill(ArrayList.class)
        private List<Integer> list2;

        public InnerBean getInner() {
            return inner;
        }

        public void setInner(InnerBean inner) {
            this.inner = inner;
        }

        public List<Integer> getList() {
            return list;
        }

        public void setList(List<Integer> list) {
            this.list = list;
        }

        public List<Integer> getList2() {
            return list2;
        }

        public void setList2(List<Integer> list2) {
            this.list2 = list2;
        }
    }

    static class OuterBean2 extends AbstractBean {

        private static final long serialVersionUID = -3840933230500207350L;

        @Primary
        private InnerBean inner;

        public InnerBean getInner() {
            return inner;
        }

        public void setInner(InnerBean inner) {
            this.inner = inner;
        }
    }

    static class InnerBean extends AbstractBean {

        private static final long serialVersionUID = -3201268534961494278L;

        @Primary
        private String key1;

        @Primary
        private Integer key2;

        private Long value1;

        public String getKey1() {
            return key1;
        }

        public void setKey1(String key1) {
            this.key1 = key1;
        }

        public Integer getKey2() {
            return key2;
        }

        public void setKey2(Integer key2) {
            this.key2 = key2;
        }

        public Long getValue1() {
            return value1;
        }

        public void setValue1(Long value1) {
            this.value1 = value1;
        }
    }
}
