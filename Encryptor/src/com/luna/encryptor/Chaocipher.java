package com.luna.encryptor;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Byte.toUnsignedInt;

public class Chaocipher extends Cipher {
    private Byte[] leftByteWheel = new Byte[256];
    private Byte[] rightByteWheel = new Byte[256];
    private Character[] leftCharWheel = new Character[26];
    private Character[] rightCharWheel = new Character[26];
    private final Byte[] allBytes = new Byte[256];
    private final Character[] allLetters = new Character[26];
    private boolean byteWheelsInstantiated = false;
    private boolean charWheelsInstantiated = false;
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
            ByteBuffer message =
                    cloneByteBuffer(this.getIntroitus());
            ByteEncrypt(message);
            setEventum(message);
        } else {
            CharBuffer message =
                    StandardCharsets.UTF_8.decode(this.getIntroitus());
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

        if (byteWheelsInstantiated) {
            wheels[0] = this.rightByteWheel;
            wheels[1] = this.leftByteWheel;
        } else {
            generateRandomByteWheels();

            wheels = new Byte[][] {
                    this.rightByteWheel,
                    this.leftByteWheel
            };
        }

        while(message.hasRemaining()){
            findNextCharacter(
                    findIndex(wheels[plainWheelIndex],
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

        if (charWheelsInstantiated) {
            wheels[0] = this.rightCharWheel;
            wheels[1] = this.leftCharWheel;
        } else {
            generateRandomCharWheels();

            wheels = new Character[][] {
                    this.rightCharWheel,
                    this.leftCharWheel
            };
        }

        while(message.hasRemaining()){
            findNextCharacter(
                    findIndex(wheels[plainWheelIndex],
                    Character.toLowerCase(message.get(message.position()))),
                    zeniths,
                    plainWheelIndex
            );

            if(Character.isLetter(message.get(message.position()))){
                message.put(
                        (Character.isUpperCase(message.get(message.position())))
                                ? Character.toUpperCase(wheels[cipherWheelIndex][zeniths[cipherWheelIndex]])
                                : wheels[cipherWheelIndex][zeniths[cipherWheelIndex]]
                );
            }

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

    public <T> void instantiateWheels(T[] leftWheelConfiguration, T[] rightWheelConfiguration){
        try {
            if(leftWheelConfiguration instanceof Byte[] bw) {
                if (!arraysEqualHash(allBytes, bw))
                    throw new IllegalAccessException("INVALID LEFT BYTE ALPHABET WHEEL");
                else this.leftByteWheel = bw;
            } else if(leftWheelConfiguration instanceof Character[] cw){
                if (!arraysEqualHash(allLetters, cw))
                    throw new IllegalAccessException("INVALID LEFT CHARACTER ALPHABET WHEEL");
                else this.leftCharWheel = cw;
            }
            if(rightWheelConfiguration instanceof Byte[] bw) {
                if (!arraysEqualHash(allBytes, bw))
                    throw new IllegalAccessException("INVALID RIGHT BYTE ALPHABET WHEEL");
                else this.rightByteWheel = bw;
            } else if(rightWheelConfiguration instanceof Character[] cw){
                if (!arraysEqualHash(allLetters, cw))
                    throw new IllegalAccessException("INVALID RIGHT CHARACTER ALPHABET WHEEL");
                else this.rightCharWheel = cw;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (leftWheelConfiguration instanceof Byte[] && !byteWheelsInstantiated)
            byteWheelsInstantiated = true;
        if (leftWheelConfiguration instanceof Character[] && !charWheelsInstantiated)
            charWheelsInstantiated = true;
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

    private void wheelStringToArray(String s, char wheelType){
        if(wheelType == 'c'){
            Character[] leftWheel = s.substring(0, s.length()/2).chars()
                    .mapToObj(c -> (char) c)
                    .toArray(Character[]::new);

            Character[] rightWheel = s.substring(s.length()/2).chars()
                    .mapToObj(c -> (char) c)
                    .toArray(Character[]::new);

            instantiateWheels(leftWheel, rightWheel);
        } else if (wheelType == 'b') {
            String[] substrings = new String[512];
            Byte[] leftWheel = new Byte[256];
            Byte[] rightWheel = new Byte[256];

            for (int i = 0; i < 512; i++)
                substrings[i] = s.substring(i*2, i*2 + 1);

            for (int i = 0; i < 256; i++)
                leftWheel[i] = Byte.parseByte(substrings[i], 16);
            
            for (int i = 256; i < 512; i++)
                rightWheel[i - 256] = Byte.parseByte(substrings[i], 16);

            instantiateWheels(leftWheel, rightWheel);
        }
    }

    private <T> String wheelArrayToString(T[] leftWheel, T[] rightWheel){
        String s = "";

        if(leftWheel instanceof Character[] lC && rightWheel instanceof Character[] rC){
            s = Arrays.stream(lC)
                    .map(String::valueOf)
                    .collect(Collectors.joining())
                    + Arrays.stream(rC)
                    .map(String::valueOf)
                    .collect(Collectors.joining());
        } else if (leftWheel instanceof Byte[] lB && rightWheel instanceof Byte[] rB) {
            Integer[] words = new Integer[128];

            for(int offset = 0; offset < 16; offset++){
                words[offset] = (toUnsignedInt(lB[offset*4])<<24) +
                        (toUnsignedInt(lB[offset*4 + 1])<<16) +
                        (toUnsignedInt(lB[offset*4 + 2])<<8) +
                        (toUnsignedInt(lB[offset*4 + 3]));
            }

            for(int offset = 16; offset < 32; offset++){
                words[offset] = (toUnsignedInt(rB[(offset - 16)*4])<<24) +
                        (toUnsignedInt(rB[(offset - 16)*4 + 1])<<16) +
                        (toUnsignedInt(rB[(offset - 16)*4 + 2])<<8) +
                        (toUnsignedInt(rB[(offset - 16)*4 + 3]));
            }

            StringBuilder o = new StringBuilder();

            for(int word : words){
                o.repeat("0", Integer.numberOfLeadingZeros(word) / 8)
                        .append(Integer.toHexString(word));
            }

            s = o.toString();
        }

        return s;
    }

    public void generateRandomByteWheels(){
        instantiateWheels(shuffleArray(this.allBytes), shuffleArray(this.allBytes));
        this.byteWheels = wheelArrayToString(this.leftByteWheel, this.rightByteWheel);
    }

    public void generateRandomCharWheels(){
        instantiateWheels(shuffleArray(this.allLetters), shuffleArray(this.allLetters));
        this.charWheels = wheelArrayToString(this.leftCharWheel, this.rightCharWheel);
    }

    public boolean isUnicode() {
        return isBytewise();
    }

    public void setUnicode(boolean unicode) {
        this.setBytewise(unicode);
    }

    public String getByteWheels() {
        return byteWheels;
    }

    public void setByteWheels(String byteWheels) {
        wheelStringToArray(byteWheels, 'b');

        this.byteWheels = byteWheels;
    }

    public String getCharWheels() {
        return charWheels;
    }

    public void setCharWheels(String charWheels) {
        wheelStringToArray(charWheels, 'c');

        this.charWheels = charWheels;
    }
}