package com.brightgenerous.orm.mapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.lang.Args;

public class Register implements Serializable {

    private static final long serialVersionUID = 5665322575628042798L;

    private final Map<String, TableMapper> tableMappers;

    private final Set<Class<?>> types;

    private FieldToColumnCase fieldToSnakeColumn;

    private boolean checkStrict;

    public Register() {
        tableMappers = new HashMap<>();
        types = new HashSet<>();
        fieldToSnakeColumn = FieldToColumnCase.SNAKE_NUMBER;
        checkStrict = true;
    }

    public Map<String, TableMapper> getTableMappers() {
        return tableMappers;
    }

    public Map<Class<?>, String> getTargetTables() {
        Map<Class<?>, String> ret = new HashMap<>();
        for (TableMapper tm : tableMappers.values()) {
            if (!tm.isAlias()) {
                ret.put(tm.getBeanClass(), tm.getTable());
            }
        }
        return ret;
    }

    public TableMapper getTableMapper(String table) {
        Args.notNull(table, "table");

        return tableMappers.get(table);
    }

    public TableMapper putTableMapper(String table, TableMapper tableMapper) {
        Args.notNull(tableMapper, "tableMapper");

        return tableMappers.put(table, tableMapper);
    }

    public Set<Class<?>> getTypes() {
        return types;
    }

    public Register addType(Class<?> type) {
        Args.notNull(type, "type");

        types.add(type);
        return this;
    }

    public FieldToColumnCase fieldToSnakeColumn() {
        return fieldToSnakeColumn;
    }

    public Register fieldToSnakeColumn(FieldToColumnCase fieldToSnakeColumn) {
        if (fieldToSnakeColumn == null) {
            fieldToSnakeColumn = FieldToColumnCase.NONE;
        }
        this.fieldToSnakeColumn = fieldToSnakeColumn;
        return this;
    }

    public boolean checkStrict() {
        return checkStrict;
    }

    public Register checkStrict(boolean checkStrict) {
        this.checkStrict = checkStrict;
        return this;
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
