package com.luna.encryptor;

public enum AlgorithmType {
    NONE("Select an algorithm..."),
    CAESAR("Caesar"),
    VIGENERE("Vigenere"),
    SHA256("Sha-256");

    private final String name;

    AlgorithmType(String s){
        this.name = s;
    }

    public String getName() {
        return this.name;
    }
}
