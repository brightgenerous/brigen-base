package com.brightgenerous.orm.mapper;

import java.io.Serializable;
import java.util.Arrays;

import com.brightgenerous.lang.Args;

public class ColumnDefine implements Serializable {

    private static final long serialVersionUID = 8637401974816423294L;

    private static final String[] EMPTY = new String[] {};

    private final String name;

    private final Class<?> type;

    private final String[] propertys;

    private final boolean enableSelect;

    private final boolean enableInsert;

    private final boolean enableUpdate;

    private final boolean defaultIfNull;

    public ColumnDefine(String name, Class<?> type, String property, boolean enableSelect,
            boolean enableInsert, boolean enableUpdate, boolean defaultIfNull) {
        this(name, type, new String[] { property }, enableSelect, enableInsert, enableUpdate,
                defaultIfNull);
    }

    public ColumnDefine(String name, Class<?> type, String[] propertys, boolean enableSelect,
            boolean enableInsert, boolean enableUpdate, boolean defaultIfNull) {
        Args.notEmpty(name, "name");
        Args.notNull(type, "type");
        Args.notEmpty(propertys, "propertys");

        this.name = name;
        this.type = type;
        this.propertys = (propertys == null) ? EMPTY : Arrays.copyOf(propertys, propertys.length);
        this.enableSelect = enableSelect;
        this.enableInsert = enableInsert;
        this.enableUpdate = enableUpdate;
        this.defaultIfNull = defaultIfNull;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public String[] getPropertys() {
        return propertys;
    }

    public boolean isEnableSelect() {
        return enableSelect;
    }

    public boolean isEnableInsert() {
        return enableInsert;
    }

    public boolean isEnableUpdate() {
        return enableUpdate;
    }

    public boolean isDefaultIfNull() {
        return defaultIfNull;
    }
}
