/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xaamruda.bbm.ui.frame;

import javax.swing.*;

/**
 *
 * @author roody
 */
public class OfferDemandUI extends javax.swing.JFrame implements IGlobalUI {

    private javax.swing.JToggleButton acceptButton;
    private javax.swing.JTextField arrivalLocationField;
    private javax.swing.JLabel arrivalLocationLabel;
    private javax.swing.JToggleButton cancelButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField maximumPointSpendField;
    private javax.swing.JLabel maximumPointSpendLabel;
    private javax.swing.JTextField sizeField;
    private javax.swing.JLabel sizeLabel;
    private javax.swing.JTextField startLocationField;
    private javax.swing.JLabel startLocationLabel;
    private javax.swing.JTextField weightField;
    private javax.swing.JLabel weightLabel;
    private javax.swing.JFrame frame;

    /**
     * Creates new form OfferDemandUI
     */
    public OfferDemandUI() {
        initialisation();
    }

    @Override
    public void initialisation() {
        frame = new JFrame("BlablaMove");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new javax.swing.JPanel();
        startLocationLabel = new javax.swing.JLabel();
        arrivalLocationLabel = new javax.swing.JLabel();
        sizeLabel = new javax.swing.JLabel();
        weightLabel = new javax.swing.JLabel();
        startLocationField = new javax.swing.JTextField();
        arrivalLocationField = new javax.swing.JTextField();
        sizeField = new javax.swing.JTextField();
        weightField = new javax.swing.JTextField();
        acceptButton = new javax.swing.JToggleButton();
        cancelButton = new javax.swing.JToggleButton();
        maximumPointSpendLabel = new javax.swing.JLabel();
        maximumPointSpendField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startLocationLabel.setText("Start Location");
        startLocationLabel.setToolTipText("");

        arrivalLocationLabel.setText("Arrival Location");

        sizeLabel.setText("Size of Item");

        weightLabel.setText("Weight of Item");

        acceptButton.setText("Accept");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        maximumPointSpendLabel.setText("Maximum Point Spend");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addComponent(sizeLabel)
                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(acceptButton)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(cancelButton))
                                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                                .addGap(159, 159, 159)
                                                                                .addComponent(weightLabel)
                                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(sizeField, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                                                        .addComponent(maximumPointSpendLabel)
                                                                        .addComponent(startLocationField)
                                                                        .addComponent(maximumPointSpendField))
                                                                .addGap(56, 56, 56)
                                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(arrivalLocationField, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(weightField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 4, Short.MAX_VALUE)))
                                                .addContainerGap())
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(startLocationLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(arrivalLocationLabel)
                                                .addGap(100, 100, 100))))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startLocationLabel)
                                        .addComponent(arrivalLocationLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startLocationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(arrivalLocationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(weightLabel)
                                        .addComponent(sizeLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(sizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(weightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addComponent(maximumPointSpendLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(maximumPointSpendField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(acceptButton)
                                        .addComponent(cancelButton))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        frame.setContentPane(this.mainPanel);
        frame.setVisible(true);
        frame.pack();
    }

    @Override
    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    @Override
    public void utility() {
    }

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        frame.dispose();
        new MainMenuUI();
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        frame.dispose();
        new MainMenuUI();
    }
}
