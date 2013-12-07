package com.brightgenerous.cli;

import java.util.ArrayList;
import java.util.List;

public class CliUtilsBuilder {

    private String opt;

    private String longOpt;

    private Integer args;

    private Boolean required;

    private Boolean optionalArg;

    private String argName;

    private String description;

    private Character valueSeparator;

    private final List<Option> options = new ArrayList<>();

    protected CliUtilsBuilder() {
    }

    public static CliUtilsBuilder create() {
        return new CliUtilsBuilder();
    }

    public CliUtilsBuilder clear() {
        options.clear();
        return this;
    }

    public CliUtilsBuilder opt(String opt) {
        this.opt = opt;
        return this;
    }

    public CliUtilsBuilder longOpt(String longOpt) {
        this.longOpt = longOpt;
        return this;
    }

    public CliUtilsBuilder args(Integer args) {
        this.args = args;
        return this;
    }

    public CliUtilsBuilder required(Boolean required) {
        this.required = required;
        return this;
    }

    public CliUtilsBuilder optionalArg(Boolean optionalArg) {
        this.optionalArg = optionalArg;
        return this;
    }

    public CliUtilsBuilder description(String description) {
        this.description = description;
        return this;
    }

    public CliUtilsBuilder valueSeparator(Character valueSeparator) {
        this.valueSeparator = valueSeparator;
        return this;
    }

    public CliUtilsBuilder add() {
        options.add(new Option(opt, longOpt, args, required, optionalArg, argName, description,
                valueSeparator));
        opt = null;
        longOpt = null;
        args = null;
        required = null;
        optionalArg = null;
        argName = null;
        description = null;
        valueSeparator = null;
        return this;
    }

    public CliUtils build() {
        return CliUtils.get(options);
    }
}
