package com.brightgenerous.datasource.mybatis.guice.bean;

import com.brightgenerous.bean.AbstractBean;

public class MultiKeyDetail extends AbstractBean {

    @Primary
    private Header header;

    @Primary
    private Long detailNo;

    private String detailValue;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Long getDetailNo() {
        return detailNo;
    }

    public void setDetailNo(Long detailNo) {
        this.detailNo = detailNo;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }
}
