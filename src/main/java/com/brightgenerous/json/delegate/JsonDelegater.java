package com.brightgenerous.json.delegate;

import java.io.Reader;
import java.lang.reflect.Type;

import com.brightgenerous.json.JsonException;

interface JsonDelegater {

    Type getType(Object obj);

    <T> T parseJson(String json, Class<T> clazz, boolean serializeNulls) throws JsonException;

    <T> T parseJson(String json, Class<T> clazz, String dateFormat, boolean serializeNulls)
            throws JsonException;

    <T> T parseJson(String json, Type type, boolean serializeNulls) throws JsonException;

    <T> T parseJson(String json, Type type, String dateFormat, boolean serializeNulls)
            throws JsonException;

    <T> T parseJson(Reader json, Class<T> clazz, boolean serializeNulls) throws JsonException;

    <T> T parseJson(Reader json, Class<T> clazz, String dateFormat, boolean serializeNulls)
            throws JsonException;

    <T> T parseJson(Reader json, Type type, boolean serializeNulls) throws JsonException;

    <T> T parseJson(Reader json, Type type, String dateFormat, boolean serializeNulls)
            throws JsonException;

    String formatJson(Object obj, boolean serializeNulls);

    String formatJson(Object obj, String dateFormat, boolean serializeNulls);

    String formatJson(Object obj, Type type, boolean serializeNulls);

    String formatJson(Object obj, Type type, String dateFormat, boolean serializeNulls);
}
