package com.brightgenerous.datasource.mybatis.guice.mapper;

import com.brightgenerous.orm.TypeKey;

public interface Key {

    TypeKey<Long, String> HEADER_NO = new TypeKey<>("headerNo");

    TypeKey<String, String> HEADER_VALUE = new TypeKey<>("headerValue");

    TypeKey<Long, String> DETAIL_NO = new TypeKey<>("detailNo");

    TypeKey<String, String> DETAIL_VALUE = new TypeKey<>("detailValue");
}
