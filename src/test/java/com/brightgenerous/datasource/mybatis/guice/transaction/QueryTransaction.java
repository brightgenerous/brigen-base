package com.brightgenerous.datasource.mybatis.guice.transaction;

import java.util.List;

import javax.inject.Inject;

import com.brightgenerous.datasource.mybatis.guice.mapper.QueryMapper;
import com.brightgenerous.orm.Sql;

public class QueryTransaction {

    public interface Editer {

        int editProcess(QueryMapper mapper);
    }

    @Inject
    private QueryMapper queryMapper;

    public int edit(Editer editer) {
        if (editer == null) {
            return 0;
        }
        return editer.editProcess(queryMapper);
    }

    public int edit(String... sqls) {
        if ((sqls == null) || (sqls.length < 1)) {
            return 0;
        }
        int ret = 0;
        for (String sql : sqls) {
            ret += queryMapper.update(sql, null);
        }
        return ret;
    }

    public int edit(List<String> sqls) {
        if ((sqls == null) || sqls.isEmpty()) {
            return 0;
        }
        int ret = 0;
        for (String sql : sqls) {
            ret += queryMapper.update(sql, null);
        }
        return ret;
    }

    public int edit(Sql... sqls) {
        if ((sqls == null) || (sqls.length < 1)) {
            return 0;
        }
        int ret = 0;
        for (Sql sql : sqls) {
            ret += queryMapper.update(sql.getSql(), sql.getParams());
        }
        return ret;
    }
}
