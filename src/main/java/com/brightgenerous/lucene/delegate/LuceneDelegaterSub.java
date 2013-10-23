package com.brightgenerous.lucene.delegate;

@SuppressWarnings("deprecation")
class LuceneDelegaterSub implements LuceneDelegater {

    @Override
    public StringDistanceDelegater createLevensteinDistance() {
        return new LevensteinDistanceSub();
    }

    @Override
    public StringDistanceDelegater createJaroWinklerDistance() {
        return new JaroWinklerDistanceSub();
    }

    private static class LevensteinDistanceSub implements StringDistanceDelegater {

        private static final long serialVersionUID = -8086637282044096826L;

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

    private static class JaroWinklerDistanceSub implements StringDistanceDelegater {

        private static final long serialVersionUID = 2106586105930921842L;

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
