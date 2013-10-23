package com.brightgenerous.injection;

import com.brightgenerous.lang.Args;

public class ReplaceAllGenerateImplResolver extends GenerateImplResolver {

    private static final long serialVersionUID = -3856930235271208434L;

    private final String regex;

    private final String replacement;

    public ReplaceAllGenerateImplResolver(String regex, String replacement) {
        Args.notNull(regex, "regex");
        Args.notNull(replacement, "replacement");

        this.regex = regex;
        this.replacement = replacement;
    }

    @Override
    protected String getImplName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        String cName = clazz.getName();
        if (cName == null) {
            return null;
        }
        return cName.replaceAll(regex, replacement);
    }
}
