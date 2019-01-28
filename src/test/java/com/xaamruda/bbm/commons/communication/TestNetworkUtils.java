package com.xaamruda.bbm.commons.communication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import javax.servlet.http.HttpServletRequest;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.xaamruda.bbm.commons.communication.NetworkUtils;

@RunWith(MockitoJUnitRunner.class)
public class TestNetworkUtils {
	
	@Ignore
	@Test
	public void testGetIpAdress() {
		HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
		String ip = NetworkUtils.getRemoteIpAddress(mockedRequest);
		assertNotNull(ip);	
		assertFalse(ip.isEmpty());
	}

}
