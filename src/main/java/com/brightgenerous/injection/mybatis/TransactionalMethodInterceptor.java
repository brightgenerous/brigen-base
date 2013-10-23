package com.brightgenerous.injection.mybatis;

//@formatter:off
/*
 * origin file
 *   groupId: org.mybatis
 *   artifactId: mybatis-guice
 *   version: 3.5
 *     org.mybatis.guice.transactional.TransactionalMethodInterceptor
 * 
 * edit
 *   - package org.mybatis.guice.transactional;
 *   + //package org.mybatis.guice.transactional;
 * 
 *   + import org.mybatis.guice.transactional.Transactional;
 * 
 *   - public final class TransactionalMethodInterceptor implements MethodInterceptor {
 *   + //public final class TransactionalMethodInterceptor implements MethodInterceptor {
 *   + public class TransactionalMethodInterceptor implements MethodInterceptor {
 * 
 *   - Transactional transactional = interceptedMethod.getAnnotation(Transactional.class);
 *   + //Transactional transactional = interceptedMethod.getAnnotation(Transactional.class);
 *   + Transactional transactional = TransactionalImpl.getAnnotation(interceptedMethod, this);
 *
 *   + private final boolean readOnly;
 *
 *   + public TransactionalMethodInterceptor() {
 *   +   this(false);
 *   + }
 *
 *   + public TransactionalMethodInterceptor(boolean readOnly) {
 *   +   this.readOnly = readOnly;
 *   + }
 * 
 *   + if (!readOnly) {
 *   +   Connection conn = sqlSessionManager.getConnection();
 *   +   if (conn.isReadOnly()) {
 *   +     conn.setReadOnly(false);
 *   +   }
 *   + }
 * 
 *   + protected void setting() {
 *   + }
 * 
 * 
 * add codes
 *   + class TransactionalImpl implements Transactional {
 *   + ...
 *   + ...to eof
 */


/*
 *    Copyright 2010-2012 The MyBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
//package org.mybatis.guice.transactional;

import static java.lang.String.*;
import static java.lang.Thread.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Arrays;

import javax.inject.Inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.mybatis.guice.transactional.Isolation;
import org.mybatis.guice.transactional.Transactional;

/**
 * Method interceptor for {@link Transactional} annotation.
 *
 * @version $Id$
 */
//public final class TransactionalMethodInterceptor implements MethodInterceptor {
public class TransactionalMethodInterceptor implements MethodInterceptor {

    private static final Class<?>[] CAUSE_TYPES = new Class[]{ Throwable.class };

    private static final Class<?>[] MESSAGE_CAUSE_TYPES = new Class[]{ String.class, Throwable.class };

    /**
     * This class logger.
     */
    private final Log log = LogFactory.getLog(getClass());

    /**
     * The {@code SqlSessionManager} reference.
     */
    @Inject
    private SqlSessionManager sqlSessionManager;

    private final boolean readOnly;

    public TransactionalMethodInterceptor() {
        this(false);
    }

    public TransactionalMethodInterceptor(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * Sets the SqlSessionManager instance.
     *
     * @param sqlSessionManager the SqlSessionManager instance.
     */
    public void setSqlSessionManager(SqlSessionManager sqlSessionManager) {
        this.sqlSessionManager = sqlSessionManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method interceptedMethod = invocation.getMethod();
        //Transactional transactional = interceptedMethod.getAnnotation(Transactional.class);
        Transactional transactional = TransactionalImpl.getAnnotation(interceptedMethod, this);

        String debugPrefix = null;
        if (log.isDebugEnabled()) {
            debugPrefix = format("[Intercepted method: %s]", interceptedMethod.toGenericString());
        }

        boolean isSessionInherited = sqlSessionManager.isManagedSessionStarted();

        if (isSessionInherited) {
            if (log.isDebugEnabled()) {
                log.debug(format("%s - SqlSession already set for thread: %s",
                        debugPrefix,
                        currentThread().getId()));
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug(format("%s - SqlSession not set for thread: %s, creating a new one",
                        debugPrefix,
                        currentThread().getId()));
            }

            sqlSessionManager.startManagedSession(transactional.executorType(), transactional.isolation().getTransactionIsolationLevel());

            if (!readOnly) {
                Connection conn = sqlSessionManager.getConnection();
                if (conn.isReadOnly()) {
                    conn.setReadOnly(false);
                }
            }
        }

        Object object = null;
        try {
            object = invocation.proceed();

            if (!isSessionInherited && !transactional.rollbackOnly()) {
                sqlSessionManager.commit(transactional.force());
            }
        } catch (Throwable t) {
            // rollback the transaction
            sqlSessionManager.rollback(transactional.force());

            // check the caught exception is declared in the invoked method
            for (Class<?> exceptionClass : interceptedMethod.getExceptionTypes()) {
                if (exceptionClass.isAssignableFrom(t.getClass())) {
                    throw t;
                }
            }

            // check the caught exception is of same rethrow type
            if (transactional.rethrowExceptionsAs().isAssignableFrom(t.getClass())) {
                throw t;
            }

            // rethrow the exception as new exception
            String errorMessage;
            Object[] initargs;
            Class<?>[] initargsType;

            if (transactional.exceptionMessage().length() != 0) {
                errorMessage = format(transactional.exceptionMessage(), invocation.getArguments());
                initargs = new Object[]{ errorMessage, t };
                initargsType = MESSAGE_CAUSE_TYPES;
            } else {
                initargs = new Object[]{ t };
                initargsType = CAUSE_TYPES;
            }

            Constructor<? extends Throwable> exceptionConstructor = getMatchingConstructor(transactional.rethrowExceptionsAs(), initargsType);
            Throwable rethrowEx = null;
            if (exceptionConstructor != null) {
                try {
                    rethrowEx = exceptionConstructor.newInstance(initargs);
                } catch (Exception e) {
                    errorMessage = format("Impossible to re-throw '%s', it needs the constructor with %s argument(s).",
                            transactional.rethrowExceptionsAs().getName(),
                            Arrays.toString(initargsType));
                    log.error(errorMessage, e);
                    rethrowEx = new RuntimeException(errorMessage, e);
                }
            } else {
                errorMessage = format("Impossible to re-throw '%s', it needs the constructor with %s or %s argument(s).",
                        transactional.rethrowExceptionsAs().getName(),
                        Arrays.toString(CAUSE_TYPES),
                        Arrays.toString(MESSAGE_CAUSE_TYPES));
                log.error(errorMessage);
                rethrowEx = new RuntimeException(errorMessage);
            }

            throw rethrowEx;
        } finally {
            // skip close when the session is inherited from another Transactional method
            if (!isSessionInherited) {
                if (transactional.rollbackOnly()) {
                    if (log.isDebugEnabled()) {
                        log.debug(debugPrefix
                                + " - SqlSession of thread: "
                                + currentThread().getId()
                                + " was in rollbackOnly mode, rolling it back");
                    }

                    sqlSessionManager.rollback(true);
                }

                if (log.isDebugEnabled()) {
                    log.debug(format("%s - SqlSession of thread: %s terminated its life-cycle, closing it",
                            debugPrefix,
                            currentThread().getId()));
                }

                sqlSessionManager.close();
            } else if (log.isDebugEnabled()) {
                log.debug(format("%s - SqlSession of thread: %s is inherited, skipped close operation",
                        debugPrefix,
                        currentThread().getId()));
            }
        }

        return object;
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> Constructor<E> getMatchingConstructor(Class<E> type,
            Class<?>[] argumentsType) {
        Class<? super E> currentType = type;
        while (Object.class != currentType) {
            for (Constructor<?> constructor : currentType.getConstructors()) {
                if (Arrays.equals(argumentsType, constructor.getParameterTypes())) {
                    return (Constructor<E>) constructor;
                }
            }
            currentType = currentType.getSuperclass();
        }
        return null;
    }

    protected void setting() {
    }

}


/*
 * add codes
 */
//@formatter:on

@SuppressWarnings("all")
class TransactionalImpl implements Transactional {

    static Transactional getAnnotation(Method method, TransactionalMethodInterceptor interceptor) {
        Transactional setting;
        try {
            Method m = interceptor.getClass().getDeclaredMethod("setting");
            setting = m.getAnnotation(Transactional.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
        return new TransactionalImpl(method.getAnnotation(Transactional.class), setting);
    }

    private static final Transactional def;
    static {
        try {
            Method method = TransactionalImpl.class.getDeclaredMethod("annotated");
            def = method.getAnnotation(Transactional.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    void annotated() {
    }

    private final Transactional particular;

    private final Transactional setting;

    TransactionalImpl(Transactional particular, Transactional setting) {
        this.particular = particular;
        this.setting = setting;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        Class<? extends Annotation> ret = null;
        if (particular != null) {
            ret = particular.annotationType();
        }
        if ((ret == null) && (setting != null)) {
            ret = setting.annotationType();
        }
        if (ret == null) {
            ret = def.annotationType();
        }
        return null;
    }

    @Override
    public ExecutorType executorType() {
        ExecutorType ret = null;
        if (particular != null) {
            ret = particular.executorType();
        }
        if ((ret == null) && (setting != null)) {
            ret = setting.executorType();
        }
        if (ret == null) {
            ret = def.executorType();
        }
        return ret;
    }

    @Override
    public Isolation isolation() {
        Isolation ret = null;
        if (particular != null) {
            ret = particular.isolation();
        }
        if ((ret == null) && (setting != null)) {
            ret = setting.isolation();
        }
        if (ret == null) {
            ret = def.isolation();
        }
        return ret;
    }

    @Override
    public TransactionIsolationLevel isolationLevel() {
        TransactionIsolationLevel ret = null;
        if (particular != null) {
            ret = particular.isolationLevel();
        }
        if ((ret == null) && (setting != null)) {
            ret = setting.isolationLevel();
        }
        if (ret == null) {
            ret = def.isolationLevel();
        }
        return ret;
    }

    @Override
    public boolean force() {
        Boolean ret = null;
        if (particular != null) {
            ret = particular.force() ? Boolean.TRUE : Boolean.FALSE;
        }
        if ((ret == null) && (setting != null)) {
            ret = setting.force() ? Boolean.TRUE : Boolean.FALSE;
        }
        if (ret == null) {
            ret = def.force() ? Boolean.TRUE : Boolean.FALSE;
        }
        return ret.booleanValue();
    }

    @Override
    public boolean autoCommit() {
        Boolean ret = null;
        if (particular != null) {
            ret = particular.autoCommit() ? Boolean.TRUE : Boolean.FALSE;
        }
        if ((ret == null) && (setting != null)) {
            ret = setting.autoCommit() ? Boolean.TRUE : Boolean.FALSE;
        }
        if (ret == null) {
            ret = def.autoCommit() ? Boolean.TRUE : Boolean.FALSE;
        }
        return ret.booleanValue();
    }

    @Override
    public Class<? extends Throwable> rethrowExceptionsAs() {
        Class<? extends Throwable> ret = null;
        if (particular != null) {
            ret = particular.rethrowExceptionsAs();
        }
        if ((ret == null) && (setting != null)) {
            ret = setting.rethrowExceptionsAs();
        }
        if (ret == null) {
            ret = def.rethrowExceptionsAs();
        }
        return ret;
    }

    @Override
    public String exceptionMessage() {
        String ret = null;
        if (particular != null) {
            ret = particular.exceptionMessage();
        }
        if ((ret == null) && (setting != null)) {
            ret = setting.exceptionMessage();
        }
        if (ret == null) {
            ret = def.exceptionMessage();
        }
        return ret;
    }

    @Override
    public boolean rollbackOnly() {
        Boolean ret = null;
        if (particular != null) {
            ret = particular.rollbackOnly() ? Boolean.TRUE : Boolean.FALSE;
        }
        if ((ret == null) && (setting != null)) {
            ret = setting.rollbackOnly() ? Boolean.TRUE : Boolean.FALSE;
        }
        if (ret == null) {
            ret = def.rollbackOnly() ? Boolean.TRUE : Boolean.FALSE;
        }
        return ret.booleanValue();
    }

}
