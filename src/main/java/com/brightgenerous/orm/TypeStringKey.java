package com.brightgenerous.orm;

import java.io.Serializable;

public class TypeStringKey<T extends Serializable> extends TypeKey<T, String> {

    private static final long serialVersionUID = -4778293062987592882L;

    public TypeStringKey(String key) {
        super(key);
    }
}
