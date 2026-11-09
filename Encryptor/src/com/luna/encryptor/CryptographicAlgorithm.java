package com.luna.encryptor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CryptographicAlgorithm {
    private byte[] buffer;
    private byte[] eventum;
    private String[] fileName;
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
    public void makeFileUscita(byte[] data, String uscita) {
        try (var output = new FileOutputStream(uscita)) {
            for(byte c : data){
                output.write(c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeFileUscita(byte[] data, File exitus) {
        try (var output = new FileOutputStream(exitus)) {
            for(byte c : data){
                output.write(c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFileEntrata(String path) {
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
    }

    public void Encrypt(){}

    public byte[] getBuffer() {
        return buffer;
    }

    public byte[] getEventum() {
        return eventum;
    }

    protected void setEventum(byte[] eventum) {
        this.eventum = eventum;
    }

    public String[] getFileName() { return fileName; }
}