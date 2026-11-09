package com.luna.encryptor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

public class CryptographicAlgorithm {
    private File introitusFile;
    private ByteBuffer introitus;
    private ByteBuffer eventum;
    public AlgorithmType type;

    public static @Nullable CryptographicAlgorithm instantiateAlgorithm(@NotNull AlgorithmType type){
        return switch(type){
            case NONE -> null;
            case CAESAR -> new Caesar();
            case VIGENERE -> new Vigenere();
            case SHA256 -> new Sha256();
        };
    }

    // TODO: DON'T DO THIS HERE. MAKE A CLASS FOR FILE READING. MAKE THE INPUT TAKE IN BYTES AND CHARS TOO.
    public void makeFileUscita(byte[] data, File exitus) {
        try (var output = new FileOutputStream(exitus)) {
            for(byte c : data){
                output.write(c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*public void readFileEntrata(String path) {
        // TODOne: research how unicode utf8 works before doing anything retarded again
        // NOTE: genuinely no idea why that worked
        // NOTE(1): genuinely no idea why I was using a string on this
        // NOTE(2): ***look into potentially more efficient way to read bytes***
        fileName = path.substring(path.lastIndexOf(File.separator) + 1).split("\\.");
        try (FileInputStream input = new FileInputStream(path)) {
            buffer = new byte[input.available()];
            int _ = input.read(getBuffer());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFileEntrata(File file) throws IOException {
        fileName = file.getName().split("\\.");
        try (FileInputStream input = new FileInputStream(file)) {
            buffer = new byte[input.available()];
            int _ = input.read(getBuffer());
        } catch (IOException e) {
            throw new IOException(e);
        }
    }*/

    private void makeIntroitus(){
        ByteBuffer buffer;

        try (FileChannel channel = FileChannel.open(introitusFile.toPath(), StandardOpenOption.READ)) {
            buffer = ByteBuffer.allocate(Math.toIntExact(channel.size()));
            while (buffer.hasRemaining()) {
                if (channel.read(buffer) == -1) {
                    break;
                }
            }
            this.introitus = buffer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Encrypt(){}

    public Buffer getEventum() {
        return eventum;
    }

    protected void setEventum(ByteBuffer eventum) {
        this.eventum = eventum;
    }

    public String[] getFileName() { return introitusFile.getName().split("\\."); }

    public File getIntroitusFile() {
        return introitusFile;
    }

    public void setIntroitusFile(File introitusFile) {
        this.introitusFile = introitusFile;
        makeIntroitus();
    }

    public void setIntroitusFile(String introitus) {
        try {
            this.introitusFile = new File(introitus);
            if(!this.introitusFile.isFile()){
                throw new IllegalArgumentException("Ingressus non est fasciculus");
            }
        } catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        }

        makeIntroitus();
    }

    public Buffer getIntroitus() {
        return introitus;
    }
}