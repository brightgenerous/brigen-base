package com.brightgenerous.cglib.deleg;

interface CglibDelegater {

    Class<?> defineInterface(String name, Class<?>... supers);
}
