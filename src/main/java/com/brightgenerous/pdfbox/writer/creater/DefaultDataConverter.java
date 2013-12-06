package com.brightgenerous.pdfbox.writer.creater;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.brightgenerous.pdfbox.writer.IDataConverter;

public class DefaultDataConverter<T> implements IDataConverter<T>, Serializable {

    private static final long serialVersionUID = 4455144877790443011L;

    private final DateFormat dateFormat;

    private final NumberFormat numberFormat;

    public DefaultDataConverter() {
        this((NumberFormat) null, (DateFormat) null);
    }

    public DefaultDataConverter(NumberFormat numberFormat) {
        this(numberFormat, null);
    }

    public DefaultDataConverter(DateFormat dateFormat) {
        this(null, dateFormat);
    }

    public DefaultDataConverter(NumberFormat numberFormat, DateFormat dateFormat) {
        this.numberFormat = numberFormat;
        this.dateFormat = dateFormat;
    }

    public DefaultDataConverter(String numberFormat, String dateFormat) {
        this((numberFormat == null) ? null : new DecimalFormat(numberFormat),
                (dateFormat == null) ? null : new SimpleDateFormat(dateFormat));
    }

    @Override
    public Map<String, String> convert(T data) {
        Map<String, String> ret = new HashMap<>();
        if (data == null) {
            return ret;
        }
        if (data instanceof Map) {
            for (java.util.Map.Entry<?, ?> e : ((Map<?, ?>) data).entrySet()) {
                String key;
                {
                    Object k = e.getKey();
                    if (k instanceof String) {
                        key = (String) k;
                    } else {
                        key = String.valueOf(k);
                    }
                }
                Object obj = e.getValue();
                String str;
                if (obj == null) {
                    str = null;
                } else if (obj instanceof String) {
                    str = (String) obj;
                } else if ((numberFormat != null) && (obj instanceof Number)) {
                    str = numberFormat.format(obj);
                } else if ((dateFormat != null) && (obj instanceof Date)) {
                    str = dateFormat.format((Date) obj);
                } else {
                    str = String.valueOf(obj);
                }
                ret.put(key, str);
            }
        } else {
            Class<?> clazz = data.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                Object obj;
                try {
                    if (!Modifier.isPublic(field.getModifiers()) && !field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    obj = field.get(data);
                } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
                    throw new RuntimeException(e);
                }
                String str;
                if (obj == null) {
                    str = null;
                } else if (obj instanceof String) {
                    str = (String) obj;
                } else if ((numberFormat != null) && (obj instanceof Number)) {
                    str = numberFormat.format(obj);
                } else if ((dateFormat != null) && (obj instanceof Date)) {
                    str = dateFormat.format((Date) obj);
                } else {
                    str = String.valueOf(obj);
                }
                ret.put(field.getName(), str);
            }
        }
        return ret;
    }
}
