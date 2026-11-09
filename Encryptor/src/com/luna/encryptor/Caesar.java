package com.luna.encryptor;

import java.nio.ByteBuffer;

public class Caesar extends Cipher {
    public Caesar(String key, boolean decrypt){
        this.type = AlgorithmType.CAESAR;
        this.decrypt = decrypt;
        this.key = key;
    }

    public Caesar() { this.type = AlgorithmType.CAESAR; }

    @Override
    public void Encrypt() {
        ByteBuffer message = cloneByteBuffer((ByteBuffer) this.getIntroitus());

        for(int i = 0; i < message.capacity(); i++){
            message.put(i, (this.decrypt) ?
                    (byte) ((message.get(i) - Integer.parseInt(this.key)) % 256) :
                    (byte) ((message.get(i) + Integer.parseInt(this.key)) % 256)
            );
        }

        setEventum(message);
    }

    // Source - https://stackoverflow.com/a/21388198
    // Posted by jdmichal, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-11-09, Licence - CC BY-SA 3.0

    public static ByteBuffer cloneByteBuffer(final ByteBuffer original) {
        // Create clone with same capacity as original.
        final ByteBuffer clone = (original.isDirect()) ?
                ByteBuffer.allocateDirect(original.capacity()) :
                ByteBuffer.allocate(original.capacity());

        // Create a read-only copy of the original.
        // This allows reading from the original without modifying it.
        final ByteBuffer readOnlyCopy = original.asReadOnlyBuffer();

        // Flip and read from the original.
        readOnlyCopy.flip();
        clone.put(readOnlyCopy);

        return clone;
    }

}
