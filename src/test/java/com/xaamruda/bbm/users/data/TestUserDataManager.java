package com.xaamruda.bbm.users.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.model.User;

public class TestUserDataManager {

	private UserDataManager dataManager;
	
	@Mock
	private IUserService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		dataManager = new UserDataManager(service);
	}
	
	@Test
	public void testUserStoring() {
		int initialUserPoints = 0;
		User user = new User();
		user.setPointsAmount(initialUserPoints);
		
		User storageResult = dataManager.storeNewUser(user);
		assertEquals(storageResult, user);
		assertNotEquals(initialUserPoints, user.getPointsAmount().intValue());
	}

}
