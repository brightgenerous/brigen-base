package com.brightgenerous.lang;

public class NoExitSecurityManagerFilter extends SimpleSecurityManagerFilter {

    @Override
    public void checkExit(int status) {
        throw new SecurityException("Exit is not supported.");
    }
}
