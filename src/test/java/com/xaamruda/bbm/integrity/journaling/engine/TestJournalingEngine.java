package com.xaamruda.bbm.integrity.journaling.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.xaamruda.bbm.commons.testing.UnitTestingUtils;
import com.xaamruda.bbm.integrity.journaling.engine.JournalingEngine;

public class TestJournalingEngine {

	private JournalingEngine journalingEngine;
	
	@Before
	@Test
	public void setup() {
		String path = "./src/test/resources/com.xaamruda.bbm.integrity.testJournal.ddb";
		journalingEngine = new JournalingEngine(path);
		UnitTestingUtils.setStaticField(JournalingEngine.class, "usersJournalEngine", journalingEngine);
		assertEquals(journalingEngine, UnitTestingUtils.getStaticField(JournalingEngine.class, "usersJournalEngine"));
		assertEquals(path, journalingEngine.journalFilePath);
	}
	
	@Ignore
	@Test
	public void testJournalingEngineStart() {
		
	}
	
	@Ignore
	@Test
	public void testJournalingEngineAnalyze() {
		
	}
	
	@Ignore
	@Test
	public void testJournalingEngineJournaling() {
		
	}
	
	@Ignore
	@Test
	public void testJournalingEngineEndJournaling() {
		
	}
}
