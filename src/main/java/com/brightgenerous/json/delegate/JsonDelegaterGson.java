package com.brightgenerous.json.delegate;

import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.brightgenerous.json.JsonException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.$Gson$Types;

class JsonDelegaterGson implements JsonDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(Gson.class.getName());
            Class.forName(GsonBuilder.class.getName());
            Class.forName(JsonSyntaxException.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Type getType(Object obj) {
        Type superclass = obj.getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    @Override
    public <T> T parseJson(String json, Class<T> clazz, boolean serializeNulls)
            throws JsonException {
        return parseJson(json, clazz, null, serializeNulls);
    }

    @Override
    public <T> T parseJson(String json, Class<T> clazz, String dateFormat, boolean serializeNulls)
            throws JsonException {
        try {
            return create(dateFormat, serializeNulls).fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> T parseJson(String json, Type type, boolean serializeNulls) throws JsonException {
        return parseJson(json, type, null, serializeNulls);
    }

    @Override
    public <T> T parseJson(String json, Type type, String dateFormat, boolean serializeNulls)
            throws JsonException {
        try {
            return create(dateFormat, serializeNulls).fromJson(json, type);
        } catch (JsonSyntaxException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> T parseJson(Reader json, Class<T> clazz, boolean serializeNulls)
            throws JsonException {
        return parseJson(json, clazz, null, serializeNulls);
    }

    @Override
    public <T> T parseJson(Reader json, Class<T> clazz, String dateFormat, boolean serializeNulls)
            throws JsonException {
        try {
            return create(dateFormat, serializeNulls).fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> T parseJson(Reader json, Type type, boolean serializeNulls) throws JsonException {
        return parseJson(json, type, null, serializeNulls);
    }

    @Override
    public <T> T parseJson(Reader json, Type type, String dateFormat, boolean serializeNulls)
            throws JsonException {
        try {
            return create(dateFormat, serializeNulls).fromJson(json, type);
        } catch (JsonSyntaxException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public String formatJson(Object obj, boolean serializeNulls) {
        return formatJson(obj, (String) null, serializeNulls);
    }

    @Override
    public String formatJson(Object obj, String dateFormat, boolean serializeNulls) {
        return create(dateFormat, serializeNulls).toJson(obj);
    }

    @Override
    public String formatJson(Object obj, Type type, boolean serializeNulls) {
        return formatJson(obj, type, null, serializeNulls);
    }

    @Override
    public String formatJson(Object obj, Type type, String dateFormat, boolean serializeNulls) {
        return create(dateFormat, serializeNulls).toJson(obj, type);
    }

    protected Gson create(String dateFormat, boolean serializeNulls) {
        GsonBuilder builder = new GsonBuilder();
        if (dateFormat != null) {
            builder.setDateFormat(dateFormat);
        }
        if (serializeNulls) {
            builder.serializeNulls();
        }
        return builder.create();
    }
}
