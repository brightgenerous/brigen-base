package com.brightgenerous.orm;

import static com.brightgenerous.commons.ObjectUtils.*;
import static com.brightgenerous.orm.ICloneable.Utils.*;

import java.io.Serializable;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public class Field<T extends Serializable> implements IField<T>, Clearable, EmptyChackable,
        ICloneable<Field<T>>, Serializable {

    private static final long serialVersionUID = -4799336684504013338L;

    private final UpdatedCallback callback;

    private T value;

    private T notValue;

    private Boolean isNull;

    private Boolean isNotNull;

    private String prefixValue;

    private String notPrefixValue;

    private String suffixValue;

    private String notSuffixValue;

    private String broadValue;

    private String notBroadValue;

    private T greaterThanValue;

    private T greaterEqualValue;

    private T lowerThanValue;

    private T lowerEqualValue;

    protected Field(UpdatedCallback callback) {
        this.callback = callback;
    }

    @Override
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (!equalsValueOrNull(this.value, value)) {
            this.value = value;
            callbackUpdated();
        }
    }

    @Override
    public T getNotValue() {
        return notValue;
    }

    public void setNotValue(T notValue) {
        if (!equalsValueOrNull(this.notValue, notValue)) {
            this.notValue = notValue;
            callbackUpdated();
        }
    }

    @Override
    public Boolean getIsNull() {
        return isNull;
    }

    public void setIsNull(Boolean isNull) {
        if (!equalsValueOrNull(this.isNull, isNull)) {
            this.isNull = isNull;
            callbackUpdated();
        }
    }

    @Override
    public Boolean getIsNotNull() {
        return isNotNull;
    }

    public void setIsNotNull(Boolean isNotNull) {
        if (!equalsValueOrNull(this.isNotNull, isNotNull)) {
            this.isNotNull = isNotNull;
            callbackUpdated();
        }
    }

    @Override
    public String getPrefixValue() {
        return prefixValue;
    }

    public void setPrefixValue(String prefixValue) {
        if (!equalsEscapeNull(this.prefixValue, prefixValue)) {
            this.prefixValue = prefixValue;
            callbackUpdated();
        }
    }

    @Override
    public String getNotPrefixValue() {
        return notPrefixValue;
    }

    public void setNotPrefixValue(String notPrefixValue) {
        if (!equalsEscapeNull(this.notPrefixValue, notPrefixValue)) {
            this.notPrefixValue = notPrefixValue;
            callbackUpdated();
        }
    }

    @Override
    public String getSuffixValue() {
        return suffixValue;
    }

    public void setSuffixValue(String suffixValue) {
        if (!equalsEscapeNull(this.suffixValue, suffixValue)) {
            this.suffixValue = suffixValue;
            callbackUpdated();
        }
    }

    @Override
    public String getNotSuffixValue() {
        return notSuffixValue;
    }

    public void setNotSuffixValue(String notSuffixValue) {
        if (!equalsEscapeNull(this.notSuffixValue, notSuffixValue)) {
            this.notSuffixValue = notSuffixValue;
            callbackUpdated();
        }
    }

    @Override
    public String getBroadValue() {
        return broadValue;
    }

    public void setBroadValue(String broadValue) {
        if (!equalsEscapeNull(this.broadValue, broadValue)) {
            this.broadValue = broadValue;
            callbackUpdated();
        }
    }

    @Override
    public String getNotBroadValue() {
        return notBroadValue;
    }

    public void setNotBroadValue(String notBroadValue) {
        if (!equalsEscapeNull(this.notBroadValue, notBroadValue)) {
            this.notBroadValue = notBroadValue;
            callbackUpdated();
        }
    }

    @Override
    public T getGreaterThanValue() {
        return greaterThanValue;
    }

    public void setGreaterThanValue(T greaterThanValue) {
        if (!equalsValueOrNull(this.greaterThanValue, greaterThanValue)) {
            this.greaterThanValue = greaterThanValue;
            callbackUpdated();
        }
    }

    @Override
    public T getGreaterEqualValue() {
        return greaterEqualValue;
    }

    public void setGreaterEqualValue(T greaterEqualValue) {
        if (!equalsValueOrNull(this.greaterEqualValue, greaterEqualValue)) {
            this.greaterEqualValue = greaterEqualValue;
            callbackUpdated();
        }
    }

    @Override
    public T getLowerThanValue() {
        return lowerThanValue;
    }

    public void setLowerThanValue(T lowerThanValue) {
        if (!equalsValueOrNull(this.lowerThanValue, lowerThanValue)) {
            this.lowerThanValue = lowerThanValue;
            callbackUpdated();
        }
    }

    @Override
    public T getLowerEqualValue() {
        return lowerEqualValue;
    }

    public void setLowerEqualValue(T lowerEqualValue) {
        if (!equalsValueOrNull(this.lowerEqualValue, lowerEqualValue)) {
            this.lowerEqualValue = lowerEqualValue;
            callbackUpdated();
        }
    }

    @Override
    public void clear() {
        boolean changed = false;
        if (value != null) {
            value = null;
            changed |= true;
        }
        if (notValue != null) {
            notValue = null;
            changed |= true;
        }
        if (isNull != null) {
            isNull = null;
            changed |= true;
        }
        if (isNotNull != null) {
            isNotNull = null;
            changed |= true;
        }
        if (prefixValue != null) {
            prefixValue = null;
            changed |= true;
        }
        if (notPrefixValue != null) {
            notPrefixValue = null;
            changed |= true;
        }
        if (suffixValue != null) {
            suffixValue = null;
            changed |= true;
        }
        if (notSuffixValue != null) {
            notSuffixValue = null;
            changed |= true;
        }
        if (broadValue != null) {
            broadValue = null;
            changed |= true;
        }
        if (notBroadValue != null) {
            notBroadValue = null;
            changed |= true;
        }
        if (greaterThanValue != null) {
            greaterThanValue = null;
            changed |= true;
        }
        if (greaterEqualValue != null) {
            greaterEqualValue = null;
            changed |= true;
        }
        if (lowerThanValue != null) {
            lowerThanValue = null;
            changed |= true;
        }
        if (lowerEqualValue != null) {
            lowerEqualValue = null;
            changed |= true;
        }
        if (changed) {
            callbackUpdated();
        }
    }

    @Override
    public boolean isEmpty() {
        return (getValue() == null) && (getNotValue() == null) && (getIsNull() == null)
                && (getIsNotNull() == null) && (getPrefixValue() == null)
                && (getNotPrefixValue() == null) && (getSuffixValue() == null)
                && (getNotSuffixValue() == null) && (getBroadValue() == null)
                && (getNotBroadValue() == null) && (getGreaterThanValue() == null)
                && (getGreaterEqualValue() == null) && (getLowerThanValue() == null)
                && (getLowerEqualValue() == null);
    }

    @Override
    public Field<T> clone() {
        Field<T> ret;
        try {
            ret = (Field<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
        ret.value = getIfClone(value);
        ret.notValue = getIfClone(notValue);
        ret.isNull = getIfClone(isNull);
        ret.isNotNull = getIfClone(isNotNull);
        ret.prefixValue = getIfClone(prefixValue);
        ret.notPrefixValue = getIfClone(notPrefixValue);
        ret.suffixValue = getIfClone(suffixValue);
        ret.notSuffixValue = getIfClone(notSuffixValue);
        ret.broadValue = getIfClone(broadValue);
        ret.notBroadValue = getIfClone(notBroadValue);
        ret.greaterThanValue = getIfClone(greaterThanValue);
        ret.greaterEqualValue = getIfClone(greaterEqualValue);
        ret.lowerThanValue = getIfClone(lowerThanValue);
        ret.lowerEqualValue = getIfClone(lowerEqualValue);
        return ret;
    }

    private void callbackUpdated() {
        if (callback != null) {
            callback.updated();
        }
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.useful()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.useful()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (ToStringUtils.useful()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }

    public IField<Object> getWrap() {
        return new FieldWrapper(this);
    }

    static class FieldWrapper implements IField<Object>, Serializable {

        private static final long serialVersionUID = 6921835409204919730L;

        private final Field<? extends Serializable> deleg;

        public FieldWrapper(Field<? extends Serializable> deleg) {
            this.deleg = deleg;
        }

        @Override
        public Object getValue() {
            return deleg.getValue();
        }

        @Override
        public Object getNotValue() {
            return deleg.getNotValue();
        }

        @Override
        public Boolean getIsNull() {
            return deleg.getIsNull();
        }

        @Override
        public Boolean getIsNotNull() {
            return deleg.getIsNotNull();
        }

        @Override
        public String getPrefixValue() {
            return deleg.getPrefixValue();
        }

        @Override
        public String getNotPrefixValue() {
            return deleg.getNotPrefixValue();
        }

        @Override
        public String getSuffixValue() {
            return deleg.getSuffixValue();
        }

        @Override
        public String getNotSuffixValue() {
            return deleg.getNotSuffixValue();
        }

        @Override
        public String getBroadValue() {
            return deleg.getBroadValue();
        }

        @Override
        public String getNotBroadValue() {
            return deleg.getNotBroadValue();
        }

        @Override
        public Object getGreaterThanValue() {
            return deleg.getGreaterThanValue();
        }

        @Override
        public Object getGreaterEqualValue() {
            return deleg.getGreaterEqualValue();
        }

        @Override
        public Object getLowerThanValue() {
            return deleg.getLowerThanValue();
        }

        @Override
        public Object getLowerEqualValue() {
            return deleg.getLowerEqualValue();
        }
    }
}
