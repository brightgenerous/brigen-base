package com.brightgenerous.orm.mapper;

import static com.brightgenerous.commons.StringConvertUtils.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.StringUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.lang.Args;
import com.brightgenerous.orm.Ignore;

public class MapperUtils {

    private MapperUtils() {
    }

    public static void define(TableDefines defines, Register register, String table,
            PropertyReference... propertyReferences) {
        define(defines, register, new String[] { table }, propertyReferences);
    }

    public static void define(TableDefines defines, Register register, String[] tables,
            PropertyReference... propertyReferences) {
        Args.notNull(defines, "defines");
        Args.notNull(register, "register");
        Args.notEmpty(tables, "tables");

        for (String table : tables) {
            if (StringUtils.isEmpty(table)) {
                throw new IllegalStateException("The tables has empty table value.");
            }
            if (defines.containsKey(table)) {
                throw new IllegalStateException(String.format("already defined for %s", table));
            }
            TableMapper tableMapper = register.getTableMapper(table);
            if (tableMapper == null) {
                throw new IllegalStateException(
                        String.format("not found TableMapper for %s", table));
            }
            TableDefine define;
            {
                Map<String, TableMapper> ptms = new HashMap<>();
                if ((propertyReferences != null) && (0 < propertyReferences.length)) {
                    for (PropertyReference pr : propertyReferences) {
                        if (pr == null) {
                            throw new IllegalStateException(
                                    "The propertyReferences has null element.");
                        }
                        String p = pr.property();
                        String r = pr.reference();
                        if (register.checkStrict()) {
                            checkProperty(p);
                        }
                        TableMapper tm = register.getTableMapper(r);
                        if (tm == null) {
                            throw new IllegalStateException(String.format(
                                    "not found TableMapper for %s", r));
                        }
                        if (ptms.containsKey(p)) {
                            throw new IllegalStateException(String.format(
                                    "duplicate property %s with reference %s.", p, r));
                        }
                        ptms.put(p, TableMapper.createAlt(tm, p));
                    }
                }
                define = new TableDefine(tableMapper, ptms);
            }
            defines.put(table, define);
        }
    }

    public static void load(Register register, Class<?> clazz, String table) {
        load(register, clazz, table, (String[]) null);
    }

    public static void load(Register register, Class<?> clazz, String table, String[] aliases) {
        load(register, clazz, table, aliases, (FieldColumn[]) null);
    }

    public static void load(Register register, Class<?> clazz, String table,
            FieldColumn... fieldColumns) {
        load(register, clazz, table, null, fieldColumns);
    }

    public static void load(Register register, Class<?> clazz, String table, String[] aliases,
            FieldColumn... fieldColumns) {
        Args.notNull(register, "register");
        Args.notNull(clazz, "clazz");
        Args.notNull(table, "table");

        LinkedHashMap<String, ColumnDefine> fcs = new LinkedHashMap<>();
        {
            for (Field field : clazz.getDeclaredFields()) {
                if (isFieldColumn(field, register.getTypes())) {
                    String f = field.getName();
                    String c = toSnake(f, register.fieldToSnakeColumn());
                    if (fcs.containsKey(f)) {
                        throw new IllegalStateException(String.format(
                                "duplicate field %s with column %s.", f, c));
                    }
                    if (register.checkStrict()) {
                        checkFieldName(f);
                        checkColumnName(c);
                    }
                    fcs.put(f, new ColumnDefine(c, field.getType(), f, enableSelect(field),
                            enableInsert(field), enableUpdate(field)));
                }
            }
            for (Field field : clazz.getFields()) {
                if (isFieldColumn(field, register.getTypes())) {
                    String f = field.getName();
                    String c = toSnake(f, register.fieldToSnakeColumn());
                    if (fcs.containsKey(f)) {
                        throw new IllegalStateException(String.format(
                                "duplicate field %s with column %s.", f, c));
                    }
                    if (register.checkStrict()) {
                        checkFieldName(f);
                        checkColumnName(c);
                    }
                    fcs.put(f, new ColumnDefine(c, field.getType(), f, enableSelect(field),
                            enableInsert(field), enableUpdate(field)));
                }
            }
            if ((fieldColumns != null) && (0 < fieldColumns.length)) {
                for (FieldColumn fc : fieldColumns) {
                    if (fc == null) {
                        throw new IllegalStateException("The fieldColumns has null element.");
                    }
                    String f = fc.field();
                    String c = fc.column();
                    if (c == null) {
                        c = toSnake(MapperUtils.spritField(f), register.fieldToSnakeColumn());
                    }
                    if (!fc.override() && fcs.containsKey(f)) {
                        throw new IllegalStateException(String.format(
                                "duplicate field %s with column %s.", f, c));
                    }
                    String[] propertys = f.split("\\."); // argument is regex. @see String#split(String)
                    Class<?> type = checkPropertysField(clazz, propertys, register.getTypes());
                    if (register.checkStrict()) {
                        checkFieldName(f);
                        checkColumnName(c);
                    }
                    Ignore.Type it = fc.ignore();
                    fcs.put(f, new ColumnDefine(c, type, propertys, enableSelect(it),
                            enableInsert(it), enableUpdate(it)));
                }
            }
        }

        putTableMapper(register, clazz, table, false, fcs);
        if ((aliases != null) && (0 < aliases.length)) {
            for (String alias : aliases) {
                putTableMapper(register, clazz, alias, true, fcs);
            }
        }
    }

    private static void putTableMapper(Register register, Class<?> clazz, String table,
            boolean alias, LinkedHashMap<String, ColumnDefine> fcs) {
        if (StringUtils.isEmpty(table)) {
            throw new IllegalStateException("The tables has empty table value.");
        }
        if (register.checkStrict()) {
            checkTableName(table);
        }
        if (register.getTableMapper(table) != null) {
            throw new IllegalStateException(String.format("already registed for %s", table));
        }
        register.putTableMapper(table, new TableMapper(clazz, alias, table, fcs));
    }

    private static void checkProperty(String property) {
        Args.notEmpty(property, "property");

        if (!property.matches("^[A-Za-z_]+[0-9A-Za-z_.]*$")) {
            throw new IllegalStateException(String.format("The property illegal string %s",
                    property));
        }
    }

    private static Class<?> checkPropertysField(Class<?> clazz, String[] propertys,
            Set<Class<?>> types) {
        Args.notNull(clazz, "clazz");
        Args.notEmpty(propertys, "propertys");

        Class<?> clz = clazz;
        for (String property : propertys) {
            Field fld;
            try {
                fld = clz.getDeclaredField(property);
            } catch (NoSuchFieldException | SecurityException e) {
                throw new IllegalStateException(String.format(
                        "occured exception, field %s in %s at part %s.",
                        Arrays.toString(propertys), clazz.getName(), property));
            }
            clz = fld.getType();
        }
        if (!isFieldColumn(clz, types)) {
            throw new IllegalStateException(String.format("The field %s illegal type %s",
                    Arrays.toString(propertys), clz.getName()));
        }
        return clz;
    }

    private static void checkFieldName(String field) {
        Args.notEmpty(field, "field");

        if (!field.matches("^[A-Za-z_]+[0-9A-Za-z_.]*$")) {
            throw new IllegalStateException(String.format("The field illegal string %s", field));
        }
    }

    private static void checkTableName(String table) {
        Args.notEmpty(table, "table");

        if (!table.matches("^[A-Za-z_]+[0-9A-Za-z_]*$")) {
            throw new IllegalStateException(String.format("The table illegal string %s", table));
        }
    }

    private static void checkColumnName(String column) {
        Args.notEmpty(column, "column");

        if (!column.matches("^[A-Za-z_]+[0-9A-Za-z_]*$")) {
            throw new IllegalStateException(String.format("The column illegal string %s", column));
        }
    }

    private static boolean isFieldColumn(Field field, Set<Class<?>> types) {
        if (field == null) {
            return false;
        }
        Class<?> type = field.getType();
        if (Modifier.isStatic(field.getModifiers())) {
            return false;
        }
        if (Void.class.isAssignableFrom(type)) {
            return false;
        }
        if (ignoreAll(field)) {
            return false;
        }
        return isFieldColumn(type, types);
    }

    private static boolean isFieldColumn(Class<?> type, Set<Class<?>> types) {
        if (type.isPrimitive() || Boolean.class.isAssignableFrom(type)
                || Number.class.isAssignableFrom(type) || Character.class.isAssignableFrom(type)
                || CharSequence.class.isAssignableFrom(type) || Date.class.isAssignableFrom(type)
                || (type.isArray() && type.getComponentType().equals(Byte.TYPE))
                || Blob.class.isAssignableFrom(type) || Clob.class.isAssignableFrom(type)) {
            return true;
        }
        if ((types != null) && !types.isEmpty()) {
            for (Class<?> t : types) {
                if ((t != null) && t.isAssignableFrom(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean ignoreAll(Field field) {
        return ignore(field, null, Ignore.Type.ALL);
    }

    private static boolean enableSelect(Field field) {
        return !ignore(field, Ignore.Type.SELECT, Ignore.Type.SELECT_INSERT,
                Ignore.Type.SELECT_UPDATE);
    }

    private static boolean enableSelect(Ignore.Type type) {
        return !exists(type, Ignore.Type.SELECT, Ignore.Type.SELECT_INSERT,
                Ignore.Type.SELECT_UPDATE);
    }

    private static boolean enableInsert(Field field) {
        return !ignore(field, Ignore.Type.SELECT_INSERT, Ignore.Type.INSERT,
                Ignore.Type.INSERT_UPDATE);
    }

    private static boolean enableInsert(Ignore.Type type) {
        return !exists(type, Ignore.Type.SELECT_INSERT, Ignore.Type.INSERT,
                Ignore.Type.INSERT_UPDATE);
    }

    private static boolean enableUpdate(Field field) {
        return !ignore(field, Ignore.Type.SELECT_UPDATE, Ignore.Type.INSERT_UPDATE,
                Ignore.Type.UPDATE);
    }

    private static boolean enableUpdate(Ignore.Type type) {
        return !exists(type, Ignore.Type.SELECT_UPDATE, Ignore.Type.INSERT_UPDATE,
                Ignore.Type.UPDATE);
    }

    private static boolean ignore(Field field, Ignore.Type... types) {
        Ignore ignore = field.getAnnotation(Ignore.class);
        if (ignore == null) {
            return false;
        }
        Ignore.Type type = ignore.value();
        return exists(type, types);
    }

    private static boolean exists(Ignore.Type type, Ignore.Type... types) {
        for (Ignore.Type t : types) {
            if ((t == null) || (type == null)) {
                if (type == t) {
                    return true;
                }
            } else {
                if (type.equals(t)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean verify(TableMapper tableMapper, Map<String, String> columnTypes,
            TypeComparator typeComparator) {
        if (tableMapper.isAlias()) {
            throw new IllegalStateException("TableMapper must not be Alias.");
        }
        for (Entry<String, ColumnDefine> e : tableMapper.getFieldColumns().entrySet()) {
            ColumnDefine cd = e.getValue();
            Class<?> fieldType = cd.getType();
            String columnName = cd.getName();
            if (columnName == null) {
                throw new IllegalStateException(String.format(
                        "not found column for field name %s in class %s.", e.getKey(),
                        fieldType.getName()));
            }
            String columnType = columnTypes.get(columnName);
            if (columnType == null) {
                throw new IllegalStateException(String.format(
                        "not found column type for column name %s, field name %s in class %s.",
                        columnName, e.getKey(), fieldType.getName()));
            }
            if ((typeComparator != null) && !typeComparator.compare(fieldType, columnType)) {
                throw new IllegalStateException(String.format(
                        "illegal column type %s for column name %s, field name %s in class %s.",
                        columnType, columnName, e.getKey(), fieldType.getName()));
            }
        }
        return true;
    }

    private static String toSnake(String field, FieldToColumnCase ftc) {
        String ret;
        switch (ftc) {
            case NONE:
                ret = field;
                break;
            case SNAKE:
                ret = toSnakeCase(field);
                break;
            case SNAKE_NUMBER:
                ret = toSnakeCase(field, true);
                break;
            default:
                throw new IllegalStateException(String.format("not found FieldColumnCase for %s",
                        ftc));
        }
        return ret;
    }

    public static String spritField(String field) {
        if (StringUtils.isEmpty(field)) {
            return field;
        }
        int index;
        if ((index = field.lastIndexOf(".")) != -1) {
            return field.substring(index + 1);
        }
        return field;
    }

    public static String joinTableColumn(String table, String column) {
        if (StringUtils.isEmpty(table) || StringUtils.isEmpty(column)) {
            return column;
        }
        return table + "." + column;
    }

    public static String joinPropertyField(String property, String field) {
        if (StringUtils.isEmpty(property) || StringUtils.isEmpty(field)) {
            return field;
        }
        return property + "." + field;
    }

    public static class FieldColumn implements Serializable {

        private static final long serialVersionUID = 6744735469951734482L;

        private final String field;

        private final String column;

        private boolean primary;

        private boolean override;

        private Ignore.Type ignore;

        protected FieldColumn(String field, String column, boolean primary, boolean override,
                Ignore.Type ignore) {
            Args.notEmpty(field, "field");

            this.field = field;
            this.column = column;
            this.primary = primary;
            this.override = override;
            this.ignore = ignore;
        }

        public static FieldColumn create(String field) {
            return create(field, null);
        }

        public static FieldColumn create(String field, String column) {
            return create(field, column, false);
        }

        public static FieldColumn create(String field, String column, boolean primary) {
            return create(field, column, primary, false);
        }

        public static FieldColumn create(String field, String column, boolean primary,
                boolean override) {
            return create(field, column, primary, override, null);
        }

        public static FieldColumn create(String field, String column, boolean primary,
                boolean override, Ignore.Type ignore) {
            return new FieldColumn(field, column, primary, override, ignore);
        }

        public String field() {
            return field;
        }

        public String column() {
            return column;
        }

        public boolean primary() {
            return primary;
        }

        public FieldColumn primaryTrue() {
            return primary(true);
        }

        public FieldColumn primary(boolean primary) {
            this.primary = primary;
            return this;
        }

        public boolean override() {
            return override;
        }

        public FieldColumn overrideTrue() {
            return override(true);
        }

        public FieldColumn override(boolean override) {
            this.override = override;
            return this;
        }

        public Ignore.Type ignore() {
            return ignore;
        }

        public FieldColumn ignore(Ignore.Type ignore) {
            this.ignore = ignore;
            return this;
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

    public static class PropertyReference implements Serializable {

        private static final long serialVersionUID = 7439389826779040219L;

        private final String property;

        private final String reference;

        protected PropertyReference(String property, String reference) {
            Args.notEmpty(property, "property");
            Args.notEmpty(reference, "reference");

            this.property = property;
            this.reference = reference;
        }

        public static PropertyReference create(String property, String reference) {
            return new PropertyReference(property, reference);
        }

        public String property() {
            return property;
        }

        public String reference() {
            return reference;
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
}
