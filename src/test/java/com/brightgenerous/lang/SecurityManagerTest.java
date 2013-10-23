package com.brightgenerous.lang;

import static org.junit.Assert.*;

import java.io.IOException;

public class SecurityManagerTest {

    //@Test
    public void test() {
        System.setSecurityManager(SecurityManagerBuilder.create(System.getSecurityManager())
                .add(new SimpleSecurityManagerFilter() {

                    @Override
                    public void checkExit(int status) {
                        throw new SecurityException(String.valueOf(status));
                    }
                }).build());

        try {
            System.exit(100);
            fail();
        } catch (SecurityException e) {
            assertEquals(String.valueOf(100), e.getMessage());
        }
    }

    //@Test
    public void exit() throws IOException, InterruptedException {
        String home = System.getProperty("java.home");
        String cp = System.getProperty("java.class.path");
        int code = new ProcessBuilder(home + "/bin/java", "-classpath", cp, getClass().getName())
                .start().waitFor();

        assertEquals(123, code);
    }

    public static void main(String[] args) {
        System.setSecurityManager(SecurityManagerBuilder.create(System.getSecurityManager())
                .build());
        System.exit(123);
    }
}
