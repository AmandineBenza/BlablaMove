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
public class TransactionUI extends javax.swing.JFrame implements IGlobalUI{

    private javax.swing.JToggleButton endTransactionButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JToggleButton startTransactionButton;
    private javax.swing.JLabel statutTransactionResLabel;
    private javax.swing.JLabel statutTransactionTxtLabel;
    private javax.swing.JFrame frame;

    /**
     * Creates new form TransactionUI
     */
    public TransactionUI() {
        initialisation();
    }

    @Override
    public void initialisation() {
        frame = new JFrame("BlablaMove : Transaction");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new javax.swing.JPanel();
        endTransactionButton = new javax.swing.JToggleButton();
        statutTransactionResLabel = new javax.swing.JLabel();
        startTransactionButton = new javax.swing.JToggleButton();
        statutTransactionTxtLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        endTransactionButton.setText("End Transaction");
        endTransactionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTransactionButtonActionPerformed(evt);
            }
        });


        statutTransactionResLabel.setText("not started");

        startTransactionButton.setText("Start Transaction");
        startTransactionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTransactionButtonActionPerformed(evt);
            }
        });

        statutTransactionTxtLabel.setText("Statut of Transaction");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(mainPanelLayout.createSequentialGroup()
                                                        .addComponent(startTransactionButton)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(endTransactionButton))
                                                .addGroup(mainPanelLayout.createSequentialGroup()
                                                        .addGap(69, 69, 69)
                                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(statutTransactionTxtLabel)
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(statutTransactionResLabel)
                                                                        .addGap(24, 24, 24)))))
                                        .addContainerGap(29, Short.MAX_VALUE)))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 110, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(startTransactionButton)
                                                .addComponent(endTransactionButton))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(statutTransactionTxtLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(statutTransactionResLabel)
                                        .addContainerGap(18, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pack();
        frame.setContentPane(this.mainPanel);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }

    @Override
    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    @Override
    public boolean utility() {
        if(statutTransactionResLabel.getText().equals("Transaction in progress")){ //END


        }else if ( statutTransactionResLabel.getText().equals("not Started")){ //START
            //curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d
            // "{"event": "claim-receipt" ,"data": {"transactionID": $transactionID}}"
            // "localhost:8080/BBM/OFFERS"

            //ioHandler.sendToApp("{ event : claim-receipt , data : { }}");

            //curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d
            // "{"event": "confirm-receipt" ,"data": {"transactionID": $offerIdD}}"
            // "localhost:8080/BBM/OFFERS"

            //ioHandler.sendToApp("{ event confirm-receipt :  , data : { }}");
        }else{
            //curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d
            // "{"event": "confirm-deposit" ,"data": {"transactionID": $transactionID}}"
            // "localhost:8080/BBM/OFFERS"

            //ioHandler.sendToApp("{ event : confirm-deposit , data : { }}");
        }
        return true;
    }

    private void startTransactionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        utility();
        statutTransactionResLabel.setText("Transaction in progress");
        startTransactionButton.setSelected(false);
    }

    private void endTransactionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        utility();
        statutTransactionResLabel.setText("Transaction ended succesfully");
        endTransactionButton.setSelected(false);
    }
}
