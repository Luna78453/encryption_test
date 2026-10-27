void main() {
    // TODO: actual command line interface
    Vigenere vigenere = new Vigenere();
    vigenere.Encrypt("Java/testo.txt", "Java/testo_Vigenere.txt", false, "teto");
    vigenere.Encrypt("Java/testo_Vigenere.txt", "Java/testo_Vigenere_de.txt", true, "teto");

    /*Caesar caesar = new Caesar();
    caesar.Encrypt("Java/testo.txt", "Java/testo_Caesar.txt", false, 200);
    caesar.Encrypt("Java/testo_Caesar.txt", "Java/testo_Caesar_de.txt", true);*/
}