package com.luna.encryptor.Algorithms;

public enum AlgorithmType {
    NONE("Select an algorithm..."),
    CAESAR("Caesar"),
    VIGENERE("Vigenere"),
    CHAOCIPHER("Chaocipher"),
    SHA256("Sha-256");

    private final String name;

    AlgorithmType(String s){
        this.name = s;
    }

    public String getName() {
        return this.name;
    }

    public static AlgorithmType fromName(String text) {
        for (AlgorithmType a : AlgorithmType.values()) {
            if (a.name.equalsIgnoreCase(text)
                    && a != NONE) {
                return a;
            }
        }
        throw new
                IllegalArgumentException
                ("No algorithm named " + text + " found");
    }
}