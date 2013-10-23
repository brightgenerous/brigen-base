package com.brightgenerous.orm.mapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.lang.Args;

public class TableDefines implements Serializable {

    private static final long serialVersionUID = 1356702505554392613L;

    private final Map<String, TableDefine> deleg;

    public TableDefines() {
        deleg = new HashMap<>();
    }

    public boolean isEmpty() {
        return deleg.isEmpty();
    }

    public boolean containsKey(String table) {
        if (table == null) {
            return false;
        }
        return deleg.containsKey(table);
    }

    public TableDefine get(String table) {
        if (table == null) {
            return null;
        }
        return deleg.get(table);
    }

    public TableDefine put(String table, TableDefine define) {
        Args.notNull(table, "table");
        Args.notNull(define, "define");

        return deleg.put(table, define);
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
