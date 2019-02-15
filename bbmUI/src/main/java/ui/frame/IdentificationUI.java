package ui.frame;

import netscape.javascript.JSObject;
import ui.UIIOHandler;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author roody
 */
public class IdentificationUI extends JFrame implements IGlobalUI {

    private JTextField identifiantField;
    private JLabel identifiantLabel;
    private JLabel img;
    private JToggleButton loginButton;
    private JPanel mainPanel;
    private JTextField passwordField;
    private JLabel passwordLabel;
    private JFrame frame;
    private UIIOHandler ioHandler;

    /**
     * Creates new form IdentificationUI
     */
    public IdentificationUI() {
        initialisation();
    }


    @Override
    public void initialisation() {
        frame = new JFrame("BalblaMove : Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new javax.swing.JPanel();
        identifiantLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        identifiantField = new javax.swing.JTextField();
        passwordField = new javax.swing.JTextField();
        loginButton = new javax.swing.JToggleButton();
        img = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        identifiantLabel.setText("Identifiant");

        passwordLabel.setText("Password");

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        //img.setIcon(new javax.swing.ImageIcon(getClass().getResource("./bbmUI/src/main/ressources/img/01.png")));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(loginButton)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(identifiantField)
                                                .addComponent(passwordLabel)
                                                .addComponent(identifiantLabel)
                                                .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 374, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(identifiantLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(identifiantField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(passwordLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(loginButton)
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
        //System.out.println("Identifient : " + identifiantField.getText());
        //System.out.println("Password : " + passwordField.getText());
        if(!identifiantField.getText().equals("") && !passwordField.getText().equals("")) {
            String url = "http://localhost:8080/BBM/USERS";
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
                    return !(sb.toString()).equals("");
                } else {
                    System.out.println( "HTTP result : " + HttpResult);
                    System.out.println( "response is : " + sb.toString());
                    return !(sb.toString()).equals("");
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
        String mailUser = identifiantField.getText();
        String passwordUser  = passwordField.getText();
        // String res= "{\"event\" : \"identify-user\" , \"data\" : {\"mail\" : \"" + mailUser + "\r\" , \"password\" : \"" + passwordUser + "\r\"}}";
        String res= "{\"event\" : \"identify-user\" , \"data\" : {\"mail\" : \"" + mailUser + "\" , \"password\" : \"" + passwordUser + "\"}}";
        return res;
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        loginButton.setSelected(false);
        if(curlAction()){
        //if(true) {
            frame.dispose();
            new MainMenuUI(identifiantField.getText());
        }else{
            JOptionPane.showMessageDialog(frame, "Wrong Password or Login.");
        }
    }
}
