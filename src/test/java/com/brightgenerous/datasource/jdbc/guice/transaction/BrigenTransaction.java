package com.brightgenerous.datasource.jdbc.guice.transaction;

import javax.inject.Inject;

import com.brightgenerous.datasource.jdbc.guice.mapper.HeaderMapper;

public class BrigenTransaction {

    @Inject
    private HeaderMapper headerMapper;

    public boolean test() {
        return !headerMapper.isClosed();
    }

    public boolean edit() {
        return !headerMapper.isClosed();
    }
}
