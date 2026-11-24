package com.luna.encryptor;

import com.luna.encryptor.Algorithms.*;
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
            AlgorithmType genusCryptographiae = AlgorithmType.fromName(args[0]);
            String introitus = args[1];
            String modus = args[2].toLowerCase();
            String directio = "";
            String exitus = "";
            CryptographicAlgorithm encryptor =
                    CryptographicAlgorithm.
                            instantiateAlgorithm(genusCryptographiae);

            assert encryptor != null;
            encryptor.readIntroitusFile(introitus);
            encryptor.setBytewise(parseModus(modus));

            switch(encryptor){
                case SingleKeyCipher skc -> {
                    String clavis;

                    if (args.length > 5) {
                        exitus = args[3];
                        directio = args[4].toLowerCase();
                        clavis = args[5];
                    } else if (args.length > 4) {
                        directio = args[3].toLowerCase();
                        clavis = args[4];
                    } else {
                        clavis = args[3];
                    }
                    if (skc instanceof Caesar
                            && !clavis.chars().allMatch(Character::isDigit)){
                        throw new
                                IllegalArgumentException
                                ("CAESAR KEY MUST BE INTEGER NUMBER");
                    }

                    skc.setDecrypt(parseDirectio(directio));
                    skc.setKey(clavis);
                }
                case Chaocipher ch -> {
                }
                case Sha256 ignored -> {
                    if (args.length > 3) exitus = args[3];
                }
                case null, default ->
                        throw new
                        MatchException
                        ("ENCRYPTION TYPE MATCH FAILURE", null);
            }

            encryptor.Encrypt();
            exitus = (Objects.equals(exitus, "")) ?
                    encryptor.getFileName()[0] + "_"
                            + genusCryptographiae.getName() + "."
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
                || directio.contentEquals("")
        ) return false;
        else throw new
                    IllegalArgumentException
                    ("ENCRYPTION DIRECTION NOT VALID");
    }
    
    private static boolean parseModus(String modus) {
        if (
                modus.contentEquals("byte")
                        || modus.contentEquals("b")
        ) return true;
        else if (
                modus.contentEquals("character")
                        || modus.contentEquals("char")
                        || modus.contentEquals("c")
                        || modus.contentEquals("")
        ) return false;
        else throw new
                    IllegalArgumentException
                    ("ENCRYPTION DIRECTION NOT VALID");
    }
}