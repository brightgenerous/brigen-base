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

    public static final boolean USEFUL;

    private static final CliDelegater delegater;

    private static final RuntimeException rex;

    static {
        CliDelegater tmp = null;
        boolean useful = false;
        RuntimeException ex = null;
        try {
            tmp = new CliDelegaterCommons();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve apache commons cli");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
                ex = (RuntimeException) e;
            } else {
                ex = new RuntimeException(e);
            }
        }
        USEFUL = useful;
        delegater = tmp;
        rex = ex;
    }

    private CliUtility() {
    }

    private static CliDelegater getDelegater() {
        if (delegater == null) {
            throw rex;
        }
        return delegater;
    }

    public static ParseResult parse(List<CliOption> options, String[] args) throws CliException {
        return getDelegater().parse(options, args);
    }
}
