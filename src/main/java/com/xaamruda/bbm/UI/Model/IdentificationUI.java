package com.xaamruda.bbm.UI.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IdentificationUI implements IGlobalUI{
    private JPanel panel1;
    private JTextField identifientField;
    private JTextField passwordField;
    private JButton loginButton;

    public IdentificationUI(){
        initialisation();
    }


    @Override
    public void initialisation() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utility();
            }
        });
        loginButton.setPreferredSize(new Dimension(panel1.getWidth()/4,panel1.getHeight()/4));
    }

    @Override
    public JPanel getMainPanel(){
        return panel1;
    }

    @Override
    public void utility() {
        System.out.println("Identifient : " + identifientField.getText());
        System.out.println("Password : " + passwordField.getText());
    }
}
