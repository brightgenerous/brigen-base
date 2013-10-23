package com.brightgenerous.lucene.delegate;

import org.apache.lucene.search.spell.JaroWinklerDistance;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.StringDistance;

@SuppressWarnings("deprecation")
class LuceneDelegaterImpl implements LuceneDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(JaroWinklerDistance.class.getName());
            Class.forName(LevensteinDistance.class.getName());
            Class.forName(StringDistance.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StringDistanceDelegater createLevensteinDistance() {
        return new LevensteinDistanceImpl();
    }

    @Override
    public StringDistanceDelegater createJaroWinklerDistance() {
        return new JaroWinklerDistanceImpl();
    }

    private static class LevensteinDistanceImpl implements StringDistanceDelegater {

        private static final long serialVersionUID = -5357020101808383240L;

        private transient volatile StringDistance deleg;

        protected StringDistance getDeleg() {
            if (deleg == null) {
                synchronized (this) {
                    if (deleg == null) {
                        deleg = new LevensteinDistance();
                    }
                }
            }
            return deleg;
        }

        @Override
        public float getDistance(String target, String other) {
            return getDeleg().getDistance(target, other);
        }
    }

    private static class JaroWinklerDistanceImpl implements StringDistanceDelegater {

        private static final long serialVersionUID = -3041177966407199566L;

        private transient volatile StringDistance deleg;

        protected StringDistance getDeleg() {
            if (deleg == null) {
                synchronized (this) {
                    if (deleg == null) {
                        deleg = new JaroWinklerDistance();
                    }
                }
            }
            return deleg;
        }

        @Override
        public float getDistance(String target, String other) {
            return getDeleg().getDistance(target, other);
        }
    }
}
