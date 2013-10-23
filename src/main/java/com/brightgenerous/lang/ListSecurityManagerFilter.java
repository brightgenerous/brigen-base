package com.brightgenerous.lang;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ListSecurityManagerFilter implements ISecurityManagerFilter {

    private final List<ISecurityManagerFilter> delegs;

    public ListSecurityManagerFilter(ISecurityManagerFilter... delegs) {
        this((delegs == null) ? Collections.EMPTY_LIST : Arrays.asList(delegs));
    }

    public ListSecurityManagerFilter(List<ISecurityManagerFilter> delegs) {
        this.delegs = delegs;
    }

    @Override
    public void checkAccept(String host, int port) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkAccept(host, port);
                }
            }
        }
    }

    @Override
    public void checkAccess(Thread t) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkAccess(t);
                }
            }
        }
    }

    @Override
    public void checkAccess(ThreadGroup t) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkAccess(t);
                }
            }
        }
    }

    @Override
    public void checkAwtEventQueueAccess() {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkAwtEventQueueAccess();
                }
            }
        }
    }

    @Override
    public void checkConnect(String host, int port) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkConnect(host, port);
                }
            }
        }
    }

    @Override
    public void checkConnect(String host, int port, Object context) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkConnect(host, port, context);
                }
            }
        }
    }

    @Override
    public void checkCreateClassLoader() {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkCreateClassLoader();
                }
            }
        }
    }

    @Override
    public void checkDelete(String file) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkDelete(file);
                }
            }
        }
    }

    @Override
    public void checkExec(String cmd) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkExec(cmd);
                }
            }
        }
    }

    @Override
    public void checkExit(int status) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkExit(status);
                }
            }
        }
    }

    @Override
    public void checkLink(String lib) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkLink(lib);
                }
            }
        }
    }

    @Override
    public void checkListen(int port) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkListen(port);
                }
            }
        }
    }

    @Override
    public void checkMemberAccess(Class<?> clazz, int which) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkMemberAccess(clazz, which);
                }
            }
        }
    }

    @Override
    public void checkMulticast(InetAddress maddr) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkMulticast(maddr);
                }
            }
        }
    }

    @Override
    public void checkMulticast(InetAddress maddr, byte ttl) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkMulticast(maddr, ttl);
                }
            }
        }
    }

    @Override
    public void checkPackageAccess(String pkg) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkPackageAccess(pkg);
                }
            }
        }
    }

    @Override
    public void checkPackageDefinition(String pkg) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkPackageDefinition(pkg);
                }
            }
        }
    }

    @Override
    public void checkPermission(Permission perm) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkPermission(perm);
                }
            }
        }
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkPermission(perm, context);
                }
            }
        }
    }

    @Override
    public void checkPrintJobAccess() {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkPrintJobAccess();
                }
            }
        }
    }

    @Override
    public void checkPropertiesAccess() {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkPropertiesAccess();
                }
            }
        }
    }

    @Override
    public void checkPropertyAccess(String key) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkPropertyAccess(key);
                }
            }
        }
    }

    @Override
    public void checkRead(FileDescriptor fd) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkRead(fd);
                }
            }
        }
    }

    @Override
    public void checkRead(String file) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkRead(file);
                }
            }
        }
    }

    @Override
    public void checkRead(String file, Object context) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkRead(file, context);
                }
            }
        }
    }

    @Override
    public void checkSecurityAccess(String target) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkSecurityAccess(target);
                }
            }
        }
    }

    @Override
    public void checkSetFactory() {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkSetFactory();
                }
            }
        }
    }

    @Override
    public void checkSystemClipboardAccess() {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkSystemClipboardAccess();
                }
            }
        }
    }

    @Override
    public boolean checkTopLevelWindow(Object window) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    if (!deleg.checkTopLevelWindow(window)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void checkWrite(FileDescriptor fd) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkWrite(fd);
                }
            }
        }
    }

    @Override
    public void checkWrite(String file) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (ISecurityManagerFilter deleg : delegs) {
                if (deleg != null) {
                    deleg.checkWrite(file);
                }
            }
        }
    }
}
