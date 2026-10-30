import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

abstract class EncryptionAlgorithm {
    protected byte[] buffer;
    private String[] fileName;
    protected String name;

    protected void makeFileUscita(byte[] data, Optional<String> pathOpt) throws IOException {
        String path = pathOpt.orElseGet(() -> fileName[0] + "_" + name + "." + fileName[1]);

        try (var output = new FileOutputStream(path)) {
            for(byte c : data){
                output.write(c);
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    protected void readFileEntrata(String path) throws IOException {
        // TODOne: research how unicode utf8 works before doing anything retarded again
        // NOTE: genuinely no idea why that worked
        // NOTE(1): genuinely no idea why I was using a string on this
        // NOTE(2): ***look into potentially more efficient way to read bytes***
        fileName = path.substring(path.lastIndexOf(File.separator) + 1).split("\\.");
        try (FileInputStream input = new FileInputStream(path)) {
            buffer = new byte[input.available()];
            input.read(buffer);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public abstract void Encrypt(String entrata, Optional<String> uscita) throws IOException;
}