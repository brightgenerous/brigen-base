package com.brightgenerous.mail;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;
import com.brightgenerous.mail.delegate.MailUtility;

@SuppressWarnings("deprecation")
public class MailUtils implements Serializable {

    private static final long serialVersionUID = 7750160617839472845L;

    public static boolean useful() {
        return MailUtility.USEFUL;
    }

    static class InstanceKey implements Serializable {

        private static final long serialVersionUID = -5571606798438371038L;

        private final String smtpHost;

        private final Integer smtpPort;

        private final Long timeout;

        private final String user;

        private final String host;

        public InstanceKey(String smtpHost, Integer smtpPort, Long timeout, String user, String host) {
            this.smtpHost = smtpHost;
            this.smtpPort = smtpPort;
            this.timeout = timeout;
            this.user = user;
            this.host = host;
        }

        @Override
        public int hashCode() {
            final int multiplier = 37;
            int result = 17;
            result = (multiplier * result) + hashCodeEscapeNull(smtpHost);
            result = (multiplier * result) + hashCodeEscapeNull(smtpPort);
            result = (multiplier * result) + hashCodeEscapeNull(timeout);
            result = (multiplier * result) + hashCodeEscapeNull(user);
            result = (multiplier * result) + hashCodeEscapeNull(host);
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

            if (!equalsEscapeNull(smtpHost, other.smtpHost)) {
                return false;
            }
            if (!equalsEscapeNull(smtpPort, other.smtpPort)) {
                return false;
            }
            if (!equalsEscapeNull(timeout, other.timeout)) {
                return false;
            }
            if (!equalsEscapeNull(user, other.user)) {
                return false;
            }
            if (!equalsEscapeNull(host, other.host)) {
                return false;
            }
            return true;
        }
    }

    private final Properties prop;

    protected MailUtils(String smtpHost, Integer smtpPort, Long timeout, String user, String host) {
        prop = new Properties();
        if (smtpHost != null) {
            prop.setProperty("mail.smtp.host", smtpHost);
            prop.setProperty("mail.host", smtpHost);
        }
        if (smtpPort != null) {
            prop.setProperty("mail.smtp.port", String.format("%d", smtpPort));
        }
        if (timeout != null) {
            prop.setProperty("mail.smtp.connectiontimeout", String.format("%d", timeout));
            prop.setProperty("mail.smtp.timeout", String.format("%d", timeout));
        }
        if (user != null) {
            prop.setProperty("mail.user", user);
        }
        if (host != null) {
            prop.setProperty("mail.host", host);
        }
    }

    public static MailUtils get(String smtpHost, Integer smtpPort, Long timeout, String user,
            String host) {
        return getInstance(smtpHost, smtpPort, timeout, user, host);
    }

    private static volatile Map<InstanceKey, SoftReference<MailUtils>> cache;

    private static MailUtils getInstance(String smtpHost, Integer smtpPort, Long timeout,
            String user, String host) {
        if (cache == null) {
            synchronized (MailUtils.class) {
                if (cache == null) {
                    cache = new ConcurrentHashMap<>();
                }
            }
        }

        InstanceKey ik = new InstanceKey(smtpHost, smtpPort, timeout, user, host);
        SoftReference<MailUtils> sr = cache.get(ik);
        MailUtils ret;
        if (sr != null) {
            ret = sr.get();
            if (ret != null) {
                return ret;
            }
            Set<InstanceKey> dels = new HashSet<>();
            for (Entry<InstanceKey, SoftReference<MailUtils>> entry : cache.entrySet()) {
                if (entry.getValue().get() == null) {
                    dels.add(entry.getKey());
                }
            }
            for (InstanceKey del : dels) {
                cache.remove(del);
            }
        }
        ret = new MailUtils(smtpHost, smtpPort, timeout, user, host);
        cache.put(ik, new SoftReference<>(ret));
        return ret;
    }

    public void send(MailInfo info) throws MailException {
        send(prop, info);
    }

    public static void send(Properties prop, MailInfo info) throws MailException {
        MailUtility.send(prop, info);
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
