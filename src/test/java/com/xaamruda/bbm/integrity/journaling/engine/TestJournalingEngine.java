package com.xaamruda.bbm.integrity.journaling.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ConfigurableApplicationContext;

import com.xaamruda.bbm.commons.files.FileUtils;
import com.xaamruda.bbm.commons.spring.context.ContextProvider;
import com.xaamruda.bbm.commons.testing.UnitTestingUtils;
import com.xaamruda.bbm.integrity.journaling.engine.JournalingEngine;
import com.xaamruda.bbm.users.model.User;

public class TestJournalingEngine {

	private final static String USER_NAME = "Noukoutou";
	
	private User user;
	
	@Mock
	private ConfigurableApplicationContext context;
	
	private JournalingEngine journalingEngine;
	
	@Before
	@Test
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		UnitTestingUtils.setStaticField(ContextProvider.class, "configurableApplicationContext", context);
		Mockito.when(context.getBean(TestJournalingEngine.class)).thenReturn(this);
		
		assertEquals(UnitTestingUtils.getStaticField(ContextProvider.class, "configurableApplicationContext"), context);
		assertEquals(context.getBean(TestJournalingEngine.class), this);
		
		user = new User();
		user.setName(null);
		
		String path = "./src/test/resources/com/xaamruda/bbm/integrity/journaling/engine/testJournal.ddb";
		journalingEngine = new JournalingEngine(path);
		UnitTestingUtils.setStaticField(JournalingEngine.class, "usersJournalEngine", journalingEngine);
		
		assertEquals(journalingEngine, UnitTestingUtils.getStaticField(JournalingEngine.class, "usersJournalEngine"));
		assertEquals(path, journalingEngine.getJournalFilePath());
	}
	
	@Test
	public void testJournalingEngineStart() {
		File file = new File(journalingEngine.getJournalFilePath());
		
		if(file.exists()) {
			file.delete();
		}
		
		// testing before
		assertFalse(file.exists());
		assertFalse(journalingEngine.isInSafeStartMode());
		assertEquals(journalingEngine.getMaxId(), JournalingEngine.ERROR_CODE);
		
		// logic
		journalingEngine.start();
		
		// testing after
		assertTrue(file.exists());
		assertTrue(journalingEngine.isInSafeStartMode());
		assertEquals(journalingEngine.getMaxId(), 0L);
		
		file.delete();
	}
	
	@Test
	public void testJournalingEngineAnalyze() {
		// start enigne
		journalingEngine.start();
		
		// content to put into journal
		String fileContent = "0;;com.xaamruda.bbm.integrity.journaling.engine.TestJournalingEngine;setUserTitleTest;;TODO";
		
		// filling journaling engine file
		FileUtils.writeTo(journalingEngine.getJournalFilePath(), fileContent, false);

		// before test
		assertNull(user.getName());
		
		// perform logic
		journalingEngine.analyze();

		// check call has been made
		assertEquals(user.getName(), USER_NAME);
	}
	
	// Tool method to be used in reflection testing
	public void setUserTitleTest() {
		user.setName(USER_NAME);
	}
	
	@Test
	public void testJournalingEngineJournaling() {
		// prepare test
		File file = new File(journalingEngine.getJournalFilePath());
		
		if(file.exists()) {
			file.delete();
		}
		
		journalingEngine.start();
		
		// launch logic
		long journalId = journalingEngine.journal(null, "com.xaamruda.bbm.integrity.journaling.engine.TestJournalingEngine", "setUserTitleTest");
		
		// post logic testing
		assertNotEquals(journalId, JournalingEngine.ERROR_CODE);
		assertEquals(journalId, 0L);
		
		String journalContent = FileUtils.fileContentToString(journalingEngine.getJournalFilePath());
		assertEquals(journalContent.trim(), "0;;com.xaamruda.bbm.integrity.journaling.engine.TestJournalingEngine;setUserTitleTest;;TODO");
	}
	
	@Test
	public void testJournalingEngineEndJournaling() {
		// prepare test
		File file = new File(journalingEngine.getJournalFilePath());
		
		if(file.exists()) {
			file.delete();
		}
		
		journalingEngine.start();
		
		// content to put into journal
		String fileContent = "0;;com.xaamruda.bbm.integrity.journaling.engine.TestJournalingEngine;setUserTitleTest;;TODO";
		
		// filling journaling engine file
		FileUtils.writeTo(journalingEngine.getJournalFilePath(), fileContent, false);
		
		// launch logic
		journalingEngine.endJournal(0);
		
		// post testing
		String journalContent = FileUtils.fileContentToString(journalingEngine.getJournalFilePath()).trim();
		
		assertNotEquals(fileContent, journalContent);
		assertEquals(journalContent.substring(0, journalContent.length() - 4),
				fileContent.substring(0, fileContent.length() - 4));
		assertEquals(journalContent.substring(journalContent.length() - 4, journalContent.length()), "DONE");
	}
	
}