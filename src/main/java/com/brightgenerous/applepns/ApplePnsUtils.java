package com.brightgenerous.applepns;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ResourceUtils;
import com.brightgenerous.commons.StringUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.commons.crypto.CryptoUtils;
import com.brightgenerous.lang.Args;

public class ApplePnsUtils implements Serializable {

    private static final long serialVersionUID = 2324865016612748204L;

    static class InstanceKey implements Serializable {

        private static final long serialVersionUID = -5571606798438371038L;

        private final String cert;

        private final String key;

        private final String server;

        private final Integer port;

        public InstanceKey(String cert, String key, String server, Integer port) {
            this.cert = cert;
            this.key = key;
            this.server = server;
            this.port = port;
        }

        @Override
        public int hashCode() {
            final int multiplier = 37;
            int result = 17;
            result = (multiplier * result) + hashCodeEscapeNull(cert);
            result = (multiplier * result) + hashCodeEscapeNull(key);
            result = (multiplier * result) + hashCodeEscapeNull(server);
            result = (multiplier * result) + hashCodeEscapeNull(port);
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

            if (!equalsEscapeNull(cert, other.cert)) {
                return false;
            }
            if (!equalsEscapeNull(key, other.key)) {
                return false;
            }
            if (!equalsEscapeNull(server, other.server)) {
                return false;
            }
            if (!equalsEscapeNull(port, other.port)) {
                return false;
            }
            return true;
        }
    }

    // instead of these constant, use script function with default argument value.
    private static final String SERVER_DEFAULT = "gateway.push.apple.com";

    private static final String SERVER_DEVELOP = "gateway.sandbox.push.apple.com";

    private static final Integer PORT_DEFAULT = Integer.valueOf(2195);

    private static final String SCRIPT_FILE = "apple_pns.rb";

    private static final String PNS_FUNCTION = "apple_pns";

    private static final String PNS_FUNCTION_INIT = "apple_pns_init";

    private static volatile Invocable invocable;

    private String cert;

    private String key;

    private String server;

    private Integer port;

    protected ApplePnsUtils(String cert, String key, String server, Integer port) {
        Args.notNull(cert, "cert");
        Args.notNull(key, "key");

        this.cert = cert;
        this.key = key;
        this.server = server;
        this.port = port;
    }

    public static ApplePnsUtils get(String cert, String key) {
        return get(cert, key, (String) null);
    }

    public static ApplePnsUtils getDevelop(String cert, String key) {
        return get(cert, key, SERVER_DEVELOP);
    }

    public static ApplePnsUtils get(String cert, String key, String server) {
        return get(cert, key, server, null);
    }

    public static ApplePnsUtils get(String cert, String key, Integer port) {
        return get(cert, key, null, port);
    }

    public static ApplePnsUtils get(String cert, String key, String server, Integer port) {
        return getInstance(cert, key, server, port);
    }

    private static volatile Map<InstanceKey, SoftReference<ApplePnsUtils>> cache;

    protected static ApplePnsUtils getInstance(String cert, String key, String server, Integer port) {
        if (cache == null) {
            synchronized (CryptoUtils.class) {
                if (cache == null) {
                    cache = new ConcurrentHashMap<>();
                }
            }
        }

        if ((server != null) && server.equals(SERVER_DEFAULT)) {
            server = null;
        }
        if ((port != null) && port.equals(PORT_DEFAULT)) {
            port = null;
        }

        InstanceKey ik = new InstanceKey(cert, key, server, port);
        SoftReference<ApplePnsUtils> sr = cache.get(ik);
        ApplePnsUtils ret;
        if (sr != null) {
            ret = sr.get();
            if (ret != null) {
                return ret;
            }
            Set<InstanceKey> dels = new HashSet<>();
            for (Entry<InstanceKey, SoftReference<ApplePnsUtils>> entry : cache.entrySet()) {
                if (entry.getValue().get() == null) {
                    dels.add(entry.getKey());
                }
            }
            for (InstanceKey del : dels) {
                cache.remove(del);
            }
        }
        ret = new ApplePnsUtils(cert, key, server, port);
        cache.put(ik, new SoftReference<>(ret));
        return ret;
    }

    private static Invocable getInvocable() throws ApplePnsException {
        if (invocable == null) {
            synchronized (ApplePnsUtils.class) {
                if (invocable == null) {
                    ScriptEngineManager manager = new ScriptEngineManager();
                    ScriptEngine engine = manager.getEngineByName("jruby");
                    String script;
                    try {
                        script = ResourceUtils.get(ApplePnsUtils.class, SCRIPT_FILE).readAsString();
                    } catch (IOException e) {
                        throw new ApplePnsException(e);
                    }
                    try {
                        engine.eval(script);
                    } catch (ScriptException e) {
                        throw new ApplePnsException(e);
                    }
                    invocable = (Invocable) engine;
                }
            }
        }
        return invocable;
    }

    /*
     * not necessary
     */
    public static void init() throws ApplePnsException {
        try {
            Invocable invocable = getInvocable();
            if (StringUtils.isNotEmpty(PNS_FUNCTION_INIT)) {
                invocable.invokeFunction(PNS_FUNCTION_INIT);
            }
        } catch (ScriptException | NoSuchMethodException e) {
            throw new ApplePnsException(e);
        }
    }

    public Object notify(Set<String> devices, String body) throws ApplePnsException {
        Args.notEmpty(devices, "devices");
        Args.notNull(body, "body");

        Object result = null;
        try {
            if (server == null) {
                result = getInvocable().invokeFunction(PNS_FUNCTION, devices, body, cert, key);
            } else if (port == null) {
                result = getInvocable().invokeFunction(PNS_FUNCTION, devices, body, cert, key,
                        server);
            } else {
                result = getInvocable().invokeFunction(PNS_FUNCTION, devices, body, cert, key,
                        server, port);
            }
        } catch (ScriptException | NoSuchMethodException e) {
            throw new ApplePnsException(e);
        }
        return result;
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.useful()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.useful()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (ToStringUtils.useful()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }
}
