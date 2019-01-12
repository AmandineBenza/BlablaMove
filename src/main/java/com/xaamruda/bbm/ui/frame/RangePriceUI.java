/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xaamruda.bbm.ui.frame;

/**
 *
 * @author roody
 */
public class RangePriceUI extends javax.swing.JFrame {

    /**
     * Creates new form RangePriceUI
     */
    public RangePriceUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
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

        acceptButton.setText("Accept");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(minimumPriceTxtLabel)
                    .addComponent(minimumPriceResLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(acceptButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(maximumPriceResLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(maximumPriceTxtLabel)
                        .addGap(87, 87, 87)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mediumPriceTxtLabel)
                    .addComponent(cancelButton)
                    .addComponent(mediumPriceResLabel))
                .addGap(13, 13, 13))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(yourPriceResLabel)
                    .addComponent(yourPriceTxtLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(yourPriceTxtLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yourPriceResLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mediumPriceTxtLabel)
                    .addComponent(minimumPriceTxtLabel)
                    .addComponent(maximumPriceTxtLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mediumPriceResLabel)
                    .addComponent(minimumPriceResLabel)
                    .addComponent(maximumPriceResLabel))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acceptButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton acceptButton;
    private javax.swing.JToggleButton cancelButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel maximumPriceResLabel;
    private javax.swing.JLabel maximumPriceTxtLabel;
    private javax.swing.JLabel mediumPriceResLabel;
    private javax.swing.JLabel mediumPriceTxtLabel;
    private javax.swing.JLabel minimumPriceResLabel;
    private javax.swing.JLabel minimumPriceTxtLabel;
    private javax.swing.JLabel yourPriceResLabel;
    private javax.swing.JLabel yourPriceTxtLabel;
    // End of variables declaration//GEN-END:variables
}
