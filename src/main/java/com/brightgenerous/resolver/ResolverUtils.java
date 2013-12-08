package com.brightgenerous.resolver;

import java.util.Set;

import com.brightgenerous.resolver.delegate.ResolverUtility;

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

    private ResolverUtils() {
    }

    public static <T> Set<Class<? extends T>> find(Matcher matcher, String packageName) {
        return ResolverUtility.find(matcher, packageName);
    }

    public static <T> Set<Class<? extends T>> find(Matcher matcher, String packageName,
            ClassLoader classloader) {
        return ResolverUtility.find(matcher, packageName, classloader);
    }
}
