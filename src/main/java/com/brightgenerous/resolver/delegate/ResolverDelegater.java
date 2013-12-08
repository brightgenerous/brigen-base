package com.brightgenerous.resolver.delegate;

import java.util.Set;

import com.brightgenerous.resolver.Matcher;

interface ResolverDelegater {

    <T> Set<Class<? extends T>> find(Matcher matcher, String packageName);

    <T> Set<Class<? extends T>> find(Matcher matcher, String packageName, ClassLoader classloader);
}
