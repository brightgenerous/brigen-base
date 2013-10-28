package com.brightgenerous.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {

    // @formatter:off
    static enum Type {

        ALL,

        SELECT,

        SELECT_INSERT,

        SELECT_UPDATE,

        INSERT,

        INSERT_UPDATE,

        UPDATE
    }
    // @formatter:on

    Type value() default Type.ALL;
}
