package com.brightgenerous.cli;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.brightgenerous.cli.delegate.CliUtility;

@SuppressWarnings("deprecation")
public class CliUtils implements Serializable {

    private static final long serialVersionUID = 8875225634485364747L;

    public static boolean useful() {
        return CliUtility.USEFUL;
    }

    // must be java.io.Serializable
    private final List<CliOption> options;

    protected CliUtils(List<CliOption> options) {
        if ((options == null) || options.isEmpty()) {
            this.options = new ArrayList<>();
        } else {
            this.options = new ArrayList<>(options);
        }
    }

    public static CliUtils get(CliOption... options) {
        return getInstance(Arrays.asList(options));
    }

    public static CliUtils get(List<CliOption> options) {
        return getInstance(options);
    }

    protected static CliUtils getInstance(List<CliOption> options) {
        return new CliUtils(options);
    }

    public ParseResult parse(String[] args) throws CliException {
        return CliUtility.parse(options, args);
    }
}
