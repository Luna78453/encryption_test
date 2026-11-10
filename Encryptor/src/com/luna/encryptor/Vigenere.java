package com.luna.encryptor;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

        /*ByteBuffer message = cloneByteBuffer(this.getIntroitus());;

        for(int i = 0; i < message.capacity(); i++){
            message.put(i, (this.decrypt) ?
                    (byte) ((message.get(i) - key_values[i % key_values.length]) % 256) :
                    (byte) ((message.get(i) + key_values[i % key_values.length]) % 256)
            );
        }*/

        if(isBytewise()) {
            byte[] keyValues = key.getBytes(StandardCharsets.UTF_8);
            ByteBuffer message = cloneByteBuffer(this.getIntroitus());
            bytewiseShift(message, keyValues);
            setEventum(message);
        }
        else {
            ArrayList<Integer> keyValues = new ArrayList<>(0);
            for(int i = 0; i < key.length(); i++){
                if(Character.isLetter(key.charAt(i)))
                    keyValues.add((Character.isUpperCase(key.charAt(i))) ?
                            key.charAt(i) - 65 :
                            key.charAt(i) - 97
                            );
            }
            CharBuffer message = StandardCharsets.ISO_8859_1.decode(this.getIntroitus());
            bytewiseShift(message, keyValues.toArray(new int[0]));
            setEventum(StandardCharsets.UTF_8.encode(message));

        }
    }

    private void bytewiseShift(ByteBuffer message, byte[] keyValues){
        for(int i = 0; i < message.capacity(); i++){
            message.put(i, (this.decrypt) ?
                    (byte) ((message.get(i) - keyValues[i % keyValues.length]) % 256) :
                    (byte) ((message.get(i) + keyValues[i % keyValues.length]) % 256)
            );
        }
    }

    private void alphabetShift(CharBuffer message, int[] keyValues){
        for(int i = 0; i < message.capacity(); i++){
            if (Character.isLetter(message.get(i))){
                message.put(i, (Character.isUpperCase(message.get(i))) ?
                        (this.decrypt) ?
                                (char) (
                                        ((message.get(i) - 65)
                                                - keyValues[i % keyValues.length])%26 + 65
                                ):
                                (char) (
                                        ((message.get(i) - 65)
                                                + keyValues[i % keyValues.length])%26 + 65
                                )
                        : (this.decrypt) ?
                        (char) (
                                ((message.get(i) - 97)
                                        - keyValues[i % keyValues.length])%26 + 97
                        ):
                        (char) (
                                ((message.get(i) - 97)
                                        + keyValues[i % keyValues.length])%26 + 97
                        )
                );
            }
        }
    }
}
