package com.xaamruda.bbm.integrity.ddos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import com.xaamruda.bbm.commons.testing.UnitTestingUtils;
import com.xaamruda.bbm.integrity.ddos.dbaccess.IAuthorizationService;

/**
 * Testing the DDOS guard.<br>
 * Simulating multiples IPs.<br>
 *
 * <h2>TODO</h2><br>
 * <ul>
 * 	<li>More tests <br></li>
 * 	<li>Threads tests<br></li>
 * </ul>
 */
public class TestDDOSGuard {

	@Mock
	private IAuthorizationService service;
	
	private Map<String, DDOSMetadata> cache;
	
	private DDOSGuard guard;
	
	@Before
	@Test
	public void setup() {
		MockitoAnnotations.initMocks(this);
		guard = new DDOSGuard();
		Whitebox.setInternalState(guard, "ddosService", service);
		cache = new HashMap<>();
		UnitTestingUtils.setStaticField(DDOSGuard.class, "cache", cache);
		
		assertEquals(Whitebox.getInternalState(guard, "ddosService"), service);
		assertEquals(UnitTestingUtils.getStaticField(DDOSGuard.class, "cache"), cache);
	}
	
	@Test
	public void testCheckNewRequestAuthorization_success() {
		// setup
		String ip1 = "10.20.30";
		List<DDOSMetadata> metadatas = new ArrayList<>();
		metadatas.add(new DDOSMetadata(ip1, false, 0L, 0));
		Mockito.when(service.getAll()).thenReturn(metadatas);
		
		// load cache
		guard.loadCacheFromDatabase();
		
		// test
		assertTrue(guard.checkNewRequestAuthorization(metadatas.get(0).getRequestRemoteAddress()));
		assertEquals(cache.get(ip1), metadatas.get(0));
		assertEquals(cache.get(ip1).getRequestsCount(), 1);
	}
	
	@Test
	public void testCheckNewRequestAuthorization_fail() {
		// setup
		String ip1 = "10.20.30";
		int boundRequestsOneMinute = 3;
		List<DDOSMetadata> metadatas = new ArrayList<>();
		metadatas.add(new DDOSMetadata(ip1, false, 0L, boundRequestsOneMinute - 1));
		Mockito.when(service.getAll()).thenReturn(metadatas);
		Whitebox.setInternalState(guard, "boundRequestBeforeBanInOneMinute", boundRequestsOneMinute);
		
		// load cache
		guard.loadCacheFromDatabase();
		
		// test
		assertFalse(guard.checkNewRequestAuthorization(metadatas.get(0).getRequestRemoteAddress()));
		assertEquals(cache.get(ip1), metadatas.get(0));
		// when false, meta request count is not updated
		assertEquals(cache.get(ip1).getRequestsCount(), boundRequestsOneMinute - 1);
	}
	
}