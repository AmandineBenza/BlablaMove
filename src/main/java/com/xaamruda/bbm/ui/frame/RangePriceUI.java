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
public class RangePriceUI extends javax.swing.JFrame implements IGlobalUI{

    private javax.swing.JToggleButton acceptButton;
    private javax.swing.JToggleButton cancelButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel maximumPriceResLabel;
    private javax.swing.JLabel maximumPriceTxtLabel;
    private javax.swing.JLabel mediumPriceResLabel;
    private javax.swing.JLabel mediumPriceTxtLabel;
    private javax.swing.JLabel minimumPriceResLabel;
    private javax.swing.JLabel minimumPriceTxtLabel;
    private javax.swing.JLabel yourPriceResLabel;
    private javax.swing.JLabel yourPriceTxtLabel;
    private javax.swing.JFrame frame;

    /**
     * Creates new form RangePriceUI
     */
    public RangePriceUI() {
        initialisation();
    }

    @Override
    public void initialisation() {
        frame = new JFrame("BlablaMove : Price Recommendation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new javax.swing.JPanel();
        yourPriceTxtLabel = new javax.swing.JLabel();
        minimumPriceTxtLabel = new javax.swing.JLabel();
        minimumPriceResLabel = new javax.swing.JLabel();
        yourPriceResLabel = new javax.swing.JLabel();
        maximumPriceResLabel = new javax.swing.JLabel();
        mediumPriceTxtLabel = new javax.swing.JLabel();
        maximumPriceTxtLabel = new javax.swing.JLabel();
        mediumPriceResLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JToggleButton();
        acceptButton = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        yourPriceTxtLabel.setText("Your Price");

        minimumPriceTxtLabel.setText("Minimum Price");

        minimumPriceResLabel.setText("100");

        yourPriceResLabel.setText("250");

        maximumPriceResLabel.setText("250");

        mediumPriceTxtLabel.setText("Medium Price");

        maximumPriceTxtLabel.setText("Maximum Price");

        mediumPriceResLabel.setText("175");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        acceptButton.setText("Accept");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(minimumPriceTxtLabel)
                                        .addComponent(minimumPriceResLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(acceptButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(maximumPriceResLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(maximumPriceTxtLabel)
                                                .addGap(87, 87, 87)))
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(mediumPriceTxtLabel)
                                        .addComponent(cancelButton)
                                        .addComponent(mediumPriceResLabel))
                                .addGap(13, 13, 13))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(yourPriceResLabel)
                                        .addComponent(yourPriceTxtLabel))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addContainerGap(22, Short.MAX_VALUE)
                                .addComponent(yourPriceTxtLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yourPriceResLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(mediumPriceTxtLabel)
                                        .addComponent(minimumPriceTxtLabel)
                                        .addComponent(maximumPriceTxtLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(mediumPriceResLabel)
                                        .addComponent(minimumPriceResLabel)
                                        .addComponent(maximumPriceResLabel))
                                .addGap(23, 23, 23)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(acceptButton)
                                        .addComponent(cancelButton))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        //curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d
        // "{"event":"create-offer","data":{"ownerID":"$driver", "price": "$(echo $minPrice*2.1 | bc | cut -f1 -d.)", "startCity":"$startAddress", "endCity":"$endAddress", "capacity":"$carV" }}"
        // "localhost:8080/BBM/OFFERS"
        //ioHandler.sendToApp("{ event : create-offer , data : { }}");
        return true;
    }

    private void setPrice(){
        yourPriceResLabel.setText("0");
        maximumPriceResLabel.setText("0");
        minimumPriceResLabel.setText("0");
        mediumPriceResLabel.setText("0");
    }

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        utility();
        acceptButton.setSelected(false);
        frame.dispose();
        new MainMenuUI();
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        cancelButton.setSelected(false);
        frame.dispose();
        new MainMenuUI();
    }
}
