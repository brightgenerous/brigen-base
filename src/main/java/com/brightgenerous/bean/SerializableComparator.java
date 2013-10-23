package com.brightgenerous.bean;

import java.io.Serializable;
import java.util.Comparator;

interface SerializableComparator<T> extends Comparator<T>, Serializable {
}
