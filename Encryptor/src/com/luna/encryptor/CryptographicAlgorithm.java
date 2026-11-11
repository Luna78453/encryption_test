package com.luna.encryptor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CryptographicAlgorithm {
    private File introitusFile;
    private ByteBuffer introitus = null;
    private ByteBuffer eventum = null;
    protected AlgorithmType type;
    private boolean bytewise;

    public static @Nullable CryptographicAlgorithm instantiateAlgorithm(@NotNull AlgorithmType type){
        return switch(type){
            case NONE -> null;
            case CAESAR -> new Caesar();
            case VIGENERE -> new Vigenere();
            case SHA256 -> new Sha256();
        };
    }

    // TODOnt: DON'T DO THIS HERE. MAKE A CLASS FOR FILE READING. MAKE THE INPUT TAKE IN BYTES AND CHARS TOO.
    // What the fuck are you talking about.
    // TODOne: DON'T LISTEN TO THIS ASSHOLE. JUST GET THE FUCKING BYTES TO CHARS SETUP DONE
    // NOTE: BYTEBUFFER HAS A HANDY INTERFACE TO HANDLE THE BYTES AS CHARS
    // USE THAT INSTEAD, DIPSHIT
    // OK SO IT SEEMS WHOEVER WROTE THE ASCHARBUFFER INTERFACE DIDN'T THINK SOMEONE MIGHT BE
    // READING BYTE BUFFERS TO SUPPORT READING POTENTIALLY LARGE FILES
    // (NOTE TO SELF: WHO THE FUCK IS GONNA ENCRYPT LARGE FILES? PEDOPHILES??? WHY AM I DOING THIS?)
    // WHICH MAY OR MAY NOT BE JUST TEXT AND DIDN'T CARE TO PUT AN OPTION TO HAVE IT DECODE
    // THAT SHIT LIKE YOU DO WITH CHARSET WITHOUT HAVING TO CREATE A WHOLE COPY OF THE OBJECT
    // I MEAN, WHO COULD POSSIBLY NEED TO DO SUCH A THING?!
    // (NOTE TO SELF: YOU DRIVE YORUSELF AGAINST A WALL AND WONDER WHY YOUR CAR IS TOTALLED
    // YOU FUCKING IMBECILE YOU DIDN'T HAVE TO DO THIS SHIT EITHER GO FUCK YOURSELF.
    // ((NOTE TO NOTE TO SELF: SHOULD'VE JUST GONE WITH THE DUAL FILE READER OPTION.))
    // TODOnt: TRY OUT THE DUAL FILE READER OPTION.
    // nah I think this is good
    public void writeExitusFile(File exitus) {
        try (var output = new FileOutputStream(exitus)) {
            FileChannel channel = output.getChannel();
            long _ = channel.write(this.eventum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readIntroitusFile(File introitusFile) {
        this.introitusFile = introitusFile;
        readIntroitus();
    }

    public void readIntroitusFile(String introitus) {
        try {
            this.introitusFile = new File(introitus);
            if(!this.introitusFile.isFile()){
                throw new IllegalArgumentException("Ingressus non est fasciculus");
            }
        } catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        }

        readIntroitus();
    }

    private void readIntroitus(){
        try (FileInputStream input = new FileInputStream(this.introitusFile)) {
            FileChannel channel = input.getChannel();
            this.introitus = ByteBuffer.allocate(Math.toIntExact(channel.size()));
            long _ = channel.read(this.introitus);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Source - https://stackoverflow.com/a/21388198
    // Posted by jdmichal, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-11-09, Licence - CC BY-SA 3.0
    protected static ByteBuffer cloneByteBuffer(final ByteBuffer original) {
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

    public void Encrypt(){}

    public ByteBuffer getEventum() {
        return eventum;
    }

    protected void setEventum(ByteBuffer eventum) {
        this.eventum = eventum;
    }

    public String[] getFileName() { return introitusFile.getName().split("\\."); }

    public File getIntroitusFile() { return introitusFile; }

    public ByteBuffer getIntroitus() {
        return introitus;
    }

    public boolean isBytewise() {
        return bytewise;
    }

    public void setBytewise(boolean bytewise) {
        this.bytewise = bytewise;
    }
}