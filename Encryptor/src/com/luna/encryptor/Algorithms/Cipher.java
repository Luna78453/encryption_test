package com.luna.encryptor.Algorithms;

public class Cipher extends CryptographicAlgorithm {
    protected boolean decrypt;

    public void setDecrypt(boolean decrypt) { this.decrypt = decrypt; }

    public static class SingleKeyCipher extends Cipher{
        protected String key;

        public void setKey(String key) {
            this.key = key;
        }
    }
}