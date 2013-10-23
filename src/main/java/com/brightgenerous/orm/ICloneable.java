package com.brightgenerous.orm;

interface ICloneable<T> extends Cloneable {

    T clone();

    static class Utils {

        public static <S> S getIfClone(S obj) {
            if (obj == null) {
                return null;
            }
            if (obj instanceof ICloneable) {
                ICloneable<S> ic = null;
                try {
                    ic = (ICloneable<S>) obj;
                } catch (ClassCastException e) {
                }
                if (ic != null) {
                    ic.clone();
                }
            }
            return obj;
        }
    }
}
