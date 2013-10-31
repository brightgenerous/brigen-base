package com.brightgenerous.resolver;

import java.util.Set;

import com.brightgenerous.resolver.deleg.ResolverUtility;

@SuppressWarnings("deprecation")
public class ResolverUtils {

    public static boolean resolved() {
        return ResolverUtility.RESOLVED;
    }

    public static boolean mybatis() {
        return ResolverUtility.MYBATIS;
    }

    public static boolean stripes() {
        return ResolverUtility.STRIPES;
    }

    public static boolean struts2() {
        return ResolverUtility.STRUTS2;
    }

    public interface Matcher {

        boolean matches(Class<?> type);
    }

    private ResolverUtils() {
    }

    public static <T> Set<Class<? extends T>> find(Matcher matcher, String packageName) {
        return ResolverUtility.find(new AdaptMatcher(matcher), packageName);
    }

    public static <T> Set<Class<? extends T>> find(Matcher matcher, String packageName,
            ClassLoader classloader) {
        return ResolverUtility.find(new AdaptMatcher(matcher), packageName, classloader);
    }

    private static class AdaptMatcher implements ResolverUtility.Matcher {

        private final Matcher matcher;

        public AdaptMatcher(Matcher matcher) {
            this.matcher = matcher;
        }

        @Override
        public boolean matches(Class<?> type) {
            return matcher.matches(type);
        }
    }
}
