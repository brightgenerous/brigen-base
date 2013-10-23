package com.brightgenerous.orm.mapper;

import static com.brightgenerous.commons.StringConvertUtils.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.brightgenerous.commons.StringUtils;
import com.brightgenerous.lang.Args;
import com.brightgenerous.orm.Ignore;

public class MapperUtils {

    private MapperUtils() {
    }

    public static void define(TableDefines defines, Register register, String table,
            String[]... propertyReferences) {
        define(defines, register, new String[] { table }, propertyReferences);
    }

    public static void define(TableDefines defines, Register register, String[] tables,
            String[]... propertyReferences) {
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
                    for (String[] pr : propertyReferences) {
                        if ((pr == null) || (pr.length < 2)) {
                            throw new IllegalStateException(
                                    "The propertyReferences has illegal array which null or too short length.");
                        }
                        String p = pr[0];
                        String r = pr[1];
                        if (StringUtils.isEmpty(p)) {
                            throw new IllegalStateException(
                                    "The propertyReferences has empty property value.");
                        }
                        if (StringUtils.isEmpty(r)) {
                            throw new IllegalStateException(
                                    "The propertyReferences has empty reference value.");
                        }
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
        load(register, clazz, table, null, null);
    }

    public static void load(Register register, Class<?> clazz, String table, String[] aliases) {
        load(register, clazz, table, aliases, null);
    }

    public static void load(Register register, Class<?> clazz, String table, String[][] fieldColumns) {
        load(register, clazz, table, null, fieldColumns);
    }

    public static void load(Register register, Class<?> clazz, String table, String[] aliases,
            String[][] fieldColumns) {
        Args.notNull(register, "register");
        Args.notNull(clazz, "clazz");
        Args.notNull(table, "table");

        LinkedHashSet<Field> fields = new LinkedHashSet<>();
        LinkedHashMap<String, String> fcs = new LinkedHashMap<>();
        {
            for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                if (isRegistColumn(field, register.getTypes())) {
                    String f = field.getName();
                    String c;
                    switch (register.fieldToSnakeColumn()) {
                        case NONE:
                            c = f;
                            break;
                        case SNAKE:
                            c = toSnakeCase(f);
                            break;
                        case SNAKE_NUMBER:
                            c = toSnakeCase(f, true);
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                    if (fcs.containsKey(f)) {
                        throw new IllegalStateException(String.format(
                                "duplicate field %s with column %s.", f, c));
                    }
                    if (register.checkStrict()) {
                        checkField(f);
                        checkColumn(c);
                    }
                    fields.add(field);
                    fcs.put(f, c);
                }
            }
            for (java.lang.reflect.Field field : clazz.getFields()) {
                if (isRegistColumn(field, register.getTypes())) {
                    String f = field.getName();
                    String c;
                    switch (register.fieldToSnakeColumn()) {
                        case NONE:
                            c = f;
                            break;
                        case SNAKE:
                            c = toSnakeCase(f);
                            break;
                        case SNAKE_NUMBER:
                            c = toSnakeCase(f, true);
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                    if (fcs.containsKey(f)) {
                        throw new IllegalStateException(String.format(
                                "duplicate field %s with column %s.", f, c));
                    }
                    if (register.checkStrict()) {
                        checkField(f);
                        checkColumn(c);
                    }
                    fields.add(field);
                    fcs.put(f, c);
                }
            }
            if ((fieldColumns != null) && (0 < fieldColumns.length)) {
                for (String[] fc : fieldColumns) {
                    if ((fc == null) || (fc.length < 2)) {
                        throw new IllegalStateException(
                                "The fieldColumns has illegal array which null or too short length.");
                    }
                    String f = fc[0];
                    String c = fc[1];
                    if (StringUtils.isEmpty(f)) {
                        throw new IllegalStateException("The fieldColumns has empty field value.");
                    }
                    if (StringUtils.isEmpty(c)) {
                        throw new IllegalStateException("The fieldColumns has empty column value.");
                    }
                    if (fcs.containsKey(f)) {
                        throw new IllegalStateException(String.format(
                                "duplicate field %s with column %s.", f, c));
                    }
                    if (register.checkStrict()) {
                        checkField(f);
                        checkColumn(c);
                    }
                    fcs.put(f, c);
                }
            }
        }

        putTableMapper(register, clazz, fields, table, false, fcs);
        if ((aliases != null) && (0 < aliases.length)) {
            for (String alias : aliases) {
                putTableMapper(register, clazz, fields, alias, true, fcs);
            }
        }
    }

    private static void putTableMapper(Register register, Class<?> clazz,
            LinkedHashSet<Field> fields, String table, boolean alias,
            LinkedHashMap<String, String> fcs) {
        if (StringUtils.isEmpty(table)) {
            throw new IllegalStateException("The tables has empty table value.");
        }
        if (register.checkStrict()) {
            checkTable(table);
        }
        if (register.getTableMapper(table) != null) {
            throw new IllegalStateException(String.format("already registed for %s", table));
        }
        register.putTableMapper(table, new TableMapper(clazz, fields, alias, table, fcs));
    }

    private static void checkProperty(String property) {
        Args.notEmpty(property, "property");

        if (!property.matches("^[A-Za-z_]+[0-9A-Za-z_.]*$")) {
            throw new IllegalStateException(String.format("The property illegal string %s",
                    property));
        }
    }

    private static void checkField(String field) {
        Args.notEmpty(field, "field");

        if (!field.matches("^[A-Za-z_]+[0-9A-Za-z_.]*$")) {
            throw new IllegalStateException(String.format("The field illegal string %s", field));
        }
    }

    private static void checkTable(String table) {
        Args.notEmpty(table, "table");

        if (!table.matches("^[A-Za-z_]+[0-9A-Za-z_]*$")) {
            throw new IllegalStateException(String.format("The table illegal string %s", table));
        }
    }

    private static void checkColumn(String column) {
        Args.notEmpty(column, "column");

        if (!column.matches("^[A-Za-z_]+[0-9A-Za-z_]*$")) {
            throw new IllegalStateException(String.format("The column illegal string %s", column));
        }
    }

    private static boolean isColumn(java.lang.reflect.Field field) {
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
        if (field.getAnnotation(Ignore.class) != null) {
            return false;
        }
        return true;
    }

    private static boolean isRegistColumn(java.lang.reflect.Field field, Set<Class<?>> types) {
        if (!isColumn(field)) {
            return false;
        }
        Class<?> type = field.getType();
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

    public static boolean verify(TableMapper tableMapper, Map<String, String> columnTypes,
            TypeComparator typeComparator) {
        if (tableMapper.isAlias()) {
            throw new IllegalStateException("TableMapper must not be Alias.");
        }
        Map<String, String> fieldColumns = tableMapper.getFieldColumns();
        for (Field field : tableMapper.getFields()) {
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            String columnName = fieldColumns.get(fieldName);
            if (columnName == null) {
                throw new IllegalStateException(String.format(
                        "not found column for field name %s in class %s.", fieldName,
                        fieldType.getName()));
            }
            String columnType = columnTypes.get(columnName);
            if (columnType == null) {
                throw new IllegalStateException(String.format(
                        "not found column type for column name %s, field name %s in class %s.",
                        columnName, fieldName, fieldType.getName()));
            }
            if ((typeComparator != null) && !typeComparator.compare(fieldType, columnType)) {
                throw new IllegalStateException(String.format(
                        "illegal column type %s for column name %s, field name %s in class %s.",
                        columnType, columnName, fieldName, fieldType.getName()));
            }
        }
        return true;
    }
}
