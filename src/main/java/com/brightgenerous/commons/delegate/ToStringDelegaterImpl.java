package com.brightgenerous.commons.delegate;

import org.apache.commons.lang3.builder.ToStringBuilder;

class ToStringDelegaterImpl implements ToStringDelegater {

    {
        check();
    }

    private static void check() {
        try {
            Class.forName(ToStringBuilder.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String reflectionToString(Object obj) {
        if (obj == null) {
            return null;
        }
        return ToStringBuilder.reflectionToString(obj);
    }
}
