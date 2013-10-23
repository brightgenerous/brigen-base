package com.brightgenerous.injection;

import java.io.Serializable;

public interface ImplResolver extends Serializable {

    <T> Class<? extends T> getImplClass(Class<T> clazz);
}
