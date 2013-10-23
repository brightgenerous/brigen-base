package com.brightgenerous.datasource.mybatis.guice.bean;

import java.util.List;

import com.brightgenerous.bean.AbstractBean;

public class Header extends AbstractBean {

    private static final long serialVersionUID = 1L;

    @Primary
    private Long headerNo;

    private String headerValue;

    @Fill
    private List<MultiKeyDetail> multiKeyDetails;

    @Fill
    private List<SimpleKeyDetail> simpleKeyDetails;

    public Long getHeaderNo() {
        return headerNo;
    }

    public void setHeaderNo(Long headerNo) {
        this.headerNo = headerNo;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    public List<MultiKeyDetail> getMultiKeyDetails() {
        return multiKeyDetails;
    }

    public void setMultiKeyDetails(List<MultiKeyDetail> multiKeyDetails) {
        this.multiKeyDetails = multiKeyDetails;
    }

    public List<SimpleKeyDetail> getSimpleKeyDetails() {
        return simpleKeyDetails;
    }

    public void setSimpleKeyDetails(List<SimpleKeyDetail> simpleKeyDetails) {
        this.simpleKeyDetails = simpleKeyDetails;
    }
}
