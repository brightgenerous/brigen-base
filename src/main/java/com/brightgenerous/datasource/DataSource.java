package com.brightgenerous.datasource;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.brightgenerous.lang.Args;
import com.google.inject.Injector;

public abstract class DataSource {

    private static volatile Class<? extends DataSource> dataSourceClazz;

    private static volatile DataSource instance;

    private static final Map<Class<? extends DataSource>, SoftReference<? extends DataSource>> cache = new ConcurrentHashMap<>();

    private transient volatile InjectorFactory injectorFactory;

    private transient volatile Initializer initializer;

    private transient volatile Destroyer destroyer;

    protected DataSource() {
    }

    public static void set(Class<? extends DataSource> clazz) {
        Args.notNull(clazz, "clazz");

        if (dataSourceClazz != clazz) {
            synchronized (DataSource.class) {
                if (dataSourceClazz != clazz) {

                    checkAndGetConstructor(clazz);

                    dataSourceClazz = clazz;
                    instance = null;
                }
            }
        }
    }

    public static DataSource get() {
        DataSource ret = instance;
        if (ret == null) {
            synchronized (DataSource.class) {
                ret = instance;
                if (ret == null) {

                    if (dataSourceClazz == null) {
                        throw new IllegalStateException(
                                "the Data Source Class yet has not been set.");
                    }

                    instance = get(dataSourceClazz);
                    ret = instance;
                }
            }
        }
        return ret;
    }

    public static DataSource get(Class<? extends DataSource> clazz) {
        if (clazz == null) {
            return get();
        }
        DataSource ret = null;
        {
            SoftReference<? extends DataSource> ref = cache.get(clazz);
            if (ref != null) {
                ret = ref.get();
            }
        }
        if (ret == null) {
            synchronized (cache) {
                SoftReference<? extends DataSource> ref = cache.get(clazz);
                if (ref != null) {
                    ret = ref.get();
                }
                if (ret == null) {
                    cache.remove(clazz);
                    Set<Class<? extends DataSource>> dels = new HashSet<>();
                    for (Entry<Class<? extends DataSource>, SoftReference<? extends DataSource>> entry : cache
                            .entrySet()) {
                        if (entry.getValue().get() == null) {
                            dels.add(entry.getKey());
                        }
                    }
                    if (!dels.isEmpty()) {
                        for (Class<? extends DataSource> del : dels) {
                            cache.remove(del);
                        }
                    }
                    ret = newInstance(clazz);
                    cache.put(clazz, new SoftReference<>(ret));
                }
            }
        }
        return ret;
    }

    private static DataSource newInstance(Class<? extends DataSource> clazz) {
        try {
            Constructor<? extends DataSource> cons = checkAndGetConstructor(clazz);
            return cons.newInstance();
        } catch (SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Constructor<? extends DataSource> checkAndGetConstructor(
            Class<? extends DataSource> clazz) {
        try {
            Constructor<? extends DataSource> cons = clazz.getDeclaredConstructor();
            if (!Modifier.isPublic(cons.getModifiers()) && !cons.isAccessible()) {
                cons.setAccessible(true);
            }
            return cons;
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract InjectorFactory createInjectorFactory();

    protected Initializer createInitializer() {
        return null;
    }

    protected Destroyer createDestroyer() {
        return null;
    }

    protected InjectorFactory getInjectorFactory() {
        if (injectorFactory == null) {
            synchronized (this) {
                if (injectorFactory == null) {
                    injectorFactory = createInjectorFactory();
                }
            }
        }
        return injectorFactory;
    }

    public void initialize() {
        getInjectorFactory().initialize();

        if (initializer == null) {
            synchronized (this) {
                if (initializer == null) {
                    initializer = createInitializer();
                }
            }
        }
        if (initializer != null) {
            initializer.initialize(getInjector());
        }

        getInjectorFactory().verify();
    }

    public void destroy() {
        if (destroyer == null) {
            synchronized (this) {
                if (destroyer == null) {
                    destroyer = createDestroyer();
                }
            }
        }
        if (destroyer != null) {
            destroyer.destroy(getInjector());
        }

        getInjectorFactory().destroy();
    }

    public <T> T instance(Class<T> clazz) {
        return instance(clazz, false);
    }

    public <T> T instance(Class<T> clazz, boolean rollback) {
        return getInjector(rollback).getInstance(clazz);
    }

    protected Injector getInjector() {
        return getInjector(false);
    }

    protected Injector getInjector(boolean rollback) {
        return getInjectorFactory().getInjector(rollback);
    }
}
