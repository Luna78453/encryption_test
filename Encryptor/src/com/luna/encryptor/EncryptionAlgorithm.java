package com.luna.encryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public abstract class EncryptionAlgorithm {
    private byte[] buffer;
    private byte[] eventum;
    private String[] fileName;
    public String name;

    public void makeFileUscita(byte[] data, Optional<String> pathOpt) throws IOException {
        String path = pathOpt.orElseGet(() -> fileName[0] + "_" + name + "." + fileName[1]);

        try (var output = new FileOutputStream(path)) {
            for(byte c : data){
                output.write(c);
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void readFileEntrata(String path) throws IOException {
        // TODOne: research how unicode utf8 works before doing anything retarded again
        // NOTE: genuinely no idea why that worked
        // NOTE(1): genuinely no idea why I was using a string on this
        // NOTE(2): ***look into potentially more efficient way to read bytes***
        fileName = path.substring(path.lastIndexOf(File.separator) + 1).split("\\.");
        try (FileInputStream input = new FileInputStream(path)) {
            buffer = new byte[input.available()];
            int _ = input.read(getBuffer());
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void readFileEntrata(File file) throws IOException {
        fileName = file.getName().split("\\.");
        try (FileInputStream input = new FileInputStream(file)) {
            buffer = new byte[input.available()];
            int _ = input.read(getBuffer());
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public abstract void Encrypt();

    public byte[] getBuffer() {
        return buffer;
    }

    public byte[] getEventum() {
        return eventum;
    }

    protected void setEventum(byte[] eventum) {
        this.eventum = eventum;
    }
}