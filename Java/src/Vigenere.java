import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Vigenere extends EncryptionAlgorithm{
    public void Encrypt(String entrata, String uscita, boolean decrypt, String key) {
        byte[] key_values = key.getBytes(StandardCharsets.UTF_8);
        readFileEntrata(entrata);
        byte[] message = buffer;

        for(int i = 0; i < message.length; i++){
            message[i] = (decrypt) ?
                    (byte) ((message[i] - key_values[i % key_values.length]) % 256) :
                    (byte) ((message[i] + key_values[i % key_values.length]) % 256);
        }

        makeFileUscita(uscita, message);
    }
}
