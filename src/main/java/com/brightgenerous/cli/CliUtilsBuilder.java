package com.brightgenerous.cli;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CliUtilsBuilder implements Serializable {

    private static final long serialVersionUID = -9171290809852973775L;

    private String cmdLineSyntax;

    private String opt;

    private String longOpt;

    private Integer args;

    private Boolean required;

    private Boolean optionalArg;

    private String argName;

    private String description;

    private Character valueSeparator;

    // must be java.io.Serializable
    private final List<CliOption> options = new ArrayList<>();

    protected CliUtilsBuilder() {
    }

    public static CliUtilsBuilder create() {
        return new CliUtilsBuilder();
    }

    public CliUtilsBuilder clear() {
        cmdLineSyntax = null;
        options.clear();
        return this;
    }

    public String cmdLineSyntax() {
        return cmdLineSyntax;
    }

    public CliUtilsBuilder cmdLineSyntax(String cmdLineSyntax) {
        this.cmdLineSyntax = cmdLineSyntax;
        return this;
    }

    public String opt() {
        return opt;
    }

    public CliUtilsBuilder opt(String opt) {
        this.opt = opt;
        return this;
    }

    public String longOpt() {
        return longOpt;
    }

    public CliUtilsBuilder longOpt(String longOpt) {
        this.longOpt = longOpt;
        return this;
    }

    public Integer args() {
        return args;
    }

    public CliUtilsBuilder args(Integer args) {
        this.args = args;
        return this;
    }

    public Boolean required() {
        return required;
    }

    public CliUtilsBuilder required(Boolean required) {
        this.required = required;
        return this;
    }

    public Boolean optionalArg() {
        return optionalArg;
    }

    public CliUtilsBuilder optionalArg(Boolean optionalArg) {
        this.optionalArg = optionalArg;
        return this;
    }

    public String description() {
        return description;
    }

    public CliUtilsBuilder description(String description) {
        this.description = description;
        return this;
    }

    public Character valueSeparator() {
        return valueSeparator;
    }

    public CliUtilsBuilder valueSeparator(Character valueSeparator) {
        this.valueSeparator = valueSeparator;
        return this;
    }

    public CliUtilsBuilder add() {
        options.add(new CliOption(opt, longOpt, args, required, optionalArg, argName, description,
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
        return CliUtils.get(cmdLineSyntax, options);
    }
}
