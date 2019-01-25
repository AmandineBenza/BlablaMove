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
public class TestUserIdentificationChecker {

	@Mock
	private IUserService service;
	
	@Before
	@Test
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test() {
		long timeSleep = 2500;
		List<User> users = new ArrayList<>();
		
		User user1 = new User();
		user1.setId(0);
		user1.setMail("mail@mail");
		user1.setIdentified(true);
		
		User user2 = new User();
		user2.setId(1);
		user2.setMail("mail2@mail");
		user2.setIdentified(true);
		
		users.add(user1);
		users.add(user2);
		
		Mockito.when(service.getAllUsers()).thenReturn(users);
		
		for(User user : users)
			assertTrue(user.isIdentified());
		
		UserIdentificationChecker.start(service, timeSleep - 500);
		
		try {
			Thread.sleep(timeSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		for(User user : users)
			assertFalse(user.isIdentified());
	}
	
}
