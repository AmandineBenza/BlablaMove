package ui.frame;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author roody
 */
public class ConsultOfferUI extends javax.swing.JFrame implements IGlobalUI {

        private JPanel mainPanel;
        private JFrame frame;
        private ArrayList listOfferId = new ArrayList();
        private JButton cancelButton;
        private String connectedUser;
        private ArrayList<String> data = new ArrayList<String>();
        private ArrayList<JButton> jbuttonList;
        private String response;
        private int click = -1;
        private boolean start = true;

    public ConsultOfferUI(String user) {
            this.connectedUser = user;
            this.response = null;

            initialisation();
        }

        private void getListId(String data) {
            JsonArray res = new Gson().fromJson(data, JsonArray.class);
            for(int i= 0; i < res.size();i++) {
                this.data.add(res.get(i).toString());
            }
        }


        @Override
        public void initialisation() {
            frame = new JFrame("BlablaMove: Offer List Awaiting");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainPanel = new javax.swing.JPanel();
            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cancelButtonActionPerformed();
                }
            });
            curlAction();
            jbuttonList = new ArrayList<JButton>();
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            if(data.size() == 0 ){
                mainPanel.add(new javax.swing.JLabel("No offer awaiting"));
            }else {
                for (int i = 0; i < data.size(); i++) {
                    lineCreation(i);
                    mainPanel.add(new javax.swing.JSeparator());
                }
            }
            mainPanel.add(cancelButton);

            pack();
            frame.setContentPane(this.mainPanel);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.pack();

        }

        private void lineCreation(int i) {
            JsonObject json = new Gson().fromJson(data.get(i), JsonObject.class);
            JPanel linePanel = new JPanel();
            final JButton acceptButton = new JButton("Accept");
            JLabel offerIdTxtLabel = new JLabel("Id :");
            JLabel offerIdResLabel = new JLabel(json.get("offerID").toString()+ "    ");
            JLabel offerPriceTxtLabel = new JLabel("Price :");
            JLabel offerPriceResLabel = new JLabel(json.get("finalPrice").toString());
            JLabel offerBuyeurTxtLabel = new JLabel("Buyer :");
            JLabel offerBuyeurResLabel = new JLabel(json.get("buyerID").toString());

            jbuttonList.add(acceptButton);
            acceptButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    acceptButtonActionPerformed(evt);
                }

            });

            linePanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 0;
            c.gridy = 0;
            linePanel.add(offerIdTxtLabel,c);
            c.gridx = 1;
            c.gridy = 0;
            linePanel.add(offerIdResLabel,c);
            c.gridx = 0;
            c.gridy = 1;
            linePanel.add(offerPriceTxtLabel,c);
            c.gridx = 1;
            c.gridy = 1;
            linePanel.add(offerPriceResLabel,c);
            c.gridx = 0;
            c.gridy = 2;
            linePanel.add(offerBuyeurTxtLabel,c);
            c.gridx = 1;
            c.gridy = 2;
            linePanel.add(offerBuyeurResLabel,c);
            c.weightx = 1;
            c.gridx = 4;
            c.gridy = 0;
            linePanel.add(acceptButton,c);




            mainPanel.add(linePanel);
        }


        @Override
        public JPanel getMainPanel() {
            return this.mainPanel;
        }

        @Override
        public boolean curlAction() {
            String url = "http://localhost:8080/BBM/OFFERS";
            try {
                URL object = new URL(url);
                HttpURLConnection con = (HttpURLConnection) object.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                if(this.start){
                    wr.write(curlJsonParser());
                }else{
                    wr.write(curlJsonParserEnd());
                }
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

                    if(this.start){
                        if( !sb.toString().equals("No offers waiting for confirmation.\n")) {
                            getListId(sb.toString());
                        }
                        this.start = false;
                    }
                    br.close();
                    response = sb.toString();
                    if(sb.toString() == "No offers waiting for confirmation."){

                    }
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
        }



        @Override
        public String curlJsonParser() {
            String res = "{\"event\": \"consult-awaiting-offers\" ,\"data\": {\"ownerID\": \""+connectedUser +"\"}, \"identification\":{\"userID\":\""+ connectedUser +"\"}}";
            System.out.println("Request : " + res);
            return res;
        }

    public String curlJsonParserEnd() {
        JsonObject json = new Gson().fromJson(data.get(click), JsonObject.class);
        String res = "{\"event\": \"confirm-awaiting-offers\" ,\"data\": {\"transactionID\":" + json.get("transactionID").toString() + "}, \"identification\":{\"userID\":\""+ connectedUser +"\"}}";
        System.out.println("Request : " + res);
        return res;
    }

        private String[] parseResponse(){
            JsonObject json = new Gson().fromJson(response, JsonObject.class);
            String id = json.get("offerID").toString();
            String buyeur = json.get("buyeurID").toString();
            String price = json.get("Price").toString();
            String[] res = {id,price,buyeur};
            return res;
        }

        private void cancelButtonActionPerformed() {
            cancelButton.setSelected(false);
            frame.dispose();
            new MainMenuUI(connectedUser);
        }

        private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt){
            int i =0;
            JButton button = jbuttonList.get(i);
            System.out.println(evt.getActionCommand());
            while(evt.getSource() != button) {
                i++;
                button = jbuttonList.get(i);
            }
            button.setSelected(false);
            click = i;
            //if (true) {
            if (curlAction()) {
                JOptionPane.showMessageDialog(frame, "This offer has been accepted!");
                frame.dispose();
                new MainMenuUI(connectedUser);

            } else {
                click = -1;
                JOptionPane.showMessageDialog(frame, "an error occur.");
            }
        }
}
