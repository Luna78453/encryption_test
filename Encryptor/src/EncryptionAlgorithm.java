import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

abstract class EncryptionAlgorithm {
    protected byte[] buffer;
    private String fileName;
    protected String name;

    protected void makeFileUscita(byte[] data, Optional<String> pathOpt){
        String path = pathOpt.orElseGet(() -> fileName + "_" + name);

        try (var output = new FileOutputStream(path)) {
            for(byte c : data){
                output.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void readFileEntrata(String path){
        // TODOne: research how unicode utf8 works before doing anything retarded again
        // NOTE: genuinely no idea why that worked
        // NOTE(1): genuinely no idea why I was using a string on this
        // NOTE(2): ***look into potentially more efficient way to read bytes***
        fileName = path.substring(path.lastIndexOf(File.separator) + 1);
        try (FileInputStream input = new FileInputStream(path)) {
            buffer = new byte[input.available()];
            input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void Encrypt(String entrata, Optional<String> uscita);
}