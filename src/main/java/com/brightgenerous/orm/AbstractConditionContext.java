package com.brightgenerous.orm;

import java.io.Serializable;

public abstract class AbstractConditionContext implements ConditionContext {

    private static final long serialVersionUID = 6345067996061492274L;

    protected AbstractConditionContext() {
    }

    @Override
    public Fields newFields(UpdatedCallback callback) {
        return new Fields(this, callback);
    }

    @Override
    public <T extends Serializable> Field<T> newField(UpdatedCallback callback) {
        return new Field<>(callback);
    }

    @Override
    public Options newOptions(UpdatedCallback callback) {
        return new Options(this, callback);
    }

    @Override
    public <T extends Serializable> Option<T> newOption(UpdatedCallback callback) {
        return new Option<>(callback);
    }

    @Override
    public Sorts newSorts(UpdatedCallback callback) {
        return new Sorts(this, callback);
    }

    @Override
    public Sort newSort(String key) {
        return new Sort(key);
    }

    @Override
    public Sort newSort(String key, boolean asc) {
        return new Sort(key, asc);
    }
}
