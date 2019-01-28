package com.xaamruda.bbm.integrity;

import org.springframework.stereotype.Component;

import com.xaamruda.bbm.integrity.journaling.engine.JournalingEngine;

@Component
public class IntegrityIOHandler {

	private IntegrityIOHandler() {}
	
	public long addUserJournalEntry(String action, String className, Object... parameters) {
		return JournalingEngine.get(JournalingEngine.USERS_SERVICE)
				.journal(JournalingEngine.USERS_SERVICE, className, action, parameters);
	}
	
	public void endUserJournalEntry(long id) {
		JournalingEngine.get(JournalingEngine.USERS_SERVICE)
				.endJournal(id);
	}
	
	public long addOfferJournalEntry(String action, String className, Object... parameters) {
		return JournalingEngine.get(JournalingEngine.OFFERS_SERVICE)
				.journal(JournalingEngine.OFFERS_SERVICE, className, action, parameters);
	}
	
	public void endOfferJournalEntry(long id) {
		JournalingEngine.get(JournalingEngine.OFFERS_SERVICE)
				.endJournal(id);
	}
}
