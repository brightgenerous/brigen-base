package com.brightgenerous.cli;

import java.io.Serializable;

public class Option implements Serializable {

    private static final long serialVersionUID = -3095077503243126133L;

    private final String opt;

    private final String longOpt;

    private final Integer args;

    private final Boolean required;

    private final Boolean optionalArg;

    private final String argName;

    private final String description;

    private final Character valueSeparator;

    public Option(String opt, String longOpt, Integer args, Boolean required, Boolean optionalArg,
            String argName, String description, Character valueSeparator) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.args = args;
        this.required = required;
        this.optionalArg = optionalArg;
        this.argName = argName;
        this.description = description;
        this.valueSeparator = valueSeparator;
    }

    public String opt() {
        return opt;
    }

    public String longOpt() {
        return longOpt;
    }

    public Integer args() {
        return args;
    }

    public Boolean required() {
        return required;
    }

    public Boolean optionalArg() {
        return optionalArg;
    }

    public String argName() {
        return argName;
    }

    public String description() {
        return description;
    }

    public Character valueSeparator() {
        return valueSeparator;
    }
}
