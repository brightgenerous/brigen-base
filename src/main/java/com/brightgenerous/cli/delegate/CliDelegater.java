package com.brightgenerous.cli.delegate;

import java.util.List;

import com.brightgenerous.cli.CliException;
import com.brightgenerous.cli.CliOption;
import com.brightgenerous.cli.ParseResult;

interface CliDelegater {

    ParseResult parse(List<CliOption> options, String[] args) throws CliException;

    String options(List<CliOption> options);

    String help(String cmdLineSyntax, List<CliOption> options);

    String usage(String cmdLineSyntax, List<CliOption> options);
}
