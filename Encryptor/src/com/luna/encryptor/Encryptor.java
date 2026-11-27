package com.luna.encryptor;

import com.luna.encryptor.Algorithms.*;
import com.luna.encryptor.forms.EncryptorGUI;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

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
            int i = 0;
            AlgorithmType genusCryptographiae =
                    AlgorithmType
                    .fromName(args[i++]);
            String introitus = args[i++];
            String modus = args[i++].toLowerCase();
            String exitus = "";

            CryptographicAlgorithm encryptor =
                    CryptographicAlgorithm.
                            instantiateAlgorithm(genusCryptographiae);

            assert encryptor != null;

            encryptor.readIntroitusFile(introitus);
            encryptor.setBytewise(parseModus(modus));

            switch(encryptor){
                case Cipher c-> {
                    String directio = args[i++];

                    c.setDecrypt(parseDirectio(directio));

                    switch(c) {
                        case Cipher.SingleKeyCipher skc -> {
                            String clavis = args[i++];

                            if (c instanceof Caesar
                                    && !clavis.chars().allMatch(Character::isDigit))
                                throw new
                                        IllegalArgumentException
                                        ("CAESAR KEY MUST BE INTEGER NUMBER");

                            skc.setKey(clavis);
                        }
                        case Chaocipher chc -> {
                            String clavis = args[i++];
                            File archivus = new File(clavis);

                            if(clavis.contentEquals("r")){
                                if(chc.isBytewise())
                                    chc.generateRandomByteWheels();
                                else
                                    chc.generateRandomCharWheels();
                            }
                            else if(archivus.isFile()){
                                String rotusClavis = flattenFileContent(archivus);

                                if(chc.isBytewise())
                                    chc.setByteWheels(rotusClavis);
                                else
                                    chc.setCharWheels(rotusClavis);
                            } else {
                                if(chc.isBytewise())
                                    chc.setByteWheels(clavis);
                                else
                                    chc.setCharWheels(clavis);
                            }
                        }
                        default ->
                                throw new
                                        IllegalStateException
                                        ("Unexpected value: " + c);
                    }

                    if (args.length > 5)
                        exitus = args[i];
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
                    "." + File.separator
                            + encryptor.getFileName()[0] + "_"
                            + genusCryptographiae.getName() + "."
                            + encryptor.getFileName()[1] :
                    exitus;

            encryptor.writeExitusFile(new File(exitus));

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
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

    private static String flattenFileContent(File file){
        StringBuilder s = new StringBuilder();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                s.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error flattening wheel file occurred.");
        }

        return s.toString();
    }
}
