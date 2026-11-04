package com.luna.encryptor.forms;

import com.luna.encryptor.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EncryptorGUI extends JFrame {
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
    private JCheckBox decryptSwitch;
    private JTextField keyInput;
    private JTextArea exitusShowText;
    private JPanel miscellaneaPanel;
    private JButton saveButton;
    private JPanel introitusSelect;
    private JPanel exitusSelect;
    // Source - https://stackoverflow.com/a/3729157
    // Posted by Roberto Luis Bisbé, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-11-03, License - CC BY-SA 3.0
    private final JFileChooser fc = new JFileChooser(){
        @Override
        public void approveSelection() {
            File file = fc.getSelectedFile();
            if (file != null && getDialogType() == SAVE_DIALOG) {
                int result = JOptionPane.showConfirmDialog(this,
                        "The file exists, overwrite?",
                        "Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
                switch(result){
                    case JOptionPane.YES_OPTION:
                        super.approveSelection();
                        return;
                    case JOptionPane.NO_OPTION, JOptionPane.CLOSED_OPTION:
                        return;
                    case JOptionPane.CANCEL_OPTION:
                        cancelSelection();
                }
            }
        }
    };

    private EncryptionAlgorithm encryptor;
    private File exitusFile;

    public EncryptorGUI(){
        setTitle("Encryptor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        populateAlgorithmComboBox();
        algorithmSelector.setRenderer(new AlgorithmListCellRenderer());
        encryptorSelection();

        setLocationRelativeTo(null);

        algorithmSelector.addActionListener(_ -> encryptorSelection());
        introitusFilePickButton.addActionListener(_ -> {
            try {
                introitusButtonFileChoose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        introitusPath.addActionListener(_ -> {
            try {
                introitusFieldFileChoose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        exitusFilePickButton.addActionListener(_ -> exitusFileChoose());
        encryptButton.addActionListener(_ -> encrypt());
        saveButton.addActionListener(_ -> {
            try {
                writeEventum();
            } catch (IOException e) {
                throw new RuntimeException(e);
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
            encryptButton.setEnabled(true);
        }
    }

    private void exitusFileChoose() {
        int returnVal = fc.showSaveDialog(EncryptorGUI.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            exitusFile = fc.getSelectedFile();
            exitusPath.setText(exitusFile.getAbsolutePath());
        }
    }

    private void introitusFieldFileChoose() throws IOException {
        if (introitusPath.getText() == null || introitusPath.getText().trim().isEmpty()){
            introitusShowText.setText("[NO FILE PATH FOUND]");
            return;
        }
        try {
            String path = introitusPath.getText();
            encryptor.readFileEntrata(path);
            String bufferString = new String(encryptor.getBuffer(), StandardCharsets.UTF_8);
            introitusShowText.setText(bufferString);
            encryptButton.setEnabled(true);
        } catch (IOException e) {
            introitusShowText.setText("[INVALID FILE PATH]");
            throw new RuntimeException(e);
        }
    }

    // Redundant file save behaviour just in case
    // with option to skip with other functions o algo
    private void writeEventum() throws IOException {
        if (exitusPath.getText() == null) exitusFileChoose();
        else if (exitusFile == null) exitusFile = new File(exitusPath.getText());

        encryptor.makeFileUscita(encryptor.getEventum(), exitusFile);
    }

    private void populateAlgorithmComboBox() {
        DefaultComboBoxModel<AlgorithmType> comboBoxModel =
                (DefaultComboBoxModel<AlgorithmType>) algorithmSelector.getModel();
        for (AlgorithmType algorithm : AlgorithmType.values()) {
            comboBoxModel.addElement(algorithm);
        }
    }

    private void encryptorSelection() {
        switch (algorithmSelector.getSelectedItem()){
            case AlgorithmType.CAESAR -> {
                encryptor = new Caesar();
                keyInput.setEnabled(true);
                decryptSwitch.setEnabled(true);
                enableStuff(true);
            }
            case AlgorithmType.VIGENERE -> {
                encryptor = new Vigenere();
                keyInput.setEnabled(true);
                decryptSwitch.setEnabled(true);
                enableStuff(true);
            }
            case AlgorithmType.SHA256 -> {
                encryptor = new Sha256();
                keyInput.setEnabled(false);
                decryptSwitch.setEnabled(false);
                enableStuff(true);
            }
            case AlgorithmType.NONE -> {
                encryptor = null;
                keyInput.setEnabled(false);
                decryptSwitch.setEnabled(false);
                enableStuff(false);
            }
            case null -> {}
            default -> throw new IllegalStateException("Unexpected value: " + algorithmSelector.getSelectedItem());
        }
    }

    private void enableStuff(boolean b) {
        encryptButton.setEnabled(b);
        introitusPath.setEnabled(b);
        exitusPath.setEnabled(b);
        introitusFilePickButton.setEnabled(b);
        exitusFilePickButton.setEnabled(b);
    }

    private void encrypt() {
        if (encryptor.getBuffer() != null) {
            if (encryptor.type == AlgorithmType.CAESAR) {
                try {
                    encryptor.setKey(Integer.parseInt(keyInput.getText()));
                } catch (NumberFormatException e) {
                    exitusShowText.setText("[INVALID CAESAR KEY: MUST BE A NUMBER]");
                    throw new RuntimeException(e);
                }
                encryptor.setDecrypt(decryptSwitch.isSelected());
            } else if (encryptor.type == AlgorithmType.VIGENERE) {
                encryptor.setKey(keyInput.getText());
                encryptor.setDecrypt(decryptSwitch.isSelected());
            }
            encryptor.Encrypt();
            exitusShowText.setText(new String(
                    encryptor.getEventum(), StandardCharsets.UTF_8));
        } else {
            exitusShowText.setText("[NO FILE SELECTED]");
        }
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
