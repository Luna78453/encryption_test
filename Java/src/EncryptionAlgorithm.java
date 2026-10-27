import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
        //genuinely no idea why that worked
        try (FileInputStream input = new FileInputStream(path)) {
            buffer = new byte[input.available()];
            input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}