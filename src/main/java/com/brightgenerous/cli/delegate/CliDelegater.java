package com.brightgenerous.cli.delegate;

import java.util.List;

import com.brightgenerous.cli.Option;
import com.brightgenerous.cli.ParseException;
import com.brightgenerous.cli.ParseResult;

interface CliDelegater {

    ParseResult parse(List<Option> options, String[] args) throws ParseException;
}
