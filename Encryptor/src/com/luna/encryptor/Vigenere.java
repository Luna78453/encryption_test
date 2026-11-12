package com.luna.encryptor;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Vigenere extends Cipher {
    public Vigenere(String key, boolean decrypt){
        this.type = AlgorithmType.VIGENERE;
        this.decrypt = decrypt;
        this.key = key;
    }

    public Vigenere() { this.type = AlgorithmType.VIGENERE; }

    @Override
    public void Encrypt() {

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
            CharBuffer message = StandardCharsets.UTF_8.decode(this.getIntroitus());
            alphabetShift(message, keyValues.toArray(new Integer[0]));
            setEventum(StandardCharsets.UTF_8.encode(message));

        }
    }

    private void bytewiseShift(ByteBuffer message, byte[] keyValues){
        for(int i = 0; i < message.limit(); i++){
            message.put(i, (this.decrypt) ?
                    (byte) (Math.floorMod((message.get(i)
                            - Math.floorMod(keyValues[i % keyValues.length],256)),256)) :
                    (byte) (Math.floorMod((message.get(i)
                            + Math.floorMod(keyValues[i % keyValues.length],256)),256))
            );
        }
        message.rewind();
    }

    private void alphabetShift(CharBuffer message, Integer[] keyValues){
        for(int i = 0; i < message.limit(); i++){
            if (Character.isLetter(message.get(i))){
                message.put(i, (Character.isUpperCase(message.get(i))) ?
                        (this.decrypt) ?
                                (char) (
                                        Math.floorMod(((message.get(i) - 65)
                                        - Math.floorMod(keyValues[i % keyValues.length],26)),26) + 65)
                                : (char) (
                                        Math.floorMod(((message.get(i) - 65)
                                        + Math.floorMod(keyValues[i % keyValues.length],26)),26) + 65)
                        : (this.decrypt) ?
                                (char) (
                                        Math.floorMod(((message.get(i) - 97)
                                        - Math.floorMod(keyValues[i % keyValues.length],26)),26) + 97)
                                : (char) (
                                        Math.floorMod(((message.get(i) - 97)
                                        + Math.floorMod(keyValues[i % keyValues.length],26)),26) + 97)
                );
            }
        }
        message.rewind();
    }
}
