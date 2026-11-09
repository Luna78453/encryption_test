void main() {
    ByteBuffer buffer;

    try (FileChannel channel = FileChannel.open(Path.of("/home/luna/en/testo.txt"), StandardOpenOption.READ)) {
        buffer = ByteBuffer.allocate(Math.toIntExact(channel.size()));
        while (buffer.hasRemaining()) {
            if (channel.read(buffer) == -1) {
                break;
            }
        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    CharBuffer characters = StandardCharsets.UTF_8.decode(buffer);

    //System.out.println(new String(buffer.array()));
    System.out.println(characters.toString());
}