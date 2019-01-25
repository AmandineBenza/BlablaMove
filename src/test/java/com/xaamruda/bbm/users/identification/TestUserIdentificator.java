package com.xaamruda.bbm.users.identification;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.model.User;

@RunWith(MockitoJUnitRunner.class)
public class TestUserIdentificator {

	private IUserIdentificator userIdentificator;
	
	@Mock
	private IUserService userService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		userIdentificator = new UserIdentificator(userService);
	}
	
	@Test
	public void testIdentify_success() {
		String mail = "mail@mail";
		String pass = "pass";
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		users.add(user);
		
		Mockito.when(userService.getUserByMail(mail)).thenReturn(users);
		assertTrue(userIdentificator.identify(mail, pass));
	}
	
	@Test
	public void testIdentify_fail() {
		String mail = "mail@mail";
		String pass = "pass";
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		users.add(user);
		
		Mockito.when(userService.getUserByMail(mail)).thenReturn(users);
		assertFalse(userIdentificator.identify(mail, "Pass"));
	}
	
	@Test
	public void testIdentify_failNull() {
		Mockito.when(userService.getUserByMail(Mockito.anyString())).thenReturn(null);
		assertFalse(userIdentificator.identify("test", "pass"));
	}
	
	@Test
	public void testIdentify_failEmpty() {
		List<User> users = new ArrayList<>();
		Mockito.when(userService.getUserByMail(Mockito.anyString())).thenReturn(users);
		assertFalse(userIdentificator.identify("test", "Pass"));
	}
	
}
