package com.brightgenerous.lang;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.brightgenerous.commons.ResourceUtils;
import com.brightgenerous.commons.StringUtils;

class ChainPropertyResourceBundle extends PropertyResourceBundle {

    public ChainPropertyResourceBundle(InputStream stream, String parentKey) throws IOException {
        this(stream, parentKey, null);
    }

    protected ChainPropertyResourceBundle(InputStream stream, String parentKey, Set<String> parents)
            throws IOException {
        super(stream);

        if (parentKey != null) {
            String value = null;
            if (containsKey(parentKey)) {
                value = getString(parentKey);
            }
            if (StringUtils.isNotEmpty(value)) {
                ResourceBundle parent = createFromPath(value, parentKey, parents);
                if (parent != null) {
                    setParent(parent);
                }
            }
        }
    }

    public ChainPropertyResourceBundle(Reader reader, String parentKey) throws IOException {
        this(reader, parentKey, null);
    }

    protected ChainPropertyResourceBundle(Reader reader, String parentKey, Set<String> parents)
            throws IOException {
        super(reader);

        if (parentKey != null) {
            String value = null;
            if (containsKey(parentKey)) {
                value = getString(parentKey);
            }
            if (StringUtils.isNotEmpty(value)) {
                ResourceBundle parent = createFromPath(value, parentKey, parents);
                if (parent != null) {
                    setParent(parent);
                }
            }
        }
    }

    private static final Map<String, WeakReference<ResourceBundle>> cache = new ConcurrentHashMap<>();

    protected static ResourceBundle createFromPath(String path, String parentKey,
            Set<String> parents) throws IOException {
        if (path == null) {
            return null;
        }
        if ((parents != null) && parents.contains(path)) {
            return null;
        }
        ResourceBundle ret = null;
        {
            WeakReference<ResourceBundle> wr = cache.get(path);
            if (wr != null) {
                ret = wr.get();
                if (ret == null) {
                    cache.remove(path);
                }
            }
        }
        if (ret == null) {
            synchronized (cache) {
                {
                    WeakReference<ResourceBundle> wr = cache.get(path);
                    if (wr != null) {
                        ret = wr.get();
                        if (ret == null) {
                            cache.remove(path);
                        }
                    }
                }
                if (ret == null) {
                    if (parents == null) {
                        parents = new HashSet<>();
                    } else {
                        parents = new HashSet<>(parents);
                    }
                    parents.add(path);
                    ret = new ChainPropertyResourceBundle(ResourceUtils.getInputStream(path),
                            parentKey, parents);
                    cache.put(path, new WeakReference<>(ret));
                }
            }
        }
        return ret;
    }
}
