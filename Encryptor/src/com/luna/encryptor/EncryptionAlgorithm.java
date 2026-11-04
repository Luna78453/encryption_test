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
    public AlgorithmType type;

    public void makeFileUscita(byte[] data, Optional<String> pathOpt) {
        String path = pathOpt.orElseGet(() -> fileName[0] + "_" + type.getName() + "." + fileName[1]);

        try (var output = new FileOutputStream(path)) {
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
            throw new RuntimeException(e);
        }
    }

    public void readFileEntrata(File file) {
        fileName = file.getName().split("\\.");
        try (FileInputStream input = new FileInputStream(file)) {
            buffer = new byte[input.available()];
            int _ = input.read(getBuffer());
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public void setDecrypt(boolean decrypt) {}

    public void setKey(int key) {}

    public void setKey(String key) {}
}