package ui.frame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author roody
 */
public class RangePriceUI extends javax.swing.JFrame implements IGlobalUI{

    private javax.swing.JToggleButton acceptButton;
    private javax.swing.JToggleButton cancelButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel maximumPriceResLabel;
    private javax.swing.JLabel maximumPriceTxtLabel;
    private javax.swing.JLabel mediumPriceResLabel;
    private javax.swing.JLabel mediumPriceTxtLabel;
    private javax.swing.JLabel minimumPriceResLabel;
    private javax.swing.JLabel minimumPriceTxtLabel;
    private javax.swing.JLabel yourPriceTxtLabel;
    private javax.swing.JLabel yourPriceResLabel;
    private javax.swing.JFrame frame;
    private javax.swing.JLabel yourNewPriceTxtLabel;
    private javax.swing.JTextField yourNewPriceField;

    private String resp = "";
    private int maxPrice;
    private int minPrice;
    private String connectedUser;
    private String[] offerData;
    private int[] minMax;

    public RangePriceUI(String user,String[] data,int[] minMaximum) {
        offerData = data;
        connectedUser = user;
        minMax = minMaximum;
        initialisation();
    }

    @Override
    public void initialisation() {
        frame = new JFrame("BlablaMove : Price Recommendation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new javax.swing.JPanel();
        yourPriceTxtLabel = new javax.swing.JLabel();
        yourPriceResLabel = new javax.swing.JLabel();
        minimumPriceTxtLabel = new javax.swing.JLabel();
        minimumPriceResLabel = new javax.swing.JLabel();
        maximumPriceResLabel = new javax.swing.JLabel();
        mediumPriceTxtLabel = new javax.swing.JLabel();
        maximumPriceTxtLabel = new javax.swing.JLabel();
        mediumPriceResLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JToggleButton();
        acceptButton = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        yourNewPriceTxtLabel = new javax.swing.JLabel();
        yourNewPriceField = new javax.swing.JTextField();
        yourNewPriceField.setSize(new Dimension(5, 5));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        yourPriceTxtLabel.setText("Your Price is : ");
        yourPriceResLabel.setText(offerData[3]);

        minimumPriceTxtLabel.setText("Minimum Price");

        minimumPriceResLabel.setText(String.valueOf(minMax[0]));

        maximumPriceTxtLabel.setText("Maximum Price");

        maximumPriceResLabel.setText(String.valueOf(minMax[1]));

        mediumPriceTxtLabel.setText("Medium Price");

        mediumPriceResLabel.setText(String.valueOf(minMax[0]+((minMax[1]-minMax[0])/2)));

        yourNewPriceTxtLabel.setText("Choose your final price : ");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
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
                                                .addComponent(mediumPriceResLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                                .addComponent(mediumPriceTxtLabel)
                                                .addGap(87, 87, 87)))
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(maximumPriceTxtLabel)
                                        .addComponent(cancelButton)
                                        .addComponent(maximumPriceResLabel))
                                .addGap(13, 13, 13))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(160, 160, 160)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(yourPriceTxtLabel)
                                        .addComponent(yourPriceResLabel))
                                .addGap(13,13, 13))
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(yourNewPriceTxtLabel)
                                        .addComponent(yourNewPriceField))
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
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addContainerGap(10, Short.MAX_VALUE)
                                .addComponent(yourNewPriceTxtLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yourNewPriceField)
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
    public boolean curlAction() {
        if(!this.yourNewPriceField.getText().equals("") && this.yourNewPriceField.getText().chars().allMatch(Character::isDigit)) {
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
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    System.out.println( "response is : " + sb.toString());
                    br.close();
                    this.resp = sb.toString();
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
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String curlJsonParser() {
    	System.out.println("connectedUser: " + connectedUser);
        /* String res= "{\"event\":\"create-offer\",\"data\":{\"ownerID\":\""+ connectedUser + "\r\", \"price\": \""+yourNewPriceField.getText()+"\r\"" +
                ", \"startCity\":\""+ offerData[0] +"\r\", \"endCity\":\""+ offerData[1] + "\r\", \"capacity\":\""+ offerData[2]+"\r\" }, \"identification\":{\"userID\":\""+ connectedUser+"\r\"}}"; */
        String res= "{\"event\":\"create-offer\",\"data\":{\"ownerID\":\""+ connectedUser + "\", \"price\": \""+yourNewPriceField.getText()+"\"" +
                ", \"startCity\":\""+ offerData[0] +"\", \"endCity\":\""+ offerData[1] + "\", \"capacity\":\""+ offerData[2]+"\" }, \"identification\":{\"userID\":\""+ connectedUser + "\"}}";
        return res;
    }

    private void setPrice(String min,String max,String median){
        maximumPriceResLabel.setText(max);
        minimumPriceResLabel.setText(min);
        mediumPriceResLabel.setText(median);
    }

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if(curlAction()  && !(resp.split("!")[0].equals("Incorrect price "))){
            JOptionPane.showMessageDialog(frame, "Your request has been granted.");
            acceptButton.setSelected(false);
            frame.dispose();
            new MainMenuUI(connectedUser);
        } else {
            JOptionPane.showMessageDialog(frame, "You didn't fill all informations or give malformed information or your price is too high.");
        }
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        cancelButton.setSelected(false);
        frame.dispose();
        new MainMenuUI(connectedUser);
    }
}
