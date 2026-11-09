package com.luna.encryptor;

public class Cipher extends CryptographicAlgorithm {
    protected String key;
    protected boolean decrypt;

    public void setDecrypt(boolean decrypt) { this.decrypt = decrypt; }

    public void setKey(String key) { this.key = key; }
}