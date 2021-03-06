package ui.frame;

import javax.swing.*;

/**
 *
 * @author roody
 */
public class ShowRecapUI extends javax.swing.JFrame implements IGlobalUI{

    private javax.swing.JToggleButton acceptButton;
    private javax.swing.JLabel arrivalLocationResLabel;
    private javax.swing.JLabel arrivalLocationTxtLabel;
   // private javax.swing.JToggleButton cancelButton;
    private javax.swing.JLabel costTxtLabel;
    private javax.swing.JLabel dateResLabel;
    private javax.swing.JLabel dateTxtLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel offerIdResLabel;
    private javax.swing.JLabel offerIdTxtLabel;
    private javax.swing.JLabel priceResLabel;
    private javax.swing.JLabel recapLabel;
    private javax.swing.JLabel startLocationResLabel;
    private javax.swing.JLabel startLocationTxtLabel;
    private javax.swing.JFrame frame;
    private String[] data;

    private String connectedUser;

    public ShowRecapUI(String user,String[] myData) {
        data = myData;
        connectedUser = user;
        initialisation();
        curlAction();
    }

    @Override
    public void initialisation() {
        frame = new JFrame("BlablaMove : Summary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new javax.swing.JPanel();
       // cancelButton = new javax.swing.JToggleButton();
        acceptButton = new javax.swing.JToggleButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        costTxtLabel = new javax.swing.JLabel();
        offerIdTxtLabel = new javax.swing.JLabel();
        arrivalLocationTxtLabel = new javax.swing.JLabel();
        startLocationTxtLabel = new javax.swing.JLabel();
        startLocationResLabel = new javax.swing.JLabel();
        recapLabel = new javax.swing.JLabel();
        arrivalLocationResLabel = new javax.swing.JLabel();
        offerIdResLabel = new javax.swing.JLabel();
        priceResLabel = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        /*cancelButton.setText("Refused");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });*/

        acceptButton.setText("OK");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });


        costTxtLabel.setText("Price : ");

        offerIdTxtLabel.setText("Offer ID : ");

        arrivalLocationTxtLabel.setText("Arrival Location :");

        startLocationTxtLabel.setText("Start Location :");

        recapLabel.setText("Command summary");

       setRecap();

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(acceptButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                //.addComponent(cancelButton)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(startLocationResLabel)
                                        .addComponent(startLocationTxtLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(arrivalLocationTxtLabel)
                                        .addComponent(arrivalLocationResLabel))
                                .addGap(39, 39, 39))
                        .addComponent(jSeparator4)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(offerIdTxtLabel)
                                                        .addComponent(costTxtLabel))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(priceResLabel)
                                                        .addComponent(offerIdResLabel)))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGap(90, 90, 90)
                                                .addComponent(recapLabel)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(recapLabel)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startLocationTxtLabel)
                                        .addComponent(arrivalLocationTxtLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(startLocationResLabel)
                                        .addComponent(arrivalLocationResLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(offerIdTxtLabel)
                                        .addComponent(offerIdResLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(costTxtLabel)
                                        .addComponent(priceResLabel))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                     //   .addComponent(cancelButton)
                                        .addComponent(acceptButton))
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
                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        //recap=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d
        // "{"event":"confirm-command" ,"data": {"offerID": $oId,"date":"$inDays", "startAddress":"$startAddress", "endAddress":"$endAddress","price":"$price" }}"
        // "localhost:8080/BBM/OFFERS")
        //ioHandler.sendToApp("{ event : confirm-command , data : {}}");
        return true;
    }

    @Override
    public String curlJsonParser() {
        return null;
    }

    private void setRecap(){
        offerIdResLabel.setText(data[4]);
        priceResLabel.setText(data[3]);
        startLocationResLabel.setText(data[1]);
        arrivalLocationResLabel.setText(data[2]);
    }

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        curlAction();
        acceptButton.setSelected(false);
        frame.dispose();
        new MainMenuUI(connectedUser);
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
      //  cancelButton.setSelected(false);
        frame.dispose();
        new MainMenuUI(connectedUser);
    }

}
