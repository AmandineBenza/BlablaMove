package com.xaamruda.bbm.ui.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IdentificationUI implements IGlobalUI {
	private JPanel panel1;
	private JTextField identifientField;
	private JTextField passwordField;
	private JButton loginButton;

	public IdentificationUI() {
		initialisation();
	}

	@Override
	public void initialisation() {
		GridLayout gridLayout = new GridLayout(5, 1, 10, 10);
		panel1 = new JPanel();
		panel1.setLayout(gridLayout);
		panel1.add(new JLabel("Identifient"));
		identifientField = new JTextField();
		panel1.add(identifientField);
		panel1.add(new JLabel("Password"));
		passwordField = new JTextField();
		panel1.add(passwordField);
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				utility();
			}
		});
		panel1.add(loginButton);
	}

	@Override
	public JPanel getMainPanel() {
		return panel1;
	}

	@Override
	public void utility() {
		System.out.println("Identifient : " + identifientField.getText());
		System.out.println("Password : " + passwordField.getText());
	}
}
