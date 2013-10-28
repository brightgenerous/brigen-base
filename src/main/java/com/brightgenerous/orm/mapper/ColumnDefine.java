package com.brightgenerous.orm.mapper;

import java.io.Serializable;

public class ColumnDefine implements Serializable {

    private static final long serialVersionUID = 8637401974816423294L;

    private final String name;

    private final Class<?> type;

    private final String[] propertys;

    private final boolean enableSelect;

    private final boolean enableInsert;

    private final boolean enableUpdate;

    public ColumnDefine(String name, Class<?> type, String property, boolean enableSelect,
            boolean enableInsert, boolean enableUpdate) {
        this(name, type, new String[] { property }, enableSelect, enableInsert, enableUpdate);
    }

    public ColumnDefine(String name, Class<?> type, String[] propertys, boolean enableSelect,
            boolean enableInsert, boolean enableUpdate) {
        this.name = name;
        this.type = type;
        this.propertys = propertys;
        this.enableSelect = enableSelect;
        this.enableInsert = enableInsert;
        this.enableUpdate = enableUpdate;
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
}
