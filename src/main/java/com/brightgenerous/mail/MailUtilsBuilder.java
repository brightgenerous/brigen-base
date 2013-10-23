package com.brightgenerous.mail;

import java.io.Serializable;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public class MailUtilsBuilder implements Serializable {

    private static final long serialVersionUID = -5852509434550530760L;

    private String smtpHost;

    private Integer smtpPort;

    private Long timeout;

    private String user;

    private String host;

    protected MailUtilsBuilder() {
    }

    public static MailUtilsBuilder create() {
        return new MailUtilsBuilder();
    }

    public MailUtilsBuilder clear() {
        smtpHost = null;
        smtpPort = null;
        timeout = null;
        user = null;
        host = null;
        return this;
    }

    public MailUtils build() {
        return MailUtils.get(smtpHost, smtpPort, timeout, user, host);
    }

    public String smtpHost() {
        return smtpHost;
    }

    public MailUtilsBuilder smtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
        return this;
    }

    public Integer smtpPort() {
        return smtpPort;
    }

    public MailUtilsBuilder smtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
        return this;
    }

    public Long timeout() {
        return timeout;
    }

    public MailUtilsBuilder timeout(Long timeout) {
        this.timeout = timeout;
        return this;
    }

    public String user() {
        return user;
    }

    public MailUtilsBuilder user(String user) {
        this.user = user;
        return this;
    }

    public String host() {
        return host;
    }

    public MailUtilsBuilder host(String host) {
        this.host = host;
        return this;
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
