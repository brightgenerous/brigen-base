package com.brightgenerous.datasource.mybatis.guice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.brightgenerous.datasource.mybatis.guice.bean.Header;
import com.brightgenerous.datasource.mybatis.guice.bean.MultiKeyDetail;
import com.brightgenerous.orm.Condition;

public interface MultiKeyDetailMapper {

    long selectSize(@Param("condition") Condition condition);

    List<MultiKeyDetail> selectAll(@Param("condition") Condition condition);

    int insert(MultiKeyDetail bean);

    int update(MultiKeyDetail bean);

    int delete(MultiKeyDetail bean);

    int deleteByHeader(Header header);
}
