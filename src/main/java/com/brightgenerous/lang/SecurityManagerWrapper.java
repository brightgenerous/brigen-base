package com.brightgenerous.lang;

import java.io.FileDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.security.Permission;

class SecurityManagerWrapper extends SecurityManager {

    private final SecurityManager deleg;

    private final Class<? extends SecurityManager> delegClazz;

    private final ISecurityManagerFilter filter;

    public SecurityManagerWrapper() {
        this((SecurityManager) null);
    }

    public SecurityManagerWrapper(SecurityManager deleg) {
        this(deleg, null);
    }

    public SecurityManagerWrapper(ISecurityManagerFilter filter) {
        this(null, filter);
    }

    public SecurityManagerWrapper(SecurityManager deleg, ISecurityManagerFilter filter) {
        this.deleg = deleg;
        if (deleg == null) {
            delegClazz = null;
        } else {
            delegClazz = deleg.getClass();
        }
        this.filter = filter;
    }

    @Override
    public void checkAccept(String host, int port) {
        if (filter != null) {
            filter.checkAccept(host, port);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkAccept(host, port);
    }

    @Override
    public void checkAccess(Thread t) {
        if (filter != null) {
            filter.checkAccess(t);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkAccess(t);
    }

    @Override
    public void checkAccess(ThreadGroup t) {
        if (filter != null) {
            filter.checkAccess(t);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkAccess(t);
    }

    @Override
    public void checkAwtEventQueueAccess() {
        if (filter != null) {
            filter.checkAwtEventQueueAccess();
        }
        if (deleg == null) {
            return;
        }
        deleg.checkAwtEventQueueAccess();
    }

    @Override
    public void checkConnect(String host, int port) {
        if (filter != null) {
            filter.checkConnect(host, port);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkConnect(host, port);
    }

    @Override
    public void checkConnect(String host, int port, Object context) {
        if (filter != null) {
            filter.checkConnect(host, port, context);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkConnect(host, port, context);
    }

    @Override
    public void checkCreateClassLoader() {
        if (filter != null) {
            filter.checkCreateClassLoader();
        }
        if (deleg == null) {
            return;
        }
        deleg.checkCreateClassLoader();
    }

    @Override
    public void checkDelete(String file) {
        if (filter != null) {
            filter.checkDelete(file);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkDelete(file);
    }

    @Override
    public void checkExec(String cmd) {
        if (filter != null) {
            filter.checkExec(cmd);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkExec(cmd);
    }

    @Override
    public void checkExit(int status) {
        if (filter != null) {
            filter.checkExit(status);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkExit(status);
    }

    @Override
    public void checkLink(String lib) {
        if (filter != null) {
            filter.checkLink(lib);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkLink(lib);
    }

    @Override
    public void checkListen(int port) {
        if (filter != null) {
            filter.checkListen(port);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkListen(port);
    }

    @Override
    public void checkMemberAccess(Class<?> clazz, int which) {
        if (filter != null) {
            filter.checkMemberAccess(clazz, which);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkMemberAccess(clazz, which);
    }

    @Override
    public void checkMulticast(InetAddress maddr) {
        if (filter != null) {
            filter.checkMulticast(maddr);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkMulticast(maddr);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void checkMulticast(InetAddress maddr, byte ttl) {
        if (filter != null) {
            filter.checkMulticast(maddr, ttl);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkMulticast(maddr, ttl);
    }

    @Override
    public void checkPackageAccess(String pkg) {
        if (filter != null) {
            filter.checkPackageAccess(pkg);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkPackageAccess(pkg);
    }

    @Override
    public void checkPackageDefinition(String pkg) {
        if (filter != null) {
            filter.checkPackageDefinition(pkg);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkPackageDefinition(pkg);
    }

    @Override
    public void checkPermission(Permission perm) {
        if (filter != null) {
            filter.checkPermission(perm);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkPermission(perm);
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        if (filter != null) {
            filter.checkPermission(perm, context);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkPermission(perm, context);
    }

    @Override
    public void checkPrintJobAccess() {
        if (filter != null) {
            filter.checkPrintJobAccess();
        }
        if (deleg == null) {
            return;
        }
        deleg.checkPrintJobAccess();
    }

    @Override
    public void checkPropertiesAccess() {
        if (filter != null) {
            filter.checkPropertiesAccess();
        }
        if (deleg == null) {
            return;
        }
        deleg.checkPropertiesAccess();
    }

    @Override
    public void checkPropertyAccess(String key) {
        if (filter != null) {
            filter.checkPropertyAccess(key);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkPropertyAccess(key);
    }

    @Override
    public void checkRead(FileDescriptor fd) {
        if (filter != null) {
            filter.checkRead(fd);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkRead(fd);
    }

    @Override
    public void checkRead(String file) {
        if (filter != null) {
            filter.checkRead(file);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkRead(file);
    }

    @Override
    public void checkRead(String file, Object context) {
        if (filter != null) {
            filter.checkRead(file, context);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkRead(file, context);
    }

    @Override
    public void checkSecurityAccess(String target) {
        if (filter != null) {
            filter.checkSecurityAccess(target);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkSecurityAccess(target);
    }

    @Override
    public void checkSetFactory() {
        if (filter != null) {
            filter.checkSetFactory();
        }
        if (deleg == null) {
            return;
        }
        deleg.checkSetFactory();
    }

    @Override
    public void checkSystemClipboardAccess() {
        if (filter != null) {
            filter.checkSystemClipboardAccess();
        }
        if (deleg == null) {
            return;
        }
        deleg.checkSystemClipboardAccess();
    }

    @Override
    public boolean checkTopLevelWindow(Object window) {
        if (filter != null) {
            if (!filter.checkTopLevelWindow(window)) {
                return false;
            }
        }
        if (deleg == null) {
            return false;
        }
        return deleg.checkTopLevelWindow(window);
    }

    @Override
    public void checkWrite(FileDescriptor fd) {
        if (filter != null) {
            filter.checkWrite(fd);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkWrite(fd);
    }

    @Override
    public void checkWrite(String file) {
        if (filter != null) {
            filter.checkWrite(file);
        }
        if (deleg == null) {
            return;
        }
        deleg.checkWrite(file);
    }

    private transient volatile Method classDepth;

    @Override
    protected int classDepth(String name) {
        if (deleg == null) {
            return -1;
        }
        if (classDepth == null) {
            synchronized (this) {
                if (classDepth == null) {
                    classDepth = getMethod(delegClazz, "classDepth", String.class);
                }
            }
        }
        return ((Integer) invoke(deleg, classDepth, name)).intValue();
    }

    private transient volatile Method classLoaderDepth;

    @Override
    protected int classLoaderDepth() {
        if (deleg == null) {
            return -1;
        }
        if (classLoaderDepth == null) {
            synchronized (this) {
                if (classLoaderDepth == null) {
                    classLoaderDepth = getMethod(delegClazz, "classLoaderDepth");
                }
            }
        }
        return ((Integer) invoke(deleg, classLoaderDepth)).intValue();
    }

    private transient volatile Method currentClassLoader;

    @Override
    protected ClassLoader currentClassLoader() {
        if (deleg == null) {
            return null;
        }
        if (currentClassLoader == null) {
            synchronized (this) {
                if (currentClassLoader == null) {
                    currentClassLoader = getMethod(delegClazz, "currentClassLoader");
                }
            }
        }
        return (ClassLoader) invoke(deleg, currentClassLoader);
    }

    private transient volatile Method currentLoadedClass;

    @Override
    protected Class<?> currentLoadedClass() {
        if (deleg == null) {
            return null;
        }
        if (currentLoadedClass == null) {
            synchronized (this) {
                if (currentLoadedClass == null) {
                    currentLoadedClass = getMethod(delegClazz, "currentLoadedClass");
                }
            }
        }
        return (Class<?>) invoke(deleg, currentLoadedClass);
    }

    private transient volatile Method getClassContext;

    @Override
    protected Class<?>[] getClassContext() {
        if (deleg == null) {
            return new Class<?>[0];
        }
        if (getClassContext == null) {
            synchronized (this) {
                if (getClassContext == null) {
                    getClassContext = getMethod(delegClazz, "getClassContext");
                }
            }
        }
        return (Class<?>[]) invoke(deleg, getClassContext);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean getInCheck() {
        if (deleg == null) {
            return false;
        }
        return deleg.getInCheck();
    }

    @Override
    public Object getSecurityContext() {
        if (deleg == null) {
            return null;
        }
        return deleg.getSecurityContext();
    }

    @Override
    public ThreadGroup getThreadGroup() {
        if (deleg == null) {
            return null;
        }
        return deleg.getThreadGroup();
    }

    private transient volatile Method inClass;

    @Override
    protected boolean inClass(String name) {
        if (deleg == null) {
            return false;
        }
        if (inClass == null) {
            synchronized (this) {
                if (inClass == null) {
                    inClass = getMethod(delegClazz, "inClass", String.class);
                }
            }
        }
        return ((Boolean) invoke(deleg, inClass, name)).booleanValue();
    }

    private transient volatile Method inClassLoader;

    @Override
    protected boolean inClassLoader() {
        if (deleg == null) {
            return false;
        }
        if (inClassLoader == null) {
            synchronized (this) {
                if (inClassLoader == null) {
                    inClassLoader = getMethod(delegClazz, "inClassLoader");
                }
            }
        }
        return ((Boolean) invoke(deleg, inClassLoader)).booleanValue();
    }

    private static Method getMethod(Class<? extends SecurityManager> clazz, String name,
            Class<?>... paramTypes) {
        try {
            Method method = clazz.getDeclaredMethod(name, paramTypes);
            if (!Modifier.isPublic(method.getModifiers()) && !method.isAccessible()) {
                method.setAccessible(true);
            }
            return method;
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T invoke(SecurityManager deleg, Method method, Object... params) {
        try {
            return (T) method.invoke(deleg, params);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
