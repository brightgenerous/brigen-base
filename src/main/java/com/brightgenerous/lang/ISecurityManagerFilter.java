package com.brightgenerous.lang;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

public interface ISecurityManagerFilter {

    void checkAccept(String host, int port);

    void checkAccess(Thread t);

    void checkAccess(ThreadGroup t);

    void checkAwtEventQueueAccess();

    void checkConnect(String host, int port);

    void checkConnect(String host, int port, Object context);

    void checkCreateClassLoader();

    void checkDelete(String file);

    void checkExec(String cmd);

    void checkExit(int status);

    void checkLink(String lib);

    void checkListen(int port);

    void checkMemberAccess(Class<?> clazz, int which);

    void checkMulticast(InetAddress maddr);

    void checkMulticast(InetAddress maddr, byte ttl);

    void checkPackageAccess(String pkg);

    void checkPackageDefinition(String pkg);

    void checkPermission(Permission perm);

    void checkPermission(Permission perm, Object context);

    void checkPrintJobAccess();

    void checkPropertiesAccess();

    void checkPropertyAccess(String key);

    void checkRead(FileDescriptor fd);

    void checkRead(String file);

    void checkRead(String file, Object context);

    void checkSecurityAccess(String target);

    void checkSetFactory();

    void checkSystemClipboardAccess();

    boolean checkTopLevelWindow(Object window);

    void checkWrite(FileDescriptor fd);

    void checkWrite(String file);
}
