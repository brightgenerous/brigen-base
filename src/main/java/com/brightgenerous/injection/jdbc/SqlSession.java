package com.brightgenerous.injection.jdbc;

import java.io.Closeable;
import java.sql.Connection;

import com.brightgenerous.injection.jdbc.Transactional.Isolation;

public interface SqlSession extends Closeable {

    boolean isStarted();

    void start(Isolation isolation);

    Connection getConnection();

    void commit();

    void rollback();

    @Override
    void close();
}
