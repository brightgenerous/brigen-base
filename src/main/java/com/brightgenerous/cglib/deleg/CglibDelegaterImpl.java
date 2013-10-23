package com.brightgenerous.cglib.deleg;

import static com.brightgenerous.commons.ObjectUtils.*;
import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.core.Predicate;
import net.sf.cglib.proxy.InterfaceMaker;

class CglibDelegaterImpl implements CglibDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(NamingPolicy.class.getName());
            Class.forName(Predicate.class.getName());
            Class.forName(InterfaceMaker.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<?> defineInterface(final String name, Class<?>... supers) {
        ExtendsInterfaceMaker maker = new ExtendsInterfaceMaker();
        {
            final NamingPolicy np = maker.getNamingPolicy();
            maker.setNamingPolicy(new NamingPolicy() {

                @Override
                public String getClassName(String prefix, String source, Object key, Predicate names) {
                    if (!source.equals(InterfaceMaker.class.getName())) {
                        return np.getClassName(prefix, source, key, names);
                    }
                    return name;
                }
            });
        }
        if (isNotNoSize(supers)) {
            for (Class<?> sc : supers) {
                maker.add(sc);
            }
        }
        return maker.create();
    }
}
