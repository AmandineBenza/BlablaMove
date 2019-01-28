package com.xaamruda.bbm.commons.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestUnitTestingUtils {

	private static Tool tool;
	
	private static class Tool {
		private String title;
		
		public Tool(String title) {
			this.title = title;
		}
	}
	
	@Before
	public void setup() {
		tool = new Tool("test");
	}
	
	@Test
	public void testStaticFieldSetting() {
		assertEquals(tool.title, "test");
		Tool newTool = new Tool("Netero");
		boolean success = UnitTestingUtils.setStaticField(TestUnitTestingUtils.class, "tool", newTool);
		assertTrue(success);
		assertEquals(tool, newTool);
		assertEquals(tool.title, newTool.title);
		assertEquals(tool.title, "Netero");
	}
	
	@Test
	public void testStaticFieldRetrieve() {
		Tool newTool = new Tool("Netero");
		boolean success = UnitTestingUtils.setStaticField(TestUnitTestingUtils.class, "tool", newTool);
		assertTrue(success);
		Tool retrievedInstance = UnitTestingUtils.getStaticField(TestUnitTestingUtils.class, "tool"); 
		assertNotNull(retrievedInstance);
		assertEquals(newTool, retrievedInstance);
	}
	
}
