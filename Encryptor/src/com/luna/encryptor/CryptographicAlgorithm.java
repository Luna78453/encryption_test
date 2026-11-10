package com.luna.encryptor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CryptographicAlgorithm {
    private File introitusFile;
    private ByteBuffer introitus;
    private ByteBuffer eventum;
    public AlgorithmType type;
    public boolean unicode;

    public static @Nullable CryptographicAlgorithm instantiateAlgorithm(@NotNull AlgorithmType type){
        return switch(type){
            case NONE -> null;
            case CAESAR -> new Caesar();
            case VIGENERE -> new Vigenere();
            case SHA256 -> new Sha256();
        };
    }

    // TODO: DON'T DO THIS HERE. MAKE A CLASS FOR FILE READING. MAKE THE INPUT TAKE IN BYTES AND CHARS TOO.
    public void writeExitus(File exitus) {
        try (var output = new FileOutputStream(exitus)) {
            FileChannel channel = output.getChannel();
            long _ = channel.write(this.eventum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public void Encrypt(){}

    public ByteBuffer getEventum() {
        return eventum;
    }

    protected void setEventum(ByteBuffer eventum) {
        this.eventum = eventum;
    }

    public String[] getFileName() { return introitusFile.getName().split("\\."); }

    public File getIntroitusFile() {
        return introitusFile;
    }

    private void readIntroitus(){
        try (FileInputStream input = new FileInputStream(this.introitusFile)) {
            FileChannel channel = input.getChannel();
            long _ = channel.read(this.introitus);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readIntroitusFile(File introitusFile) {
        this.introitusFile = introitusFile;
        readIntroitus();
    }

    public void readIntroitusFile(String introitus) {
        try {
            this.introitusFile = new File(introitus);
            if(!this.introitusFile.isFile()){
                throw new IllegalArgumentException("Ingressus non est fasciculus");
            }
        } catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        }

        readIntroitus();
    }

    public ByteBuffer getIntroitus() {
        return introitus;
    }

    // Source - https://stackoverflow.com/a/21388198
    // Posted by jdmichal, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-11-09, Licence - CC BY-SA 3.0
    protected static ByteBuffer cloneByteBuffer(final ByteBuffer original) {
        // Create clone with same capacity as original.
        final ByteBuffer clone = (original.isDirect()) ?
                ByteBuffer.allocateDirect(original.capacity()) :
                ByteBuffer.allocate(original.capacity());

        // Create a read-only copy of the original.
        // This allows reading from the original without modifying it.
        final ByteBuffer readOnlyCopy = original.asReadOnlyBuffer();

        // Flip and read from the original.
        readOnlyCopy.flip();
        clone.put(readOnlyCopy);

        return clone;
    }
}