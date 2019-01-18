/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame;

import javax.swing.*;

/**
 *
 * @author roody
 */
public class DemandConfirmationUI extends javax.swing.JFrame implements IGlobalUI{
    /** TO DO? **/

    /**
     * Creates new form DemandConfirmationUI
     */
    public DemandConfirmationUI() {
        initialisation();
    }


    @Override
    public void initialisation() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }

    @Override
    public JPanel getMainPanel() {
        return null;
    }

    @Override
    public boolean curlAction() {
        return false;
    }

    @Override
    public String curlJsonParser() {
        return null;
    }
}
