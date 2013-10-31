package com.brightgenerous.injection.jdbc.guice;

public class InjectorConfig extends com.brightgenerous.injection.InjectorConfig {

    private static final long serialVersionUID = -6919051427808331995L;

    private String dataSourceName;

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }
}
