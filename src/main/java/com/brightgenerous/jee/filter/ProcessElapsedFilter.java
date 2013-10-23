package com.brightgenerous.jee.filter;

import static com.brightgenerous.commons.StringUtils.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProcessElapsedFilter extends SimpleFilter {

    public static final long DEFAULT_THRESHOLD = 1000;

    private long threshold;

    @Override
    public void init(FilterConfig config) throws ServletException {
        Long thresholdLong = null;
        String thresholdStr = trimToNull(config.getInitParameter("threshold"));
        if (isNotEmpty(thresholdStr)) {
            try {
                thresholdLong = Long.valueOf(thresholdStr);
            } catch (NumberFormatException e) {
                Logger log = getLogger();
                if ((log != null) && log.isLoggable(Level.INFO)) {
                    log.info(String.format(
                            "the threshold is illegal value [%s]. so use defalut value [%d]",
                            thresholdStr, Long.valueOf(DEFAULT_THRESHOLD)));
                }
            }
        } else {
            Logger log = getLogger();
            if ((log != null) && log.isLoggable(Level.INFO)) {
                log.info(String.format("the threshold is empty. so use defalut value [%d]",
                        Long.valueOf(DEFAULT_THRESHOLD)));
            }
        }
        threshold = (thresholdLong == null) ? DEFAULT_THRESHOLD : thresholdLong.longValue();
        if (threshold < 0) {
            threshold = 0;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long start = System.currentTimeMillis();

        chain.doFilter(request, response);

        long th = System.currentTimeMillis() - start;
        if (threshold <= th) {
            writeLog(request, response, th);
        }
    }

    protected void writeLog(ServletRequest request, ServletResponse response, long th) {
        Logger log = getLogger();
        if ((log != null) && log.isLoggable(Level.WARNING)) {
            String ip = request.getRemoteAddr();
            String uri = null;
            String sessionId = null;
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                uri = httpRequest.getRequestURI();
                HttpSession session = httpRequest.getSession(false);
                if (session != null) {
                    sessionId = session.getId();
                }
            }
            log.warning(String
                    .format("over the threshold. request to [%s]. elapsed-time => %d ms. ip is [%s]. session-id is [%s].",
                            uri, Long.valueOf(th), ip, sessionId));
        }
    }

    private transient volatile Logger logger;

    protected Logger getLogger() {
        if (logger == null) {
            synchronized (this) {
                if (logger == null) {
                    logger = Logger.getAnonymousLogger();
                }
            }
        }
        return logger;
    }
}
