import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

abstract class EncryptionAlgorithm {
    public byte[] buffer;

    protected void makeFileUscita(String path, byte[] uscita){
        try (var output = new FileOutputStream(path)) {
            for(byte c : uscita){
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
        try (FileInputStream input = new FileInputStream(path)) {
            buffer = new byte[input.available()];
            input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}