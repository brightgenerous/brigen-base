package com.brightgenerous.injection.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Connection;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Transactional {

    enum Isolation {

        DEFAULT(null),

        NONE(Connection.TRANSACTION_NONE),

        READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),

        READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),

        REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),

        SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

        private final Integer level;

        private Isolation(Integer level) {
            this.level = level;
        }

        public Integer getLevel() {
            return level;
        }
    }

    Isolation isolation() default Isolation.DEFAULT;

    boolean force() default false;

    Class<? extends Throwable> rethrowExceptionsAs() default Exception.class;

    String exceptionMessage() default "";

    boolean rollbackOnly() default false;
}
