package com.brightgenerous.zxing;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.brightgenerous.zxing.javase.BufferedImageDecodeArguments;

public class ZxingUtilsTest {

    @Test
    public void test() throws IOException {

        ZxingBuilder builder = ZxingUtils.builder();

        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        builder.buildEncoder("hoge").encode(bao);

        assertTrue(builder.buildEncoder("hoge").encode() instanceof BufferedImage);

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bao.toByteArray()));
        BufferedImageDecodeArguments arg = new BufferedImageDecodeArguments(image);

        assertNotEquals("foo", builder.buildDecoder().decode(arg));
        assertEquals("hoge", builder.buildDecoder().decode(arg));
    }
}
