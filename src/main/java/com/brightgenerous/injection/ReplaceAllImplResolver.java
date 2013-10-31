package com.brightgenerous.injection;

import com.brightgenerous.lang.Args;

public class ReplaceAllImplResolver implements ImplResolver {

    private static final long serialVersionUID = -3106461902151108835L;

    private final String regex;

    private final String replacement;

    public ReplaceAllImplResolver(String regex, String replacement) {
        Args.notNull(regex, "regex");
        Args.notNull(replacement, "replacement");

        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        String cName = clazz.getName();
        if (cName == null) {
            return null;
        }
        try {
            return (Class<? extends T>) Class.forName(cName.replaceAll(regex, replacement));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
