package com.brightgenerous.cli;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.brightgenerous.cli.delegate.CliUtility;
import com.brightgenerous.commons.EqualsUtils;
import com.brightgenerous.commons.HashCodeUtils;
import com.brightgenerous.commons.ToStringUtils;

@SuppressWarnings("deprecation")
public class CliUtils implements Serializable {

    private static final long serialVersionUID = 8875225634485364747L;

    public static boolean resolved() {
        return CliUtility.RESOLVED;
    }

    private final String cmdLineSyntax;

    // must be java.io.Serializable
    private final List<CliOption> options;

    protected CliUtils(String cmdLineSyntax, List<CliOption> options) {
        this.cmdLineSyntax = cmdLineSyntax;
        if ((options == null) || options.isEmpty()) {
            this.options = new ArrayList<>();
        } else {
            this.options = new ArrayList<>(options);
        }
    }

    public static CliUtils get(String cmdLineSyntax, CliOption... options) {
        return getInstance(cmdLineSyntax, Arrays.asList(options));
    }

    public static CliUtils get(String cmdLineSyntax, List<CliOption> options) {
        return getInstance(cmdLineSyntax, options);
    }

    protected static CliUtils getInstance(String cmdLineSyntax, List<CliOption> options) {
        return new CliUtils(cmdLineSyntax, options);
    }

    public ParseResult parse(String[] args) throws CliException {
        return CliUtility.parse(options, args);
    }

    public String options(List<CliOption> options) {
        return CliUtility.options(options);
    }

    public String help() {
        return CliUtility.help(cmdLineSyntax, options);
    }

    public String usage() {
        return CliUtility.usage(cmdLineSyntax, options);
    }

    @Override
    public int hashCode() {
        if (HashCodeUtils.resolved()) {
            return HashCodeUtils.hashCodeAlt(null, this);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (EqualsUtils.resolved()) {
            return EqualsUtils.equalsAlt(null, this, obj);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (ToStringUtils.resolved()) {
            return ToStringUtils.toStringAlt(this);
        }
        return super.toString();
    }
}
