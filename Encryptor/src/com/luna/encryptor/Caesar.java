package com.luna.encryptor;

import java.util.Arrays;

public class Caesar extends EncryptionAlgorithm{
    private boolean decrypt;
    private int key;

    public Caesar(int key, boolean decrypt){
        this.type = AlgorithmType.CAESAR;
        this.setDecrypt(decrypt);
        this.setKey(key);
    }

    public Caesar() { this.type = AlgorithmType.CAESAR; }

    @Override
    public void Encrypt() {
        byte[] message = Arrays.copyOf(this.getBuffer(), this.getBuffer().length);

        for(int i = 0; i < message.length; i++){
            message[i] = (this.decrypt) ?
                    (byte) ((message[i] - this.key) % 256) :
                    (byte) ((message[i] + this.key) % 256);
        }

        setEventum(message);
    }

    @Override
    public void setDecrypt(boolean decrypt) {
        this.decrypt = decrypt;
    }

    @Override
    public void setKey(int key) {
        this.key = key;
    }
}
