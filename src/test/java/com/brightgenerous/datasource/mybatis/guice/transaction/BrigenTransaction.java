package com.brightgenerous.datasource.mybatis.guice.transaction;

import java.util.List;

import javax.inject.Inject;

import com.brightgenerous.commons.ObjectUtils;
import com.brightgenerous.datasource.mybatis.guice.bean.Header;
import com.brightgenerous.datasource.mybatis.guice.bean.MultiKeyDetail;
import com.brightgenerous.datasource.mybatis.guice.bean.SimpleKeyDetail;
import com.brightgenerous.datasource.mybatis.guice.mapper.HeaderMapper;
import com.brightgenerous.datasource.mybatis.guice.mapper.Key;
import com.brightgenerous.datasource.mybatis.guice.mapper.MultiKeyDetailMapper;
import com.brightgenerous.datasource.mybatis.guice.mapper.SimpleKeyDetailMapper;
import com.brightgenerous.orm.Condition;

public class BrigenTransaction {

    @Inject
    private HeaderMapper headerMapper;

    @Inject
    private MultiKeyDetailMapper multiKeyDetailMapper;

    @Inject
    private SimpleKeyDetailMapper simpleKeyDetailMapper;

    public boolean existsMapper() {
        return (headerMapper != null) && (multiKeyDetailMapper != null)
                && (simpleKeyDetailMapper != null);
    }

    public Header getHeader(Header key) {
        return getHeader(key, false);
    }

    public Header getHeaderDetails(Header key) {
        return getHeader(key, true);
    }

    private Header getHeader(Header key, boolean details) {
        Condition condition = new Condition();
        condition.getFields().get(Key.HEADER_NO).setValue(key.getHeaderNo());
        Header ret = ObjectUtils.getSingleOrException(headerMapper.selectAll(condition));
        if ((ret != null) && details) {
            condition.getSorts().prependAsc(Key.DETAIL_NO);
            ret.setMultiKeyDetails(multiKeyDetailMapper.selectAll(condition));
            ret.setSimpleKeyDetails(simpleKeyDetailMapper.selectAll(condition));
        }
        return ret;
    }

    public List<Header> getHeaders(Condition condition) {
        return headerMapper.selectAll(condition);
    }

    public void add(Header header) {
        headerMapper.insert(header);
        if (header.getMultiKeyDetails() != null) {
            for (MultiKeyDetail mkd : header.getMultiKeyDetails()) {
                multiKeyDetailMapper.insert(mkd);
            }
        }
        if (header.getSimpleKeyDetails() != null) {
            for (SimpleKeyDetail skd : header.getSimpleKeyDetails()) {
                simpleKeyDetailMapper.insert(skd);
            }
        }
    }

    public List<MultiKeyDetail> getMultiKeyDetails(Condition condition) {
        return multiKeyDetailMapper.selectAll(condition);
    }

    public List<SimpleKeyDetail> getSimpleKeyDetails(Condition condition) {
        return simpleKeyDetailMapper.selectAll(condition);
    }

    public void editHeaderDetails(Header header) {
        editHeaderDetails(header, null);
    }

    public void editHeaderDetails(Header header, RuntimeException rex) {
        {
            // lock record by using for update
            Condition condition = new Condition();
            condition.getFields().get(Key.HEADER_NO).setValue(header.getHeaderNo());
            condition.setForUpdate(true);
            headerMapper.selectAll(condition);
        }
        multiKeyDetailMapper.deleteByHeader(header);
        simpleKeyDetailMapper.deleteByHeader(header);
        headerMapper.update(header);
        for (MultiKeyDetail mkd : header.getMultiKeyDetails()) {
            multiKeyDetailMapper.insert(mkd);
        }
        for (SimpleKeyDetail skd : header.getSimpleKeyDetails()) {
            simpleKeyDetailMapper.insert(skd);
        }
        if (rex != null) {
            throw rex;
        }
    }
}
