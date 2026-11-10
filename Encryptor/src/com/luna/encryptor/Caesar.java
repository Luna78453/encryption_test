package com.luna.encryptor;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

public class Caesar extends Cipher {
    public Caesar(String key, boolean decrypt){
        this.type = AlgorithmType.CAESAR;
        this.decrypt = decrypt;
        this.key = key;
    }

    public Caesar() { this.type = AlgorithmType.CAESAR; }

    @Override
    public void Encrypt() {
        if(isBytewise()) {
            ByteBuffer message = cloneByteBuffer(this.getIntroitus());
            bytewiseShift(message);
            setEventum(message);
        }
        else {
            CharBuffer message = StandardCharsets.ISO_8859_1.decode(this.getIntroitus());
            alphabetShift(message);
            setEventum(StandardCharsets.UTF_8.encode(message));
        }
    }

    private void bytewiseShift(ByteBuffer message){
        for(int i = 0; i < message.capacity(); i++){
            message.put(i, (this.decrypt) ?
                    (byte) ((message.get(i) - Integer.parseInt(this.key)) % 256) :
                    (byte) ((message.get(i) + Integer.parseInt(this.key)) % 256)
            );
        }
    }

    private void alphabetShift(CharBuffer message){
        for(int i = 0; i < message.capacity(); i++){
            if (Character.isLetter(message.get(i))){
                message.put(i, (Character.isUpperCase(message.get(i))) ?
                        (this.decrypt) ?
                                (char) (
                                        ((message.get(i) - 65)
                                        - Integer.parseInt(this.key))%26 + 65
                                ):
                                (char) (
                                        ((message.get(i) - 65)
                                        + Integer.parseInt(this.key))%26 + 65
                                )
                        : (this.decrypt) ?
                                (char) (
                                        ((message.get(i) - 97)
                                        - Integer.parseInt(this.key))%26 + 97
                                ):
                                (char) (
                                        ((message.get(i) - 97)
                                        + Integer.parseInt(this.key))%26 + 97
                                )
                );
            }
        }
    }
}
