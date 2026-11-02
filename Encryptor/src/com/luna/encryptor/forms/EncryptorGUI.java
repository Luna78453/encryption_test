package com.luna.encryptor.forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final JFileChooser fc = new JFileChooser();

    public EncryptorGUI(){
        setTitle("Encryptor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        setLocationRelativeTo(null);

        introitusFilePickButton.addActionListener(_ -> introitusFileChoose());
    }

    public void introitusFileChoose(){
        int returnVal = fc.showOpenDialog(EncryptorGUI.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
        } else {
        }
    }
}
