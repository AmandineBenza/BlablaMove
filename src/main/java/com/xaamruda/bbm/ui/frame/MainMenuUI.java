package com.xaamruda.bbm.ui.frame;

import javax.swing.*;

/**
 *
 * @author roody
 */
public class MainMenuUI extends javax.swing.JFrame implements IGlobalUI{

    private javax.swing.JButton createOfferButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton offerDemandButton;
    private javax.swing.JButton transactionButton;
    private javax.swing.JLabel welcomeLabel;
    private javax.swing.JLabel yourPointResLabel;
    private javax.swing.JLabel yourPointTxtLabel;

    /**
     * Creates new form MainMenuUI
     */
    public MainMenuUI() {
        initialisation();
    }

    private void createOfferButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createOfferButtonActionPerformed
        // TODO add your handling code here:
    }


    @Override
    public void initialisation() {
        mainPanel = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        createOfferButton = new javax.swing.JButton();
        offerDemandButton = new javax.swing.JButton();
        transactionButton = new javax.swing.JButton();
        yourPointTxtLabel = new javax.swing.JLabel();
        yourPointResLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        welcomeLabel.setText("Welcome to Blablamove");

        createOfferButton.setText("Create Offer");
        createOfferButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createOfferButtonActionPerformed(evt);
            }
        });

        offerDemandButton.setText("Offer Demand");

        transactionButton.setText("Transaction");

        yourPointTxtLabel.setText("Your Point");

        yourPointResLabel.setText("5800");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(createOfferButton)
                                                .addGap(52, 52, 52)
                                                .addComponent(offerDemandButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(transactionButton))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(yourPointResLabel))
                                                        .addComponent(yourPointTxtLabel))
                                                .addGap(166, 166, 166))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(welcomeLabel)
                                                .addGap(135, 135, 135)))
                                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(welcomeLabel)
                                .addGap(18, 18, 18)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(createOfferButton)
                                        .addComponent(offerDemandButton)
                                        .addComponent(transactionButton))
                                .addGap(27, 27, 27)
                                .addComponent(yourPointTxtLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(yourPointResLabel)
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
    }

    @Override
    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    @Override
    public void utility() {

    }
}
