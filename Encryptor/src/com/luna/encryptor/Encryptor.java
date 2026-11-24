package com.luna.encryptor;

import com.luna.encryptor.forms.EncryptorGUI;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

public class Encryptor {
    static void main(String[] args) {
        if(args.length > 0){
            CLI(args);
        } else {
            SwingUtilities.invokeLater(() -> {
                EncryptorGUI gui = new EncryptorGUI();
                gui.setVisible(true);
            });
        }
    }

    private static void CLI(String[] args) {
        try {
            AlgorithmType genusCryptographiae = switch(args[0].toLowerCase()) {
                case "caesar" -> AlgorithmType.CAESAR;
                case "vigenere" -> AlgorithmType.VIGENERE;
                case "chaocipher" -> AlgorithmType.CHAOCIPHER;
                case "sha256" -> AlgorithmType.SHA256;
                default ->
                        throw new IllegalStateException
                                ("Unexpected value: " + args[0].toLowerCase());
            };
            String introitus = args[1];
            String directio;
            String clavis;
            String exitus = "";
            CryptographicAlgorithm encryptor = CryptographicAlgorithm.instantiateAlgorithm(genusCryptographiae);

            switch(encryptor){
                case SingleKeyCipher skc-> {
                    if (args.length > 4) {
                        exitus = args[2];
                        directio = args[3].toLowerCase();
                        clavis = args[4];
                    } else {
                        directio = args[2].toLowerCase();
                        clavis = args[3];
                    }
                    if (!clavis.chars().allMatch(Character::isDigit)){
                        throw new
                                IllegalArgumentException
                                ("CAESAR KEY MUST BE INTEGER NUMBER");
                    }

                    skc.setDecrypt(parseDirectio(directio));
                }
                case Sha256 ignored -> {
                    if (args.length > 2) exitus = args[2];
                }
                case null, default -> throw new
                        MatchException
                        ("ENCRYPTION TYPE MATCH FAILURE", null);
            }

            encryptor.readIntroitusFile(introitus);
            encryptor.Encrypt();
            exitus = (Objects.equals(exitus, "")) ?
                    encryptor.getFileName()[0] + "_"
                            + genusCryptographiae + "."
                            + encryptor.getFileName()[1] :
                    exitus;
            encryptor.writeExitusFile(new File(exitus));

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new
                    ArrayIndexOutOfBoundsException
                    ("NOT ENOUGH ARGUMENTS PROVIDED");
        }
    }

    private static boolean parseDirectio(String directio) {
        if (
                directio.contentEquals("decrypt")
                || directio.contentEquals("de")
                || directio.contentEquals("d")
        ) return true;
        else if (
                directio.contentEquals("encrypt")
                || directio.contentEquals("en")
                || directio.contentEquals("e")
        ) return false;
        else throw new
                    IllegalArgumentException
                    ("ENCRYPTION DIRECTION NOT VALID");
    }
}