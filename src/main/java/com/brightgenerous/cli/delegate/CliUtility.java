package com.brightgenerous.cli.delegate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.cli.CliException;
import com.brightgenerous.cli.CliOption;
import com.brightgenerous.cli.ParseResult;

@Deprecated
public class CliUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean RESOLVED;

    private static final CliDelegater delegater;

    static {
        CliDelegater tmp = null;
        boolean resolved = false;
        try {
            tmp = new CliDelegaterImpl();
            resolved = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve apache commons cli");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        if (tmp == null) {
            tmp = new CliDelegaterSub();
        }
        RESOLVED = resolved;
        delegater = tmp;
    }

    private CliUtility() {
    }

    public static ParseResult parse(List<CliOption> options, String[] args) throws CliException {
        return delegater.parse(options, args);
    }

    public static String options(List<CliOption> options) {
        return delegater.options(options);
    }

    public static String help(String cmdLineSyntax, List<CliOption> options) {
        return delegater.help(cmdLineSyntax, options);
    }

    public static String usage(String cmdLineSyntax, List<CliOption> options) {
        return delegater.usage(cmdLineSyntax, options);
    }
}
