/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    private javax.swing.JLabel numberTransactionTxtLabel;
    private javax.swing.JLabel numberTransactionResLabel;
    private javax.swing.JFrame frame;
    private int transactionID;
    private String connectedUser;
    /**
     * Creates new form TransactionUI
     */
    public TransactionUI(String connect) {
        popupID();
        connectedUser = connect;
    }

    private void popupID(){
        String s = (String)JOptionPane.showInputDialog(
                frame,
                "ID of Transaction",
                "Id Transaction choice",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");


        if ((s != null) && !s.equals("") && (s.length() > 0 && s.chars().allMatch( Character::isDigit))){
            this.transactionID =  Integer.parseInt(s);
            initialisation();
            return;
        }else{
            new  MainMenuUI(connectedUser);
        }
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
    public boolean curlAction() {
        if(!statutTransactionResLabel.getText().equals("Transaction ended succesfully")){
        String url = "http://localhost:8080/BBM/OFFERS";
        try {
            URL object = new URL(url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(curlJsonParser());
            wr.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                /////build String....//////
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println( "response is : " + sb.toString());
                return !("" + sb.toString()).equals("");
            } else {
                System.out.println( "HTTP result : " + HttpResult);
                System.out.println( "response is : " + sb.toString());
                return !("" + sb.toString()).equals("");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    } else {
        return false;
    }
    }

    @Override
    public String curlJsonParser() {
        String res = "";
        if(statutTransactionResLabel.getText().equals("not started")){
            res =  "{\"event\": \"claim-receipt\" ,\"data\": {\"transactionID\": \""+  transactionID + "\"},\"identification\":{\"userID\":\""+ connectedUser +"\"}}";
        }else if(statutTransactionResLabel.getText().equals("Transaction in progress")){
            res =  "{\"event\": \"claim-deposit\" ,\"data\": {\"transactionID\": \""+  transactionID +"\"},\"identification\":{\"userID\":\""+ connectedUser +"\"}}";
        }else if(statutTransactionResLabel.getText().equals(("Waiting for confirmation"))){
            res = "{\"event\": \"confirm-receipt\" ,\"data\": {\"transactionID\": \""+  transactionID +"\"},\"identification\":{\"userID\":\""+ connectedUser +"\"}}";
        } else if(statutTransactionResLabel.getText().equals("Waiting confirmation for Payment")){
            res = "{\"event\": \"confirm-deposit\" ,\"data\": {\"transactionID\": \""+  transactionID +"\"},\"identification\":{\"userID\":\""+ connectedUser +"\"}}";
        }
        System.out.println("Request : " + res);
        return res;
    }

    private void startTransactionButtonActionPerformed(java.awt.event.ActionEvent evt){
        startTransactionButton.setSelected(false);
        curlAction();
        statutTransactionResLabel.setText("Waiting for confirmation");
        curlAction();
        setTransactioLabelToConfirmRecipe();
    }

    private void setTransactioLabelToConfirmRecipe(){
        statutTransactionResLabel.setText("Transaction in progress");
    }

    private void setTransactionLabelToWaitingPayment(){
        statutTransactionResLabel.setText("Waiting confirmation for Payment");
    }

    private void endTransactionButtonActionPerformed(java.awt.event.ActionEvent evt){
        curlAction();
        setTransactionLabelToWaitingPayment();
        curlAction();
        statutTransactionResLabel.setText("Transaction ended succesfully");
        endTransactionButton.setSelected(false);
    }

    private void startingScenario(){

    }
}
