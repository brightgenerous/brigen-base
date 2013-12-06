package com.brightgenerous.cglib.delegate;

interface CglibDelegater {

    Class<?> defineInterface(String name, Class<?>... supers);
}
