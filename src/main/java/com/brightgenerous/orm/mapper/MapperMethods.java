package com.brightgenerous.orm.mapper;

import static com.brightgenerous.commons.ObjectUtils.*;
import static com.brightgenerous.commons.StringUtils.*;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.brightgenerous.bean.AbstractBean;
import com.brightgenerous.bean.BeanUtils;
import com.brightgenerous.commons.StringUtils;
import com.brightgenerous.datasource.DataSource;
import com.brightgenerous.lang.Args;
import com.brightgenerous.orm.Field;
import com.brightgenerous.orm.Fields;
import com.brightgenerous.orm.IField;
import com.brightgenerous.orm.Sort;
import com.brightgenerous.orm.Sorts;

/*
 * Helper Class for MyBatis Mapper.xml script.
 * 
 * may be, not necessary to you...
 */
public abstract class MapperMethods implements Serializable {

    private static final long serialVersionUID = 964438583312587615L;

    static class InstanceKey implements Serializable {

        private static final long serialVersionUID = 8248992966756884872L;

        private final Class<? extends MapperMethods> clazz;

        private final String table;

        public InstanceKey(Class<? extends MapperMethods> clazz, String table) {
            this.clazz = clazz;
            this.table = table;
        }

        @Override
        public int hashCode() {
            final int multiplier = 37;
            int result = 17;
            result = (multiplier * result) + hashCodeEscapeNull(clazz);
            result = (multiplier * result) + hashCodeEscapeNull(table);
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
            if (!equalsEscapeNull(table, other.table)) {
                return false;
            }
            return true;
        }
    }

    private static volatile Class<? extends MapperMethods> mapperMethodClazz;

    private static final Map<InstanceKey, SoftReference<? extends MapperMethods>> cache = new ConcurrentHashMap<>();

    protected MapperMethods() {
    }

    public static void set(Class<? extends MapperMethods> clazz) {
        Args.notNull(clazz, "clazz");
        if (mapperMethodClazz != clazz) {
            synchronized (DataSource.class) {
                if (mapperMethodClazz != clazz) {

                    checkAndGetConstructor(clazz);

                    mapperMethodClazz = clazz;
                }
            }
        }
    }

    public static MapperMethods get() {
        return get((String) null);
    }

    public static MapperMethods get(String table) {
        Class<? extends MapperMethods> clazz = mapperMethodClazz;
        if (clazz == null) {
            synchronized (MapperMethods.class) {
                clazz = mapperMethodClazz;

                if (clazz == null) {
                    throw new IllegalStateException(
                            "the Mapper Methods Class yet has not been set.");
                }

            }
        }
        return get(clazz, table);
    }

    public static MapperMethods get(Class<? extends MapperMethods> clazz) {
        return get(clazz, null);
    }

    public static MapperMethods get(Class<? extends MapperMethods> clazz, String table) {
        if (clazz == null) {
            return get(table);
        }
        InstanceKey key = new InstanceKey(clazz, table);
        MapperMethods ret = null;
        {
            SoftReference<? extends MapperMethods> ref = cache.get(key);
            if (ref != null) {
                ret = ref.get();
            }
        }
        if (ret == null) {
            synchronized (cache) {
                SoftReference<? extends MapperMethods> ref = cache.get(key);
                if (ref != null) {
                    ret = ref.get();
                }
                if (ret == null) {
                    cache.remove(key);
                    Set<InstanceKey> dels = new HashSet<>();
                    for (Entry<InstanceKey, SoftReference<? extends MapperMethods>> entry : cache
                            .entrySet()) {
                        if (entry.getValue().get() == null) {
                            dels.add(entry.getKey());
                        }
                    }
                    if (!dels.isEmpty()) {
                        for (InstanceKey del : dels) {
                            cache.remove(del);
                        }
                    }
                    ret = newInstance(clazz, table);
                    cache.put(key, new SoftReference<>(ret));
                }
            }
        }
        return ret;
    }

    private static MapperMethods newInstance(Class<? extends MapperMethods> clazz, String table) {
        try {
            Constructor<? extends MapperMethods> cons = checkAndGetConstructor(clazz);
            MapperMethods ret = cons.newInstance();
            ret.setTable(table);
            return ret;
        } catch (SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Constructor<? extends MapperMethods> checkAndGetConstructor(
            Class<? extends MapperMethods> clazz) {
        try {
            Constructor<? extends MapperMethods> cons = clazz.getDeclaredConstructor();
            if (!Modifier.isPublic(cons.getModifiers()) && !cons.isAccessible()) {
                cons.setAccessible(true);
            }
            return cons;
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    private String table;

    protected String getTable() {
        return table;
    }

    protected void setTable(String table) {
        this.table = table;
    }

    public boolean isNull(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractBean)) {
            return false;
        }
        return BeanUtils.primaryNull((AbstractBean) obj);
    }

    public boolean notNull(Object obj) {
        return !isNull(obj);
    }

    public boolean contains(String[] propertys) {
        return contains(propertys, Collections.EMPTY_SET);
    }

    public boolean contains(String[] propertys, String include) {
        return contains(propertys, asList(include));
    }

    public boolean contains(String[] propertys, String include, String[] excludes) {
        return contains(propertys, asList(include), excludes);
    }

    public boolean contains(String[] propertys, String include, Collection<String> excludes) {
        return contains(propertys, asList(include), excludes);
    }

    public boolean contains(String[] propertys, String[] includes) {
        return contains(propertys, asList(includes));
    }

    public boolean contains(String[] propertys, String[] includes, String[] excludes) {
        return contains(propertys, asList(includes), excludes);
    }

    public boolean contains(String[] propertys, String[] includes, Collection<String> excludes) {
        return contains(propertys, asList(includes), excludes);
    }

    public boolean contains(String[] propertys, Collection<String> includes) {
        return contains(propertys, includes, Collections.EMPTY_SET);
    }

    public boolean contains(String[] propertys, Collection<String> includes, String[] excludes) {
        return contains(propertys, includes, asList(excludes));
    }

    public boolean contains(String[] propertys, Collection<String> includes,
            Collection<String> excludes) {
        return contains(asList(propertys), includes, excludes);
    }

    public boolean contains(Collection<String> propertys) {
        return contains(propertys, Collections.EMPTY_SET);
    }

    public boolean contains(Collection<String> propertys, String include) {
        return contains(propertys, asList(include));
    }

    public boolean contains(Collection<String> propertys, String include, String[] excludes) {
        return contains(propertys, asList(include), excludes);
    }

    public boolean contains(Collection<String> propertys, String include,
            Collection<String> excludes) {
        return contains(propertys, asList(include), excludes);
    }

    public boolean contains(Collection<String> propertys, String[] includes) {
        return contains(propertys, asList(includes));
    }

    public boolean contains(Collection<String> propertys, String[] includes, String[] excludes) {
        return contains(propertys, asList(includes), excludes);
    }

    public boolean contains(Collection<String> propertys, String[] includes,
            Collection<String> excludes) {
        return contains(propertys, asList(includes), excludes);
    }

    public boolean contains(Collection<String> propertys, Collection<String> includes) {
        return contains(propertys, includes, Collections.EMPTY_SET);
    }

    public boolean contains(Collection<String> propertys, Collection<String> includes,
            String[] excludes) {
        return contains(propertys, includes, asList(excludes));
    }

    public boolean contains(Collection<String> propertys, Collection<String> includes,
            Collection<String> excludes) {
        if ((propertys == null) || propertys.isEmpty() || (includes == null) || includes.isEmpty()) {
            return false;
        }
        List<String> retains = removeAll(propertys, excludes);
        for (String include : includes) {
            if (retains.contains(include)) {
                return true;
            }
        }
        return false;
    }

    public LinkedHashMap<String, String> filterSelects() {
        return filterSelects(Collections.EMPTY_SET);
    }

    public LinkedHashMap<String, String> filterSelects(String[] propertys) {
        return filterSelects(asList(propertys));
    }

    public LinkedHashMap<String, String> filterSelects(String[] propertys, String[] excludes) {
        return filterSelects(propertys, asList(excludes));
    }

    public LinkedHashMap<String, String> filterSelects(String[] propertys,
            Collection<String> excludes) {
        return filterSelects(asList(propertys), excludes);
    }

    public LinkedHashMap<String, String> filterSelects(Collection<String> propertys) {
        return filterSelects(propertys, Collections.EMPTY_SET);
    }

    public LinkedHashMap<String, String> filterSelects(Collection<String> propertys,
            String[] excludes) {
        return filterSelects(propertys, asList(excludes));
    }

    public LinkedHashMap<String, String> filterSelects(Collection<String> propertys,
            Collection<String> excludes) {
        LinkedHashMap<String, String> ret = new LinkedHashMap<>();
        {
            List<TableMapper> tms = getTableMappers(getTable(), removeAll(propertys, excludes));
            if ((tms != null) && !tms.isEmpty()) {
                for (TableMapper tm : tms) {
                    if (tm == null) {
                        continue;
                    }
                    Map<String, String> pffcs = tm.getPropertyFieldFullColumns();
                    if ((pffcs != null) && !pffcs.isEmpty()) {
                        for (Entry<String, String> e : pffcs.entrySet()) {
                            String k = e.getKey();
                            if (!ret.containsKey(k)) {
                                ret.put(k, e.getValue());
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }

    public LinkedHashMap<String, IField<Object>> filterFields(Fields fields) {
        return filterFields(fields, Collections.EMPTY_SET);
    }

    public LinkedHashMap<String, IField<Object>> filterFields(Fields fields, String[] propertys) {
        return filterFields(fields, asList(propertys));
    }

    public LinkedHashMap<String, IField<Object>> filterFields(Fields fields, String[] propertys,
            String[] excludes) {
        return filterFields(fields, propertys, asList(excludes));
    }

    public LinkedHashMap<String, IField<Object>> filterFields(Fields fields, String[] propertys,
            Collection<String> excludes) {
        return filterFields(fields, asList(propertys), excludes);
    }

    public LinkedHashMap<String, IField<Object>> filterFields(Fields fields,
            Collection<String> propertys) {
        return filterFields(fields, propertys, Collections.EMPTY_SET);
    }

    public LinkedHashMap<String, IField<Object>> filterFields(Fields fields,
            Collection<String> propertys, String[] excludes) {
        return filterFields(fields, propertys, asList(excludes));
    }

    public LinkedHashMap<String, IField<Object>> filterFields(Fields fields,
            Collection<String> propertys, Collection<String> excludes) {
        LinkedHashMap<String, IField<Object>> ret = new LinkedHashMap<>();
        if ((fields != null) && !fields.isEmpty()) {
            List<TableMapper> tms = getTableMappers(getTable(), removeAll(propertys, excludes));
            if ((tms != null) && !tms.isEmpty()) {
                for (Entry<String, Field<Serializable>> entry : fields.entrySet()) {
                    List<String> columns = getFullColumns(entry.getKey(), tms);
                    if ((columns == null) || columns.isEmpty()) {
                        continue;
                    }
                    for (String column : columns) {
                        if (isNotEmpty(column)) {
                            Field<Serializable> field = entry.getValue();
                            if (!field.isEmpty()) {
                                ret.put(column, field.getWrap());
                                if (!filterFieldsMulti()) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }

    public LinkedHashMap<String, Boolean> filterSorts(Sorts sorts) {
        return filterSorts(sorts, Collections.EMPTY_SET);
    }

    public LinkedHashMap<String, Boolean> filterSorts(Sorts sorts, String[] propertys) {
        return filterSorts(sorts, asList(propertys));
    }

    public LinkedHashMap<String, Boolean> filterSorts(Sorts sorts, String[] propertys,
            String[] excludes) {
        return filterSorts(sorts, propertys, asList(excludes));
    }

    public LinkedHashMap<String, Boolean> filterSorts(Sorts sorts, String[] propertys,
            Collection<String> excludes) {
        return filterSorts(sorts, asList(propertys), excludes);
    }

    public LinkedHashMap<String, Boolean> filterSorts(Sorts sorts, Collection<String> propertys) {
        return filterSorts(sorts, propertys, Collections.EMPTY_SET);
    }

    public LinkedHashMap<String, Boolean> filterSorts(Sorts sorts, Collection<String> propertys,
            String[] excludes) {
        return filterSorts(sorts, propertys, asList(excludes));
    }

    public LinkedHashMap<String, Boolean> filterSorts(Sorts sorts, Collection<String> propertys,
            Collection<String> excludes) {
        LinkedHashMap<String, Boolean> ret = new LinkedHashMap<>();
        if ((sorts != null) && !sorts.isEmpty()) {
            List<TableMapper> tms = getTableMappers(getTable(), removeAll(propertys, excludes));
            if ((tms != null) && !tms.isEmpty()) {
                for (Sort sort : sorts) {
                    List<String> columns = getFullColumns(sort.getKey(), tms);
                    if ((columns == null) || columns.isEmpty()) {
                        continue;
                    }
                    for (String column : columns) {
                        if (isNotEmpty(column)) {
                            ret.put(column, sort.getAsc() ? Boolean.TRUE : Boolean.FALSE);
                            if (!filterSortsMulti()) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }

    protected <T> List<T> asList(T... objs) {
        if ((objs == null) || (objs.length < 1)) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(objs);
    }

    protected <T> List<T> removeAll(Collection<T> origin, Collection<T> remove) {
        if ((remove == null) || remove.isEmpty()) {
            return new ArrayList<>(origin);
        }
        List<T> ret = new ArrayList<>();
        for (T obj : origin) {
            if (!remove.contains(obj)) {
                ret.add(obj);
            }
        }
        return ret;
    }

    protected boolean filterFieldsMulti() {
        return false;
    }

    protected boolean filterSortsMulti() {
        return false;
    }

    protected List<String> getFullColumns(String field, List<TableMapper> tableMappers) {
        List<String> ret = new ArrayList<>();
        if (StringUtils.isNotEmpty(field) && (tableMappers != null) && !tableMappers.isEmpty()) {
            if (field.indexOf(".") == -1) {
                for (TableMapper tableMapper : tableMappers) {
                    if (tableMapper == null) {
                        continue;
                    }
                    String fc = null;
                    {
                        Map<String, String> sffcs = tableMapper.getStripFieldFullColumns();
                        if ((sffcs != null) && sffcs.containsKey(field)) {
                            fc = sffcs.get(field);
                        }
                    }
                    if (StringUtils.isNotEmpty(fc)) {
                        ret.add(fc);
                    }
                }
            } else {
                for (TableMapper tableMapper : tableMappers) {
                    if (tableMapper == null) {
                        continue;
                    }
                    String fc = null;
                    {
                        Map<String, String> pffcs = tableMapper.getPropertyFieldFullColumns();
                        if ((pffcs != null) && pffcs.containsKey(field)) {
                            fc = pffcs.get(field);
                        }
                    }
                    if (StringUtils.isEmpty(fc)) {
                        Map<String, String> ffcs = tableMapper.getFieldFullColumns();
                        if ((ffcs != null) && ffcs.containsKey(field)) {
                            fc = ffcs.get(field);
                        }
                    }
                    if (StringUtils.isNotEmpty(fc)) {
                        ret.add(fc);
                    }
                }
            }
        }
        return ret;
    }

    protected List<TableMapper> getTableMappers(String table, Collection<String> propertys) {
        List<TableMapper> ret = new ArrayList<>();
        TableDefines defines = getDefines();
        if ((defines != null) && !defines.isEmpty()) {
            TableDefine define = defines.get(table);
            if (define == null) {
                throw new IllegalStateException(
                        String.format("not found TableDefine for %s", table));
            }
            ret.add(define.getTableMapper());
            if ((propertys != null) && !propertys.isEmpty()) {
                Map<String, TableMapper> propTms = define.getPropertyTableMappers();
                if ((propTms != null) && !propTms.isEmpty()) {
                    for (String p : propertys) {
                        if (StringUtils.isEmpty(p)) {
                            continue;
                        }
                        TableMapper propTm = propTms.get(p);
                        if (propTm != null) {
                            ret.add(propTm);
                        }
                    }
                }
            }
        }
        return ret;
    }

    public boolean verify(String table, Map<String, String> columnTypes,
            TypeComparator typeComparator) {
        TableDefines defines = getDefines();
        if ((defines != null) && !defines.isEmpty()) {
            TableDefine define = defines.get(table);
            if (define == null) {
                throw new IllegalStateException(
                        String.format("not found TableDefine for %s", table));
            }
            return MapperUtils.verify(define.getTableMapper(), columnTypes, typeComparator);
        }
        return true;
    }

    protected abstract TableDefines getDefines();

    public abstract Map<Class<?>, String> getTargetTables();
}
