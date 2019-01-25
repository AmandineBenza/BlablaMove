package com.xaamruda.bbm.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.MockitoJUnitRunner;

import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.identification.IUserIdentificator;
import com.xaamruda.bbm.users.info.IUserDataManager;
import com.xaamruda.bbm.users.model.User;

@RunWith(MockitoJUnitRunner.class)
public class TestUsersIOHandler {

	private UsersIOHandler usersIOHandler;
	
	@Mock
	private IUserService service;
	
	@Mock
	private IUserDataManager dataManager;
	
	@Mock
	private IUserIdentificator identificator;
	
	@Before
	@Test
	public void setup() {
		MockitoAnnotations.initMocks(this);
		usersIOHandler = new UsersIOHandler();
		
		Whitebox.setInternalState(usersIOHandler, "service", service);
		Whitebox.setInternalState(usersIOHandler, "identificator", identificator);
		Whitebox.setInternalState(usersIOHandler, "dataManager", dataManager);

		assertNotNull(service);
		assertNotNull(identificator);
		assertNotNull(dataManager);
		
		assertEquals(Whitebox.getInternalState(usersIOHandler, "service"), service);
		assertEquals(Whitebox.getInternalState(usersIOHandler, "identificator"), identificator);
		assertEquals(Whitebox.getInternalState(usersIOHandler, "dataManager"), dataManager);
	}
	
	@Test
	public void testIdentifyUserByMailPlusPassword_success() {
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(identificator.identify(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.when(service.getUserByMail(Mockito.anyString())).thenReturn(users);
		
		boolean result = usersIOHandler.identifyUserByMailPlusPassword("test", "test");
		assertEquals(result, user.isIdentified());
		assertTrue(user.isIdentified());
	}
	
	@Test
	public void testIdentifyUserByMailPlusPassword_fail() {
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(identificator.identify(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		
		boolean result = usersIOHandler.identifyUserByMailPlusPassword("test", "test");
		assertEquals(result, user.isIdentified());
		assertEquals(user.isIdentified(), false);
	}
	
}
