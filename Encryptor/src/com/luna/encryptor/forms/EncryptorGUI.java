package com.luna.encryptor.forms;

import com.luna.encryptor.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
    private JButton resetButton;
    private final JFileChooser fc = new JFileChooser();

    private CryptographicAlgorithm encryptor;
    private File exitusFile;
    private File introitusFile;
    private boolean resetAvailable = false;

    public EncryptorGUI(){
        Start();
        componentFunctionality();
    }

    private void introitusButtonFileChoose() throws IOException {
        int returnVal = fc.showOpenDialog(EncryptorGUI.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            introitusFile = fc.getSelectedFile();
            encryptor.readIntroitusFile(introitusFile);
            introitusShowText.setText(new String(
                    encryptor.getIntroitus().array(),
                    StandardCharsets.UTF_8
            ));
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
        introitusFile = new File(introitusPath.getText());
        try {encryptor.readIntroitusFile(introitusFile);}
        catch (Exception e) {JOptionPane.showMessageDialog
                (null, "INVALID FILE PATH");}
        introitusShowText.setText(new String(
                encryptor.getIntroitus().array(),
                StandardCharsets.UTF_8
        ));
        encryptButton.setEnabled(true);
    }

    // Redundant file save behaviour just in case
    // with option to skip with other functions o algo
    private void writeEventum() throws IOException {
        if (exitusPath.getText().isBlank() ||
                exitusPath.getText().equals("Archivum exitus..."))
            exitusFileChoose();
        else if (exitusFile == null) exitusFile = new File(exitusPath.getText());

        if(exitusFile.isFile()){
            int result = JOptionPane.showConfirmDialog(this,
                    "The file exists, overwrite?",
                    "Existing file",JOptionPane.OK_CANCEL_OPTION);
            switch(result){
                case JOptionPane.OK_OPTION:
                    encryptor.writeExitusFile(exitusFile);
                    exitusFile = null;
                    return;
                case JOptionPane.CLOSED_OPTION, JOptionPane.CANCEL_OPTION:
            }
        } else {
            encryptor.writeExitusFile(exitusFile);
            exitusFile = null;
        }
        JOptionPane.showMessageDialog(null,"File saved successfully.");
    }

    private void populateAlgorithmComboBox() {
        DefaultComboBoxModel<AlgorithmType> comboBoxModel =
                (DefaultComboBoxModel<AlgorithmType>) algorithmSelector.getModel();
        for (AlgorithmType algorithm : AlgorithmType.values()) {
            comboBoxModel.addElement(algorithm);
        }
    }

    private void encryptorSelection() throws IOException {
        try {
            if(introitusFile != null && algorithmSelector.getSelectedItem() != AlgorithmType.NONE){
                encryptor = CryptographicAlgorithm.instantiateAlgorithm((AlgorithmType)
                        Objects.requireNonNull(algorithmSelector.getSelectedItem()));
                assert encryptor != null;
                encryptor.readIntroitusFile(introitusFile);
            } else if(algorithmSelector.getSelectedItem() != AlgorithmType.NONE)
                encryptor = CryptographicAlgorithm.instantiateAlgorithm((AlgorithmType)
                        Objects.requireNonNull(algorithmSelector.getSelectedItem()));
        } catch (NullPointerException e) { return; }
        if(!resetAvailable) {
            resetAvailable = true;
            enableStuff(true);
            algorithmSelector.removeItemAt(0);
        }
        switch (algorithmSelector.getSelectedItem()){
            case AlgorithmType.CAESAR, AlgorithmType.VIGENERE -> {
                keyInput.setEnabled(true);
                decryptSwitch.setEnabled(true);
            }
            case AlgorithmType.SHA256 -> {
                keyInput.setEnabled(false);
                decryptSwitch.setEnabled(false);
            }
            case null, default -> {}
        }
        exitusShowText.setText("");
    }

    private void enableStuff(boolean b) {
        introitusPath.setEnabled(b);
        exitusPath.setEnabled(b);
        introitusFilePickButton.setEnabled(b);
        exitusFilePickButton.setEnabled(b);
        introitusPath.setEnabled(b);
        introitusFilePickButton.setEnabled(b);
        exitusFilePickButton.setEnabled(b);
        exitusPath.setEnabled(b);
        resetButton.setEnabled(b);
        introitusShowText.setEnabled(b);
        exitusShowText.setEnabled(b);
        if(!b){
            keyInput.setEnabled(false);
            decryptSwitch.setEnabled(false);
            saveButton.setEnabled(false);
            encryptButton.setEnabled(false);
        }
    }

    private void encrypt() {
        if (encryptor.getIntroitus() != null) {
            if (encryptor instanceof Cipher sc){
                try {
                    if(
                            !keyInput.getText().chars()
                            .allMatch(Character::isDigit)
                            && sc instanceof Caesar
                    )
                        throw new NumberFormatException();

                    sc.setKey(keyInput.getText());
                }
                catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog
                            (null,
                                    "INVALID CAESAR KEY: MUST BE A NUMBER.");
                    return;
                }

                sc.setDecrypt(decryptSwitch.isSelected());
            }

            encryptor.Encrypt();
            exitusShowText.setText(new String(
                    encryptor.getEventum().array(),
                    StandardCharsets.UTF_8
            ));
            saveButton.setEnabled(true);
        } else JOptionPane.showMessageDialog
                (null,"NO FILE SELECTED.");
    }

    private void Reset(){
        encryptor = null;
        keyInput.setEnabled(false);
        decryptSwitch.setEnabled(false);
        enableStuff(false);
        algorithmSelector.removeAllItems();
        populateAlgorithmComboBox();
        resetAvailable = false;
    }

    private void Start(){
        setTitle("Encryptor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        populateAlgorithmComboBox();
        algorithmSelector.setRenderer(new AlgorithmListCellRenderer());
        algorithmSelector.setSelectedIndex(0);

        setLocationRelativeTo(null);
    }

    private void focusFunctionality(){
        keyInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(keyInput.getText().equals("Write the key..."))
                    keyInput.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(keyInput.getText().isBlank())
                    keyInput.setText("Write the key...");
            }
        });
        introitusPath.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                SwingUtilities.invokeLater(() -> {
                    if(introitusPath.getText().equals("Archivum inputus..."))
                        introitusPath.setText("");
                    else introitusPath.selectAll();
                });
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(introitusPath.getText().isBlank()){
                    introitusPath.setText("Archivum inputus...");
                } else {
                    try { introitusFieldFileChoose(); }
                    catch (IOException ex) { JOptionPane.showMessageDialog
                            (null,"INVALID FILE PATH."); }
                }
            }
        });
        exitusPath.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                SwingUtilities.invokeLater(() -> {
                    if(exitusPath.getText().equals("Archivum exitus..."))
                        exitusPath.setText("");
                    else exitusPath.selectAll();
                });
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(exitusPath.getText().isBlank())
                    exitusPath.setText("Archivum exitus...");
            }
        });
    }

    private void componentFunctionality(){
        algorithmSelector.addActionListener(_ -> {
            try { encryptorSelection(); }
            catch (IOException e) { throw new RuntimeException(e); }
        });
        introitusFilePickButton.addActionListener(_ -> {
            try { introitusButtonFileChoose(); }
            catch (IOException e) { throw new RuntimeException(e); }
        });
        introitusPath.addActionListener(_ -> {
            try { introitusFieldFileChoose(); }
            catch (IOException e) { JOptionPane
                    .showMessageDialog(null,
                            "INVALID FILE PATH."); }
        });
        exitusFilePickButton.addActionListener(_ -> exitusFileChoose());
        encryptButton.addActionListener(_ -> encrypt());
        saveButton.addActionListener(_ -> {
            try { writeEventum(); }
            catch (IOException e) { JOptionPane
                    .showMessageDialog(null,
                            "INVALID FILE PATH."); }
            catch (NullPointerException _) {}
        });
        resetButton.addActionListener(_ -> Reset());
        focusFunctionality();
    }
}

class AlgorithmListCellRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        if (value instanceof AlgorithmType) value = ((AlgorithmType)value).getName();

        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }
}
