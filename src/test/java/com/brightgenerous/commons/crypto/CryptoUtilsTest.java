package com.brightgenerous.commons.crypto;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;

import com.brightgenerous.commons.ObjectUtils;

public class CryptoUtilsTest {

    @Test
    public void encryptDecrypt() throws Exception {
        Set<String> cache = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            String keyStr = UUID.randomUUID().toString(); // enough length required
            byte[] key = keyStr.getBytes();
            for (int j = 0; j < 5; j++) {
                String plainStr = UUID.randomUUID().toString();
                byte[] plainBytes = plainStr.getBytes();
                for (CryptoAlgorithm algorithm : CryptoAlgorithm.values()) {
                    {
                        CryptoUtils cu = CryptoUtils.get(algorithm, key);
                        byte[] encrypted = cu.encrypt(plainBytes);

                        String str = ObjectUtils.convertBytesToHexString(encrypted);
                        assertFalse(cache.contains(str));
                        cache.add(str);

                        assertArrayEquals(encrypted,
                                CryptoUtils.encrypt(algorithm, key, plainBytes));

                        String failStr = null;
                        try {
                            failStr = new String(encrypted);
                        } catch (Exception e) {
                        }

                        // maybe, same...
                        assertNotSame(plainStr, failStr);

                        byte[] decrypted = cu.decrypt(encrypted);

                        assertArrayEquals(decrypted, CryptoUtils.decrypt(algorithm, key, encrypted));
                        assertEquals(plainStr, new String(decrypted));
                    }
                    for (HashAlgorithm keyAlgorithm : HashAlgorithm.values()) {
                        CryptoUtils cu = CryptoUtils.get(algorithm, keyAlgorithm, key);
                        byte[] encrypted = cu.encrypt(plainBytes);

                        String str = ObjectUtils.convertBytesToHexString(encrypted);
                        assertFalse(cache.contains(str));
                        cache.add(str);

                        assertArrayEquals(encrypted,
                                CryptoUtils.encrypt(algorithm, keyAlgorithm, key, plainBytes));

                        String failStr = null;
                        try {
                            failStr = new String(encrypted);
                        } catch (Exception e) {
                        }

                        // maybe, same...
                        assertNotSame(plainStr, failStr);

                        byte[] decrypted = cu.decrypt(encrypted);

                        assertArrayEquals(decrypted,
                                CryptoUtils.decrypt(algorithm, keyAlgorithm, key, encrypted));
                        assertEquals(plainStr, new String(decrypted));
                    }
                }
            }
        }
    }
}
