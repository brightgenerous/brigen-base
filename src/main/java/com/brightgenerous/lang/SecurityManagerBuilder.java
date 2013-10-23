package com.brightgenerous.lang;

import java.util.ArrayList;
import java.util.List;

public class SecurityManagerBuilder {

    private SecurityManager deleg;

    private volatile List<ISecurityManagerFilter> filters;

    protected SecurityManagerBuilder() {
    }

    protected SecurityManagerBuilder(SecurityManager deleg) {
        this.deleg = deleg;
    }

    public static SecurityManagerBuilder create() {
        return new SecurityManagerBuilder();
    }

    public static SecurityManagerBuilder create(SecurityManager deleg) {
        return new SecurityManagerBuilder(deleg);
    }

    public SecurityManager build() {
        return new SecurityManagerWrapper(deleg, new ListSecurityManagerFilter(filters));
    }

    public SecurityManager get() {
        return deleg;
    }

    public SecurityManagerBuilder set(SecurityManager deleg) {
        this.deleg = deleg;
        return this;
    }

    public SecurityManagerBuilder add(ISecurityManagerFilter... fs) {
        if ((fs != null) && (0 < fs.length)) {
            for (ISecurityManagerFilter filter : fs) {
                if (filter != null) {
                    getFilters().add(filter);
                }
            }
        }
        return this;
    }

    public SecurityManagerBuilder addNoExit() {
        return add(new NoExitSecurityManagerFilter());
    }

    public SecurityManagerBuilder remove(ISecurityManagerFilter... fs) {
        if ((fs != null) && (0 < fs.length)) {
            for (ISecurityManagerFilter filter : fs) {
                if (filter != null) {
                    getFilters().remove(filter);
                }
            }
        }
        return this;
    }

    public SecurityManagerBuilder clear() {
        if (filters != null) {
            filters.clear();
        }
        return this;
    }

    protected List<ISecurityManagerFilter> getFilters() {
        if (filters == null) {
            synchronized (this) {
                if (filters == null) {
                    filters = new ArrayList<>();
                }
            }
        }
        return filters;
    }
}
