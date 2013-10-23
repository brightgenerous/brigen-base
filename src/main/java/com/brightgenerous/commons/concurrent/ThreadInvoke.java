package com.brightgenerous.commons.concurrent;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ThreadInvoke implements Runnable {

    private static final Logger log = Logger.getAnonymousLogger();

    @Override
    public final void run() {
        try {
            invoke();
        } catch (RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "", e);
            }

            throw e;
        } catch (Exception e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "", e);
            }

            throw new RuntimeException(e);
        }
    }

    protected abstract void invoke() throws Exception;
}
