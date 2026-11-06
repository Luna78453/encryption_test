package com.luna.encryptor;

public class SubCipher extends Cipher {
    protected String key;
    protected boolean decrypt;

    public void setDecrypt(boolean decrypt) { this.decrypt = decrypt; }

    public void setKey(String key) { this.key = key; }
}
