package com.brightgenerous.json;

import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;

import com.brightgenerous.json.delegate.JsonUtility;

@SuppressWarnings("deprecation")
public class JsonUtils implements Serializable {

    private static final long serialVersionUID = -8083708706919305178L;

    public static boolean useful() {
        return JsonUtility.USEFUL;
    }

    public static boolean gson() {
        return JsonUtility.GSON;
    }

    public static boolean jsonic() {
        return JsonUtility.JSONIC;
    }

    public static boolean jackson() {
        return JsonUtility.JACKSON;
    }

    private static final boolean DEFAULT_SERIALIZE_NULLS = false;

    private final String dateFormat;

    private final boolean serializeNulls;

    private JsonUtils(String dateFormat, boolean serializeNulls) {
        this.dateFormat = dateFormat;
        this.serializeNulls = serializeNulls;
    }

    public static JsonUtils get() {
        return get(null);
    }

    public static JsonUtils get(String dateFormat) {
        return get(dateFormat, DEFAULT_SERIALIZE_NULLS);
    }

    public static JsonUtils get(boolean serializeNulls) {
        return get(null, serializeNulls);
    }

    public static JsonUtils get(String dateFormat, boolean serializeNulls) {
        return getInstance(dateFormat, serializeNulls);
    }

    protected static JsonUtils getInstance(String dateFormat, boolean serializeNulls) {
        return new JsonUtils(dateFormat, serializeNulls);
    }

    public <T> T parseJson(String json, Class<T> clazz) throws JsonException {
        return parseJson(json, clazz, dateFormat, serializeNulls);
    }

    public <T> T parseJson(String json, Type type) throws JsonException {
        return parseJson(json, type, dateFormat, serializeNulls);
    }

    public <T> T parseJson(Reader json, Class<T> clazz) throws JsonException {
        return parseJson(json, clazz, dateFormat, serializeNulls);
    }

    public <T> T parseJson(Reader json, Type type) throws JsonException {
        return parseJson(json, type, dateFormat, serializeNulls);
    }

    public String formatJson(Object obj) {
        return formatJson(obj, dateFormat, serializeNulls);
    }

    public String formatJson(Object obj, Type type) {
        return formatJson(obj, type, dateFormat, serializeNulls);
    }

    public static <T> T parseJson(String json, Class<T> clazz, String dateFormat)
            throws JsonException {
        return JsonUtility.parseJson(json, clazz, dateFormat, DEFAULT_SERIALIZE_NULLS);
    }

    public static <T> T parseJson(String json, Class<T> clazz, boolean serializeNulls)
            throws JsonException {
        return JsonUtility.parseJson(json, clazz, serializeNulls);
    }

    public static <T> T parseJson(String json, Class<T> clazz, String dateFormat,
            boolean serializeNulls) throws JsonException {
        return JsonUtility.parseJson(json, clazz, dateFormat, serializeNulls);
    }

    public static <T> T parseJson(String json, Type type, String dateFormat) throws JsonException {
        return JsonUtility.parseJson(json, type, dateFormat, DEFAULT_SERIALIZE_NULLS);
    }

    public static <T> T parseJson(String json, Type type, boolean serializeNulls)
            throws JsonException {
        return JsonUtility.parseJson(json, type, serializeNulls);
    }

    public static <T> T parseJson(String json, Type type, String dateFormat, boolean serializeNulls)
            throws JsonException {
        return JsonUtility.parseJson(json, type, dateFormat, serializeNulls);
    }

    public static <T> T parseJson(Reader json, Class<T> clazz, String dateFormat)
            throws JsonException {
        return JsonUtility.parseJson(json, clazz, dateFormat, DEFAULT_SERIALIZE_NULLS);
    }

    public static <T> T parseJson(Reader json, Class<T> clazz, boolean serializeNulls)
            throws JsonException {
        return JsonUtility.parseJson(json, clazz, serializeNulls);
    }

    public static <T> T parseJson(Reader json, Class<T> clazz, String dateFormat,
            boolean serializeNulls) throws JsonException {
        return JsonUtility.parseJson(json, clazz, dateFormat, serializeNulls);
    }

    public static <T> T parseJson(Reader json, Type type, String dateFormat) throws JsonException {
        return JsonUtility.parseJson(json, type, dateFormat, DEFAULT_SERIALIZE_NULLS);
    }

    public static <T> T parseJson(Reader json, Type type, boolean serializeNulls)
            throws JsonException {
        return JsonUtility.parseJson(json, type, serializeNulls);
    }

    public static <T> T parseJson(Reader json, Type type, String dateFormat, boolean serializeNulls)
            throws JsonException {
        return JsonUtility.parseJson(json, type, dateFormat, serializeNulls);
    }

    public static String formatJson(Object obj, String dateFormat) {
        return JsonUtility.formatJson(obj, dateFormat, DEFAULT_SERIALIZE_NULLS);
    }

    public static String formatJson(Object obj, boolean serializeNulls) {
        return JsonUtility.formatJson(obj, serializeNulls);
    }

    public static String formatJson(Object obj, String dateFormat, boolean serializeNulls) {
        return JsonUtility.formatJson(obj, dateFormat, serializeNulls);
    }

    public static String formatJson(Object obj, Type type, String dateFormat) {
        return JsonUtility.formatJson(obj, type, dateFormat, DEFAULT_SERIALIZE_NULLS);
    }

    public static String formatJson(Object obj, Type type, boolean serializeNulls) {
        return JsonUtility.formatJson(obj, type, serializeNulls);
    }

    public static String formatJson(Object obj, Type type, String dateFormat, boolean serializeNulls) {
        return JsonUtility.formatJson(obj, type, dateFormat, serializeNulls);
    }
}
