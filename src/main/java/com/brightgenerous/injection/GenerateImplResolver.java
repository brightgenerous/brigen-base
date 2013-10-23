package com.brightgenerous.injection;

import com.brightgenerous.cglib.CglibUtils;

public abstract class GenerateImplResolver implements ImplResolver {

    private static final long serialVersionUID = -3882972724203907781L;

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> clazz) {
        String name = getImplName(clazz);
        {
            Class<?> clz = null;
            try {
                clz = Class.forName(name);
            } catch (ClassNotFoundException e) {
            }
            if (clz != null) {
                if (!clazz.isAssignableFrom(clz)) {
                    throw new IllegalStateException(String.format("%s is not assignable from %s.",
                            clazz, clz));
                }
                return (Class<? extends T>) clz;
            }
        }
        return (Class<T>) CglibUtils.defineInterface(name, clazz);
    }

    protected abstract String getImplName(Class<?> clazz);
}
