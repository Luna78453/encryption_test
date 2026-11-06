package com.luna.encryptor;

import java.util.Arrays;

public class Caesar extends SubCipher {
    private int intKey;

    public Caesar(String key, boolean decrypt){
        this.type = AlgorithmType.CAESAR;
        this.decrypt = decrypt;
        this.key = key;
    }

    public Caesar() { this.type = AlgorithmType.CAESAR; }

    @Override
    public void Encrypt() {
        byte[] message = Arrays.copyOf(this.getBuffer(), this.getBuffer().length);

        for(int i = 0; i < message.length; i++){
            message[i] = (this.decrypt) ?
                    (byte) ((message[i] - Integer.parseInt(this.key)) % 256) :
                    (byte) ((message[i] + Integer.parseInt(this.key)) % 256);
        }

        setEventum(message);
    }
}
