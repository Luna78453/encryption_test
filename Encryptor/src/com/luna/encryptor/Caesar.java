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
        this.getIntroitus().rewind();

        if(isBytewise()) {
            ByteBuffer message = cloneByteBuffer(this.getIntroitus());
            bytewiseShift(message);
            setEventum(message);
        }
        else {
            CharBuffer message = StandardCharsets.UTF_8.decode(this.getIntroitus());
            alphabetShift(message);
            setEventum(StandardCharsets.UTF_8.encode(message));
        }
    }

    private void bytewiseShift(ByteBuffer message){
        for(int i = 0; i < message.limit(); i++){
            message.put(i, (this.decrypt) ?
                    (byte) (Math.floorMod((message.get(i)
                            - Math.floorMod(Integer.parseInt(this.key),256)),256)) :
                    (byte) (Math.floorMod((message.get(i)
                            + Math.floorMod(Integer.parseInt(this.key),256)),256))
            );
        }
        message.rewind();
    }

    private void alphabetShift(CharBuffer message){
        for(int i = 0; i < message.limit(); i++){
            if (Character.isLetter(message.get(i))){
                message.put(i, (Character.isUpperCase(message.get(i))) ?
                        (this.decrypt) ?
                                (char) (
                                        Math.floorMod(((message.get(i) - 65)
                                        - Math.floorMod(Integer.parseInt(this.key),26)),26) + 65)
                                : (char) (
                                        Math.floorMod(((message.get(i) - 65)
                                        + Math.floorMod(Integer.parseInt(this.key),26)),26) + 65)
                        : (this.decrypt) ?
                                (char) (
                                        Math.floorMod(((message.get(i) - 97)
                                        - Math.floorMod(Integer.parseInt(this.key),26)),26) + 97)
                                : (char) (
                                        Math.floorMod(((message.get(i) - 97)
                                        + Math.floorMod(Integer.parseInt(this.key),26)),26) + 97)
                );
            }
        }
        message.rewind();
    }
}
