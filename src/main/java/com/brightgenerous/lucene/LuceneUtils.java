package com.brightgenerous.lucene;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.lang.Args;
import com.brightgenerous.lucene.delegate.LuceneUtility;
import com.brightgenerous.lucene.delegate.StringDistanceDelegater;

@SuppressWarnings("deprecation")
public class LuceneUtils implements Serializable {

    private static final long serialVersionUID = -5768707421292489384L;

    public static boolean resolved() {
        return LuceneUtility.RESOLVED;
    }

    static class InstanceKey implements Serializable {

        private static final long serialVersionUID = -5571606798438371038L;

        private final boolean levenstein;

        private final boolean jaroWinkler;

        public InstanceKey(boolean levenstein, boolean jaroWinkler) {
            this.levenstein = levenstein;
            this.jaroWinkler = jaroWinkler;
        }

        @Override
        public int hashCode() {
            final int multiplier = 37;
            int result = 17;
            result = (multiplier * result) + (levenstein ? 1 : 0);
            result = (multiplier * result) + (jaroWinkler ? 1 : 0);
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

            if (levenstein != other.levenstein) {
                return false;
            }
            if (jaroWinkler != other.jaroWinkler) {
                return false;
            }
            return true;
        }
    }

    private final boolean levenstein;

    private final boolean jaroWinkler;

    private LuceneUtils(boolean levenstein, boolean jaroWinkler) {
        Args.isTrue(levenstein || jaroWinkler, "(levenstein || jaroWinkler) must be true.");

        this.levenstein = levenstein;
        this.jaroWinkler = jaroWinkler;
    }

    public static LuceneUtils get() {
        return getInstance(true, true);
    }

    public static LuceneUtils getLevenstein() {
        return getInstance(true, false);
    }

    public static LuceneUtils getJaroWinkler() {
        return getInstance(false, true);
    }

    private static volatile Map<InstanceKey, SoftReference<LuceneUtils>> cache;

    protected static LuceneUtils getInstance(boolean levenstein, boolean jaroWinkler) {
        if (cache == null) {
            synchronized (LuceneUtils.class) {
                if (cache == null) {
                    cache = new ConcurrentHashMap<>();
                }
            }
        }
        InstanceKey ik = new InstanceKey(levenstein, jaroWinkler);
        SoftReference<LuceneUtils> sr = cache.get(ik);
        LuceneUtils ret;
        if (sr != null) {
            ret = sr.get();
            if (ret != null) {
                return ret;
            }
            Set<InstanceKey> dels = new HashSet<>();
            for (Entry<InstanceKey, SoftReference<LuceneUtils>> entry : cache.entrySet()) {
                if (entry.getValue().get() == null) {
                    dels.add(entry.getKey());
                }
            }
            for (InstanceKey del : dels) {
                cache.remove(del);
            }
        }
        ret = new LuceneUtils(levenstein, jaroWinkler);
        cache.put(ik, new SoftReference<>(ret));
        return ret;
    }

    public String near(String value, Collection<String> objs) {
        return near(value, objs, levenstein, jaroWinkler);
    }

    public String near(String value, String[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return near(value, Arrays.asList(objs), levenstein, jaroWinkler);
    }

    public <T> T near(String value, Extracter<T> extracter, Collection<T> objs) {
        return near(value, extracter, objs, levenstein, jaroWinkler);
    }

    public <T> T near(String value, Extracter<T> extracter, T[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return near(value, extracter, Arrays.asList(objs), levenstein, jaroWinkler);
    }

    public String far(String value, Collection<String> objs) {
        return far(value, objs, levenstein, jaroWinkler);
    }

    public String far(String value, String[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return far(value, Arrays.asList(objs), levenstein, jaroWinkler);
    }

    public <T> T far(String value, Extracter<T> extracter, Collection<T> objs) {
        return far(value, extracter, objs, levenstein, jaroWinkler);
    }

    public <T> T far(String value, Extracter<T> extracter, T[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return far(value, extracter, Arrays.asList(objs), levenstein, jaroWinkler);
    }

    public static String nearLevenstein(String value, Collection<String> objs) {
        return near(value, objs, true, false);
    }

    public static String nearLevenstein(String value, String[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return nearLevenstein(value, Arrays.asList(objs));
    }

    public static String nearJaroWinkler(String value, Collection<String> objs) {
        return near(value, objs, false, true);
    }

    public static String nearJaroWinkler(String value, String[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return nearJaroWinkler(value, Arrays.asList(objs));
    }

    public static String nearBoth(String value, Collection<String> objs) {
        return near(value, objs, true, true);
    }

    public static String nearBoth(String value, String[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return nearBoth(value, Arrays.asList(objs));
    }

    private static String near(String value, Collection<String> objs, boolean levenstein,
            boolean jaroWinkler) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || objs.isEmpty()) {
            return null;
        }
        double dist = Double.MIN_VALUE;
        String ret = null;
        for (String obj : objs) {
            double d = getDistance(obj, value, levenstein, jaroWinkler);
            if (1.0d <= d) {
                return obj;
            }
            if (dist < d) {
                dist = d;
                ret = obj;
            }
        }
        return ret;
    }

    public static <T> T nearLevenstein(String value, Extracter<T> extracter, Collection<T> objs) {
        return near(value, extracter, objs, true, false);
    }

    public static <T> T nearLevenstein(String value, Extracter<T> extracter, T[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return nearLevenstein(value, extracter, Arrays.asList(objs));
    }

    public static <T> T nearJaroWinkler(String value, Extracter<T> extracter, Collection<T> objs) {
        return near(value, extracter, objs, false, true);
    }

    public static <T> T nearJaroWinkler(String value, Extracter<T> extracter, T[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return nearJaroWinkler(value, extracter, Arrays.asList(objs));
    }

    public static <T> T nearBoth(String value, Extracter<T> extracter, Collection<T> objs) {
        return near(value, extracter, objs, true, true);
    }

    public static <T> T nearBoth(String value, Extracter<T> extracter, T[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return nearBoth(value, extracter, Arrays.asList(objs));
    }

    private static <T> T near(String value, Extracter<T> extracter, Collection<T> objs,
            boolean levenstein, boolean jaroWinkler) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || objs.isEmpty()) {
            return null;
        }
        if (extracter == null) {
            extracter = getDefaultExtracter();
        }
        double dist = Double.MIN_VALUE;
        T ret = null;
        for (T obj : objs) {
            double d = getDistance(extracter.extract(obj), value, levenstein, jaroWinkler);
            if (1.0d <= d) {
                return obj;
            }
            if (dist < d) {
                dist = d;
                ret = obj;
            }
        }
        return ret;
    }

    public static String farLevenstein(String value, Collection<String> objs) {
        return far(value, objs, true, false);
    }

    public static String farLevenstein(String value, String[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return farLevenstein(value, Arrays.asList(objs));
    }

    public static String farJaroWinkler(String value, Collection<String> objs) {
        return far(value, objs, false, true);
    }

    public static String farJaroWinkler(String value, String[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return farJaroWinkler(value, Arrays.asList(objs));
    }

    public static String farBoth(String value, Collection<String> objs) {
        return far(value, objs, true, true);
    }

    public static String farBoth(String value, String[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return farBoth(value, Arrays.asList(objs));
    }

    private static String far(String value, Collection<String> objs, boolean levenstein,
            boolean jaroWinkler) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || objs.isEmpty()) {
            return null;
        }
        double dist = Double.MAX_VALUE;
        String ret = null;
        for (String obj : objs) {
            double d = getDistance(obj, value, levenstein, jaroWinkler);
            if (d <= 0.0d) {
                return obj;
            }
            if (d < dist) {
                dist = d;
                ret = obj;
            }
        }
        return ret;
    }

    public static <T> T farLevenstein(String value, Extracter<T> extracter, Collection<T> objs) {
        return far(value, extracter, objs, true, false);
    }

    public static <T> T farLevenstein(String value, Extracter<T> extracter, T[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return farLevenstein(value, extracter, Arrays.asList(objs));
    }

    public static <T> T farJaroWinkler(String value, Extracter<T> extracter, Collection<T> objs) {
        return far(value, extracter, objs, false, true);
    }

    public static <T> T farJaroWinkler(String value, Extracter<T> extracter, T[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return farJaroWinkler(value, extracter, Arrays.asList(objs));
    }

    public static <T> T farBoth(String value, Extracter<T> extracter, Collection<T> objs) {
        return far(value, extracter, objs, true, true);
    }

    public static <T> T farBoth(String value, Extracter<T> extracter, T[] objs) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || (objs.length < 1)) {
            return null;
        }
        return farBoth(value, extracter, Arrays.asList(objs));
    }

    private static <T> T far(String value, Extracter<T> extracter, Collection<T> objs,
            boolean levenstein, boolean jaroWinkler) {
        if (value == null) {
            return null;
        }
        if ((objs == null) || objs.isEmpty()) {
            return null;
        }
        if (extracter == null) {
            extracter = getDefaultExtracter();
        }
        double dist = Double.MAX_VALUE;
        T ret = null;
        for (T obj : objs) {
            double d = getDistance(extracter.extract(obj), value, levenstein, jaroWinkler);
            if (d <= 0.0d) {
                return obj;
            }
            if (d < dist) {
                dist = d;
                ret = obj;
            }
        }
        return ret;
    }

    public Comparator<String> comparator(String value) {
        return comparator(value, levenstein, jaroWinkler);
    }

    public static Comparator<String> comparatorLevenstein(String value) {
        return comparator(value, true, false);
    }

    public static Comparator<String> comparatorJaroWinkler(String value) {
        return comparator(value, false, true);
    }

    public static Comparator<String> comparatorBoth(String value) {
        return comparator(value, true, true);
    }

    private static Comparator<String> comparator(String value, boolean levenstein,
            boolean jaroWinkler) {
        Args.notNull(value, "value");
        Args.isTrue(levenstein || jaroWinkler, "(levenstein || jaroWinkler) must be true.");

        return new StringComparator(value, levenstein, jaroWinkler);
    }

    public <T> Comparator<T> comparator(String value, Extracter<T> extracter) {
        return comparator(value, extracter, levenstein, jaroWinkler);
    }

    public static <T> Comparator<T> comparatorLevenstein(String value, Extracter<T> extracter) {
        return comparator(value, extracter, true, false);
    }

    public static <T> Comparator<T> comparatorJaroWinkler(String value, Extracter<T> extracter) {
        return comparator(value, extracter, false, true);
    }

    public static <T> Comparator<T> comparatorBoth(String value, Extracter<T> extracter) {
        return comparator(value, extracter, true, true);
    }

    private static <T> Comparator<T> comparator(String value, Extracter<T> extracter,
            boolean levenstein, boolean jaroWinkler) {
        Args.notNull(value, "value");
        Args.isTrue(levenstein || jaroWinkler, "(levenstein || jaroWinkler) must be true.");

        if (extracter == null) {
            extracter = getDefaultExtracter();
        }
        return new ExtractComparator<>(value, extracter, levenstein, jaroWinkler);
    }

    private static <T> Extracter<T> getDefaultExtracter() {
        return new Extracter<T>() {

            @Override
            public String extract(T obj) {
                if (obj == null) {
                    return null;
                }
                if (obj instanceof String) {
                    return (String) obj;
                }
                return String.valueOf(obj);
            }
        };
    }

    private static volatile StringDistanceDelegater ld;

    private static volatile StringDistanceDelegater jd;

    private static double getDistance(String obj, String value, boolean levenstein,
            boolean jaroWinkler) {
        if ((levenstein && (ld == null)) || (jaroWinkler && (jd == null))) {
            synchronized (LuceneUtils.class) {
                if (levenstein && (ld == null)) {
                    ld = LuceneUtility.createLevensteinDistance();
                }
                if (jaroWinkler && (jd == null)) {
                    jd = LuceneUtility.createJaroWinklerDistance();
                }
            }
        }
        if (levenstein && jaroWinkler) {
            return Math.pow(ld.getDistance(obj, value), 2)
                    + Math.pow(jd.getDistance(obj, value), 2);
        }
        if (levenstein) {
            return ld.getDistance(obj, value);
        }
        if (jaroWinkler) {
            return jd.getDistance(obj, value);
        }
        throw new IllegalStateException();
    }

    static class StringComparator implements Comparator<String> {

        private final String value;

        private final boolean levenstein;

        private final boolean jaroWinkler;

        private final Map<String, Double> caches = Collections
                .synchronizedMap(new WeakHashMap<String, Double>());

        public StringComparator(String value, boolean levenstein, boolean jaroWinkler) {
            this.value = value;
            this.levenstein = levenstein;
            this.jaroWinkler = jaroWinkler;
        }

        @Override
        public int compare(String o1, String o2) {
            double d1 = distance(o1);
            double d2 = distance(o2);
            // between 0 ... 1.0, correct closer to 1.0
            if (d1 == d2) {
                if (o1 == o2) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                return o1.compareTo(o2);
            }
            if (d1 < d2) {
                return 1;
            }
            return -1;
        }

        private double distance(String obj) {
            Double ret = caches.get(obj);
            if (ret == null) {
                synchronized (caches) {
                    ret = caches.get(obj);
                    if (ret == null) {
                        ret = Double.valueOf(getDistance(obj, value, levenstein, jaroWinkler));
                        caches.put(obj, ret);
                    }
                }
            }
            return ret.doubleValue();
        }
    }

    static class ExtractComparator<T> implements Comparator<T> {

        private final String value;

        private final Extracter<T> extracter;

        private final boolean levenstein;

        private final boolean jaroWinkler;

        private final Map<T, Holder> caches = Collections
                .synchronizedMap(new WeakHashMap<T, Holder>());

        public ExtractComparator(String value, Extracter<T> extracter, boolean levenstein,
                boolean jaroWinkler) {
            this.value = value;
            this.extracter = extracter;
            this.levenstein = levenstein;
            this.jaroWinkler = jaroWinkler;
        }

        @Override
        public int compare(T o1, T o2) {
            Holder h1 = distance(o1);
            Holder h2 = distance(o2);
            // between 0 ... 1.0, correct closer to 1.0
            if (h1.distance == h2.distance) {
                if (h1.value == h2.value) {
                    return 0;
                }
                if (h1.value == null) {
                    return -1;
                }
                if (h2.value == null) {
                    return 1;
                }
                return h1.value.compareTo(h2.value);
            }
            if (h1.distance < h2.distance) {
                return 1;
            }
            return -1;
        }

        private Holder distance(T obj) {
            Holder ret = caches.get(obj);
            if (ret == null) {
                synchronized (caches) {
                    ret = caches.get(obj);
                    if (ret == null) {
                        ret = new Holder();
                        ret.value = extracter.extract(obj);
                        ret.distance = getDistance(ret.value, value, levenstein, jaroWinkler);
                        caches.put(obj, ret);
                    }
                }
            }
            return ret;
        }

        static class Holder {

            double distance;

            String value;
        }
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.resolved()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.resolved()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (ToStringUtils.resolved()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }
}
