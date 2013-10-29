package com.brightgenerous.orm.mapper;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

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

    private final LinkedHashMap<String, ColumnDefine> primarys;

    private final LinkedHashMap<String, ColumnDefine> fieldColumns;

    public TableMapper(Class<?> clazz, boolean alias, String table,
            LinkedHashMap<String, ColumnDefine> primarys,
            LinkedHashMap<String, ColumnDefine> fieldColumns) {
        this(clazz, alias, table, null, primarys, fieldColumns);
    }

    protected TableMapper(Class<?> clazz, boolean alias, String table, String property,
            LinkedHashMap<String, ColumnDefine> primarys,
            LinkedHashMap<String, ColumnDefine> fieldColumns) {
        Args.notNull(clazz, "clazz");
        Args.notEmpty(table, "table");

        this.clazz = clazz;
        this.alias = alias;
        this.table = table;
        this.property = property;
        this.primarys = new LinkedHashMap<>((primarys == null) ? Collections.EMPTY_MAP : primarys);
        this.fieldColumns = new LinkedHashMap<>((fieldColumns == null) ? Collections.EMPTY_MAP
                : fieldColumns);
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

    public LinkedHashMap<String, ColumnDefine> getPrimarys() {
        return primarys;
    }

    public LinkedHashMap<String, ColumnDefine> getFieldColumns() {
        return fieldColumns;
    }

    private static final String[] excludeFields = new String[] { "flagFieldColumns",
            "flagStripFieldFullColumns", "flagFieldFullColumns", "flagPropertyFieldFullColumns" };

    private transient volatile Map<Flag, LinkedHashMap<String, ColumnDefine>> flagFieldColumns;

    private transient volatile Map<Flag, LinkedHashMap<String, String>> flagStripFieldFullColumns;

    private transient volatile Map<Flag, LinkedHashMap<String, String>> flagFieldFullColumns;

    private transient volatile Map<Flag, LinkedHashMap<String, String>> flagPropertyFieldFullColumns;

    public LinkedHashMap<String, ColumnDefine> getFieldColumns(Flag flag) {
        Args.notNull(flag, "flag");

        if (flagFieldColumns == null) {
            synchronized (this) {
                if (flagFieldColumns == null) {
                    flagFieldColumns = new ConcurrentHashMap<>();
                }
            }
        }
        LinkedHashMap<String, ColumnDefine> ret = flagFieldColumns.get(flag);
        if (ret == null) {
            synchronized (flagFieldColumns) {
                ret = flagFieldColumns.get(flag);
                if (ret == null) {
                    ret = new LinkedHashMap<>();
                    for (Entry<String, ColumnDefine> e : fieldColumns.entrySet()) {
                        ColumnDefine cd = e.getValue();
                        if (!checkFlag(cd, flag)) {
                            continue;
                        }
                        ret.put(e.getKey(), cd);
                    }
                    flagFieldColumns.put(flag, ret);
                }
            }
        }
        return ret;
    }

    public LinkedHashMap<String, String> getStripFieldFullColumns(Flag flag) {
        Args.notNull(flag, "flag");

        if (flagStripFieldFullColumns == null) {
            synchronized (this) {
                if (flagStripFieldFullColumns == null) {
                    flagStripFieldFullColumns = new ConcurrentHashMap<>();
                }
            }
        }
        LinkedHashMap<String, String> ret = flagStripFieldFullColumns.get(flag);
        if (ret == null) {
            synchronized (flagStripFieldFullColumns) {
                ret = flagStripFieldFullColumns.get(flag);
                if (ret == null) {
                    ret = new LinkedHashMap<>();
                    LinkedHashMap<String, String> ffcs = getFieldFullColumns(flag);
                    for (Entry<String, String> e : ffcs.entrySet()) {
                        String k = MapperUtils.spritField(e.getKey());
                        if (!ret.containsKey(k)) {
                            ret.put(k, e.getValue());
                        }
                    }
                    flagStripFieldFullColumns.put(flag, ret);
                }
            }
        }
        return ret;
    }

    public LinkedHashMap<String, String> getFieldFullColumns(Flag flag) {
        Args.notNull(flag, "flag");

        if (flagFieldFullColumns == null) {
            synchronized (this) {
                if (flagFieldFullColumns == null) {
                    flagFieldFullColumns = new ConcurrentHashMap<>();
                }
            }
        }
        LinkedHashMap<String, String> ret = flagFieldFullColumns.get(flag);
        if (ret == null) {
            synchronized (flagFieldFullColumns) {
                ret = flagFieldFullColumns.get(flag);
                if (ret == null) {
                    ret = new LinkedHashMap<>();
                    for (Entry<String, ColumnDefine> e : fieldColumns.entrySet()) {
                        ColumnDefine cd = e.getValue();
                        if (!checkFlag(cd, flag)) {
                            continue;
                        }
                        String f = e.getKey();
                        String c = MapperUtils.joinTableColumn(table, cd.getName());
                        if (!ret.containsKey(f)) {
                            ret.put(f, c);
                        }
                    }
                    flagFieldFullColumns.put(flag, ret);
                }
            }
        }
        return ret;
    }

    public LinkedHashMap<String, String> getPropertyFieldFullColumns(Flag flag) {
        Args.notNull(flag, "flag");

        if (flagPropertyFieldFullColumns == null) {
            synchronized (this) {
                if (flagPropertyFieldFullColumns == null) {
                    flagPropertyFieldFullColumns = new ConcurrentHashMap<>();
                }
            }
        }
        LinkedHashMap<String, String> ret = flagPropertyFieldFullColumns.get(flag);
        if (ret == null) {
            synchronized (flagPropertyFieldFullColumns) {
                ret = flagPropertyFieldFullColumns.get(flag);
                if (ret == null) {
                    LinkedHashMap<String, String> ffcs = getFieldFullColumns(flag);
                    if (StringUtils.isEmpty(property)) {
                        ret = ffcs;
                    } else {
                        ret = new LinkedHashMap<>();
                        for (Entry<String, String> e : ffcs.entrySet()) {
                            String pf = MapperUtils.joinPropertyField(property, e.getKey());
                            if (!ret.containsKey(pf)) {
                                ret.put(pf, e.getValue());
                            }
                        }
                        flagPropertyFieldFullColumns.put(flag, ret);
                    }
                }
            }
        }
        return ret;
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

        return new TableMapper(tableMapper.clazz, tableMapper.alias, tableMapper.table, property,
                tableMapper.primarys, tableMapper.fieldColumns);
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
