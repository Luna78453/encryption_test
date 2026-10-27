import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Byte.toUnsignedInt;
import static java.lang.Integer.rotateRight;

public class Sha256 extends EncryptionAlgorithm {
    // TODOne: fix the fucking integer overloading problem
    /*  it wasn't a wrapping around problem I was just accessing the incorrect parts
        of the message Byte list buffer in the message schedule for loop lol
     */
    public void Encrypt(String entrata, String uscita) {
        //this will become the output
        int[] hashes = {0x6a09e667,
            0xbb67ae85,
            0x3c6ef372,
            0xa54ff53a,
            0x510e527f,
            0x9b05688c,
            0x1f83d9ab,
            0x5be0cd19};

        int[] k = {
                0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
                0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
                0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
                0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
                0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
                0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
                0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
                0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
        };

        int work_a = hashes[0];
        int work_b = hashes[1];
        int work_c = hashes[2];
        int work_d = hashes[3];
        int work_e = hashes[4];
        int work_f = hashes[5];
        int work_g = hashes[6];
        int work_h = hashes[7];

        readFileEntrata(entrata);

        long originalLen = buffer.length * 8L;
        byte[] originalLenBytes = ByteBuffer.allocate(Long.BYTES).putLong(originalLen).array();

        //There should be a cleaner way to do this shit bro
        //from immutable original message to mutable message copy list
        //to make the following operations easier
        List<Byte> message = new ArrayList<>();
        for(byte b : buffer){
            message.add(b);
        }

        message.add((byte)128);

        //I miss python floor divisions already
        int numOfBlocks = Math.floorDiv(message.size(), 64) + 1;
        int numOfBytes = 64*numOfBlocks;

        //go fuck yourself
        message.addAll(Collections.nCopies(numOfBytes - 8 - message.size(), (byte) 0));

        for(byte b : originalLenBytes){
            message.add(b);
        }

        //main message schedule encrypting
        for(int sec = 0; sec < numOfBytes; sec += 64){
            int[] messageSchedule = new int[64];

            for(int offset = 0; offset < 16; offset++){
                messageSchedule[offset] = (toUnsignedInt(message.get(sec + offset*4))<<24) +
                        (toUnsignedInt(message.get(sec + offset*4 + 1))<<16) +
                        (toUnsignedInt(message.get(sec + offset*4 + 2))<<8) +
                        (toUnsignedInt(message.get(sec + offset*4 + 3)));
            }

            for (int offset = 16; offset < 64; offset++){
                int sigma0 = (rotateRight(messageSchedule[offset - 15], 7))^(rotateRight(messageSchedule[offset - 15], 18))^(messageSchedule[offset - 15]>>>3);
                int sigma1 = (rotateRight(messageSchedule[offset - 2], 17))^(rotateRight(messageSchedule[offset - 2], 19))^(messageSchedule[offset - 2]>>>10);
                messageSchedule[offset] = messageSchedule[offset - 16] + sigma0 + messageSchedule[offset - 7] + sigma1;
            }

            for(int offset = 0; offset < 64; offset++){
                int captsigma0 = rotateRight(work_a, 2)^rotateRight(work_a, 13)^rotateRight(work_a, 22);
                int captsigma1 = rotateRight(work_e, 6)^rotateRight(work_e, 11)^rotateRight(work_e, 25);

                int choice = (work_e&work_f)^((~work_e)&work_g);
                int majority = (work_a&work_b)^(work_a&work_c)^(work_b&work_c);

                int temp1 = work_h + captsigma1 + choice + k[offset] + messageSchedule[offset];

                int temp2 = captsigma0 + majority;

                work_h = work_g;
                work_g = work_f;
                work_f = work_e;
                work_e = work_d + temp1;
                work_d = work_c;
                work_c = work_b;
                work_b = work_a;
                work_a = temp1 + temp2;
            }

            hashes[0] = hashes[0] + work_a;
            hashes[1] = hashes[1] + work_b;
            hashes[2] = hashes[2] + work_c;
            hashes[3] = hashes[3] + work_d;
            hashes[4] = hashes[4] + work_e;
            hashes[5] = hashes[5] + work_f;
            hashes[6] = hashes[6] + work_g;
            hashes[7] = hashes[7] + work_h;
        }

        //apparently string builders are better than just concatenating onto a string?
        StringBuilder o = new StringBuilder();

        for(int hash : hashes){
            //toHexString doesn't include leftmost zero bits so they're added with the repeat call
            //looking back on this it's a bit overboard given how unlikely this is to happen,
            //but I don't fucking care
            o.repeat("0", Integer.numberOfLeadingZeros(hash) / 8).append(Integer.toHexString(hash));
        }

        makeFileUscita(uscita, o.toString().getBytes(StandardCharsets.UTF_8));
    }
}
