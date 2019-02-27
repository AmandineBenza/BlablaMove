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
public class OfferCreationUI extends JFrame implements IGlobalUI{

	private static final long serialVersionUID = 6729169453555513706L;
	
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
    private int[] minMax;

    private String connectedUser;

    public OfferCreationUI(String user) {
        connectedUser = user;
        minMax = new int[2];
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
        if (!startLocationField.getText().equals("") && !arrivalLocationField.getText().equals("")
                && !priceField.getText().equals("") && !carCapacityField.getText().equals("") &&
                carCapacityField.getText().chars().allMatch( Character::isDigit )  && priceField.getText().chars().allMatch(Character::isDigit) ) {
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
                    responseParser(sb.toString());
                    // System.out.println( "response is : " + sb.toString());
                    return !(sb.toString()).equals("");

                } else {
                    // System.out.println( "HTTP result : " + HttpResult);
                    // System.out.println( "response is : " + sb.toString());
                    return !(sb.toString()).equals("");
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

    private void responseParser(String rep){
        String third [] = rep.split("\\[");
        String fourth[] = third[1].split("]");
        minMax[0] = Integer.parseInt(fourth[0].split(":")[0].split(" ")[0]);
        minMax[1] = Integer.parseInt(fourth[0].split(":")[1].split(" ")[1]);

    }

    @Override
    public String curlJsonParser(){
        String startLocation = startLocationField.getText();
        String arrivalLocation  = arrivalLocationField.getText();
        String price  = priceField.getText();
        String carCapacity = carCapacityField.getText();
        /* String res= "{\"event\" : \"validate-price\" , \"data\" : {\"data\" : \"x\"}, " +
                "\"filters\": {\"startAddress\": \""+startLocation+"\r\",\"endAddress\": \""+arrivalLocation+"\r\"," +
                "\"maxPrice\": \""+price+"\r\"}, \"identification\":{\"userID\":\""+ connectedUser+"\r\"}}"; */
        String res= "{\"event\" : \"validate-price\" , \"data\" : {\"data\" : \"x\"}, " +
                "\"filters\": {\"startAddress\": \""+startLocation+"\",\"endAddress\": \""+arrivalLocation+"\"," +
                "\"maxPrice\": \""+price+"\"}, \"identification\":{\"userID\":\""+ connectedUser+"\"}}";
        return res;
    }

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        acceptButton.setSelected(false);
        if(curlAction()){
            frame.dispose();
            String[] data = {startLocationField.getText(),arrivalLocationField.getText(),carCapacityField.getText(),priceField.getText()};
            new RangePriceUI(connectedUser,data,minMax);
        } else {
            JOptionPane.showMessageDialog(frame, "You didn't fill all the information or give malformed information.");
        }
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        cancelButton.setSelected(false);
        frame.dispose();
        new MainMenuUI(connectedUser);
    }

}
