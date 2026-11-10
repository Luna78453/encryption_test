void main() {
    ByteBuffer buffer;
    CharBuffer cb;

    try (FileChannel channel = FileChannel.open(Path.of("/home/luna/en/testo.txt"), StandardOpenOption.READ)) {
        buffer = ByteBuffer.allocate(Math.toIntExact(channel.size()));
        while (buffer.hasRemaining()) {
            if (channel.read(buffer) == -1) {
                break;
            }
        }
        buffer.flip();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    cb = StandardCharsets.ISO_8859_1.decode(buffer);

    System.out.println(cb.length());

    for(int i = 0; i < cb.length(); i++){
        System.out.print(cb.get(i));
        if(Character.isLetter(cb.get(i))){
            cb.put(i,(char) (cb.get(i) + 0));
        }
    }

    System.out.print("\n");

    for(int i = 0; i < buffer.capacity(); i++){
        System.out.print(buffer.get(i) + " ");
    }
}