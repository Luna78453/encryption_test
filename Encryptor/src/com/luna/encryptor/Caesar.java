package com.luna.encryptor;

import java.io.IOException;
import java.util.Optional;

public class Caesar extends EncryptionAlgorithm{
    private final boolean decrypt;
    private final int key;

    public Caesar(int key, boolean decrypt){
        this.name = "com.luna.encryptor.Caesar";
        this.decrypt = decrypt;
        this.key = key;
    }

    @Override
    public void Encrypt(String entrata, Optional<String> uscita) throws IOException {
        readFileEntrata(entrata);
        byte[] message = buffer;

        for(int i = 0; i < message.length; i++){
            message[i] = (decrypt) ?
                    (byte) ((message[i] - key) % 256) :
                    (byte) ((message[i] + key) % 256);
        }

        makeFileUscita(message, uscita);
    }
}
