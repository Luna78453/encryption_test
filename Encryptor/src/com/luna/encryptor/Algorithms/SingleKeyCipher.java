package com.luna.encryptor.Algorithms;

public class SingleKeyCipher extends Cipher{
    protected String key;

    public void setKey(String key) {
        this.key = key;
    }
}
