package com.brightgenerous.compress.zip;

import java.io.File;

import org.junit.Test;

public class ZipUtilsTest {

    @Test
    public void test() throws ZipException {

        File zip = new File("C:/Users/master/Desktop/ZipUtilsTest.zip");

        ZipUtils.addFile(zip, new File("C:/Users/master/Desktop/11922299_907749692_normal.png"),
                "hoge");
        ZipUtils.addFolder(zip, new File("C:/Users/master/Desktop/tmp"), "hoge2");
        ZipUtils.addFolder(zip, new File("C:/Users/master/Desktop/tmp"), "hoge2");

        ZipUtils.remove(zip, "tmp/11922299_907749692_normal.png");
    }
}
