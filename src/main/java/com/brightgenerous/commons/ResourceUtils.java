package com.brightgenerous.commons;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.brightgenerous.commons.crypto.CryptoUtils;
import com.brightgenerous.lang.Args;

public class ResourceUtils implements Serializable {

    private static final long serialVersionUID = 9090432624771374976L;

    static class InstanceKey implements Serializable {

        private static final long serialVersionUID = 7729994660988922129L;

        private final Class<?> clazz;

        private final String file;

        private final String encode;

        public InstanceKey(Class<?> clazz, String file, String encode) {
            this.clazz = clazz;
            this.file = file;
            this.encode = encode;
        }

        @Override
        public int hashCode() {
            final int multiplier = 37;
            int result = 17;
            result = (multiplier * result) + hashCodeEscapeNull(clazz);
            result = (multiplier * result) + hashCodeEscapeNull(file);
            result = (multiplier * result) + hashCodeEscapeNull(encode);
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
            if (!equalsEscapeNull(file, other.file)) {
                return false;
            }
            if (!equalsEscapeNull(encode, other.encode)) {
                return false;
            }
            return true;
        }
    }

    private final Class<?> clazz;

    private final String file;

    private final String encode;

    protected ResourceUtils(Class<?> clazz, String file, String encode) {
        Args.notNull(file, "file");

        this.clazz = clazz;
        this.file = file;
        this.encode = encode;
    }

    public static ResourceUtils get(String file) {
        return get(file, null);
    }

    public static ResourceUtils get(String file, String encode) {
        return get(null, file, encode);
    }

    public static ResourceUtils get(Class<?> clazz, String file) {
        return get(clazz, file, null);
    }

    public static ResourceUtils get(Class<?> clazz, String file, String encode) {
        return getInstance(clazz, file, encode);
    }

    private static volatile Map<InstanceKey, SoftReference<ResourceUtils>> cache;

    protected static ResourceUtils getInstance(Class<?> clazz, String file, String encode) {
        if (cache == null) {
            synchronized (CryptoUtils.class) {
                if (cache == null) {
                    cache = new ConcurrentHashMap<>();
                }
            }
        }
        InstanceKey ik = new InstanceKey(clazz, file, encode);
        SoftReference<ResourceUtils> sr = cache.get(ik);
        ResourceUtils ret;
        if (sr != null) {
            ret = sr.get();
            if (ret != null) {
                return ret;
            }
            Set<InstanceKey> dels = new HashSet<>();
            for (Entry<InstanceKey, SoftReference<ResourceUtils>> entry : cache.entrySet()) {
                if (entry.getValue().get() == null) {
                    dels.add(entry.getKey());
                }
            }
            for (InstanceKey del : dels) {
                cache.remove(del);
            }
        }
        ret = new ResourceUtils(clazz, file, encode);
        cache.put(ik, new SoftReference<>(ret));
        return ret;
    }

    public InputStream getInputStream() {
        return getInputStream(clazz, file);
    }

    public Reader getReader() throws IOException {
        return getReader(clazz, file, encode);
    }

    public String readAsString() throws IOException {
        return readAsString(clazz, file, encode);
    }

    public Properties readAsPropetries() throws IOException {
        return readAsPropetries(clazz, file, encode);
    }

    public static InputStream getInputStream(String file) {
        return getInputStream(null, file);
    }

    public static InputStream getInputStream(Class<?> clazz, String file) {
        Args.notNull(file, "file");

        if (clazz == null) {
            return ClassLoader.getSystemResourceAsStream(file);
        }
        return clazz.getResourceAsStream(file);
    }

    public static Reader getReader(String file) throws IOException {
        return getReader(file, null);
    }

    public static Reader getReader(String file, String encode) throws IOException {
        return getReader(null, file, encode);
    }

    public static Reader getReader(Class<?> clazz, String file) throws IOException {
        return getReader(clazz, file, null);
    }

    public static Reader getReader(Class<?> clazz, String file, String encode) throws IOException {
        Args.notNull(file, "file");

        InputStream inputStream = getInputStream(clazz, file);
        if (inputStream == null) {
            return null;
        }
        if (encode == null) {
            return new InputStreamReader(inputStream);
        }
        return new InputStreamReader(inputStream, encode);
    }

    public static String readAsString(String file) throws IOException {
        return readAsString(file, null);
    }

    public static String readAsString(String file, String encode) throws IOException {
        return readAsString(null, file, encode);
    }

    public static String readAsString(Class<?> clazz, String file) throws IOException {
        return readAsString(clazz, file, null);
    }

    public static String readAsString(Class<?> clazz, String file, String encode)
            throws IOException {
        Args.notNull(file, "file");

        String ret = null;
        InputStream inputStream = getInputStream(clazz, file);
        if (inputStream != null) {
            BufferedReader bufferedReader;
            if (encode == null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encode));
            }
            try (BufferedReader br = bufferedReader) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                ret = sb.toString();
            }
        }
        return ret;
    }

    public static Properties readAsPropetries(String file) throws IOException {
        return readAsPropetries(file, null);
    }

    public static Properties readAsPropetries(String file, String encode) throws IOException {
        return readAsPropetries(null, file, encode);
    }

    public static Properties readAsPropetries(Class<?> clazz, String file) throws IOException {
        return readAsPropetries(clazz, file, null);
    }

    public static Properties readAsPropetries(Class<?> clazz, String file, String encode)
            throws IOException {
        Args.notNull(file, "file");

        Properties ret = null;
        InputStream inputStream = getInputStream(clazz, file);
        if (inputStream != null) {
            if (encode == null) {
                try (InputStream is = inputStream) {
                    ret = new Properties();
                    ret.load(is);
                }
            } else {
                try (Reader r = new InputStreamReader(inputStream, encode)) {
                    ret = new Properties();
                    ret.load(r);
                }
            }
        }
        return ret;
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.useful()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.useful()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (ToStringUtils.useful()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }
}
