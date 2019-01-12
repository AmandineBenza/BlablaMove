package com.xaamruda.bbm.ui.frame;

import javax.swing.*;

/**
 *
 * @author roody
 */
public class OfferDemandUI extends JFrame implements IGlobalUI {

    private JToggleButton acceptButton;
    private JTextField arrivalLocationField;
    private JLabel arrivalLocationLabel;
    private JToggleButton cancelButton;
    private JPanel mainPanel;
    private JTextField maximumPointSpendField;
    private JLabel maximumPointSpendLabel;
    private JTextField sizeField;
    private JLabel sizeLabel;
    private JTextField startLocationField;
    private JLabel startLocationLabel;
    private JTextField weightField;
    private JLabel weightLabel;
    private JFrame frame;

    /**
     * Creates new form OfferDemandUI
     */
    public OfferDemandUI() {
        initialisation();
    }

    @Override
    public void initialisation() {
        frame = new JFrame("BlablaMove : Offer Demand");
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
        System.out.println("Start Location : " + startLocationField.getText());
        System.out.println("Arrival Location : " + arrivalLocationField.getText());
        System.out.println("Max Price : " + maximumPointSpendField.getText());
        System.out.println("Weight : " + weightField.getText());
        System.out.println("Size : " + sizeField.getText());
        //searchResultList=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d
        // "{"event":"consult-offers","data": {"weight": "$bedW", "volume":"$bedV", "date":"$inDays" },
        // "filters": {"weight": "$bedV","startAddress": "$startAddress","endAddress": "$endAddress","maxPrice": "10000"}}"
        // "localhost:8080/BBM/OFFERS")

        if(!startLocationField.getText().equals("") && !arrivalLocationField.getText().equals("") && !maximumPointSpendField.getText().equals("")
                && !weightField.getText().equals("") && !sizeField.getText().equals("") ){
            //ioHandler.sendToApp("{ event : consult-offers , data : { }}");
            return true;
        }else{
            return false;
        }
    }

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        acceptButton.setSelected(false);
        utility();
        frame.dispose();
        new MainMenuUI();
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        cancelButton.setSelected(false);
        utility();
        frame.dispose();
        new MainMenuUI();
    }
}
