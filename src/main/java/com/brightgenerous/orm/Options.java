package com.brightgenerous.orm;

import java.io.Serializable;

import com.brightgenerous.lang.Args;

public class Options extends AbstractUpdatedCallbackableMap<String, Option<Serializable>> {

    private static final long serialVersionUID = -74948305336935190L;

    private final ConditionContext context;

    protected Options(ConditionContext context, UpdatedCallback callback) {
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
    protected Option<Serializable> createValue(UpdatedCallback uc) {
        return getContext().newOption(uc);
    }

    @Override
    public Options clone() {
        return (Options) super.clone();
    }

    public <T extends Serializable> boolean containsKey(TypeKey<T, ?> key) {
        Args.notNull(key, "key");

        Object k = key.getKey();
        return super.containsKey(k);
    }

    public <T extends Serializable> Option<T> get(TypeKey<T, ?> key) {
        Args.notNull(key, "key");

        return (Option<T>) super.get(key.getKey());
    }

    public <T extends Serializable> Option<T> put(TypeKey<T, String> key, Option<Serializable> value) {
        Args.notNull(key, "key");

        String k = key.getKey();
        return (Option<T>) super.put(k, value);
    }

    public <T extends Serializable> Option<T> remove(TypeKey<T, ?> key) {
        Args.notNull(key, "key");

        return (Option<T>) super.remove(key.getKey());
    }
}
