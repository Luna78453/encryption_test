import java.io.*;
import java.util.ArrayList;

abstract class EncryptionAlgorithm {
    public String name;
    public ArrayList<Byte> buffer;

    protected void makeFileUscita(String path){
        try (var output = new FileOutputStream(path)) {
            output.write(buffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void readFileEntrata(String path){
        ArrayList<Byte> data = new ArrayList<Byte>(0);

        try (var input = new FileInputStream(path)) {
            int i;
            while ((i = input.read()) != -1) {
                data.add((byte)i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = data;
    }

    public abstract void Encrypt(String entrata, String uscita);
}