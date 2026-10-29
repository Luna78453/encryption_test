import java.util.Optional;

public class Caesar extends EncryptionAlgorithm{
    private boolean decrypt;
    private int key;
    String name = "caesar";

    public void setDecrypt(boolean decrypt){
        this.decrypt = decrypt;
    }

    public void setKey(int key){
        this.key = key;
    }

    @Override
    public void Encrypt(String entrata, Optional<String> uscita) {
        readFileEntrata(entrata);
        byte[] message = buffer;

        for(int i = 0; i < message.length; i++){
            message[i] = (decrypt) ?
                    (byte) ((message[i] - key) % 256) :
                    (byte) ((message[i] + key) % 256);
        }

        makeFileUscita(message, uscita);
    }
}
