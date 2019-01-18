package ui.frame;

import javax.swing.*;

public interface IGlobalUI {
    
	public void initialisation();

	public JPanel getMainPanel();

	public boolean curlAction();

	public String curlJsonParser();
}
