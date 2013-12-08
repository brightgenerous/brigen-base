package com.brightgenerous.json.delegate;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import com.brightgenerous.json.JsonException;

class JsonDelegaterJsonic implements JsonDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(JSON.class.getName());
            Class.forName(JSONException.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Type getType(Object obj) {
        Type type = obj.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] args = ((ParameterizedType) type).getActualTypeArguments();
            if ((args != null) && (args.length == 1)) {
                return args[0];
            }
        }
        throw new IllegalStateException("Reference must be specified actual type.");
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
            return create(dateFormat, serializeNulls).parse(json, clazz);
        } catch (JSONException e) {
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
            return create(dateFormat, serializeNulls).parse(json, type);
        } catch (JSONException e) {
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
            return create(dateFormat, serializeNulls).parse(json, clazz);
        } catch (JSONException | IOException e) {
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
            return create(dateFormat, serializeNulls).parse(json, type);
        } catch (JSONException | IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public String formatJson(Object obj, boolean serializeNulls) {
        return formatJson(obj, (String) null, serializeNulls);
    }

    @Override
    public String formatJson(Object obj, String dateFormat, boolean serializeNulls) {
        return create(dateFormat, serializeNulls).format(obj);
    }

    @Override
    public String formatJson(Object obj, Type type, boolean serializeNulls) {
        return formatJson(obj, type, null, serializeNulls);
    }

    @Override
    public String formatJson(Object obj, Type type, String dateFormat, boolean serializeNulls) {
        return create(dateFormat, serializeNulls).format(obj);
    }

    protected JSON create(String dateFormat, boolean serializeNulls) {
        JSON ret = new JSON();
        if (dateFormat != null) {
            ret.setDateFormat(dateFormat);
        }
        ret.setSuppressNull(!serializeNulls);
        return ret;
    }
}
