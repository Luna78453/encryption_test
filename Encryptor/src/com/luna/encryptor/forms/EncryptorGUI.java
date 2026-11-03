package com.luna.encryptor.forms;

import com.luna.encryptor.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncryptorGUI extends JFrame{
    private JPanel contentPane;
    private JButton introitusFilePickButton;
    private JTextField introitusPath;
    private JButton exitusFilePickButton;
    private JTextField exitusPath;
    private JLabel introitusLabel;
    private JLabel exitusLabel;
    private JScrollPane introitusShow;
    private JScrollPane exitusShow;
    private JPanel exitusPane;
    private JPanel introitusPane;
    private JComboBox<AlgorithmType> algorithmSelector;
    private JPanel introitusExitusPanel;
    private JTextArea introitusShowText;
    private JButton encryptButton;
    private JCheckBox encryptDecryptSwitch;
    private JTextField keyInput;
    private JTextArea exitusShowText;
    private JPanel miscellaneaPanel;
    private final JFileChooser fc = new JFileChooser();

    private File exitusFile;
    private EncryptionAlgorithm encryptor;

    public EncryptorGUI(){
        setTitle("Encryptor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        populateAlgorithmComboBox();
        algorithmSelector.setRenderer(new AlgorithmListCellRenderer());
        encryptorSelection();

        setLocationRelativeTo(null);

        algorithmSelector.addActionListener(_ -> {
            encryptorSelection();
        });
        introitusFilePickButton.addActionListener(_ -> {
            try {
                introitusButtonFileChoose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        exitusFilePickButton.addActionListener(_ -> exitusFileChoose());
        introitusPath.addActionListener(_ -> {
            if (introitusPath.getText() == null || introitusPath.getText().trim().isEmpty()){
                introitusShowText.setText("[NO FILE PATH FOUND]");
                return;
            }
            try {
                introitusFieldFileChoose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                introitusShowText.setText("[FILE PATH INCORRECT]");
            }
        });
        encryptButton.addActionListener(_ -> {
            if (encryptor.getBuffer() != null){
                encryptor.Encrypt();
                exitusShowText.setText(new String(encryptor.getEventum(), StandardCharsets.UTF_8));
            } else {
                exitusShowText.setText("[NO FILE SELECTED]");
            }
        });
    }

    private void introitusButtonFileChoose() throws IOException {
        int returnVal = fc.showOpenDialog(EncryptorGUI.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File introitusFile = fc.getSelectedFile();
            encryptor.readFileEntrata(introitusFile);
            String bufferString = new String(encryptor.getBuffer(), StandardCharsets.UTF_8);
            introitusShowText.setText(bufferString);
            introitusPath.setText(introitusFile.getAbsolutePath());
        }
    }

    private void introitusFieldFileChoose() throws IOException {
        String path = introitusPath.getText();
        encryptor.readFileEntrata(path);
        String bufferString = new String(encryptor.getBuffer(), StandardCharsets.UTF_8);
        introitusShowText.setText(bufferString);
    }

    private void exitusFileChoose(){
        int returnVal = fc.showOpenDialog(EncryptorGUI.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) { this.exitusFile = fc.getSelectedFile(); }
    }

    private void populateAlgorithmComboBox() {
        DefaultComboBoxModel<AlgorithmType> comboBoxModel =
                (DefaultComboBoxModel<AlgorithmType>) algorithmSelector.getModel();
        for (AlgorithmType algorithm : AlgorithmType.values()) {
            comboBoxModel.addElement(algorithm);
        }
    }

    private void encryptorSelection(){
        switch (algorithmSelector.getSelectedItem()){
            case AlgorithmType.caesar -> {
                encryptor = new Caesar();
                keyInput.setEnabled(true);
                encryptDecryptSwitch.setEnabled(true);
                enableStuff();
            }
            case AlgorithmType.vigenere -> {
                encryptor = new Vigenere();
                keyInput.setEnabled(true);
                encryptDecryptSwitch.setEnabled(true);
                enableStuff();
            }
            case AlgorithmType.sha256 -> {
                encryptor = new Sha256();
                keyInput.setEnabled(false);
                encryptDecryptSwitch.setEnabled(false);
                enableStuff();
            }
            case AlgorithmType.none -> {
                encryptor = null;
                keyInput.setEnabled(false);
                encryptDecryptSwitch.setEnabled(false);
                encryptButton.setEnabled(false);
                introitusPath.setEnabled(false);
                exitusPath.setEnabled(false);
                introitusFilePickButton.setEnabled(false);
                exitusFilePickButton.setEnabled(false);
                encryptButton.setEnabled(false);
            }
            case null -> {}
            default -> throw new IllegalStateException("Unexpected value: " + algorithmSelector.getSelectedItem());
        }
    }

    private void enableStuff(){
        encryptButton.setEnabled(true);
        introitusPath.setEnabled(true);
        exitusPath.setEnabled(true);
        introitusFilePickButton.setEnabled(true);
        exitusFilePickButton.setEnabled(true);
    }
}

class AlgorithmListCellRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        if (value instanceof AlgorithmType) {
            value = ((AlgorithmType)value).getName();
        }
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }
}
