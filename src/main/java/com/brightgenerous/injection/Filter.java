package com.brightgenerous.injection;

import java.io.Serializable;

public interface Filter<T> extends Serializable {

    boolean filter(T obj);
}
