package com.brightgenerous.mail.deleg;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.brightgenerous.mail.MailException;
import com.brightgenerous.mail.MailInfo;

@Deprecated
public class MailUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean USEFUL;

    private static final MailDelegater delegater;

    private static final RuntimeException rex;

    static {
        MailDelegater tmp = null;
        RuntimeException ex = null;
        boolean useful = false;
        try {
            tmp = new MailDelegaterImpl();
            useful = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve javax mail");
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

    private MailUtility() {
    }

    private static MailDelegater getDelegater() {
        if (delegater == null) {
            throw rex;
        }
        return delegater;
    }

    public static void send(Properties prop, MailInfo info) throws MailException {
        getDelegater().send(prop, info);
    }
}
