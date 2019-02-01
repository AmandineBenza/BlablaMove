package com.xaamruda.bbm.integrity;

import org.springframework.stereotype.Component;

import com.xaamruda.bbm.integrity.journaling.engine.JournalingEngine;

@Component
public class IntegrityIOHandler {

	private IntegrityIOHandler() {}
	
	public long addUserJournalEntry(String action, String className, Object... parameters) {
		return JournalingEngine.get(JournalingEngine.USERS_MODULE)
				.journal(JournalingEngine.USERS_MODULE, className, action, parameters);
	}
	
	public void endUserJournalEntry(long id) {
		JournalingEngine.get(JournalingEngine.USERS_MODULE)
				.endJournal(id);
	}
	
	public long addOfferJournalEntry(String action, String className, Object... parameters) {
		return JournalingEngine.get(JournalingEngine.OFFERS_MODULE)
				.journal(JournalingEngine.OFFERS_MODULE, className, action, parameters);
	}
	
	public void endOfferJournalEntry(long id) {
		JournalingEngine.get(JournalingEngine.OFFERS_MODULE)
				.endJournal(id);
	}
	
	public long addBillingJournalEntry(String action, String className, Object... parameters) {
		return JournalingEngine.get(JournalingEngine.BILLING_MODULE)
				.journal(JournalingEngine.BILLING_MODULE, className, action, parameters);
	}
	
	public void endBillingJournalEntry(long id) {
		JournalingEngine.get(JournalingEngine.BILLING_MODULE)
			.endJournal(id);
	}
}
