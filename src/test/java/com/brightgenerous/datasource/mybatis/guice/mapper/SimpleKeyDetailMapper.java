package com.brightgenerous.datasource.mybatis.guice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.brightgenerous.datasource.mybatis.guice.bean.Header;
import com.brightgenerous.datasource.mybatis.guice.bean.SimpleKeyDetail;
import com.brightgenerous.orm.Condition;

public interface SimpleKeyDetailMapper {

    long selectSize(@Param("condition") Condition condition);

    List<SimpleKeyDetail> selectAll(@Param("condition") Condition condition);

    int insert(@Param("bean") SimpleKeyDetail bean);

    int update(@Param("bean") SimpleKeyDetail bean);

    int delete(@Param("bean") SimpleKeyDetail bean);

    int deleteByHeader(Header header);
}
