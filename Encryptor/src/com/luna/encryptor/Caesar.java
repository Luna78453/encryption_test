package com.luna.encryptor;

public class Caesar extends EncryptionAlgorithm{
    private boolean decrypt;
    private int key;

    public Caesar(int key, boolean decrypt){
        this.name = "Caesar";
        this.setDecrypt(decrypt);
        this.setKey(key);
    }

    public Caesar() { this.name = "Caesar"; }

    @Override
    public void Encrypt() {
        byte[] message = getBuffer();

        for(int i = 0; i < message.length; i++){
            message[i] = (decrypt) ?
                    (byte) ((message[i] - key) % 256) :
                    (byte) ((message[i] + key) % 256);
        }

        setEventum(message);
    }

    public void setDecrypt(boolean decrypt) {
        this.decrypt = decrypt;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
