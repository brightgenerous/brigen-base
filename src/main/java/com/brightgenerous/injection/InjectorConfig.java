package com.brightgenerous.injection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Provider;
import javax.sql.DataSource;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public class InjectorConfig implements Serializable {

    private static final long serialVersionUID = -4376299232759423461L;

    private Class<? extends Provider<DataSource>> dataSourceProviderType;

    private Map<String, String> dbProperties;

    private Class<?>[] transactionPackages;

    private Filter<Class<?>> transactionClassFilter;

    private Filter<Method> transactionMethodFilter;

    private Class<?>[] mapperPackages;

    private Filter<Class<?>> mapperClassFilter;

    private ImplResolver mapperImplResolver;

    private Class<?>[] beanPackages;

    private Filter<Class<?>> beanClassFilter;

    // for serialize
    private void writeObject(ObjectOutputStream stream) throws IOException {
        if ((dbProperties != null) && !(dbProperties instanceof Serializable)) {
            dbProperties = new HashMap<>(dbProperties); // to java.io.Serializable
        }
        stream.defaultWriteObject();
    }

    public Class<? extends Provider<DataSource>> getDataSourceProviderType() {
        return dataSourceProviderType;
    }

    public void setDataSourceProviderType(
            Class<? extends Provider<DataSource>> dataSourceProviderType) {
        this.dataSourceProviderType = dataSourceProviderType;
    }

    public Map<String, String> getDbProperties() {
        return dbProperties;
    }

    public void setDbProperties(Map<String, String> dbProperties) {
        this.dbProperties = dbProperties;
    }

    public Class<?>[] getTransactionPackages() {
        return transactionPackages;
    }

    public void setTransactionPackages(Class<?>... transactionPackages) {
        this.transactionPackages = transactionPackages;
    }

    public Filter<Class<?>> getTransactionClassFilter() {
        return transactionClassFilter;
    }

    public void setTransactionClassFilter(Filter<Class<?>> transactionClassFilter) {
        this.transactionClassFilter = transactionClassFilter;
    }

    public Filter<Method> getTransactionMethodFilter() {
        return transactionMethodFilter;
    }

    public void setTransactionMethodFilter(Filter<Method> transactionMethodFilter) {
        this.transactionMethodFilter = transactionMethodFilter;
    }

    public Class<?>[] getMapperPackages() {
        return mapperPackages;
    }

    public void setMapperPackages(Class<?>... mapperPackages) {
        this.mapperPackages = mapperPackages;
    }

    public Filter<Class<?>> getMapperClassFilter() {
        return mapperClassFilter;
    }

    public void setMapperClassFilter(Filter<Class<?>> mapperClassFilter) {
        this.mapperClassFilter = mapperClassFilter;
    }

    public ImplResolver getMapperImplResolver() {
        return mapperImplResolver;
    }

    public void setMapperImplResolver(ImplResolver mapperImplResolver) {
        this.mapperImplResolver = mapperImplResolver;
    }

    public Class<?>[] getBeanPackages() {
        return beanPackages;
    }

    public void setBeanPackages(Class<?>... beanPackages) {
        this.beanPackages = beanPackages;
    }

    public Filter<Class<?>> getBeanClassFilter() {
        return beanClassFilter;
    }

    public void setBeanClassFilter(Filter<Class<?>> beanClassFilter) {
        this.beanClassFilter = beanClassFilter;
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
