package com.brightgenerous.orm;

import java.io.Serializable;

import com.brightgenerous.lang.Args;

public class Fields extends AbstractUpdatedCallbackableMap<String, Field<Serializable>> {

    private static final long serialVersionUID = -74948305336935190L;

    private final ConditionContext context;

    protected Fields(ConditionContext context, UpdatedCallback callback) {
        super(callback);

        this.context = context;
    }

    @Override
    protected ConditionContext getContext() {
        return context;
    }

    @Override
    protected String convertKey(Object key) {
        if (key == null) {
            return null;
        }
        if (key instanceof String) {
            return (String) key;
        }
        return String.valueOf(key);
    }

    @Override
    protected Field<Serializable> createValue(UpdatedCallback uc) {
        return getContext().newField(uc);
    }

    @Override
    public Fields clone() {
        return (Fields) super.clone();
    }

    public <T extends Serializable> boolean containsKey(TypeKey<T, ?> key) {
        Args.notNull(key, "key");

        Object k = key.getKey();
        return super.containsKey(k);
    }

    public <T extends Serializable> Field<T> get(TypeKey<T, ?> key) {
        Args.notNull(key, "key");

        return (Field<T>) super.get(key.getKey());
    }

    public <T extends Serializable> Field<T> put(TypeKey<T, String> key, Field<Serializable> value) {
        Args.notNull(key, "key");

        String k = key.getKey();
        return (Field<T>) super.put(k, value);
    }

    public <T extends Serializable> Field<T> remove(TypeKey<T, ?> key) {
        Args.notNull(key, "key");

        return (Field<T>) super.remove(key.getKey());
    }
}
