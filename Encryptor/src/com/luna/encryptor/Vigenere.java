package com.luna.encryptor;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Vigenere extends Cipher {
    public Vigenere(String key, boolean decrypt){
        this.type = AlgorithmType.VIGENERE;
        this.decrypt = decrypt;
        this.key = key;
    }

    public Vigenere() { this.type = AlgorithmType.VIGENERE; }

    @Override
    public void Encrypt() {
        byte[] key_values = key.getBytes(StandardCharsets.UTF_8);
        ByteBuffer message = cloneByteBuffer(this.getIntroitus());;

        for(int i = 0; i < message.capacity(); i++){
            message.put(i, (this.decrypt) ?
                    (byte) ((message.get(i) - key_values[i % key_values.length]) % 256) :
                    (byte) ((message.get(i) + key_values[i % key_values.length]) % 256)
            );
        }

        setEventum(message);
    }
}
