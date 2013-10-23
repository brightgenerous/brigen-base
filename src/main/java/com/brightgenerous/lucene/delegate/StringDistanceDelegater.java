package com.brightgenerous.lucene.delegate;

import java.io.Serializable;

@Deprecated
public interface StringDistanceDelegater extends Serializable {

    float getDistance(String target, String other);
}
