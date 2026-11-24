package com.luna.encryptor;

import java.util.ArrayList;
import java.util.List;

public enum AlgorithmType {
    NONE(null, "Select an algorithm..."),
    ALGORITHM(null, "Algoritm"),
        CIPHER(ALGORITHM, "Cipher"),
            CAESAR(CIPHER, "Caesar"),
            VIGENERE(CIPHER, "Vigenere"),
            CHAOCIPHER(CIPHER, "Chaocipher"),
        SHA256(ALGORITHM, "Sha-256");

    private final AlgorithmType parent;
    private final List<AlgorithmType> children = new ArrayList<>();
    private final String name;

    AlgorithmType(AlgorithmType parent, String s){
        this.parent = parent;
        this.name = s;
        if (this.parent != null)
            this.parent.children.add(this);
    }

    public String getName() {
        return this.name;
    }

    public AlgorithmType getParent() { return parent; }

    public boolean is(AlgorithmType other) {
        if (other == null) {
            return false;
        }

        for (AlgorithmType t = this;  t != null;  t = t.parent) {
            if (other == t) {
                return true;
            }
        }
        return false;
    }

    public List<AlgorithmType> getChildren() {
        return children;
    }
}
