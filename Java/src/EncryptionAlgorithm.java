import java.io.*;
import java.nio.charset.StandardCharsets;

abstract class EncryptionAlgorithm {
    public String name;
    public byte[] buffer;

    protected void makeFileUscita(String path, String uscita){
        try (var output = new FileOutputStream(path)) {
            for(byte c : uscita.getBytes(StandardCharsets.UTF_8)){
                output.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void readFileEntrata(String path){
        StringBuilder data = new StringBuilder();

        try (FileInputStream input = new FileInputStream(path)) {
            int i;
            while ((i = input.read()) != -1) {
                data.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = data.toString().getBytes(StandardCharsets.UTF_8);
    }

    public abstract void Encrypt(String entrata, String uscita);
}