package com.brightgenerous.bean;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public abstract class AbstractBean implements Serializable {

    private static final long serialVersionUID = 1115717515967321199L;

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    protected static @interface Primary {
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    protected static @interface Fill {

        class Null {
        }

        Class<?> value() default Null.class;
    }

    {
        if (defaultFill()) {
            BeanUtils.fill(this);
        }
    }

    protected boolean defaultFill() {
        return true;
    }

    protected boolean useHashCodeImplIfResolved() {
        return false;
    }

    protected boolean useEqualsImplIfResolved() {
        return false;
    }

    protected boolean useToStringImplIfResolved() {
        return false;
    }

    @Override
    public int hashCode() {
        if (useHashCodeImplIfResolved() && HashCodeUtils.resolved()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (useEqualsImplIfResolved() && EqualsUtils.resolved()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (useToStringImplIfResolved() && ToStringUtils.resolved()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }
}
