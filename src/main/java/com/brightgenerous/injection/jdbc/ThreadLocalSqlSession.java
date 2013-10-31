package com.brightgenerous.injection.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import com.brightgenerous.injection.jdbc.Transactional.Isolation;

public class ThreadLocalSqlSession implements SqlSessionManager, SqlSession {

    private final ThreadLocal<Holder> threadLocal = new ThreadLocal<>();

    @Inject
    private DataSource dataSource;

    // as SqlSessionManager

    @Override
    public boolean isManagedSessionStarted() {
        return isStarted();
    }

    @Override
    public void startManagedSession(Isolation isolation) {
        start(isolation);
    }

    @Override
    public void commit(boolean force) {
        commit();
    }

    @Override
    public void rollback(boolean force) {
        rollback();
    }

    // as SqlSession

    @Override
    public boolean isStarted() {
        Holder holder = threadLocal.get();
        return (holder != null) && holder.started;
    }

    @Override
    public void start(Isolation isolation) {
        Holder holder = threadLocal.get();
        if (holder != null) {
            if (holder.connection != null) {
                try {
                    if (!holder.connection.isClosed()) {
                        throw new IllegalStateException();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        threadLocal.set(new Holder(true, isolation));
    }

    @Override
    public Connection getConnection() {
        Holder holder = threadLocal.get();
        if (holder == null) {
            return null;
        }
        if (holder.connection == null) {
            try {
                holder.connection = dataSource.getConnection();
                Isolation iso = holder.isolation;
                if (iso != null) {
                    Integer level = iso.getLevel();
                    if (level != null) {
                        holder.connection.setTransactionIsolation(level.intValue());
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return holder.connection;
    }

    @Override
    public void commit() {
        Holder holder = threadLocal.get();
        if (holder == null) {
            return;
        }
        if (holder.connection != null) {
            try {
                holder.connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void rollback() {
        Holder holder = threadLocal.get();
        if (holder == null) {
            return;
        }
        if (holder.connection != null) {
            try {
                holder.connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void close() {
        Holder holder = threadLocal.get();
        if (holder == null) {
            return;
        }
        try {
            if (holder.connection != null) {
                try {
                    holder.connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            threadLocal.set(null);
        }
    }

    private static class Holder {

        boolean started;

        Isolation isolation;

        Connection connection;

        Holder(boolean started, Isolation isolation) {
            this.started = started;
            this.isolation = isolation;
        }
    }
}
