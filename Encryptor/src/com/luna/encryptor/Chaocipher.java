package com.luna.encryptor;

import java.util.HashMap;
import java.util.Random;

public class Chaocipher extends Cipher{
    private Byte[] cipherByteWheel = new Byte[256];
    private Byte[] plainByteWheel = new Byte[256];
    private Character[] cipherCharWheel = new Character[26];
    private Character[] plainCharWheel = new Character[26];
    private final Byte[] allBytes = new Byte[256];
    private final Character[] allLetters = new Character[26];
    private boolean customConfiguration;

    public Chaocipher(){
        for (int b = 0; b < 256; b++) allBytes[b] = (byte)b;
        for (int c = 0; c < 26; c++) allLetters[c] = (char)(c + 97);
    }

    @Override
    public void Encrypt() {
        Byte[] cipherByteWheel = new Byte[256];
        Byte[] plainByteWheel = new Byte[256];
        Character[] cipherCharWheel = new Character[26];
        Character[] plainCharWheel = new Character[26];

        byte[] byteMessage = this.getBuffer();
        Character[] charMessage;

        byte[] byteCipher;

        if(customConfiguration) {
            if (unicode) {
                cipherByteWheel = this.cipherByteWheel;
                plainByteWheel = this.plainByteWheel;
            }
            else {
                cipherCharWheel = this.cipherCharWheel;
                plainCharWheel = this.plainCharWheel;
            }
        } else {
            if (unicode) {
                cipherByteWheel = shuffleArray(this.allBytes);
                plainByteWheel = shuffleArray(this.allBytes);
            }
            else {
                cipherCharWheel = shuffleArray(this.allLetters);
                plainCharWheel = shuffleArray(this.allLetters);
            }
        }
    }

    private Character[] CharacterEncrypt(Character[] plainCharWheel, Character[] cipherCharWheel){
        return null;
    }

    public <T> void instantiateWheel(T[] plainWheelConfiguration, T[] cipherWheelConfiguration){
        try {
            if(plainWheelConfiguration instanceof Byte[]) {
                if (!arraysEqualHash(allBytes, plainWheelConfiguration))
                    throw new IllegalAccessException("BYTE ALPHABET WHEEL NOT COMPLETE");
                else this.plainByteWheel = (Byte[])plainWheelConfiguration;
            } else if(plainWheelConfiguration instanceof Character[]){
                if (!arraysEqualHash(allLetters, plainWheelConfiguration))
                    throw new IllegalAccessException("BYTE ALPHABET WHEEL NOT COMPLETE");
                else this.plainCharWheel = (Character[])plainWheelConfiguration;
            }
            if(cipherWheelConfiguration instanceof Byte[]) {
                if (!arraysEqualHash(allBytes, cipherWheelConfiguration))
                    throw new IllegalAccessException("BYTE ALPHABET WHEEL NOT COMPLETE");
                else this.plainByteWheel = (Byte[])cipherWheelConfiguration;
            } else if(cipherWheelConfiguration instanceof Character[]){
                if (!arraysEqualHash(allLetters, cipherWheelConfiguration))
                    throw new IllegalAccessException("BYTE ALPHABET WHEEL NOT COMPLETE");
                else this.plainCharWheel = (Character[])cipherWheelConfiguration;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //https://faceprep.in/article/program-to-check-if-two-arrays-are-equal-or-not-faceprep/
    public <T> boolean arraysEqualHash(T[] a, T[] b) {
        if (a.length != b.length) return false;
        HashMap<T, Integer> freq = new HashMap<>();
        for (T x : a) freq.merge(x, 1, Integer::sum);
        for (T x : b) {
            if (!freq.containsKey(x)) return false;
            int updated = freq.get(x) - 1;
            if (updated == 0) freq.remove(x);
            else freq.put(x, updated);
        }
        return freq.isEmpty();
    }

    private <T> T[] shuffleArray(T[] ar) {
        // Source - https://stackoverflow.com/a/1520212
        // Posted by PhiLho, modified by community. See post 'Timeline' for change history
        // Retrieved 2026-11-07, Licence - CC BY-SA 4.0
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            T a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

        return ar;
    }

    public boolean isUnicode() {
        return unicode;
    }

    public void setUnicode(boolean unicode) {
        this.unicode = unicode;
    }

    public Byte[] getCipherByteWheel() {
        return cipherByteWheel;
    }

    public void setCipherByteWheel(Byte[] cipherByteWheel) {
        this.cipherByteWheel = cipherByteWheel;
    }

    public Byte[] getPlainByteWheel() {
        return plainByteWheel;
    }

    public void setPlainByteWheel(Byte[] plainByteWheel) {
        this.plainByteWheel = plainByteWheel;
    }

    public Character[] getCipherCharWheel() {
        return cipherCharWheel;
    }

    public void setCipherCharWheel(Character[] cipherCharWheel) {
        this.cipherCharWheel = cipherCharWheel;
    }

    public Character[] getPlainCharWheel() {
        return plainCharWheel;
    }

    public void setPlainCharWheel(Character[] plainCharWheel) {
        this.plainCharWheel = plainCharWheel;
    }

    public boolean isCustomConfiguration() {
        return customConfiguration;
    }

    public void setCustomConfiguration(boolean customConfiguration) {
        this.customConfiguration = customConfiguration;
    }
}