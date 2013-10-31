package com.brightgenerous.datasource.jdbc.guice.mapper.derby;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;

import com.brightgenerous.injection.jdbc.SqlSession;

public class HeaderMapper implements com.brightgenerous.datasource.jdbc.guice.mapper.HeaderMapper {

    @Inject
    private SqlSession session;

    @Override
    public boolean isClosed() {
        Connection conn = session.getConnection();
        try {
            return conn.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
