package ui.frame;

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
    private ArrayList<String[]> data;
    private ArrayList<JButton> jbuttonList;
    /**
     * Creates new form ShowOfferUI
     */
    public ShowOfferUI(String user, ArrayList<String[]> getListOffer) {
        this.connectedUser = user;
        this.listOfferId = getListId(getListOffer);
        this.data = getListOffer;
        initialisation();
    }

    private ArrayList getListId(ArrayList<String[]> getListOffer) {
        ArrayList res = new ArrayList();
        for(int i =0; i<getListOffer.size();i++){
            res.add(getListOffer.get(i)[0]);
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
        JPanel linePanel = new JPanel();
        final JButton acceptButton = new JButton("Accept");
        JLabel offerIdTxtLabel = new JLabel("Id :");
        JLabel offerIdResLabel = new JLabel(data.get(i)[0]+ "    ");
        JLabel offerWeightTxtLabel = new JLabel("Weight Max :");
        JLabel offerWeightResLabel = new JLabel(data.get(i)[1]);
        JLabel offerVolumeTxtLabel = new JLabel("Volume :");
        JLabel offerVolumeResLabel = new JLabel(data.get(i)[2]);
        JLabel offerDateTxtLabel = new JLabel("Date :");
        JLabel offerDateResLabel = new JLabel(data.get(i)[3]);
        JLabel offerPriceTxtLabel = new JLabel("Price :");
        JLabel offerPriceResLabel = new JLabel(data.get(i)[5]);

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
                br.close();
                return !("" + sb.toString()).equals(null);
            } else {
                return !("" + sb.toString()).equals(null);
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
        String res = "{\"event\":\"ask-offer\" ,\"data\": {\"offerID\": "+",\"buyerID\": \""+"\",\"weight\": \""+"\", \"volume\":\""+"\", \"date\":\""+"\" }," +
                " \"identification\":{\"userID\":\""+"\"}}";
        return res;
    }

    private void cancelButtonActionPerformed() {
        cancelButton.setSelected(false);
        frame.dispose();
        new MainMenuUI(connectedUser);
    }

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt){
            if (true) {
                int i =0;
                JButton button = jbuttonList.get(i);
                System.out.println(evt.getActionCommand());
                while(evt.getSource() != button) {
                    i++;
                    button = jbuttonList.get(i);
                }
                button.setSelected(false);
                frame.dispose();
                new ShowRecapUI(connectedUser,data.get(i));

            } else {
                JOptionPane.showMessageDialog(frame, "an error occur.");
            }
        }


}
