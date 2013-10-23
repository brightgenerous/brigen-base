package com.brightgenerous.lucene.delegate;

@SuppressWarnings("deprecation")
interface LuceneDelegater {

    StringDistanceDelegater createLevensteinDistance();

    StringDistanceDelegater createJaroWinklerDistance();
}
