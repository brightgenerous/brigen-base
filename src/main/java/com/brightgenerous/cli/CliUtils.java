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

    private final List<Option> options;

    protected CliUtils(List<Option> options) {
        if ((options == null) || options.isEmpty()) {
            this.options = new ArrayList<>();
        } else {
            this.options = new ArrayList<>(options);
        }
    }

    public static CliUtils get(Option... options) {
        return getInstance(Arrays.asList(options));
    }

    public static CliUtils get(List<Option> options) {
        return getInstance(options);
    }

    protected static CliUtils getInstance(List<Option> options) {
        return new CliUtils(options);
    }

    public ParseResult parse(String[] args) throws ParseException {
        return CliUtility.parse(options, args);
    }
}
