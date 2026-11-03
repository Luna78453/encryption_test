package com.luna.encryptor;

import java.nio.charset.StandardCharsets;

public class Vigenere extends EncryptionAlgorithm{
    private boolean decrypt;
    private String key;

    public Vigenere(String key, boolean decrypt){
        this.name = "Vigenere";
        this.decrypt = decrypt;
        this.key = key;
    }

    public Vigenere() { this.name = "Vigenere"; }

    @Override
    public void Encrypt() {
        byte[] key_values = key.getBytes(StandardCharsets.UTF_8);
        byte[] message = getBuffer();

        for(int i = 0; i < message.length; i++){
            message[i] = (decrypt) ?
                    (byte) ((message[i] - key_values[i % key_values.length]) % 256) :
                    (byte) ((message[i] + key_values[i % key_values.length]) % 256);
        }

        setEventum(message);
    }
}
