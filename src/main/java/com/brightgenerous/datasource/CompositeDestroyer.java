package com.brightgenerous.datasource;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Injector;

public class CompositeDestroyer implements Destroyer {

    private final List<Destroyer> delegs;

    public CompositeDestroyer(List<Destroyer> delegs) {
        this.delegs = new ArrayList<>();
        add(delegs);
    }

    public CompositeDestroyer(Destroyer... delegs) {
        this.delegs = new ArrayList<>();
        add(delegs);
    }

    public CompositeDestroyer add(List<Destroyer> delegs) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (Destroyer d : delegs) {
                if (d != null) {
                    this.delegs.add(d);
                }
            }
        }
        return this;
    }

    public CompositeDestroyer add(Destroyer[] delegs) {
        if ((delegs != null) && (0 < delegs.length)) {
            List<Destroyer> ds = new ArrayList<>(delegs.length);
            for (Destroyer d : delegs) {
                ds.add(d);
            }
            add(ds);
        }
        return this;
    }

    public CompositeDestroyer add(Destroyer deleg, Destroyer... delegs) {
        List<Destroyer> ds = new ArrayList<>(((delegs == null) ? 0 : delegs.length) + 1);
        ds.add(deleg);
        if ((delegs != null) && (0 < delegs.length)) {
            for (Destroyer d : delegs) {
                ds.add(d);
            }
        }
        return add(ds);
    }

    @Override
    public void destroy(Injector injector) {
        for (Destroyer deleg : delegs) {
            deleg.destroy(injector);
        }
    }
}
