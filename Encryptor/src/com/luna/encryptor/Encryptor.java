package com.luna.encryptor;

import java.util.Optional;

public class Encryptor {
    static void main(String[] args) {
        if(args.length > 0){
            CLI(args);
        } else {
            GUI();
        }
    }

    private static void GUI(){
        EncryptorGUI gui = new EncryptorGUI();
        gui.makeGUI();
    }

    private static void CLI(String[] args){
        try {
            String genusCryptographiae = args[0].toLowerCase();
            String introitus = args[1];
            String modus;
            String clavis;
            Optional<String> exitus = Optional.empty();
            EncryptionAlgorithm encryptor = switch(genusCryptographiae){
                case "caesar" -> {
                    if (args.length > 4) {
                        exitus = Optional.of(args[2]);
                        modus = args[3].toLowerCase();
                        clavis = args[4];
                    } else {
                        modus = args[2].toLowerCase();
                        clavis = args[3];
                    }
                    if (!clavis.chars().allMatch(Character::isDigit)){
                        throw new IllegalArgumentException("CAESAR KEY MUST BE INTEGER NUMBER");
                    }
                    yield new Caesar(Integer.parseInt(clavis), parseModus(modus));
                }
                case "vigenere" -> {
                    if (args.length > 4) {
                        exitus = Optional.of(args[2]);
                        modus = args[3].toLowerCase();
                        clavis = args[4].toLowerCase();
                    } else {
                        modus = args[2].toLowerCase();
                        clavis = args[3].toLowerCase();
                    }
                    yield new Vigenere(clavis, parseModus(modus));
                }
                case "sha256" -> {
                    if (args.length > 2) exitus = Optional.of(args[2]);
                    yield new Sha256();
                }
                default -> throw new MatchException("ENCRYPTION TYPE MATCH FAILURE", null);
            };
            encryptor.Encrypt(introitus, exitus);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("NOT ENOUGH ARGUMENTS PROVIDED");
        } catch (Throwable _){}
    }

    private static boolean parseModus(String modus){
        if (modus.contentEquals("decrypt") || modus.contentEquals("de") || modus.contentEquals("d")){
            return true;
        } else if (modus.contentEquals("encrypt") || modus.contentEquals("en") || modus.contentEquals("e")) {
            return false;
        } else {
            throw new IllegalArgumentException("ENCRYPTION DIRECTION NOT VALID");
        }
    }
}