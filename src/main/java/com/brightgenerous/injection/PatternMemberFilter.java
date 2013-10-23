package com.brightgenerous.injection;

import java.lang.reflect.Member;
import java.util.regex.Pattern;

import com.brightgenerous.lang.Args;

public class PatternMemberFilter<T extends Member> implements MemberFilter<T> {

    private static final long serialVersionUID = -811713479865263298L;

    private final Pattern pattern;

    public PatternMemberFilter(String regex) {
        Args.notNull(regex, "regex");

        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean filter(T obj) {
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
