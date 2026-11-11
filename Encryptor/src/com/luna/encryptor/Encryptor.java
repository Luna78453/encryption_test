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
            String genusCryptographiae = args[0].toLowerCase();
            String introitus = args[1];
            String modus;
            String clavis;
            String exitus = "";
            CryptographicAlgorithm encryptor = switch(genusCryptographiae){
                case "caesar" -> {
                    if (args.length > 4) {
                        exitus = args[2];
                        modus = args[3].toLowerCase();
                        clavis = args[4];
                    } else {
                        modus = args[2].toLowerCase();
                        clavis = args[3];
                    }
                    if (!clavis.chars().allMatch(Character::isDigit)){
                        throw new
                                IllegalArgumentException
                                ("CAESAR KEY MUST BE INTEGER NUMBER");
                    }
                    yield new Caesar(clavis, parseModus(modus));
                }
                case "vigenere" -> {
                    if (args.length > 4) {
                        exitus = args[2];
                        modus = args[3].toLowerCase();
                        clavis = args[4].toLowerCase();
                    } else {
                        modus = args[2].toLowerCase();
                        clavis = args[3].toLowerCase();
                    }
                    yield new Vigenere(clavis, parseModus(modus));
                }
                case "sha256" -> {
                    if (args.length > 2) exitus = args[2];
                    yield new Sha256();
                }
                default -> throw new
                        MatchException
                        ("ENCRYPTION TYPE MATCH FAILURE", null);
            };

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

    private static boolean parseModus(String modus) {
        if (
                modus.contentEquals("decrypt")
                || modus.contentEquals("de")
                || modus.contentEquals("d")
        ) return true;
        else if (
                modus.contentEquals("encrypt")
                || modus.contentEquals("en")
                || modus.contentEquals("e")
        ) return false;
        else throw new
                    IllegalArgumentException
                    ("ENCRYPTION DIRECTION NOT VALID");
    }
}