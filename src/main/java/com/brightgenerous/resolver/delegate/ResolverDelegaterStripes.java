package com.brightgenerous.resolver.delegate;

import java.util.Set;

import net.sourceforge.stripes.util.ResolverUtil;
import net.sourceforge.stripes.util.ResolverUtil.Test;

import com.brightgenerous.resolver.Matcher;

class ResolverDelegaterStripes implements ResolverDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(ResolverUtil.class.getName());
            Class.forName(Test.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> Set<Class<? extends T>> find(Matcher matcher, String packageName) {
        ResolverUtil<T> util = new ResolverUtil<>();
        return util.find(new AdaptTest(matcher), packageName).getClasses();
    }

    @Override
    public <T> Set<Class<? extends T>> find(Matcher matcher, String packageName,
            ClassLoader classloader) {
        ResolverUtil<T> util = new ResolverUtil<>();
        util.setClassLoader(classloader);
        return util.find(new AdaptTest(matcher), packageName).getClasses();
    }

    private static class AdaptTest implements Test {

        private final Matcher matcher;

        public AdaptTest(Matcher matcher) {
            this.matcher = matcher;
        }

        @Override
        public boolean matches(Class<?> type) {
            return matcher.matches(type);
        }
    }
}
