package com.brightgenerous.orm;

import java.io.Serializable;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public class Sort implements ICloneable<Sort>, Serializable {

    private static final long serialVersionUID = 7240160359757519741L;

    private final String key;

    private final boolean asc;

    protected Sort(String key) {
        this(key, true);
    }

    protected Sort(String key, boolean asc) {
        this.key = key;
        this.asc = asc;
    }

    public String getKey() {
        return key;
    }

    public boolean getAsc() {
        return asc;
    }

    @Override
    public Sort clone() {
        Sort ret;
        try {
            ret = (Sort) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
        return ret;
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
