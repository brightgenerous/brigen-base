package com.brightgenerous.lucene;

public interface Extracter<T> {

    String extract(T obj);
}
