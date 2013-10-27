package com.brightgenerous.orm;

import static com.brightgenerous.orm.ICloneable.Utils.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.lang.Args;

public class Sorts implements Iterable<Sort>, Clearable, ICloneable<Sorts>, Serializable {

    private static final long serialVersionUID = -8846112615405822485L;

    private final ConditionContext context;

    private final UpdatedCallback callback;

    private List<Sort> list = createList();

    private void writeObject(ObjectOutputStream stream) throws IOException {
        if ((list != null) && !(list instanceof Serializable)) {
            // convert to java.io.Serializable
            list = new LinkedList<>(list);
        }
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();

        if (list != null) {
            List<Sort> tmp = createList();
            if (tmp != null) {
                Class<?> clazz = list.getClass();
                Class<?> tClazz = tmp.getClass();
                if (!tClazz.isAssignableFrom(clazz)) {
                    tmp.addAll(list);
                    list = tmp;
                }
            }
        }
    }

    protected Sorts(ConditionContext context, UpdatedCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    protected String convertKey(Object key) {
        if (key == null) {
            return null;
        }
        if (key instanceof TypeKey) {
            key = ((TypeKey<?, ?>) key).getKey();
        }
        if (key instanceof Sort) {
            return ((Sort) key).getKey();
        }
        if (key instanceof String) {
            return (String) key;
        }
        return String.valueOf(key);
    }

    protected void notNullKey(Object key) {
        Args.notNull(key, "key");

        if (key instanceof TypeKey) {
            Args.notNull(((TypeKey<?, ?>) key).getKey(), "TypeKey.key");
        }
    }

    private List<Sort> createList() {
        return new LinkedList<>();
    }

    protected ConditionContext getContext() {
        return context;
    }

    @Override
    public Iterator<Sort> iterator() {
        return list.iterator();
    }

    public void appendAsc(List<Object> keys) {
        if ((keys != null) && !keys.isEmpty()) {
            for (Object k : keys) {
                notNullKey(k);
            }

            for (Object k : keys) {
                append(k, true);
            }
        }
    }

    public void appendAsc(Object[] keys) {
        if ((keys == null) || (keys.length < 1)) {
            return;
        }
        appendAsc(Arrays.asList(keys));
    }

    public void appendAsc(Object key, Object... keys) {
        List<Object> ks = new ArrayList<>(((keys == null) ? 0 : keys.length) + 1);
        ks.add(key);
        if ((keys != null) && (0 < keys.length)) {
            for (Object k : keys) {
                ks.add(k);
            }
        }
        appendAsc(ks);
    }

    public void appendDesc(List<Object> keys) {
        if ((keys != null) && !keys.isEmpty()) {
            for (Object k : keys) {
                notNullKey(k);
            }

            for (Object k : keys) {
                append(k, false);
            }
        }
    }

    public void appendDesc(Object[] keys) {
        if ((keys == null) || (keys.length < 1)) {
            return;
        }
        appendDesc(Arrays.asList(keys));
    }

    public void appendDesc(Object key, Object... keys) {
        List<Object> ks = new ArrayList<>(((keys == null) ? 0 : keys.length) + 1);
        ks.add(key);
        if ((keys != null) && (0 < keys.length)) {
            for (Object k : keys) {
                ks.add(k);
            }
        }
        appendDesc(ks);
    }

    public void append(Object key, boolean asc) {
        notNullKey(key);

        prvAdd(getContext().newSort(convertKey(key), asc));
    }

    public void prependAsc(List<Object> keys) {
        if ((keys != null) && !keys.isEmpty()) {
            for (Object k : keys) {
                notNullKey(k);
            }

            for (Object k : keys) {
                prepend(k, true);
            }
        }
    }

    public void prependAsc(Object[] keys) {
        if ((keys == null) || (keys.length < 1)) {
            return;
        }
        prependAsc(Arrays.asList(keys));
    }

    public void prependAsc(Object key, Object... keys) {
        List<Object> ks = new ArrayList<>(((keys == null) ? 0 : keys.length) + 1);
        ks.add(key);
        if ((keys != null) && (0 < keys.length)) {
            for (Object k : keys) {
                ks.add(k);
            }
        }
        prependAsc(ks);
    }

    public void prependDesc(List<Object> keys) {
        if ((keys != null) && !keys.isEmpty()) {
            for (Object k : keys) {
                notNullKey(k);
            }

            for (Object k : keys) {
                prepend(k, false);
            }
        }
    }

    public void prependDesc(Object[] keys) {
        if ((keys == null) || (keys.length < 1)) {
            return;
        }
        prependDesc(Arrays.asList(keys));
    }

    public void prependDesc(Object key, Object... keys) {
        List<Object> ks = new ArrayList<>(((keys == null) ? 0 : keys.length) + 1);
        ks.add(key);
        if ((keys != null) && (0 < keys.length)) {
            for (Object k : keys) {
                ks.add(k);
            }
        }
        prependDesc(ks);
    }

    public void prepend(Object key, boolean asc) {
        notNullKey(key);

        prvAdd(0, getContext().newSort(convertKey(key), asc));
    }

    public void insertAsc(int index, List<Object> keys) {
        if ((keys != null) && !keys.isEmpty()) {
            for (Object k : keys) {
                notNullKey(k);
            }

            for (Object k : keys) {
                insert(index, k, true);
            }
        }
    }

    public void insertAsc(int index, Object[] keys) {
        if ((keys == null) || (keys.length < 1)) {
            return;
        }
        insertAsc(index, Arrays.asList(keys));
    }

    public void insertAsc(int index, Object key, Object... keys) {
        List<Object> ks = new ArrayList<>(((keys == null) ? 0 : keys.length) + 1);
        ks.add(key);
        if ((keys != null) && (0 < keys.length)) {
            for (Object k : keys) {
                ks.add(k);
            }
        }
        insertAsc(index, ks);
    }

    public void insertDesc(int index, List<Object> keys) {
        if ((keys != null) && !keys.isEmpty()) {
            for (Object k : keys) {
                notNullKey(k);
            }

            for (Object k : keys) {
                insert(index, k, false);
            }
        }
    }

    public void insertDesc(int index, Object[] keys) {
        if ((keys == null) || (keys.length < 1)) {
            return;
        }
        insertDesc(index, Arrays.asList(keys));
    }

    public void insertDesc(int index, Object key, Object... keys) {
        List<Object> ks = new ArrayList<>(((keys == null) ? 0 : keys.length) + 1);
        ks.add(key);
        if ((keys != null) && (0 < keys.length)) {
            for (Object k : keys) {
                ks.add(k);
            }
        }
        insertDesc(index, ks);
    }

    public void insert(int index, Object key, boolean asc) {
        notNullKey(key);

        prvAdd(index, getContext().newSort(convertKey(key), asc));
    }

    private void prvAdd(Sort sort) {
        Args.notNull(sort, "sort");

        prvRemove(sort);
        list.add(sort);
        callbackUpdated();
    }

    private void prvAdd(int index, Sort sort) {
        Args.notNull(sort, "sort");

        int size = list.size();
        if ((index < 0) || (size < index)) {
            throw new IndexOutOfBoundsException(String.format(
                    "The index is out of range. size => %d, index => %d", Integer.valueOf(size),
                    Integer.valueOf(index)));
        }

        prvRemove(sort);
        list.add(index, sort);
        callbackUpdated();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        if (!list.isEmpty()) {
            list.clear();
            callbackUpdated();
        }
    }

    public void remove(Object key) {
        if (prvRemove(key)) {
            callbackUpdated();
        }
    }

    public void removeAll(Collection<?> keys) {
        if ((keys == null) || keys.isEmpty()) {
            return;
        }
        boolean del = false;
        for (Object k : keys) {
            del |= prvRemove(k);
        }
        if (del) {
            callbackUpdated();
        }
    }

    public void removeAll(Object[] keys) {
        if ((keys == null) || (keys.length < 1)) {
            return;
        }
        removeAll(Arrays.asList(keys));
    }

    public void removeAll(Object key, Object... keys) {
        List<Object> ks = new ArrayList<>(((keys == null) ? 0 : keys.length) + 1);
        ks.add(key);
        if ((keys != null) && (0 < keys.length)) {
            for (Object k : keys) {
                ks.add(k);
            }
        }
        removeAll(ks);
    }

    private boolean prvRemove(Object key) {
        if (key == null) {
            return false;
        }
        String k = convertKey(key);
        if (k == null) {
            return false;
        }
        Sort del = null;
        for (Sort sort : list) {
            String sk = sort.getKey();
            if ((sk != null) && k.equals(sk)) {
                del = sort;
                break;
            }
        }
        if (del == null) {
            return false;
        }
        return list.remove(del);
    }

    public List<? extends Sort> getAll(List<Object> keys) {
        List<Sort> ret = new ArrayList<>((keys == null) ? 0 : keys.size());
        if ((keys != null) && !keys.isEmpty()) {
            for (Object k : keys) {
                Sort sort = prvGetContains(k);
                if (sort != null) {
                    ret.add(sort);
                }
            }
        }
        return Collections.unmodifiableList(ret);
    }

    public List<? extends Sort> getAll(Object[] keys) {
        if ((keys == null) || (keys.length < 1)) {
            return Collections.EMPTY_LIST;
        }
        return getAll(Arrays.asList(keys));
    }

    public List<? extends Sort> getAll(Object key, Object... keys) {
        List<Object> os = new ArrayList<>(((keys == null) ? 0 : keys.length) + 1);
        os.add(key);
        if ((keys != null) && (0 < keys.length)) {
            for (Object k : keys) {
                os.add(k);
            }
        }
        return getAll(os);
    }

    private Sort prvGetContains(Object key) {
        if (key == null) {
            return null;
        }
        String k = convertKey(key);
        if (k == null) {
            return null;
        }
        for (Sort s : list) {
            if (s == null) {
                continue;
            }
            String sk = s.getKey();
            if ((sk != null) && k.equals(sk)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public Sorts clone() {
        Sorts ret;
        try {
            ret = (Sorts) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
        ret.list = createList();
        for (Sort sort : list) {
            ret.list.add(getIfClone(sort));
        }
        return ret;
    }

    private void callbackUpdated() {
        if (callback != null) {
            callback.updated();
        }
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.resolved()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.resolved()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (ToStringUtils.resolved()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }
}
