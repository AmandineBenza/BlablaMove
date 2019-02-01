package ui.frame;

import java.util.ArrayList;

public class MainUI {
	public static void main(String args[]) {
		/*JFrame frame = new JFrame("Log IN");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new IdentificationUI().getMainPanel());
		frame.setVisible(true);
		frame.pack();*/
		//String[] data = {"Nice","Sophia Antipolis","145"};
		//new RangePriceUI("test@email.fr",data);
		//ID,wieght,volume,date,userID
		/*String[] data1 = {"056402864","61","15","10/02/2019","0125678","125","Sophia","Nice"};
		String[] data2 = {"045408964","42","12","10/02/2019","0167954","100","Sophia","Nice"};
		String[] data3 = {"026076584","12","10","10/02/2019","0236125","200","Sophia","Nice"};
		ArrayList data = new ArrayList<String[]>();
		data.add(data1);
		data.add(data2);
		data.add(data3);
		new ShowOfferUI("test@email.fr",data);*/
		new IdentificationUI();
	}
}
