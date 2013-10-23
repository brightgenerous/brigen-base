package com.brightgenerous.orm;

import java.io.Serializable;
import java.util.Map;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public class Sql implements Serializable {

    private static final long serialVersionUID = 2883914650222146067L;

    private String sql;

    private Map<String, ? extends Serializable> params;

    public Sql() {
    }

    public Sql(String sql) {
        this.sql = sql;
    }

    public Sql(String sql, Map<String, ? extends Serializable> params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<String, ? extends Serializable> getParams() {
        return params;
    }

    public void setParams(Map<String, ? extends Serializable> params) {
        this.params = params;
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
