package com.luna.encryptor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Vigenere extends EncryptionAlgorithm{
    private boolean decrypt;
    private String key;

    public Vigenere(String key, boolean decrypt){
        this.type = AlgorithmType.VIGENERE;
        this.decrypt = decrypt;
        this.key = key;
    }

    public Vigenere() { this.type = AlgorithmType.VIGENERE; }

    @Override
    public void Encrypt() {
        byte[] key_values = key.getBytes(StandardCharsets.UTF_8);
        byte[] message = Arrays.copyOf(this.getBuffer(), this.getBuffer().length);

        for(int i = 0; i < message.length; i++){
            message[i] = (decrypt) ?
                    (byte) ((message[i] - key_values[i % key_values.length]) % 256) :
                    (byte) ((message[i] + key_values[i % key_values.length]) % 256);
        }

        setEventum(message);
    }

    @Override
    public void setDecrypt(boolean decrypt) { this.decrypt = decrypt; }

    @Override
    public void setKey(String key) { this.key = key; }
}
