package com.brightgenerous.datasource;

import com.google.inject.Injector;

public interface InjectorFactory {

    Injector getInjector();

    Injector getInjector(boolean rollbackOnly);

    void initialize();

    void verify();

    void destroy();
}
