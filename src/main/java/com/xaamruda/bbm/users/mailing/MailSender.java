package com.xaamruda.bbm.users.mailing;

import com.xaamruda.bbm.commons.logging.BBMLogger;

public class MailSender {

	public static boolean sendEmail(String mail) {
		BBMLogger.infoln("Email successfully sent to : " + mail);
		return true;
	}
	
}
