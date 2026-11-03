package com.luna.encryptor;

public enum AlgorithmType {
    none("Select an algorithm..."),
    caesar("Caesar"),
    vigenere("Vigenere"),
    sha256("Sha-256");

    private final String name;

    AlgorithmType(String s){
        this.name = s;
    }

    public String getName() {
        return this.name;
    }
}
