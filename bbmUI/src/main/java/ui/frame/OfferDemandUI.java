package ui.frame;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
    private ArrayList response;

    private String connectedUser;

    /**
     * Creates new form OfferDemandUI
     */
    public OfferDemandUI(String user) {
        connectedUser = user;
        response = new ArrayList<String[]>();
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
    public boolean curlAction() {
        //System.out.println("Start Location : " + startLocationField.getText());
        //System.out.println("Arrival Location : " + arrivalLocationField.getText());
        //System.out.println("Max Price : " + maximumPointSpendField.getText());
        //System.out.println("Weight : " + weightField.getText());
        //System.out.println("Size : " + sizeField.getText());
        //searchResultList=$(curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d
        // "{"event":"consult-offers","data": {"weight": "$bedW", "volume":"$bedV", "date":"$inDays" },
        // "filters": {"weight": "$bedV","startAddress": "$startAddress","endAddress": "$endAddress","maxPrice": "10000"}}"
        // "localhost:8080/BBM/OFFERS")
        if(!startLocationField.getText().equals("") && !arrivalLocationField.getText().equals("") && !maximumPointSpendField.getText().equals("")
                && !weightField.getText().equals("") && !sizeField.getText().equals("") &&
        maximumPointSpendField.getText().chars().allMatch( Character::isDigit ) &&  sizeField.getText().chars().allMatch( Character::isDigit )
                && weightField.getText().chars().allMatch( Character::isDigit)) {
            String url = "http://localhost:8080/BBM/OFFERS";
            try {
                URL object = new URL(url);

                HttpURLConnection con = (HttpURLConnection) object.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("POST");
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
                    System.out.println( "response is : " + sb.toString());
                    br.close();
                    setResponses(sb.toString());
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
        }
        return false;
    }

    @Override
    public String curlJsonParser() {
        String weight = this.weightField.getText();
        String volume = this.sizeField.getText();
        int date = 5;
        String startAddress = this.startLocationField.getText();
        String endAddress = this.arrivalLocationField.getText();
        String maxPrice = this.maximumPointSpendField.getText();
        //String res = "{\"event\":\"consult-offers\",\"data\":{\"weight\":\"" + weight + "\",\"volume\":\""+volume+
        //        "\",\"date\":\""+ date+"\"},\"filters\":{\"weight\":\""+volume+"\",\"startAddress\":\""+startAddress+
        //        "\r\",\"endAddress\":\""+endAddress+"\r\",\"maxPrice\":\""+maxPrice+"\"},\"identification\":{\"userID\":\""+connectedUser+"\r\"}}";
        String res = "{\"event\":\"consult-offers\",\"data\":{\"weight\":\"" + weight + "\",\"volume\":\""+volume+
                "\",\"date\":\""+ date+"\"},\"filters\":{\"weight\":\""+volume+"\",\"startAddress\":\""+startAddress+
                "\",\"endAddress\":\""+endAddress+"\",\"maxPrice\":\""+maxPrice+"\"},\"identification\":{\"userID\":\""+connectedUser+"\"}}";

        return res;
    }

    private void setResponses(String data){
        String[] res = {};
        
        String[] data1 = {"056402864","61","15","10/02/2019","0125678","125","Sophia","Nice"};
        String[] data2 = {"045408964","42","12","10/02/2019","0167954","100","Sophia","Nice"};
        String[] data3 = {"026076584","12","10","10/02/2019","0236125","200","Sophia","Nice"};
        response.add(data1);
        response.add(data2);
        response.add(data3);
        response.add(res);
    }

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if(curlAction()) {
        //if(true){
            acceptButton.setSelected(false);
            frame.dispose();
            //setResponses("");
            //String[] newData = {"o2501191000",this.maximumPointSpendField.getText(),"25/01/19",this.startLocationField.getText(),this.arrivalLocationField.getText()};
            new ShowOfferUI(connectedUser,response);
        }else{
            JOptionPane.showMessageDialog(frame, "You didn't fill all informations.");
        }
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {
        cancelButton.setSelected(false);
        frame.dispose();
        new MainMenuUI(connectedUser);
    }
}
