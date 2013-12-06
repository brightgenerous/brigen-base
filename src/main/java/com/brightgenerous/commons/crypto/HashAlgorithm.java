package com.brightgenerous.commons.crypto;

public enum HashAlgorithm {

    // md2    creates 128bit(16byte) length hash.
    // md5    creates 128bit(16byte) length hash.
    // sha    creates 160bit(20byte) length hash.
    // sha256 creates 256bit(32byte) length hash.
    // sha384 creates 384bit(48byte) length hash.
    // sha512 creates 512bit(64byte) length hash.
    MD2("MD2"),
    MD5("MD5"),
    SHA("SHA"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512");

    private final String value;

    HashAlgorithm(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
