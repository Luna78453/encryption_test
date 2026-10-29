import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Vigenere extends EncryptionAlgorithm{
    private boolean decrypt;
    private String key;

    public void setDecrypt(boolean decrypt){
        this.decrypt = decrypt;
    }

    public void setKey(String key){
        this.key = key;
    }

    @Override
    public void Encrypt(String entrata, Optional<String> uscita) {
        byte[] key_values = key.getBytes(StandardCharsets.UTF_8);
        readFileEntrata(entrata);
        byte[] message = buffer;

        for(int i = 0; i < message.length; i++){
            message[i] = (decrypt) ?
                    (byte) ((message[i] - key_values[i % key_values.length]) % 256) :
                    (byte) ((message[i] + key_values[i % key_values.length]) % 256);
        }

        makeFileUscita(message, uscita);
    }
}
