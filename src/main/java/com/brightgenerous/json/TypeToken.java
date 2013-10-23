package com.brightgenerous.json;

import java.lang.reflect.Type;

import com.brightgenerous.json.delegate.JsonUtility;

@SuppressWarnings("deprecation")
public abstract class TypeToken<T> {

    private transient volatile Type type;

    public Type getType() {
        if (type == null) {
            synchronized (this) {
                if (type == null) {
                    type = JsonUtility.getType(this);
                }
            }
        }
        return type;
    }
}
