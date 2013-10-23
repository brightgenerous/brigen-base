package com.brightgenerous.mail;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public class MailInfo implements Serializable {

    private static final long serialVersionUID = 1112285105392290465L;

    private Set<String> tos;

    private Set<String> ccs;

    private Set<String> bccs;

    private Set<String> replyTos;

    private Date sentDate;

    private String fromAddress;

    private String fromPersonal;

    private String senderAddress;

    private String senderPersonal;

    private String subjectText;

    private String subjectEncode;

    private String bodyText;

    private String bodyEncode;

    public Set<String> getTos() {
        return tos;
    }

    public void setTos(Set<String> tos) {
        this.tos = tos;
    }

    public Set<String> getCcs() {
        return ccs;
    }

    public void setCcs(Set<String> ccs) {
        this.ccs = ccs;
    }

    public Set<String> getBccs() {
        return bccs;
    }

    public void setBccs(Set<String> bccs) {
        this.bccs = bccs;
    }

    public Set<String> getReplyTos() {
        return replyTos;
    }

    public void setReplyTos(Set<String> replyTos) {
        this.replyTos = replyTos;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromPersonal() {
        return fromPersonal;
    }

    public void setFromPersonal(String fromPersonal) {
        this.fromPersonal = fromPersonal;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderPersonal() {
        return senderPersonal;
    }

    public void setSenderPersonal(String senderPersonal) {
        this.senderPersonal = senderPersonal;
    }

    public String getSubjectText() {
        return subjectText;
    }

    public void setSubjectText(String subjectText) {
        this.subjectText = subjectText;
    }

    public String getSubjectEncode() {
        return subjectEncode;
    }

    public void setSubjectEncode(String subjectEncode) {
        this.subjectEncode = subjectEncode;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getBodyEncode() {
        return bodyEncode;
    }

    public void setBodyEncode(String bodyEncode) {
        this.bodyEncode = bodyEncode;
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
