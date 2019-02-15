package com.xaamruda.bbm.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

import com.xaamruda.bbm.commons.exceptions.DatabaseException;
import com.xaamruda.bbm.integrity.IntegrityIOHandler;
import com.xaamruda.bbm.users.data.IUserDataManager;
import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.identification.IUserIdentificator;
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
	
	@Mock
	private IntegrityIOHandler integrityIOHandler;
	
	@Before
	@Test
	public void setup() {
		MockitoAnnotations.initMocks(this);
		usersIOHandler = new UsersIOHandler();
		
		Whitebox.setInternalState(usersIOHandler, "service", service);
		Whitebox.setInternalState(usersIOHandler, "identificator", identificator);
		Whitebox.setInternalState(usersIOHandler, "dataManager", dataManager);
		Whitebox.setInternalState(usersIOHandler, "integrityIOHandler", integrityIOHandler);

		assertNotNull(service);
		assertNotNull(identificator);
		assertNotNull(dataManager);
		
		assertEquals(Whitebox.getInternalState(usersIOHandler, "service"), service);
		assertEquals(Whitebox.getInternalState(usersIOHandler, "identificator"), identificator);
		assertEquals(Whitebox.getInternalState(usersIOHandler, "dataManager"), dataManager);
		assertEquals(Whitebox.getInternalState(usersIOHandler, "integrityIOHandler"), integrityIOHandler);
	}
	
	@Test
	public void testIdentifyUserByMailPlusPassword_success() throws DatabaseException {
		String mail = "mail@mail";
		String pass = "pass";
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(identificator.identify(mail, pass)).thenReturn(true);
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);
		
		boolean result = usersIOHandler.identifyUserByMailPlusPassword(mail, pass);
		assertEquals(result, user.isIdentified());
		assertTrue(user.isIdentified());
	}
	
	@Test(expected = DatabaseException.class)
	public void testIdentifyUserByMailPlusPassword_databaseException() throws DatabaseException {
		String mail = "mail@mail";
		String pass = "pass";
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(identificator.identify(mail, pass)).thenReturn(true);
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);
		Mockito.doThrow(IllegalStateException.class).when(service).store(Mockito.any(User.class));
		
		usersIOHandler.identifyUserByMailPlusPassword(mail, pass);
	}
	
	@Test
	public void testIdentifyUserByMailPlusPassword_fail() throws DatabaseException {
		String mail = "mail@mail";
		String pass = "pass";
		
		Mockito.when(identificator.identify(mail, pass)).thenReturn(false);
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		boolean result = usersIOHandler.identifyUserByMailPlusPassword(mail, pass);
		assertEquals(result, user.isIdentified());
		assertFalse(user.isIdentified());
	}
	
	@Test
	public void testIdentify_success() throws DatabaseException {
		String mail = "mail@mail";
		String pass = "pass";
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(true);
		users.add(user);
		
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);

		boolean identified = usersIOHandler.isIdentified(mail);  
		assertEquals(user.isIdentified(), identified);
		assertTrue(identified);
	}
	
	@Test
	public void testIdentify_fail() throws DatabaseException {
		String mail = "mail@mail";
		String pass = "pass";
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);

		boolean identified = usersIOHandler.isIdentified(mail);  
		assertEquals(user.isIdentified(), identified);
		assertFalse(identified);	
	}
	
	@Test
	public void testIdentify_empty() throws DatabaseException {
		String mail = "mail@mail";
		Mockito.when(service.getUserByMail(mail)).thenReturn(null);
		boolean identified = usersIOHandler.isIdentified(mail);  
		assertFalse(identified);	
	}
	
	@Test
	public void testRetrieveUsers() throws DatabaseException {
		String mail = "mail@mail";
		String pass = "pass";
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(service.getAllUsers()).thenReturn(users);
		assertEquals(users, usersIOHandler.retrieveUsers());
	}
	
	@Test
	public void testRetrieveUser() throws DatabaseException {
		String mail = "mail@mail";
		String pass = "pass";
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);
		assertEquals(usersIOHandler.retrieveUser(mail), users.get(0));
	}
	
	@Test
	public void makeTransactionTest() throws DatabaseException {
		String ownerID = "owner@mail";
		String buyerID = "buyer@mail";
		int finalPrice = 10;
		
		List<User> owners = new ArrayList<>();
		User owner = new User();
		owner.setPointsAmount(10);
		owner.setMail(ownerID);
		owners.add(owner);
		
		List<User> buyers = new ArrayList<>();
		User buyer = new User();
		buyer.setMail(buyerID);
		buyer.setPointsAmount(22);
		buyers.add(buyer);
		
		Mockito.when(service.getUserByMail(ownerID)).thenReturn(owners);
		Mockito.when(service.getUserByMail(buyerID)).thenReturn(buyers);
		
		int ownerPoints = owner.getPointsAmount();
		int buyerPoints = buyer.getPointsAmount();
		
		usersIOHandler.makeTransaction(ownerID, buyerID, finalPrice);
		
		assertEquals(owner.getPointsAmount().intValue(), ownerPoints + finalPrice);
		assertEquals(buyer.getPointsAmount().intValue(), buyerPoints - finalPrice);
	}
	
	// checking exception is raised
	@Test(expected = DatabaseException.class)
	public void testMakeTransaction_databaseException() throws DatabaseException {
		Mockito.when(service.getUserByMail(Mockito.anyString())).thenThrow(new IllegalStateException());
		usersIOHandler.makeTransaction("id1", "id2", 5);
	}
	
	@Test
	public void testDebit() throws DatabaseException {
		String mail = "mail@mail";
		int finalPrice = 10;
		
		List<User> users = new ArrayList<>();
		User user = new User();
		int userPoints = 10;
		user.setPointsAmount(userPoints);
		user.setMail(mail);
		users.add(user);
		
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);
		
		usersIOHandler.debit(mail, finalPrice);
		assertEquals(user.getPointsAmount().intValue(), userPoints - finalPrice);
	}
	
	// checking exception is raised
	@Test(expected = DatabaseException.class)
	public void testDebit_databaseException() throws DatabaseException {
		Mockito.when(service.getUserByMail(Mockito.anyString())).thenThrow(new IllegalStateException());
		usersIOHandler.debit("id1", 5);
	}
	
	@Test
	public void testCredit() throws DatabaseException {
		String mail = "mail@mail";
		int finalPrice = 10;
		
		List<User> users = new ArrayList<>();
		User user = new User();
		int userPoints = 10;
		user.setPointsAmount(userPoints);
		user.setMail(mail);
		users.add(user);
		
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);
		
		usersIOHandler.credit(mail, finalPrice);
		assertEquals(user.getPointsAmount().intValue(), userPoints + finalPrice);
	}
	
	// checking exception is raised
	@Test(expected = DatabaseException.class)
	public void testCredit_databaseException() throws DatabaseException {
		Mockito.when(service.getUserByMail(Mockito.anyString())).thenThrow(new IllegalStateException());
		usersIOHandler.credit("id2", 5);
	}
	
}