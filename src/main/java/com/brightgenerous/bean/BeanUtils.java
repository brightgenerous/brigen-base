package com.brightgenerous.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

import com.brightgenerous.bean.AbstractBean.Fill;
import com.brightgenerous.bean.AbstractBean.Fill.Null;
import com.brightgenerous.bean.AbstractBean.Primary;

public class BeanUtils {

    private static final Map<Class<?>, LinkedHashSet<Field>> classPirmarys = new ConcurrentHashMap<>();

    private static final Map<Class<?>, LinkedHashMap<Field, Entry<Class<?>, Constructor<?>>>> classFills = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Constructor<?>> classConstructor = new ConcurrentHashMap<>();

    private static final Object[] empty = new Object[0];

    private static final Comparator<? extends AbstractBean> comparator = new SerializableComparator<AbstractBean>() {

        private static final long serialVersionUID = 5555378345381027585L;

        @Override
        public int compare(AbstractBean bean1, AbstractBean bean2) {
            return comparePrimary(bean1, bean2);
        }
    };

    private static final Comparator<? extends AbstractBean> eachComparator = new SerializableComparator<AbstractBean>() {

        private static final long serialVersionUID = 5555378345381027585L;

        @Override
        public int compare(AbstractBean bean1, AbstractBean bean2) {
            return comparePrimary(bean1, bean2, true);
        }
    };

    private BeanUtils() {
    }

    public static <T extends AbstractBean> Comparator<T> primaryComparator() {
        return primaryComparator(false);
    }

    public static <T extends AbstractBean> Comparator<T> primaryComparator(boolean eachField) {
        return (Comparator<T>) (eachField ? eachComparator : comparator);
    }

    public static <T extends AbstractBean> T defaultIfPrimaryNull(T bean, T def, T... defs) {
        if (notPrimaryNull(bean)) {
            return bean;
        }
        if (notPrimaryNull(def)) {
            return def;
        }
        if (defs != null) {
            for (T d : defs) {
                if (notPrimaryNull(d)) {
                    return d;
                }
            }
        }
        return null;
    }

    public static <T extends AbstractBean> boolean allPrimaryNull(T bean, T... beans) {
        if (notPrimaryNull(bean)) {
            return false;
        }
        if ((beans != null) && (0 < beans.length)) {
            for (T b : beans) {
                if (notPrimaryNull(b)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static <T extends AbstractBean> boolean allNotPrimaryNull(T bean, T... beans) {
        if (primaryNull(bean)) {
            return false;
        }
        if (beans == null) {
            return false;
        }
        if (0 < beans.length) {
            for (T b : beans) {
                if (primaryNull(b)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static <T extends AbstractBean> boolean anyPrimaryNull(T bean, T... beans) {
        return !allNotPrimaryNull(bean, beans);
    }

    public static <T extends AbstractBean> boolean anyNotPrimaryNull(T bean, T... beans) {
        return !allPrimaryNull(bean, beans);
    }

    public static boolean primaryNull(AbstractBean bean) {
        if (bean == null) {
            return true;
        }
        LinkedHashSet<Field> fields = getPrimaryFields(bean);
        if (fields.isEmpty()) {
            return true;
        }
        try {
            for (Field field : fields) {
                Object obj;
                if ((obj = field.get(bean)) == null) {
                    return true;
                }
                if (obj instanceof AbstractBean) {
                    if (primaryNull((AbstractBean) obj)) {
                        return true;
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean notPrimaryNull(AbstractBean bean) {
        return !primaryNull(bean);
    }

    public static boolean equalsPrimary(AbstractBean bean1, AbstractBean bean2) {
        return equalsPrimary(bean1, bean2, false);
    }

    public static boolean equalsPrimary(AbstractBean bean1, AbstractBean bean2, boolean eachField) {
        try {
            return comparePrimary(bean1, bean2, eachField) == 0;
        } catch (ClassCastException e) {
        }
        return false;
    }

    public static boolean notEqualsPrimary(AbstractBean bean1, AbstractBean bean2) {
        return !equalsPrimary(bean1, bean2);
    }

    public static boolean notEqualsPrimary(AbstractBean bean1, AbstractBean bean2, boolean eachField) {
        return !equalsPrimary(bean1, bean2, eachField);
    }

    public static int comparePrimary(AbstractBean bean1, AbstractBean bean2) {
        return comparePrimary(bean1, bean2, false);
    }

    public static int comparePrimary(AbstractBean bean1, AbstractBean bean2, boolean eachField) {
        if (bean1 == bean2) {
            return 0;
        }
        if (eachField) {
            if (bean1 == null) {
                return 1;
            }
            if (bean2 == null) {
                return -1;
            }
        } else {
            if (bean1 == null) {
                return primaryNull(bean2) ? 0 : 1;
            }
            if (bean2 == null) {
                return primaryNull(bean1) ? 0 : -1;
            }
        }
        checkComperable(bean1, bean2);

        Object[] values1 = getPrimaryValues(bean1);
        Object[] values2 = getPrimaryValues(bean2);
        final int length1 = values1.length;
        final int length2 = values2.length;
        boolean null1 = false;
        boolean null2 = false;
        int compare = 0;
        for (int i = 0; i < Math.min(length1, length2); i++) {
            Object value1 = values1[i];
            Object value2 = values2[i];
            int c = 0;
            if ((c = compareValue(value1, value2, eachField)) != 0) {
                if (eachField) {
                    return c;
                }
                if (compare == 0) {
                    compare = c;
                }
            }
            null1 |= value1 == null;
            null2 |= value2 == null;
        }

        if (eachField) {
            return length1 - length2;
        }
        if (null1 && null2) {
            return 0;
        }

        if (length1 != length2) {
            if (!null1 && (length2 < length1)) {
                for (int i = length2; i < length1; i++) {
                    null1 |= values1[i] == null;
                }
            }
            if (!null2 && (length1 < length2)) {
                for (int i = length1; i < length2; i++) {
                    null2 |= values2[i] == null;
                }
            }
        }

        if (null1 && null2) {
            return 0;
        }
        if (null1) {
            return 1;
        }
        if (null2) {
            return -1;
        }
        if (compare != 0) {
            return compare;
        }
        return length1 - length2;
    }

    private static int compareValue(Object obj1, Object obj2, boolean eachField) {
        if (obj1 == obj2) {
            return 0;
        }
        if (obj1 == null) {
            return 1;
        }
        if (obj2 == null) {
            return -1;
        }
        checkComperable(obj1, obj2);

        if ((obj1 instanceof AbstractBean) && (obj2 instanceof AbstractBean)) {
            return comparePrimary((AbstractBean) obj1, (AbstractBean) obj2, eachField);
        }
        return ((Comparable<Object>) obj1).compareTo(obj2);
    }

    private static void checkComperable(Object obj1, Object obj2) {
        Class<?> clazz1 = obj1.getClass();
        Class<?> clazz2 = obj2.getClass();
        if (clazz1.equals(clazz2) || clazz1.isAssignableFrom(clazz2)
                || clazz2.isAssignableFrom(clazz1)) {
            return;
        }
        throw new ClassCastException(String.format("The arguments is not assignable %s with %s",
                clazz1, clazz2));
    }

    private static Object[] getPrimaryValues(AbstractBean bean) {
        LinkedHashSet<Field> fields = getPrimaryFields(bean);
        if (fields.isEmpty()) {
            return empty;
        }
        Object[] ret = new Object[fields.size()];
        try {
            int i = 0;
            for (Field field : fields) {
                ret[i] = field.get(bean);
                i++;
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public static Set<Entry<String, Object>> getPrimarySets(AbstractBean bean) {
        return getPrimarySets(bean, true);
    }

    public static Set<Entry<String, Object>> getPrimarySets(AbstractBean bean, boolean deep) {
        LinkedHashSet<Field> fields = getPrimaryFields(bean);
        if (fields.isEmpty()) {
            return Collections.EMPTY_SET;
        }
        Set<Entry<String, Object>> ret = new HashSet<>();
        try {
            for (Field field : fields) {
                Object val = field.get(bean);
                if (deep && (val instanceof AbstractBean)) {
                    val = getPrimarySets((AbstractBean) val, deep);
                }
                ret.add(new SimpleImmutableEntry<>(field.getName(), val));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    private static LinkedHashSet<Field> getPrimaryFields(AbstractBean bean) {
        LinkedHashSet<Field> ret;
        {
            Class<?> clazz = bean.getClass();
            ret = classPirmarys.get(clazz);
            if (ret == null) {
                synchronized (clazz) {
                    ret = classPirmarys.get(clazz);
                    if (ret == null) {
                        ret = loadPrimarys(clazz);
                        classPirmarys.put(clazz, ret);
                    }
                }
            }
        }
        return ret;
    }

    private static LinkedHashSet<Field> loadPrimarys(Class<?> clazz) {
        LinkedHashSet<Field> ret = new LinkedHashSet<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(Primary.class) != null) {
                checkAsPrimary(field);
                if (!Modifier.isPublic(field.getModifiers()) && !field.isAccessible()) {
                    field.setAccessible(true);
                }
                ret.add(field);
            }
        }
        for (Field field : clazz.getFields()) {
            if (field.getAnnotation(Primary.class) != null) {
                checkAsPrimary(field);
                if (!Modifier.isPublic(field.getModifiers()) && !field.isAccessible()) {
                    field.setAccessible(true);
                }
                ret.add(field);
            }
        }
        return ret;
    }

    private static void checkAsPrimary(Field field) {
        Class<?> type = field.getType();
        if (AbstractBean.class.isAssignableFrom(type)) {
            return;
        }
        if (type.isPrimitive() || Boolean.class.isAssignableFrom(type)
                || Number.class.isAssignableFrom(type) || Character.class.isAssignableFrom(type)
                || CharSequence.class.isAssignableFrom(type) || Date.class.isAssignableFrom(type)) {
            return;
        }
        throw new RuntimeException(String.format("The field %s is illegal type as primary, %s",
                field, type));
    }

    private static LinkedHashMap<Field, Entry<Class<?>, Constructor<?>>> getFillFields(
            AbstractBean bean) {
        LinkedHashMap<Field, Entry<Class<?>, Constructor<?>>> ret;
        {
            Class<?> clazz = bean.getClass();
            ret = classFills.get(clazz);
            if (ret == null) {
                synchronized (clazz) {
                    ret = classFills.get(clazz);
                    if (ret == null) {
                        ret = loadFills(clazz);
                        classFills.put(clazz, ret);
                    }
                }
            }
        }
        return ret;
    }

    private static LinkedHashMap<Field, Entry<Class<?>, Constructor<?>>> loadFills(Class<?> clazz) {
        LinkedHashMap<Field, Entry<Class<?>, Constructor<?>>> ret = new LinkedHashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            Fill fill;
            if ((fill = field.getAnnotation(Fill.class)) != null) {
                checkAsFill(field);
                if (!Modifier.isPublic(field.getModifiers()) && !field.isAccessible()) {
                    field.setAccessible(true);
                }
                ret.put(field, getFillConstructor(fill, field));
            }
        }
        for (Field field : clazz.getFields()) {
            Fill fill;
            if ((fill = field.getAnnotation(Fill.class)) != null) {
                if (ret.containsKey(field)) {
                    continue;
                }
                checkAsFill(field);
                if (!Modifier.isPublic(field.getModifiers()) && !field.isAccessible()) {
                    field.setAccessible(true);
                }
                ret.put(field, getFillConstructor(fill, field));
            }
        }
        return ret;
    }

    private static Entry<Class<?>, Constructor<?>> getFillConstructor(Fill fill, Field field) {
        Class<?> clazz = null;
        {
            Class<?> val = fill.value();
            if ((val != null) && !val.equals(Null.class)) {
                clazz = val;
            }
        }
        if (clazz == null) {
            clazz = field.getType();
        }
        return new SimpleImmutableEntry<Class<?>, Constructor<?>>(clazz, getConstructor(clazz));
    }

    private static final Map<Class<?>, Class<?>> clazzImpls;
    static {
        clazzImpls = new ConcurrentHashMap<>();

        clazzImpls.put(Map.class, LinkedHashMap.class);
        clazzImpls.put(SortedMap.class, TreeMap.class);
        clazzImpls.put(NavigableMap.class, TreeMap.class);
        clazzImpls.put(ConcurrentMap.class, ConcurrentSkipListMap.class);
        clazzImpls.put(ConcurrentNavigableMap.class, ConcurrentSkipListMap.class);

        clazzImpls.put(List.class, LinkedList.class);

        clazzImpls.put(Queue.class, LinkedList.class);
        clazzImpls.put(Deque.class, LinkedList.class);
        clazzImpls.put(BlockingQueue.class, LinkedBlockingQueue.class);
        clazzImpls.put(BlockingDeque.class, LinkedBlockingDeque.class);

        clazzImpls.put(Set.class, LinkedHashSet.class);
        clazzImpls.put(SortedSet.class, TreeSet.class);
        clazzImpls.put(NavigableSet.class, TreeSet.class);
    }

    private static Constructor<?> getConstructor(Class<?> clazz) {
        Constructor<?> ret = classConstructor.get(clazz);
        if (ret == null) {
            synchronized (clazz) {
                ret = classConstructor.get(clazz);
                if (ret == null) {
                    Class<?> impl = null;
                    if (clazz.isInterface()) {
                        impl = clazzImpls.get(clazz);
                        if (impl == null) {
                            throw new RuntimeException(String.format(
                                    "The class %s is illegal type as fill, %s", clazz));
                        }
                    } else {
                        impl = clazz;
                    }
                    try {
                        ret = impl.getDeclaredConstructor();
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    if (!Modifier.isPublic(ret.getModifiers()) && !ret.isAccessible()) {
                        ret.setAccessible(true);
                    }
                    classConstructor.put(clazz, ret);
                }
            }
        }
        return ret;
    }

    private static void checkAsFill(Field field) {
        Class<?> type = field.getType();
        if (type.isAnnotation() || type.isAnonymousClass() || type.isArray() || type.isEnum()
                || type.isPrimitive()) {
            throw new RuntimeException(String.format("The field %s is illegal type as fill, %s",
                    field, type));
        }
    }

    public static void fill(AbstractBean bean) {
        if (bean == null) {
            return;
        }
        Map<Class<?>, Object> instances = new HashMap<>();
        instances.put(bean.getClass(), bean);
        fill(bean, instances);
    }

    private static void fill(AbstractBean bean, Map<Class<?>, Object> instances) {
        try {
            for (Entry<Field, Entry<Class<?>, Constructor<?>>> e : getFillFields(bean).entrySet()) {
                Field field = e.getKey();
                if (field.get(bean) != null) {
                    return;
                }
                Entry<Class<?>, Constructor<?>> cc = e.getValue();
                Class<?> ic = cc.getKey();
                Object child = instances.get(ic);
                if (child == null) {
                    Constructor<?> cons = cc.getValue();
                    child = cons.newInstance();
                    if (child instanceof AbstractBean) {
                        Map<Class<?>, Object> tmp = new HashMap<>(instances);
                        tmp.put(ic, child);
                        fill((AbstractBean) child, tmp);
                    }
                }
                field.set(bean, child);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
