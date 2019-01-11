package com.xaamruda.bbm.UI.Model;

import javax.swing.*;

public class MainUI {
    public static void main(String args[]){
        JFrame frame = new JFrame("BlablaMove");
        frame.setContentPane(new IdentificationUI().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
