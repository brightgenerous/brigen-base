package com.brightgenerous.injection;

import com.brightgenerous.lang.Args;

public class PackageImplResolver implements ImplResolver {

    private static final long serialVersionUID = 3749055882136431301L;

    private final Class<?> clazz;

    private volatile boolean loaded;

    private volatile String pkgName;

    //
    // ( ',_>`).oO( it is recommended that to use this, instead of #PackageGenerateImplResolver(Package).
    //
    public PackageImplResolver(Class<?> clazz) {
        Args.notNull(clazz, "clazz");

        this.clazz = clazz;
    }

    //
    // ( ',_>`).oO( it would be troublesome that should prepare the Package instance..., Surely not ?
    //
    @Deprecated
    public PackageImplResolver(Package pkg) {
        Args.notNull(pkg, "pkg");

        clazz = null;
        pkgName = getPackageName(pkg);
        loaded = true;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        String pkgName = getPackageName();
        if (pkgName == null) {
            return null;
        }
        String pName = getPackageName(clazz);
        if (pName == null) {
            return null;
        }
        try {
            return (Class<? extends T>) Class.forName(clazz.getName().replace(pName, pkgName));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getPackageName() {
        if (!loaded) {
            synchronized (this) {
                if (!loaded) {
                    pkgName = getPackageName(clazz);
                    loaded = true;
                }
            }
        }
        return pkgName;
    }

    protected String getPackageName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return getPackageName(clazz.getPackage());
    }

    protected String getPackageName(Package pkg) {
        if (pkg == null) {
            return null;
        }
        return pkg.getName();
    }
}
