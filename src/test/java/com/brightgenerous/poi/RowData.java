package com.brightgenerous.poi;

import java.io.Serializable;
import java.util.Date;

public class RowData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String value1;

    private Long value2;

    private Date value3;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public Long getValue2() {
        return value2;
    }

    public void setValue2(Long value2) {
        this.value2 = value2;
    }

    public Date getValue3() {
        return value3;
    }

    public void setValue3(Date value3) {
        this.value3 = value3;
    }
}
