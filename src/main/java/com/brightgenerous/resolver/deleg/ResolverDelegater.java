package com.brightgenerous.resolver.deleg;

import java.util.Set;

import com.brightgenerous.resolver.deleg.ResolverUtility.Matcher;

@SuppressWarnings("deprecation")
interface ResolverDelegater {

    <T> Set<Class<? extends T>> find(Matcher matcher, String packageName);

    <T> Set<Class<? extends T>> find(Matcher matcher, String packageName, ClassLoader classloader);
}
