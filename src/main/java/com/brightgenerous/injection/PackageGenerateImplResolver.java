package com.brightgenerous.injection;

import com.brightgenerous.lang.Args;

public class PackageGenerateImplResolver extends GenerateImplResolver {

    private static final long serialVersionUID = -6631275058658970787L;

    private final Class<?> clazz;

    private volatile boolean loaded;

    private volatile String pkgName;

    //
    // ( ',_>`).oO( it is recommended that to use this, instead of #PackageGenerateImplResolver(Package).
    //
    public PackageGenerateImplResolver(Class<?> clazz) {
        Args.notNull(clazz, "clazz");

        this.clazz = clazz;
    }

    //
    // ( ',_>`).oO( it would be troublesome that should prepare the Package instance..., Surely not ?
    //
    @Deprecated
    public PackageGenerateImplResolver(Package pkg) {
        Args.notNull(pkg, "pkg");

        clazz = null;
        pkgName = getPackageName(pkg);
        loaded = true;
    }

    @Override
    protected String getImplName(Class<?> clazz) {
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
        return clazz.getName().replace(pName, pkgName);
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
