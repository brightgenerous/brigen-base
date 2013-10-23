package com.brightgenerous.datasource;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Injector;

public class CompositeInitializer implements Initializer {

    private final List<Initializer> delegs;

    public CompositeInitializer(List<Initializer> delegs) {
        this.delegs = new ArrayList<>();
        add(delegs);
    }

    public CompositeInitializer(Initializer... delegs) {
        this.delegs = new ArrayList<>();
        add(delegs);
    }

    public CompositeInitializer add(List<Initializer> delegs) {
        if ((delegs != null) && !delegs.isEmpty()) {
            for (Initializer d : delegs) {
                if (d != null) {
                    this.delegs.add(d);
                }
            }
        }
        return this;
    }

    public CompositeInitializer add(Initializer[] delegs) {
        if ((delegs != null) && (0 < delegs.length)) {
            List<Initializer> ds = new ArrayList<>(delegs.length);
            for (Initializer d : delegs) {
                ds.add(d);
            }
            add(ds);
        }
        return this;
    }

    public CompositeInitializer add(Initializer deleg, Initializer... delegs) {
        List<Initializer> ds = new ArrayList<>(((delegs == null) ? 0 : delegs.length) + 1);
        ds.add(deleg);
        if ((delegs != null) && (0 < delegs.length)) {
            for (Initializer d : delegs) {
                ds.add(d);
            }
        }
        return add(ds);
    }

    @Override
    public void initialize(Injector injector) {
        for (Initializer deleg : delegs) {
            deleg.initialize(injector);
        }
    }
}
