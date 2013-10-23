package com.brightgenerous.injection;

import java.util.regex.Pattern;

import com.brightgenerous.lang.Args;

public class PatternClassFilter implements ClassFilter {

    private static final long serialVersionUID = -2362318105092992656L;

    private final Pattern pattern;

    public PatternClassFilter(String regex) {
        Args.notNull(regex, "regex");

        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean filter(Class<?> obj) {
        if ((pattern == null) || (obj == null)) {
            return false;
        }
        String name = obj.getName();
        if (name == null) {
            return false;
        }
        return pattern.matcher(name).matches();
    }
}
