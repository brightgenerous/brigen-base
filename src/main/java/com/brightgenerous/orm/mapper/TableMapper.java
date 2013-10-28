package com.brightgenerous.orm.mapper;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.StringUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.lang.Args;

public class TableMapper implements Serializable {

    private static final long serialVersionUID = -8599881819890993547L;

    public static enum Flag {
        ALL, SELECT, INSERT, UPDATE
    }

    private final Class<?> clazz;

    private final boolean alias;

    private final String table;

    private final String property;

    private final LinkedHashMap<String, ColumnDefine> fieldColumns;

    public TableMapper(Class<?> clazz, boolean alias, String table) {
        this(clazz, alias, table, (String) null);
    }

    protected TableMapper(Class<?> clazz, boolean alias, String table, String property) {
        this(clazz, alias, table, property, null);
    }

    public TableMapper(Class<?> clazz, boolean alias, String table,
            LinkedHashMap<String, ColumnDefine> fieldColumns) {
        this(clazz, alias, table, null, fieldColumns);
    }

    protected TableMapper(Class<?> clazz, boolean alias, String table, String property,
            LinkedHashMap<String, ColumnDefine> fieldColumns) {
        Args.notNull(clazz, "clazz");
        Args.notEmpty(table, "table");

        this.clazz = clazz;
        this.alias = alias;
        this.table = table;
        this.property = property;
        this.fieldColumns = fieldColumns;
    }

    public Class<?> getBeanClass() {
        return clazz;
    }

    public boolean isAlias() {
        return alias;
    }

    public String getTable() {
        return table;
    }

    public String getProperty() {
        return property;
    }

    public LinkedHashMap<String, ColumnDefine> getFieldColumns() {
        return fieldColumns;
    }

    private static final String[] excludeFields = new String[] { "stripFieldFullColumns",
            "fieldFullColumns", "propertyFieldFullColumns" };

    private transient volatile LinkedHashMap<String, String> stripFieldFullColumns;

    private transient volatile LinkedHashMap<String, String> fieldFullColumns;

    private transient volatile LinkedHashMap<String, String> propertyFieldFullColumns;

    public LinkedHashMap<String, String> getStripFieldFullColumns(Flag flag) {
        if (stripFieldFullColumns == null) {
            synchronized (this) {
                if (stripFieldFullColumns == null) {
                    LinkedHashMap<String, String> ffcs = getFieldFullColumns(flag);
                    LinkedHashMap<String, String> tmp = new LinkedHashMap<>();
                    for (Entry<String, String> e : ffcs.entrySet()) {
                        String k = MapperUtils.spritField(e.getKey());
                        if (!tmp.containsKey(k)) {
                            tmp.put(k, e.getValue());
                        }
                    }
                    stripFieldFullColumns = tmp;
                }
            }
        }
        return stripFieldFullColumns;
    }

    public LinkedHashMap<String, String> getFieldFullColumns(Flag flag) {
        if (fieldFullColumns == null) {
            synchronized (this) {
                if (fieldFullColumns == null) {
                    LinkedHashMap<String, String> tmp = new LinkedHashMap<>();
                    if ((fieldColumns != null) && !fieldColumns.isEmpty()) {
                        for (Entry<String, ColumnDefine> e : fieldColumns.entrySet()) {
                            ColumnDefine cd = e.getValue();
                            if (!checkFlag(cd, flag)) {
                                continue;
                            }
                            String f = e.getKey();
                            String c = cd.getName();
                            if (StringUtils.isEmpty(f) || StringUtils.isEmpty(c)) {
                                throw new IllegalStateException(String.format(
                                        "must not be null field(=%s) and column(%s)", f, c));
                            }
                            c = MapperUtils.joinTableColumn(table, c);
                            if (!tmp.containsKey(f)) {
                                tmp.put(f, c);
                            }
                        }
                    }
                    fieldFullColumns = tmp;
                }
            }
        }
        return fieldFullColumns;
    }

    public LinkedHashMap<String, String> getPropertyFieldFullColumns(Flag flag) {
        if (propertyFieldFullColumns == null) {
            synchronized (this) {
                if (propertyFieldFullColumns == null) {
                    LinkedHashMap<String, String> ffcs = getFieldFullColumns(flag);
                    if (StringUtils.isEmpty(property)) {
                        propertyFieldFullColumns = ffcs;
                    } else {
                        LinkedHashMap<String, String> tmp = new LinkedHashMap<>();
                        for (Entry<String, String> e : ffcs.entrySet()) {
                            String pf = MapperUtils.joinPropertyField(property, e.getKey());
                            if (!tmp.containsKey(pf)) {
                                tmp.put(pf, e.getValue());
                            }
                        }
                        propertyFieldFullColumns = tmp;
                    }
                }
            }
        }
        return propertyFieldFullColumns;
    }

    protected boolean checkFlag(ColumnDefine columnDefine, Flag flag) {
        switch (flag) {
            case ALL:
                return true;
            case SELECT:
                return columnDefine.isEnableSelect();
            case INSERT:
                return columnDefine.isEnableInsert();
            case UPDATE:
                return columnDefine.isEnableUpdate();
            default:
                throw new IllegalStateException();
        }
    }

    public static TableMapper createAlt(TableMapper tableMapper, String property) {
        Args.notNull(tableMapper, "tableMapper");

        TableMapper ret;
        if (tableMapper.fieldColumns == null) {
            ret = new TableMapper(tableMapper.clazz, tableMapper.alias, tableMapper.table, property);
        } else {
            ret = new TableMapper(tableMapper.clazz, tableMapper.alias, tableMapper.table,
                    property, tableMapper.fieldColumns);
        }
        return ret;
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.resolved()) {
            return HashCodeUtils.hashCodeAlt(null, this, excludeFields);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.resolved()) {
            return EqualsUtils.equalsAlt(null, this, obj, excludeFields);
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
