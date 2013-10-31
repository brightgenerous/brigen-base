package com.brightgenerous.injection.jdbc;

import java.sql.Connection;

import com.brightgenerous.injection.jdbc.Transactional.Isolation;

public interface SqlSessionManager {

    boolean isManagedSessionStarted();

    void startManagedSession(Isolation isolation);

    Connection getConnection();

    void commit(boolean force);

    void rollback(boolean force);

    void close();
}
