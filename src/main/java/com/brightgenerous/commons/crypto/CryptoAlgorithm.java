package com.brightgenerous.commons.crypto;

public enum CryptoAlgorithm {

    AES("AES"), DES("DES"), DESEDE("DESEDE");

    private final String value;

    CryptoAlgorithm(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
