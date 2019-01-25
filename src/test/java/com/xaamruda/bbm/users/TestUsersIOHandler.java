package com.xaamruda.bbm.users;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.support.membermodification.MemberModifier;

import com.xaamruda.bbm.users.dbaccess.service.UserService;
import com.xaamruda.bbm.users.identification.UserIdentificator;
import com.xaamruda.bbm.users.info.UserDataManager;
import com.xaamruda.bbm.users.model.User;

public class TestUsersIOHandler {

	@Mock
	private UsersIOHandler usersIOHandler;
	
	@Mock
	private UserService service;
	
	@Mock
	private UserDataManager dataManager;
	
	@Mock
	private UserIdentificator identificator;
	
	@Before
	@Test
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Whitebox.setInternalState(usersIOHandler, "service", service);
		Whitebox.setInternalState(usersIOHandler, "identificator", identificator);
		Whitebox.setInternalState(usersIOHandler, "dataManager", dataManager);
		
		// assertEquals(usersIOHandler.get, actual);
	}
	
	//@Test
	public void testIdentifyUserByMailPlusPassword_success() {
		// setup
		Mockito.when(identificator.identify(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		
		User user = new User();
		user.setIdentified(false);
		user.setId(0);
		
		boolean result = usersIOHandler.identifyUserByMailPlusPassword("test", "test");
		assertEquals(result, user.isIdentified());
		assertEquals(user.isIdentified(), true);
	}
	
	@Test
	public void testIdentifyUserByMailPlusPassword_fail() {
		
	}
	
}
