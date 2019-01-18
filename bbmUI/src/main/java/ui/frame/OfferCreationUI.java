package ui.frame;

import javax.swing.*;

/**
 *
 * @author roody
 */
public class OfferCreationUI extends JFrame implements IGlobalUI{

    private JToggleButton acceptButton;
    private JTextField arrivalLocationField;
    private JLabel arrivalLocationLabel;
    private JToggleButton cancelButton;
    private JTextField carCapacityField;
    private JLabel carCapacityLabel;
    private JPanel mainPanel;
    private JTextField priceField;
    private JLabel priceLabel;
    private JTextField startLocationField;
    private JLabel startLocationLabel;
    private JFrame frame;

    /**
     * Creates new form OfferCreationUI
     */
    public OfferCreationUI() {
        initialisation();
    }

    @Override
    public void initialisation() {
        frame = new JFrame("BlablaMove: Offer Creation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new javax.swing.JPanel();
        startLocationLabel = new javax.swing.JLabel();
        arrivalLocationLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        startLocationField = new javax.swing.JTextField();
        arrivalLocationField = new javax.swing.JTextField();
        priceField = new javax.swing.JTextField();
        carCapacityLabel = new javax.swing.JLabel();
        carCapacityField = new javax.swing.JTextField();
        acceptButton = new javax.swing.JToggleButton();
        cancelButton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startLocationLabel.setText("Start Location");

        arrivalLocationLabel.setText("Arrival Location");

        priceLabel.setText("Price");

        carCapacityLabel.setText("Car Capacity");

        acceptButton.setText("Post");
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

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(priceLabel)
                                                        .addComponent(carCapacityLabel))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(startLocationLabel)
                                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(carCapacityField, javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(priceField, javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(startLocationField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(arrivalLocationLabel)
                                                        .addComponent(arrivalLocationField, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(10, 10, 10))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(acceptButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cancelButton)
                                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startLocationLabel)
                                        .addComponent(arrivalLocationLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startLocationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(arrivalLocationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(priceLabel)
                                .addGap(3, 3, 3)
                                .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(carCapacityLabel)
                                .addGap(4, 4, 4)
                                .addComponent(carCapacityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
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
    public boolean curlAction() {
        System.out.println("Start Location : " + startLocationField.getText());
        System.out.println("Arrival Location : " + arrivalLocationField.getText());
        System.out.println("Price : " + priceField.getText());
        System.out.println("Car Capacity : " + carCapacityField.getText());
        //priceRequest=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d
        // "{"event" : "validate-price" , "data" : {"data" : "x"},
        // "filters": {"startAddress": "$startAdress","endAddress": "$endAddress","maxPrice": "0"}}"
        // "localhost:8080/BBM/OFFERS/" | grep -o -P 'F.*' );

        if(!startLocationField.getText().equals("") && !arrivalLocationField.getText().equals("")
                && !priceField.getText().equals("") && !carCapacityField.getText().equals("")){
            //ioHandler.sendToApp("{ event : validate-price , data : { }}");
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String curlJsonParser() {
        return null;
    }

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        acceptButton.setSelected(false);
        if(curlAction()) {
            frame.dispose();
            new RangePriceUI();
        }
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        cancelButton.setSelected(false);
        frame.dispose();
        new MainMenuUI();
    }

}
