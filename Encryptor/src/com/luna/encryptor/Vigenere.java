package com.luna.encryptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Vigenere extends EncryptionAlgorithm{
    private final boolean decrypt;
    private final String key;

    public Vigenere(String key, boolean decrypt){
        this.name = "com.luna.encryptor.Vigenere";
        this.decrypt = decrypt;
        this.key = key;
    }

    @Override
    public void Encrypt(String entrata, Optional<String> uscita) throws IOException {
        byte[] key_values = key.getBytes(StandardCharsets.UTF_8);
        readFileEntrata(entrata);
        byte[] message = buffer;

        for(int i = 0; i < message.length; i++){
            message[i] = (decrypt) ?
                    (byte) ((message[i] - key_values[i % key_values.length]) % 256) :
                    (byte) ((message[i] + key_values[i % key_values.length]) % 256);
        }

        makeFileUscita(message, uscita);
    }
}
