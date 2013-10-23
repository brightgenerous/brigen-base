package com.brightgenerous.orm;

import static com.brightgenerous.commons.ObjectUtils.*;
import static com.brightgenerous.orm.ICloneable.Utils.*;

import java.io.Serializable;

import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

public class Condition implements Clearable, ICloneable<Condition>, Serializable {

    private static final long serialVersionUID = -6912482213181209264L;

    private final ConditionContext context;

    private final UpdatedCallback callback;

    private Fields fields;

    private Options options;

    private Sorts sorts;

    private Long offset;

    private Long limit;

    private boolean forUpdate;

    public Condition() {
        this((ConditionContext) null);
    }

    public Condition(ConditionContext context) {
        this(context, null);
    }

    public Condition(UpdatedCallback callback) {
        this(null, callback);
    }

    public Condition(ConditionContext context, UpdatedCallback callback) {
        this.context = (context == null) ? DefaultConditionContext.get() : context;
        this.callback = callback;
    }

    protected ConditionContext getContext() {
        return context;
    }

    public Fields getFields() {
        if (fields == null) {
            fields = getContext().newFields(callback);
        }
        return fields;
    }

    public Options getOptions() {
        if (options == null) {
            options = getContext().newOptions(callback);
        }
        return options;
    }

    public Sorts getSorts() {
        if (sorts == null) {
            sorts = getContext().newSorts(callback);
        }
        return sorts;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        setOffset(Long.valueOf(offset));
    }

    public void setOffset(Long offset) {
        if (!equalsEscapeNull(this.offset, offset)) {
            this.offset = offset;
            callbackUpdated();
        }
    }

    public void clearOffset() {
        setOffset(null);
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        setLimit(Long.valueOf(limit));
    }

    public void setLimit(Long limit) {
        if (!equalsEscapeNull(this.limit, limit)) {
            this.limit = limit;
            callbackUpdated();
        }
    }

    public void clearLimit() {
        setLimit(null);
    }

    public void setOffsetLimit(long offset, long limit) {
        setOffsetLimit(Long.valueOf(offset), Long.valueOf(limit));
    }

    public void setOffsetLimit(long offset, Long limit) {
        setOffsetLimit(Long.valueOf(offset), limit);
    }

    public void setOffsetLimit(Long offset, long limit) {
        setOffsetLimit(offset, Long.valueOf(limit));
    }

    public void setOffsetLimit(Long offset, Long limit) {
        boolean changed = false;
        if (!equalsEscapeNull(this.offset, offset)) {
            this.offset = offset;
            changed |= true;
        }
        if (!equalsEscapeNull(this.limit, limit)) {
            this.limit = limit;
            changed |= true;
        }
        if (changed) {
            callbackUpdated();
        }
    }

    public void clearOffsetLimit() {
        setOffsetLimit(null, null);
    }

    public boolean getForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(boolean forUpdate) {
        if (this.forUpdate != forUpdate) {
            this.forUpdate = forUpdate;
            callbackUpdated();
        }
    }

    public void clean() {
        if (fields != null) {
            fields.clean();
        }
        if (options != null) {
            options.clean();
        }
    }

    @Override
    public void clear() {
        clean();
        boolean changed = false;
        if ((fields != null) && !fields.isEmpty()) {
            fields.clear();
            changed |= true;
        }
        if ((options != null) && !options.isEmpty()) {
            options.clear();
            changed |= true;
        }
        if ((sorts != null) && !sorts.isEmpty()) {
            sorts.clear();
            changed |= true;
        }
        if (offset != null) {
            offset = null;
            changed |= true;
        }
        if (limit != null) {
            limit = null;
            changed |= true;
        }
        if (forUpdate) {
            forUpdate = false;
            changed |= true;
        }
        if (changed) {
            callbackUpdated();
        }
    }

    @Override
    public Condition clone() {
        clean();
        Condition ret;
        try {
            ret = (Condition) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
        ret.fields = getIfClone(fields);
        ret.options = getIfClone(options);
        ret.sorts = getIfClone(sorts);
        ret.offset = getIfClone(offset);
        ret.limit = getIfClone(limit);
        ret.forUpdate = forUpdate;
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
}
