package com.xaamruda.bbm.communication;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.xaamruda.bbm.commons.exceptions.DatabaseException;
import com.xaamruda.bbm.communication.internal.FlowOrchestrationResult;
import com.xaamruda.bbm.communication.internal.FlowOrchestrator;
import com.xaamruda.bbm.offers.OffersIOHandler;
import com.xaamruda.bbm.users.UsersIOHandler;
import com.xaamruda.bbm.users.model.User;

@RunWith(MockitoJUnitRunner.class)
public class TestFlowOrchestratorUsers {
	
	private FlowOrchestrator flowOrchestrator;
	
	@Mock 
	private UsersIOHandler userIO;
	
	@Mock
	private OffersIOHandler offerIO;
	
	
	@Before
	@Test
	public void setup() throws DatabaseException {
		MockitoAnnotations.initMocks(this);
		flowOrchestrator = new FlowOrchestrator();
		
		Whitebox.setInternalState(flowOrchestrator, "userIO", userIO);
		Whitebox.setInternalState(flowOrchestrator, "offerIO", offerIO);
		
		when(userIO.identifyUserByMailPlusPassword("noisette@mail.com", "fabulousnoisette")).thenReturn(true);
		
		assertEquals(Whitebox.getInternalState(flowOrchestrator, "offerIO"), offerIO);
		assertEquals(Whitebox.getInternalState(flowOrchestrator, "userIO"), userIO);
	}
	
	//------- USERS ENTRY POINTS
	
	@Test
	public void testCreateUser() {		
		String jsonString = "{\"event\": \"create-user\" , \"data\": {\"name\":\"Noisette\", \"mail\":\"noisette@mail.com\",\"phone\":\"0675767778\",\"password\":\"fabulousnoisette\"}}";
		FlowOrchestrationResult res = flowOrchestrator.orchestrateUsersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"User created.\n");	
	}
	
	@Test
	public void testidentifyGoodUser() {	
		String goodUser = "{\"event\": \"identify-user\" ,\"data\":{\"mail\":\"noisette@mail.com\",\"password\":\"fabulousnoisette\"}}";
		FlowOrchestrationResult resGood = flowOrchestrator.orchestrateUsersEntryPoint(goodUser);
		
		assertEquals(resGood.getHttpStatus(),HttpStatus.OK);
		assertEquals(resGood.getContent(),"Successfully identified.\n");	
	}
	
	
	@Test
	public void testidentifyBadUser() {		
		String badUser = "{\"event\": \"identify-user\" ,\"data\":{\"mail\":\"noisette@mail.com\",\"password\":\"!fabulousnoisette\"}}";
		FlowOrchestrationResult resBad = flowOrchestrator.orchestrateUsersEntryPoint(badUser);
		
		assertEquals(resBad.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(resBad.getContent(),"Wrong identifiers ! Please retry.\n");	
	}
	
	
	@Test
	public void testConsultUsers() {	
		User noisette = new User();
		List<User> users = singletonList(noisette);
		when(userIO.retrieveUsers()).thenReturn(users);

		
		String jsonString = "{'event':'consult-users','data':{}}";
		FlowOrchestrationResult res = flowOrchestrator.orchestrateUsersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),users);	
	}
	
	
	@Test
	public void testConsultUser() {	
		User noisette = new User();
		when(userIO.retrieveUser("noisette@mail.com")).thenReturn(noisette);

		
		String jsonString = "{'event':'consult-user','data':{'mail':'noisette@mail.com'}}";
		FlowOrchestrationResult res = flowOrchestrator.orchestrateUsersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),noisette);	
	}
	
}
