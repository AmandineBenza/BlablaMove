package ui.frame;

import ui.UIIOHandler;

import javax.swing.*;

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

        img.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ressources/img/01.png"))); // NOI18N

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
    public boolean utility() {
        System.out.println("Identifient : " + identifiantField.getText());
        System.out.println("Password : " + passwordField.getText());
        //curl -s -H "Accept: application/json" -H "Content-type: application/json" -X POST -d
        // "{"event" : "identify-user" , "data" : {"mail" : "$client" , "password" : "DWpasswOrdL"}}"
        // "localhost:8080/BBM/OFFERS"
        if(!identifiantField.getText().equals("") && !passwordField.getText().equals("")){
            //ioHandler.sendToApp("{ event : identify-user , data : { mail : " +  identifiantField.getText() + ", password : " + passwordField.getText() + "}}");
            return true;
        }else{
            return false;
        }

    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        loginButton.setSelected(false);
        if(utility()) {
            frame.dispose();
            new MainMenuUI();
        }
    }
}
