package com.brightgenerous.json.delegate;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.json.JsonParseException;

@Deprecated
public class JsonUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    public static final boolean GSON;

    public static final boolean JSONIC;

    public static final boolean JACKSON;

    private static final JsonDelegater delegater;

    private static final RuntimeException rex;

    static {
        JsonDelegater tmp = null;
        boolean gson = false;
        boolean jsonic = false;
        boolean jackson = false;
        RuntimeException ex = null;
        try {
            tmp = new JsonDelegaterGson();
            gson = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve gson");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        if (tmp == null) {
            try {
                tmp = new JsonDelegaterJsonic();
                jsonic = true;
            } catch (NoClassDefFoundError | RuntimeException e) {

                if (log.isLoggable(Level.INFO)) {
                    log.log(Level.INFO, "does not resolve jsonic");
                }

                if (e instanceof RuntimeException) {
                    Throwable th = e.getCause();
                    if ((th == null) || !(th instanceof ClassNotFoundException)) {
                        throw e;
                    }
                }
            }
        }
        if (tmp == null) {
            try {
                tmp = new JsonDelegaterJackson();
                jackson = true;
            } catch (NoClassDefFoundError | RuntimeException e) {

                if (log.isLoggable(Level.INFO)) {
                    log.log(Level.INFO, "does not resolve javax mail");
                }

                if (e instanceof RuntimeException) {
                    Throwable th = e.getCause();
                    if ((th == null) || !(th instanceof ClassNotFoundException)) {
                        throw e;
                    }
                    ex = (RuntimeException) e;
                } else {
                    ex = new RuntimeException(e);
                }
            }
        }
        USEFUL = (gson | jsonic | jackson);
        GSON = gson;
        JSONIC = jsonic;
        JACKSON = jackson;
        delegater = tmp;
        rex = ex;
    }

    private JsonUtility() {
    }

    private static JsonDelegater getDelegater() {
        if (delegater == null) {
            throw rex;
        }
        return delegater;
    }

    public static Type getType(Object obj) {
        return getDelegater().getType(obj);
    }

    public static <T> T parseJson(String json, Class<T> clazz, boolean serializeNulls)
            throws JsonParseException {
        return getDelegater().parseJson(json, clazz, serializeNulls);
    }

    public static <T> T parseJson(String json, Class<T> clazz, String dateFormat,
            boolean serializeNulls) throws JsonParseException {
        return getDelegater().parseJson(json, clazz, dateFormat, serializeNulls);
    }

    public static <T> T parseJson(String json, Type type, boolean serializeNulls)
            throws JsonParseException {
        return getDelegater().parseJson(json, type, serializeNulls);
    }

    public static <T> T parseJson(String json, Type type, String dateFormat, boolean serializeNulls)
            throws JsonParseException {
        return getDelegater().parseJson(json, type, dateFormat, serializeNulls);
    }

    public static <T> T parseJson(Reader json, Class<T> clazz, boolean serializeNulls)
            throws JsonParseException {
        return getDelegater().parseJson(json, clazz, serializeNulls);
    }

    public static <T> T parseJson(Reader json, Class<T> clazz, String dateFormat,
            boolean serializeNulls) throws JsonParseException {
        return getDelegater().parseJson(json, clazz, dateFormat, serializeNulls);
    }

    public static <T> T parseJson(Reader json, Type type, boolean serializeNulls)
            throws JsonParseException {
        return getDelegater().parseJson(json, type, serializeNulls);
    }

    public static <T> T parseJson(Reader json, Type type, String dateFormat, boolean serializeNulls)
            throws JsonParseException {
        return getDelegater().parseJson(json, type, dateFormat, serializeNulls);
    }

    public static String formatJson(Object obj, boolean serializeNulls) {
        return getDelegater().formatJson(obj, serializeNulls);
    }

    public static String formatJson(Object obj, String dateFormat, boolean serializeNulls) {
        return getDelegater().formatJson(obj, dateFormat, serializeNulls);
    }

    public static String formatJson(Object obj, Type type, boolean serializeNulls) {
        return getDelegater().formatJson(obj, type, serializeNulls);
    }

    public static String formatJson(Object obj, Type type, String dateFormat, boolean serializeNulls) {
        return getDelegater().formatJson(obj, type, dateFormat, serializeNulls);
    }
}
