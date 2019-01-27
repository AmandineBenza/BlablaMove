package com.xaamruda.bbm.commons;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import javax.servlet.http.HttpServletRequest;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.xaamruda.bbm.commons.communication.NetworkUtils;

@RunWith(MockitoJUnitRunner.class)
public class TestCommons {
	
	@Ignore
	@Test
	public void testGetIpAdress() {
		HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
		String ip = NetworkUtils.getRemoteIpAddress(mockedRequest);
				
		assertNotEquals(ip, "");
		assertNotNull(ip);	
	}

}
