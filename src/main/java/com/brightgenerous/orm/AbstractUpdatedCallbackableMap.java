package com.brightgenerous.orm;

import static com.brightgenerous.orm.ICloneable.Utils.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.lang.Args;

abstract class AbstractUpdatedCallbackableMap<K extends Serializable, V extends EmptyChackable & Serializable>
        implements Map<K, V>, Clearable, ICloneable<AbstractUpdatedCallbackableMap<K, V>>,
        Serializable {

    private static final long serialVersionUID = -74948305336935190L;

    private final UpdatedCallback callback;

    private Map<K, V> map = createMap();

    protected AbstractUpdatedCallbackableMap(UpdatedCallback callback) {
        this.callback = callback;
    }

    private Map<K, V> createMap() {
        return new HashMap<>();
    }

    protected abstract ConditionContext getContext();

    protected abstract K convertKey(Object key);

    protected abstract V createValue(UpdatedCallback uc);

    protected UpdatedCallback getCallback() {
        return callback;
    }

    @Override
    public void clear() {
        clean();
        if (!map.isEmpty()) {
            map.clear();
            callbackUpdated();
        }
    }

    protected void clean() {
        Set<K> keys = new HashSet<>();
        for (Entry<K, V> entry : map.entrySet()) {
            V value = entry.getValue();
            if ((value == null) || value.isEmpty()) {
                keys.add(entry.getKey());
            }
        }
        for (K key : keys) {
            remove(key);
        }
    }

    @Override
    public boolean containsKey(Object key) {
        V ret = map.get(convertKey(key));
        return (ret != null) && !ret.isEmpty();
    }

    @Override
    public boolean containsValue(Object value) {
        clean();
        return map.containsValue(value);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        clean();
        return Collections.unmodifiableSet(map.entrySet());
    }

    @Override
    public V get(Object key) {
        K k = convertKey(key);

        if (k == null) {
            throw new IllegalStateException(String.format(
                    "The converted key must not be null. converted key => %s, key => %s", k, key));
        }

        V ret = map.get(k);
        if (ret == null) {
            ret = createValue(callback);
            map.put(k, ret);
        }
        return ret;
    }

    @Override
    public boolean isEmpty() {
        clean();
        return map.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        clean();
        return Collections.unmodifiableSet(map.keySet());
    }

    @Override
    public V put(K key, V value) {
        Args.notNull(key, "key");
        Args.notNull(value, "value");

        V ret = null;
        if (map.containsKey(key)) {
            ret = map.get(key);
            if (ret != value) {
                map.put(key, value);
                callbackUpdated();
            }
        } else {
            map.put(key, value);
            callbackUpdated();
        }
        if ((ret != null) && ret.isEmpty()) {
            ret = null;
        }
        return ret;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        K k = convertKey(key);
        V ret = null;
        if (k != null) {
            if (map.containsKey(k)) {
                ret = map.remove(k);
                if ((ret != null) && !ret.isEmpty()) {
                    callbackUpdated();
                }
            }
        }
        if ((ret != null) && ret.isEmpty()) {
            key = null;
        }
        return ret;
    }

    @Override
    public int size() {
        clean();
        return map.size();
    }

    @Override
    public Collection<V> values() {
        clean();
        return Collections.unmodifiableCollection(map.values());
    }

    private void callbackUpdated() {
        if (callback != null) {
            callback.updated();
        }
    }

    @Override
    public AbstractUpdatedCallbackableMap<K, V> clone() {
        clean();
        AbstractUpdatedCallbackableMap<K, V> ret;
        try {
            ret = (AbstractUpdatedCallbackableMap<K, V>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
        ret.map = createMap();
        for (Entry<K, V> entry : map.entrySet()) {
            ret.map.put(getIfClone(entry.getKey()), getIfClone(entry.getValue()));
        }
        return ret;
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.useful()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.useful()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (ToStringUtils.useful()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }
}
