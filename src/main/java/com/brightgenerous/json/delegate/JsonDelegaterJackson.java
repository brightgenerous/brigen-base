package com.brightgenerous.json.delegate;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.brightgenerous.json.JsonParseException;

class JsonDelegaterJackson implements JsonDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(ObjectMapper.class.getName());
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
            throws JsonParseException {
        return parseJson(json, clazz, null, serializeNulls);
    }

    @Override
    public <T> T parseJson(String json, Class<T> clazz, String dateFormat, boolean serializeNulls)
            throws JsonParseException {
        try {
            return create(dateFormat, serializeNulls).readValue(json, clazz);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public <T> T parseJson(String json, Type type, boolean serializeNulls)
            throws JsonParseException {
        return parseJson(json, type, null, serializeNulls);
    }

    @Override
    public <T> T parseJson(String json, Type type, String dateFormat, boolean serializeNulls)
            throws JsonParseException {
        try {
            ObjectMapper mapper = create(dateFormat, serializeNulls);
            return mapper.readValue(json, mapper.constructType(type));
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public <T> T parseJson(Reader json, Class<T> clazz, boolean serializeNulls)
            throws JsonParseException {
        return parseJson(json, clazz, null, serializeNulls);
    }

    @Override
    public <T> T parseJson(Reader json, Class<T> clazz, String dateFormat, boolean serializeNulls)
            throws JsonParseException {
        try {
            return create(dateFormat, serializeNulls).readValue(json, clazz);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public <T> T parseJson(Reader json, Type type, boolean serializeNulls)
            throws JsonParseException {
        return parseJson(json, type, null, serializeNulls);
    }

    @Override
    public <T> T parseJson(Reader json, Type type, String dateFormat, boolean serializeNulls)
            throws JsonParseException {
        try {
            ObjectMapper mapper = create(dateFormat, serializeNulls);
            return mapper.readValue(json, mapper.constructType(type));
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public String formatJson(Object obj, boolean serializeNulls) {
        return formatJson(obj, (String) null, serializeNulls);
    }

    @Override
    public String formatJson(Object obj, String dateFormat, boolean serializeNulls) {
        try {
            return create(dateFormat, serializeNulls).writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String formatJson(Object obj, Type type, boolean serializeNulls) {
        return formatJson(obj, type, null, serializeNulls);
    }

    @Override
    public String formatJson(Object obj, Type type, String dateFormat, boolean serializeNulls) {
        try {
            return create(dateFormat, serializeNulls).writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected ObjectMapper create(String dateFormat, boolean serializeNulls) {
        ObjectMapper ret = new ObjectMapper();
        if (dateFormat != null) {
            ret.setDateFormat(new SimpleDateFormat(dateFormat));
        }
        if (serializeNulls) {
            ret.setSerializationInclusion(Inclusion.ALWAYS);
        } else {
            ret.setSerializationInclusion(Inclusion.NON_NULL);
        }
        return ret;
    }
}
