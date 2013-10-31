package com.brightgenerous.resolver.deleg;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class ResolverUtility {

    private static final Logger log = Logger.getAnonymousLogger();

    public static final boolean RESOLVED;

    public static final boolean MYBATIS;

    public static final boolean STRIPES;

    public static final boolean STRUTS2;

    private static final ResolverDelegater delegater;

    static {
        ResolverDelegater tmp = null;
        boolean mybatis = false;
        boolean stripes = false;
        boolean struts2 = false;
        try {
            tmp = new ResolverDelegaterMybatis();
            mybatis = true;
        } catch (NoClassDefFoundError | RuntimeException e) {

            if (log.isLoggable(Level.INFO)) {
                log.log(Level.INFO, "does not resolve mybatis");
            }

            if (e instanceof RuntimeException) {
                Throwable th = e.getCause();
                if ((th == null) || !(th instanceof ClassNotFoundException)) {
                    throw e;
                }
            }
        }
        if (tmp == null) {
            try {
                tmp = new ResolverDelegaterStripes();
                struts2 = true;
            } catch (NoClassDefFoundError | RuntimeException e) {

                if (log.isLoggable(Level.INFO)) {
                    log.log(Level.INFO, "does not resolve stripes");
                }

                if (e instanceof RuntimeException) {
                    Throwable th = e.getCause();
                    if ((th == null) || !(th instanceof ClassNotFoundException)) {
                        throw e;
                    }
                }
            }
        }
        if (tmp == null) {
            try {
                tmp = new ResolverDelegaterStruts2();
                struts2 = true;
            } catch (NoClassDefFoundError | RuntimeException e) {

                if (log.isLoggable(Level.INFO)) {
                    log.log(Level.INFO, "does not resolve struts2");
                }

                if (e instanceof RuntimeException) {
                    Throwable th = e.getCause();
                    if ((th == null) || !(th instanceof ClassNotFoundException)) {
                        throw e;
                    }
                }
            }
        }
        if (tmp == null) {
            tmp = new ResolverDelegaterSub();
        }
        RESOLVED = (mybatis | stripes | struts2);
        MYBATIS = mybatis;
        STRIPES = stripes;
        STRUTS2 = struts2;
        delegater = tmp;
    }

    private ResolverUtility() {
    }

    public interface Matcher {

        boolean matches(Class<?> type);
    }

    public static <T> Set<Class<? extends T>> find(Matcher matcher, String packageName) {
        return delegater.find(matcher, packageName);
    }

    public static <T> Set<Class<? extends T>> find(Matcher matcher, String packageName,
            ClassLoader classloader) {
        return delegater.find(matcher, packageName, classloader);
    }
}
