package com.luna.encryptor;

import java.nio.ByteBuffer;

public class Caesar extends Cipher {
    public Caesar(String key, boolean decrypt){
        this.type = AlgorithmType.CAESAR;
        this.decrypt = decrypt;
        this.key = key;
    }

    public Caesar() { this.type = AlgorithmType.CAESAR; }

    @Override
    public void Encrypt() {
        ByteBuffer message = cloneByteBuffer(this.getIntroitus());

        for(int i = 0; i < message.capacity(); i++){
            message.put(i, (this.decrypt) ?
                    (byte) ((message.get(i) - Integer.parseInt(this.key)) % 256) :
                    (byte) ((message.get(i) + Integer.parseInt(this.key)) % 256)
            );
        }

        setEventum(message);
    }
}
