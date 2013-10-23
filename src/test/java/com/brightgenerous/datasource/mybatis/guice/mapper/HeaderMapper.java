package com.brightgenerous.datasource.mybatis.guice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.brightgenerous.datasource.mybatis.guice.bean.Header;
import com.brightgenerous.orm.Condition;

public interface HeaderMapper {

    long selectSize(@Param("condition") Condition condition);

    List<Header> selectAll(@Param("condition") Condition condition);

    int insert(Header bean);

    int update(Header bean);

    int delete(Header bean);
}
