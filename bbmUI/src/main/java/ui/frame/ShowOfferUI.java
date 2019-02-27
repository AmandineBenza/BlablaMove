package ui.frame;

import com.google.gson.Gson;
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
public class ShowOfferUI extends javax.swing.JFrame implements IGlobalUI {

    private JPanel mainPanel;
    private JFrame frame;
    private ArrayList listOfferId;
    private JButton cancelButton;
    private String connectedUser;
    private ArrayList<String> data;
    private ArrayList<JButton> jbuttonList;
    private String response;
    private int click = -1;
    private String from;
    private String to;
    private String weight;
    private String volume;

    public ShowOfferUI(String user, ArrayList<String> getListOffer,String[] oldData) {
        this.connectedUser = user;
        this.listOfferId = getListId(getListOffer);
        this.response = null;
        this.data = getListOffer;
        this.from = oldData[0];
        this.to = oldData[1];
        this.weight = oldData[3];
        this.volume = oldData[2];
        initialisation();
    }

    private ArrayList getListId(ArrayList<String> getListOffer) {
        ArrayList res = new ArrayList();
        for(int i =0; i<getListOffer.size();i++){
            JsonObject json = new Gson().fromJson(getListOffer.get(i), JsonObject.class);
            res.add(json.get("offerID").toString());
        }
        return res;
    }


    @Override
    public void initialisation() {
        frame = new JFrame("BlablaMove: Offer List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new javax.swing.JPanel();
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed();
            }
        });
        jbuttonList = new ArrayList<JButton>();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        for (int i=0; i < listOfferId.size();i++) {
            lineCreation(i);
            mainPanel.add(new javax.swing.JSeparator());
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
        JLabel offerWeightTxtLabel = new JLabel("Weight Max :");
        JLabel offerWeightResLabel = new JLabel(json.get("capacity").toString());
        JLabel offerVolumeTxtLabel = new JLabel("Volume :");
        JLabel offerVolumeResLabel = new JLabel(json.get("capacity").toString());
        JLabel offerDateTxtLabel = new JLabel("Date :");
        JLabel offerDateResLabel = new JLabel(json.get("offerID").toString());
        JLabel offerPriceTxtLabel = new JLabel("Price :");
        JLabel offerPriceResLabel = new JLabel(json.get("price").toString());

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
        c.gridx = 2;
        c.gridy = 0;
        linePanel.add(offerWeightTxtLabel,c);
        c.gridx = 3;
        c.gridy = 0;
        linePanel.add(offerWeightResLabel,c);
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 0;
        linePanel.add(acceptButton,c);
        c.gridx = 0;
        c.gridy = 1;
        linePanel.add(offerVolumeTxtLabel,c);
        c.gridx = 1;
        c.gridy = 1;
        linePanel.add(offerVolumeResLabel,c);
        c.gridx = 2;
        c.gridy = 1;
        linePanel.add(offerDateTxtLabel,c);
        c.gridx = 3;
        c.gridy = 1;
        linePanel.add(offerDateResLabel,c);
        c.gridx = 0;
        c.gridy = 2;
        linePanel.add(offerPriceTxtLabel,c);
        c.gridx = 1;
        c.gridy = 2;
        linePanel.add(offerPriceResLabel,c);



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
                response = sb.toString();
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
        JsonObject json = new Gson().fromJson(data.get(click), JsonObject.class);
       String res = "{\"event\":\"ask-offer\" ,\"data\": {\"offerID\": \""+ json.get("offerID").toString() +"\",\"buyerID\": " + connectedUser +
                ",\"weight\": \""+ this.weight +"\", \"volume\":\""+ this.volume +"\", \"date\":\""+ json.get("offerID").toString() +
                "\" }," + " \"identification\":{\"userID\":\""+ connectedUser +"\"}}";
       // String res = "{\"event\":\"ask-offer\" ,\"data\": {\"offerID\": "+ json.get("offerID").toString() +",\r\"buyerID\": \""+ json.get("ownerID").toString() +
       //         "\r\",\"weight\": \""+ json.get("capacity").toString() +"\r\", \"volume\":\""+ json.get("capacity").toString() +"\r\", \"date\":\""+ json.get("offerID").toString() +
       //         "\r\" }," + " \"identification\":{\"userID\":\""+ connectedUser +"\r\"}}";
        System.out.println("Request : " + res);
        return res;
    }

    private String[] parseResponse(){
        JsonObject json = new Gson().fromJson(response, JsonObject.class);
        String date = json.get("dateBeforeOrder").toString();
        String start = this.from;
        String end = this.to;
        String price = json.get("finalPrice").toString().split("\"")[1];
        String offerID= json.get("offerID").toString();
        String[] res = {date,start,end,price,offerID};
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
        if (curlAction()) {
            frame.dispose();
            new ShowRecapUI(connectedUser,parseResponse());

        } else {
            click = -1;
            JOptionPane.showMessageDialog(frame, "an error occur.");
        }
    }


}
