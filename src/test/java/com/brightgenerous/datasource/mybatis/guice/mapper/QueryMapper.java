package com.brightgenerous.datasource.mybatis.guice.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface QueryMapper {

    @Select("${sql}")
    List<? extends Map<String, ?>> select(@Param("sql") String sql,
            @Param("params") Map<String, ?> params);

    @Insert("${sql}")
    int insert(@Param("sql") String sql, @Param("params") Map<String, ?> params);

    @Update("${sql}")
    int update(@Param("sql") String sql, @Param("params") Map<String, ?> params);

    @Delete("${sql}")
    int delete(@Param("sql") String sql, @Param("params") Map<String, ?> params);
}
