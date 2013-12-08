package com.brightgenerous.resolver.delegate;

import java.util.Set;

import com.brightgenerous.resolver.Matcher;
import com.opensymphony.xwork2.util.ResolverUtil;
import com.opensymphony.xwork2.util.ResolverUtil.ClassTest;

class ResolverDelegaterStruts2 implements ResolverDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(ResolverUtil.class.getName());
            Class.forName(ClassTest.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> Set<Class<? extends T>> find(Matcher matcher, String packageName) {
        ResolverUtil<T> util = new ResolverUtil<>();
        util.find(new AdaptTest(matcher), packageName);
        return util.getClasses();
    }

    @Override
    public <T> Set<Class<? extends T>> find(Matcher matcher, String packageName,
            ClassLoader classloader) {
        ResolverUtil<T> util = new ResolverUtil<>();
        util.setClassLoader(classloader);
        util.find(new AdaptTest(matcher), packageName);
        return util.getClasses();
    }

    private static class AdaptTest extends ClassTest {

        private final Matcher matcher;

        public AdaptTest(Matcher matcher) {
            this.matcher = matcher;
        }

        @SuppressWarnings("rawtypes")
        @Override
        public boolean matches(Class type) {
            return matcher.matches(type);
        }
    }
}
