public class Caesar extends EncryptionAlgorithm{
    public void Encrypt(String entrata, String uscita, boolean decrypt, int key) {
        readFileEntrata(entrata);
        byte[] message = buffer;

        for(int i = 0; i < message.length; i++){
            message[i] = (decrypt) ?
                    (byte) ((message[i] - key) % 256) :
                    (byte) ((message[i] + key) % 256);
        }

        makeFileUscita(uscita, message);
    }
}
