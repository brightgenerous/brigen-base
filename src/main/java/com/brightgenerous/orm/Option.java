package com.brightgenerous.orm;

import static com.brightgenerous.commons.ObjectUtils.*;
import static com.brightgenerous.orm.ICloneable.Utils.*;

import java.io.Serializable;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public class Option<T extends Serializable> implements Clearable, EmptyChackable,
        ICloneable<Option<T>>, Serializable {

    private static final long serialVersionUID = 2675962143167865812L;

    private final UpdatedCallback callback;

    private T value;

    protected Option(UpdatedCallback callback) {
        this.callback = callback;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (!equalsValueOrNull(this.value, value)) {
            this.value = value;
            callbackUpdated();
        }
    }

    @Override
    public boolean isEmpty() {
        return getValue() == null;
    }

    @Override
    public void clear() {
        boolean changed = false;
        if (value != null) {
            value = null;
            changed |= true;
        }
        if (changed) {
            callbackUpdated();
        }
    }

    @Override
    public Option<T> clone() {
        Option<T> ret;
        try {
            ret = (Option<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
        ret.value = getIfClone(value);
        return ret;
    }

    private void callbackUpdated() {
        if (callback != null) {
            callback.updated();
        }
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
