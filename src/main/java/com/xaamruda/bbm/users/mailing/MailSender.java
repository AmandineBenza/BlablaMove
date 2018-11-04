package com.xaamruda.bbm.users.mailing;

import com.xaamruda.bbm.commons.logging.BBMLogger;

public class MailSender implements IMailSender {

	static public void sendEmail(String mail) {
		BBMLogger.infoln("Email successfully sent to : " + mail);
	}
	
}
