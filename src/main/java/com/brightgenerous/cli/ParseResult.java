package com.brightgenerous.cli;

public interface ParseResult {

    boolean has(char opt);

    boolean has(String opt);

    String value(char opt);

    String value(String opt);

    String[] values(char opt);

    String[] values(String opt);
}
