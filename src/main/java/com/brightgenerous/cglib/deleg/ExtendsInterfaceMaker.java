package com.brightgenerous.cglib.deleg;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.cglib.asm.ClassVisitor;
import net.sf.cglib.asm.Type;
import net.sf.cglib.core.ClassEmitter;
import net.sf.cglib.core.Constants;
import net.sf.cglib.core.Signature;
import net.sf.cglib.core.TypeUtils;
import net.sf.cglib.proxy.InterfaceMaker;

import com.brightgenerous.lang.Args;

class ExtendsInterfaceMaker extends InterfaceMaker {

    private Set<Class<?>> interfaces = new LinkedHashSet<>();

    private Map<Signature, Type[]> signatures = new HashMap<>();

    @Override
    public void add(@SuppressWarnings("rawtypes") Class clazz) {
        Args.notNull(clazz, "clazz");
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException(String.format("Argument '%s' is not interface.",
                    clazz));
        }

        interfaces.add(clazz);
    }

    @Override
    public void add(Signature sig, Type[] exceptions) {
        super.add(sig, exceptions);

        signatures.put(sig, exceptions);
    }

    @Override
    public void generateClass(ClassVisitor v) throws Exception {
        ClassEmitter ce = new ClassEmitter(v);
        ce.begin_class(Constants.V1_2, Constants.ACC_PUBLIC | Constants.ACC_INTERFACE,
                getClassName(), null,
                TypeUtils.getTypes(interfaces.toArray(new Class[interfaces.size()])),
                Constants.SOURCE_FILE);
        for (Entry<Signature, Type[]> e : signatures.entrySet()) {
            Signature sig = e.getKey();
            Type[] exceptions = e.getValue();
            ce.begin_method(Constants.ACC_PUBLIC | Constants.ACC_ABSTRACT, sig, exceptions)
                    .end_method();
        }
        ce.end_class();
    }
}
