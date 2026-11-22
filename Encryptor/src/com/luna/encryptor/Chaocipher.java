package com.luna.encryptor;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Random;

public class Chaocipher extends Cipher {
    private Byte[] leftByteWheel = new Byte[256];
    private Byte[] rightByteWheel = new Byte[256];
    private Character[] leftCharWheel = new Character[26];
    private Character[] rightCharWheel = new Character[26];
    private final Byte[] allBytes = new Byte[256];
    private final Character[] allLetters = new Character[26];
    private boolean customWheelsInstantiated = false;
    private String byteWheels;
    private String charWheels;

    public Chaocipher(){
        for (int b = 0; b < 256; b++) allBytes[b] = (byte)b;
        for (int c = 0; c < 26; c++) allLetters[c] = (char)(c + 97);
    }

    @Override
    public void Encrypt() {
        this.getIntroitus().rewind();

        if(isBytewise()){
            ByteBuffer message = cloneByteBuffer(this.getIntroitus());
            ByteEncrypt(message);
            setEventum(message);
        } else {
            CharBuffer message = StandardCharsets.UTF_8.decode(this.getIntroitus());
            CharacterEncrypt(message);
            setEventum(StandardCharsets.UTF_8.encode(message));
        }
    }

    // sum = clockwise; subtr = counter
    // en : [0] = plain; [1] = cipher
    // de : [1] = plain; [0] = cipher
    // 0 : right; 1 : left
    private void ByteEncrypt(ByteBuffer message){
        Byte[][] wheels = new Byte[2][];
        int[] zeniths = {0, 0};
        int plainWheelIndex = (this.decrypt) ? 1 : 0;
        int cipherWheelIndex = (this.decrypt) ? 0 : 1;

        if (customWheelsInstantiated) {
            wheels[0] = this.rightByteWheel;
            wheels[1] = this.leftByteWheel;
        } else {
            wheels = new Byte[][]{
                    shuffleArray(this.allBytes),
                    shuffleArray(this.allBytes)
            };
        }

        while(message.hasRemaining()){
            findNextCharacter(
                    findIndex(
                    wheels[plainWheelIndex],
                    message.get(message.position())),
                    zeniths,
                    plainWheelIndex
            );

            message.put(wheels[cipherWheelIndex][zeniths[cipherWheelIndex]]);

            permuteLeftWheel(wheels[1], zeniths[1]);
            permuteRightWheel(wheels[0], zeniths[0]);
        }
    }

    private void CharacterEncrypt(CharBuffer message) {
        Character[][] wheels = new Character[2][];
        int[] zeniths = {0, 0};
        int plainWheelIndex = (this.decrypt) ? 1 : 0;
        int cipherWheelIndex = (this.decrypt) ? 0 : 1;

        if (customWheelsInstantiated) {
            wheels[0] = this.rightCharWheel;
            wheels[1] = this.leftCharWheel;
        } else {
            wheels = new Character[][]{
                    shuffleArray(this.allLetters),
                    shuffleArray(this.allLetters)
            };
        }

        while(message.hasRemaining()){
            findNextCharacter(
                    findIndex(wheels[plainWheelIndex],
                    Character.toLowerCase(message.get(message.position()))),
                    zeniths,
                    plainWheelIndex
            );

            message.put(
                    (Character.isUpperCase(message.get(message.position())))
                    ? Character.toUpperCase(wheels[cipherWheelIndex][zeniths[cipherWheelIndex]])
                    : wheels[cipherWheelIndex][zeniths[cipherWheelIndex]]
            );

            permuteLeftWheel(wheels[1], zeniths[1]);
            permuteRightWheel(wheels[0], zeniths[0]);
        }
    }

    private void findNextCharacter(int nextPlainPosition, int[] zeniths, int plainWheelIndex){
        int wheelSize = (isBytewise()) ? 256 : 26;

        //if further than nadir point go counter-clockwise
        if(nextPlainPosition > wheelSize + zeniths[plainWheelIndex])
            rotateEngagedWheels(
                    plainWheelIndex,
                    zeniths,
                    -Math.floorMod(
                            (-nextPlainPosition - zeniths[plainWheelIndex]),
                            wheelSize
                    )
            );
        else
            rotateEngagedWheels(
                    plainWheelIndex,
                    zeniths,
                    Math.floorMod(
                            (nextPlainPosition - zeniths[plainWheelIndex]),
                            wheelSize
                    )
            );
    }

    private <T> void permuteLeftWheel(T[] cipherWheel, int zenith){
        int wheelSize = (isBytewise()) ? 256 : 26;
        T foo = cipherWheel[Math.floorMod(zenith - 1, wheelSize)];

        for(int i = 1; i < wheelSize/2; i++){
            cipherWheel[Math.floorMod(zenith - i, wheelSize)]
                    = cipherWheel[Math.floorMod(zenith - i - 1, wheelSize)];
        }

        cipherWheel[(zenith + wheelSize/2)%wheelSize] = foo;
    }

    private <T> void permuteRightWheel(T[] plainWheel, int zenith){
        int wheelSize = (isBytewise()) ? 256 : 26;
        zenith = Math.floorMod(--zenith, wheelSize);
        T foo = plainWheel[Math.floorMod(--zenith, wheelSize)];

        for(int i = 2; i < wheelSize/2; i++){
            plainWheel[Math.floorMod(zenith + i, wheelSize)]
                    = plainWheel[Math.floorMod(zenith + i + 1, wheelSize)];
        }

        plainWheel[(zenith + wheelSize/2)%wheelSize] = foo;
    }

    private void rotateEngagedWheels(int rotatedWheel, int[] zeniths, int rotations){
        int wheelSize = (isBytewise()) ? 256 : 26;
        zeniths[rotatedWheel] += (rotations % wheelSize);
        zeniths[(rotatedWheel + 1)%2] += Math.floorMod(-rotations, wheelSize);
    }

    private static <T> int findIndex(T[] arr, T e){
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == e) {
                index = i;
                break;
            }
        }
        return index;
    }

    public <T> void verifyWheels(T[] plainWheelConfiguration, T[] cipherWheelConfiguration){
        try {
            if(plainWheelConfiguration instanceof Byte[]) {
                if (!arraysEqualHash(allBytes, plainWheelConfiguration))
                    throw new IllegalAccessException("BYTE ALPHABET WHEEL NOT COMPLETE");
                else this.rightByteWheel = (Byte[])plainWheelConfiguration;
            } else if(plainWheelConfiguration instanceof Character[]){
                if (!arraysEqualHash(allLetters, plainWheelConfiguration))
                    throw new IllegalAccessException("CHARACTER ALPHABET WHEEL NOT COMPLETE");
                else this.rightCharWheel = (Character[])plainWheelConfiguration;
            }
            if(cipherWheelConfiguration instanceof Byte[]) {
                if (!arraysEqualHash(allBytes, cipherWheelConfiguration))
                    throw new IllegalAccessException("BYTE ALPHABET WHEEL NOT COMPLETE");
                else this.rightByteWheel = (Byte[])cipherWheelConfiguration;
            } else if(cipherWheelConfiguration instanceof Character[]){
                if (!arraysEqualHash(allLetters, cipherWheelConfiguration))
                    throw new IllegalAccessException("CHARACTER ALPHABET WHEEL NOT COMPLETE");
                else this.rightCharWheel = (Character[])cipherWheelConfiguration;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (!customWheelsInstantiated)
            customWheelsInstantiated = true;
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

    public void wheelStringToArray(){
    }

    public boolean isUnicode() {
        return isBytewise();
    }

    public void setUnicode(boolean unicode) {
        this.setBytewise(unicode);
    }

    public Byte[] getLeftByteWheel() {
        return leftByteWheel;
    }

    public void setLeftByteWheel(Byte[] leftByteWheel) {
        this.leftByteWheel = leftByteWheel;
    }

    public Byte[] getRightByteWheel() {
        return rightByteWheel;
    }

    public void setRightByteWheel(Byte[] rightByteWheel) {
        this.rightByteWheel = rightByteWheel;
    }

    public Character[] getLeftCharWheel() {
        return leftCharWheel;
    }

    public void setLeftCharWheel(Character[] leftCharWheel) {
        this.leftCharWheel = leftCharWheel;
    }

    public Character[] getRightCharWheel() {
        return rightCharWheel;
    }

    public void setRightCharWheel(Character[] rightCharWheel) {
        this.rightCharWheel = rightCharWheel;
    }

    public String getByteWheels() {
        return byteWheels;
    }

    public void setByteWheels(String byteWheels) {
        this.byteWheels = byteWheels;
    }

    public String getCharWheels() {
        return charWheels;
    }

    public void setCharWheels(String charWheels) {
        this.charWheels = charWheels;
    }
}
