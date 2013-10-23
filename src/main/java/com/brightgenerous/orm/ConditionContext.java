package com.brightgenerous.orm;

import java.io.Serializable;

public interface ConditionContext extends Serializable {

    Condition newCondition(UpdatedCallback uc);

    Fields newFields(UpdatedCallback uc);

    <T extends Serializable> Field<T> newField(UpdatedCallback uc);

    Options newOptions(UpdatedCallback uc);

    <T extends Serializable> Option<T> newOption(UpdatedCallback uc);

    Sorts newSorts(UpdatedCallback uc);

    Sort newSort(String key);

    Sort newSort(String key, boolean asc);
}
