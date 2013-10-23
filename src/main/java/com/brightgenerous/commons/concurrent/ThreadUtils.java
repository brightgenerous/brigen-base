package com.brightgenerous.commons.concurrent;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.brightgenerous.lang.Args;

public class ThreadUtils implements Serializable {

    private static final long serialVersionUID = 1485395160469358267L;

    static class InstanceKey implements Serializable {

        private static final long serialVersionUID = -5571606798438371038L;

        private final Class<?> clazz;

        private final String key;

        public InstanceKey(Class<?> clazz, String key) {
            this.clazz = clazz;
            this.key = key;
        }

        @Override
        public int hashCode() {
            final int multiplier = 37;
            int result = 17;
            result = (multiplier * result) + hashCodeEscapeNull(clazz);
            result = (multiplier * result) + hashCodeEscapeNull(key);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof InstanceKey)) {
                return false;
            }

            InstanceKey other = (InstanceKey) obj;

            if (!equalsEscapeNull(clazz, other.clazz)) {
                return false;
            }
            if (!equalsEscapeNull(key, other.key)) {
                return false;
            }
            return true;
        }
    }

    private transient volatile boolean init;

    private transient volatile ExecutorService executorService;

    private Integer threads;

    protected ThreadUtils() {
        this(null);
    }

    protected ThreadUtils(Integer threads) {
        setIfGreater(threads);
    }

    public static ThreadUtils get() {
        return get((Integer) null);
    }

    public static ThreadUtils get(Integer threads) {
        return get((Class<?>) null, threads);
    }

    public static ThreadUtils get(Class<?> clazz) {
        return get(clazz, null);
    }

    public static ThreadUtils get(Class<?> clazz, Integer threads) {
        return getInstance(clazz, null, threads);
    }

    public static ThreadUtils get(String key) {
        return get(key, null);
    }

    public static ThreadUtils get(String key, Integer threads) {
        return getInstance(null, key, threads);
    }

    // maybe, it may not should use SoftReference.
    private static volatile Map<InstanceKey, SoftReference<ThreadUtils>> cache;

    protected static ThreadUtils getInstance(Class<?> clazz, String key, Integer threads) {
        if (cache == null) {
            synchronized (ThreadUtils.class) {
                if (cache == null) {
                    cache = new ConcurrentHashMap<>();
                }
            }
        }
        InstanceKey ik = new InstanceKey(clazz, key);
        SoftReference<ThreadUtils> sr = cache.get(ik);
        ThreadUtils ret;
        if (sr != null) {
            ret = sr.get();
            if (ret != null) {
                ret.setIfGreater(threads);
                return ret;
            }
            Set<InstanceKey> dels = new HashSet<>();
            for (Entry<InstanceKey, SoftReference<ThreadUtils>> entry : cache.entrySet()) {
                if (entry.getValue().get() == null) {
                    dels.add(entry.getKey());
                }
            }
            for (InstanceKey del : dels) {
                cache.remove(del);
            }
        }
        ret = new ThreadUtils(threads);
        cache.put(ik, new SoftReference<>(ret));
        return ret;
    }

    protected void setIfGreater(Integer threads) {
        if (threads != null) {
            Args.greaterEqual(Integer.valueOf(0), threads, "threads");

            synchronized (this) {
                if ((this.threads != threads)
                        && ((this.threads == null) || (this.threads.compareTo(threads) < 0))) {
                    this.threads = threads;
                    init = false;
                }
            }
        }
    }

    protected ExecutorService getExecutorService() {
        if (!init) {
            synchronized (this) {
                if (!init) {
                    if ((threads == null) || (threads.intValue() < 1)) {
                        executorService = null;
                    } else {
                        executorService = Executors.newFixedThreadPool(threads.intValue());
                    }
                    init = true;
                }
            }
        }
        return executorService;
    }

    public void execute(Runnable runnable) {
        Args.notNull(runnable, "runnable");

        ExecutorService executerService = getExecutorService();
        if (executerService != null) {
            executorService.execute(runnable);
        } else {
            runnable.run();
        }
    }
}
