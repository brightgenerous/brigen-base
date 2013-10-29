package com.brightgenerous.datasource.mybatis.guice.bean;

import com.brightgenerous.bean.AbstractBean;
import com.brightgenerous.orm.Ignore;
import com.brightgenerous.orm.Ignore.Type;

public class SimpleKeyDetail extends AbstractBean {

    private static final long serialVersionUID = 1L;

    @Primary
    @Ignore(Type.INSERT)
    private Long detailNo;

    private Header header;

    private String detailValue;

    public Long getDetailNo() {
        return detailNo;
    }

    public void setDetailNo(Long detailNo) {
        this.detailNo = detailNo;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }
}
