package com.brightgenerous.orm;

import java.io.Serializable;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.lang.Args;

public class TypeKey<T extends Serializable, V extends Serializable> implements Serializable {

    private static final long serialVersionUID = 53904102886448541L;

    private V key;

    public TypeKey(V key) {
        Args.notNull(key, "key");
        if (key instanceof CharSequence) {
            Args.notEmpty((CharSequence) key, "key");
        }

        this.key = key;
    }

    public V getKey() {
        return key;
    }

    public void setKey(V key) {
        this.key = key;
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
