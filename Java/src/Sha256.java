import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Sha256 extends EncryptionAlgorithm {
    public String name = "sha256";
    @Override
    public void Encrypt(String entrata, String uscita) {
        Integer h0 = 0x6a09e667;
        Integer h1 = 0xbb67ae85;
        Integer h2 = 0x3c6ef372;
        Integer h3 = 0xa54ff53a;
        Integer h4 = 0x510e527f;
        Integer h5 = 0x9b05688c;
        Integer h6 = 0x1f83d9ab;
        Integer h7 = 0x5be0cd19;

        Integer[] k = {
                0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
                0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
                0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
                0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
                0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
                0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
                0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
                0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
        };

        Integer work_a = h0;
        Integer work_b = h1;
        Integer work_c = h2;
        Integer work_d = h3;
        Integer work_e = h4;
        Integer work_f = h5;
        Integer work_g = h6;
        Integer work_h = h7;

        readFileEntrata(entrata);

        long original_len = buffer.size() * 8L;
        byte[] original_len_bytes = ByteBuffer.allocate(Long.BYTES).putLong(original_len).array();

        buffer.add((byte)0b10000000);

        int num_of_blocks = Math.floorDiv(buffer.size(), 64) + 1;
        int num_of_bytes = 64*num_of_blocks;

        buffer.addAll(Collections.nCopies(num_of_bytes - 8 - buffer.size(), (byte) 0));

        for(int i = 0; i < buffer.size(); i += 4){
            System.out.println(Integer.toHexString(buffer.get(i).intValue()) +
                    Integer.toHexString(buffer.get(i + 1).intValue()) +
                    Integer.toHexString(buffer.get(i + 2).intValue()) +
                    Integer.toHexString(buffer.get(i + 3).intValue()));
        }
        for(byte b : original_len_bytes){
            System.out.print(Integer.toBinaryString(Byte.toUnsignedInt(b)) + " ");
        }
        System.out.println(buffer.size());
    }
}