package com.brightgenerous.orm.mapper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.StringUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.lang.Args;

public class TableMapper implements Serializable {

    private static final long serialVersionUID = -8599881819890993547L;

    private final Class<?> clazz;

    private final LinkedHashSet<Field> fields;

    private final boolean alias;

    private final String table;

    private final String property;

    private final LinkedHashMap<String, String> fieldColumns;

    public TableMapper(Class<?> clazz, LinkedHashSet<Field> fields, boolean alias, String table) {
        this(clazz, fields, alias, table, (String) null);
    }

    protected TableMapper(Class<?> clazz, LinkedHashSet<Field> fields, boolean alias, String table,
            String property) {
        this(clazz, fields, alias, table, property, null);
    }

    public TableMapper(Class<?> clazz, LinkedHashSet<Field> fields, boolean alias, String table,
            LinkedHashMap<String, String> fieldColumns) {
        this(clazz, fields, alias, table, null, fieldColumns);
    }

    protected TableMapper(Class<?> clazz, LinkedHashSet<Field> fields, boolean alias, String table,
            String property, LinkedHashMap<String, String> fieldColumns) {
        Args.notNull(clazz, "clazz");
        Args.notEmpty(table, "table");

        this.clazz = clazz;
        this.fields = fields;
        this.alias = alias;
        this.table = table;
        this.property = property;
        this.fieldColumns = fieldColumns;
    }

    public Class<?> getBeanClass() {
        return clazz;
    }

    public LinkedHashSet<Field> getFields() {
        return fields;
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

    public LinkedHashMap<String, String> getFieldColumns() {
        return fieldColumns;
    }

    private static final String[] excludeFields = new String[] { "stripFieldFullColumns",
            "fieldFullColumns", "propertyFieldFullColumns" };

    private transient volatile LinkedHashMap<String, String> stripFieldFullColumns;

    private transient volatile LinkedHashMap<String, String> fieldFullColumns;

    private transient volatile LinkedHashMap<String, String> propertyFieldFullColumns;

    public LinkedHashMap<String, String> getStripFieldFullColumns() {
        if (stripFieldFullColumns == null) {
            synchronized (this) {
                if (stripFieldFullColumns == null) {
                    LinkedHashMap<String, String> ffcs = getFieldFullColumns();
                    LinkedHashMap<String, String> tmp = new LinkedHashMap<>();
                    for (Entry<String, String> e : ffcs.entrySet()) {
                        String k = e.getKey();
                        int index;
                        if ((index = k.lastIndexOf(".")) != -1) {
                            k = k.substring(index + 1);
                        }
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

    public LinkedHashMap<String, String> getFieldFullColumns() {
        if (fieldFullColumns == null) {
            synchronized (this) {
                if (fieldFullColumns == null) {
                    LinkedHashMap<String, String> tmp = new LinkedHashMap<>();
                    if ((fieldColumns != null) && !fieldColumns.isEmpty()) {
                        for (Entry<String, String> e : fieldColumns.entrySet()) {
                            String f = e.getKey();
                            String c = e.getValue();
                            if (StringUtils.isEmpty(f) || StringUtils.isEmpty(c)) {
                                continue;
                            }
                            if (StringUtils.isNotEmpty(table)) {
                                c = table + "." + c;
                            }
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

    public LinkedHashMap<String, String> getPropertyFieldFullColumns() {
        if (propertyFieldFullColumns == null) {
            synchronized (this) {
                if (propertyFieldFullColumns == null) {
                    LinkedHashMap<String, String> ffcs = getFieldFullColumns();
                    if (StringUtils.isEmpty(property)) {
                        propertyFieldFullColumns = ffcs;
                    } else {
                        LinkedHashMap<String, String> tmp = new LinkedHashMap<>();
                        for (Entry<String, String> e : ffcs.entrySet()) {
                            String pf = property + "." + e.getKey();
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

    public static TableMapper createAlt(TableMapper tableMapper, String property) {
        Args.notNull(tableMapper, "tableMapper");

        TableMapper ret;
        if (tableMapper.fieldColumns == null) {
            ret = new TableMapper(tableMapper.clazz, tableMapper.fields, tableMapper.alias,
                    tableMapper.table, property);
        } else {
            ret = new TableMapper(tableMapper.clazz, tableMapper.fields, tableMapper.alias,
                    tableMapper.table, property, tableMapper.fieldColumns);
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
