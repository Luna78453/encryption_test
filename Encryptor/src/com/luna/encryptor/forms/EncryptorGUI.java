package com.luna.encryptor.forms;

import com.luna.encryptor.AlgorithmType;

import javax.swing.*;
import java.io.File;

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
    private final JFileChooser fc = new JFileChooser();
    private File introitusFile;
    private File exitusFile;

    public EncryptorGUI(){
        setTitle("Encryptor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        populateGenreComboBox();

        setLocationRelativeTo(null);

        introitusFilePickButton.addActionListener(_ -> introitusFileChoose());
        exitusFilePickButton.addActionListener(_ -> exitusFileChoose());
    }

    private void introitusFileChoose(){
        int returnVal = fc.showOpenDialog(EncryptorGUI.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) { this.introitusFile = fc.getSelectedFile(); }
    }

    private void exitusFileChoose(){
        int returnVal = fc.showOpenDialog(EncryptorGUI.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) { this.exitusFile = fc.getSelectedFile(); }
    }

    private void populateGenreComboBox() {
        DefaultComboBoxModel<AlgorithmType> comboBoxModel = (DefaultComboBoxModel<AlgorithmType>) algorithmSelector.getModel();
        for (AlgorithmType algorithm : AlgorithmType.values()) {
            comboBoxModel.addElement(algorithm);
        }
    }
}
