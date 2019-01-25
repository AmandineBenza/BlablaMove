package com.xaamruda.bbm.communication;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;


import com.xaamruda.bbm.billing.BillingIOHandler;
import com.xaamruda.bbm.communication.internal.FlowOrchestrationResult;
import com.xaamruda.bbm.communication.internal.FlowOrchestrator;
import com.xaamruda.bbm.integrity.IntegrityIOHandler;
import com.xaamruda.bbm.offers.OffersIOHandler;
import com.xaamruda.bbm.roads.RoadsIOHandler;
import com.xaamruda.bbm.users.UsersIOHandler;
import com.xaamruda.bbm.users.model.User;
import static java.util.Collections.singletonList;

import static org.mockito.Mockito.when;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TestFlowOrchestrator {
	
	private FlowOrchestrator flowOrchestrator;
	
	@Mock 
	private UsersIOHandler userIO;
	
	@Mock
	private BillingIOHandler billingIO;
	
	@Mock
	private OffersIOHandler offerIO;
	
	@Mock
	private RoadsIOHandler roadsIO;
	
	@Mock
	private IntegrityIOHandler integrityIOHandler;
	
	@Before
	@Test
	public void setup() {
		MockitoAnnotations.initMocks(this);
		flowOrchestrator = new FlowOrchestrator();
		Whitebox.setInternalState(flowOrchestrator, "userIO", userIO);
		Whitebox.setInternalState(flowOrchestrator, "billingIO", billingIO);
		Whitebox.setInternalState(flowOrchestrator, "offerIO", offerIO);
		Whitebox.setInternalState(flowOrchestrator, "roadsIO", roadsIO);
		Whitebox.setInternalState(flowOrchestrator, "integrityIOHandler", integrityIOHandler);
		
		
		when(userIO.identifyUserByMailPlusPassword("noisette@mail.com", "fabulousnoisette")).thenReturn(true);
		//when(userIO.isIdentified("noisette@mail.com")).thenReturn(true);

	}
	
	@Test
	public void testCreateUser() {		
		String jsonString = "{\"event\": \"create-user\" , \"data\": {\"name\":\"Noisette\", \"mail\":\"noisette@mail.com\",\"phone\":\"0675767778\",\"password\":\"fabulousnoisette\"}}";
		FlowOrchestrationResult res = flowOrchestrator.orchestrateUsersEntryPoint(jsonString);
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"User created.\n");	
	}
	
	@Test
	public void testidentifyUser() {		
		String badUser = "{\"event\": \"identify-user\" ,\"data\":{\"mail\":\"noisette@mail.com\",\"password\":\"!fabulousnoisette\"}}";
		FlowOrchestrationResult resBad = flowOrchestrator.orchestrateUsersEntryPoint(badUser);
		assertEquals(resBad.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(resBad.getContent(),"Wrong identifiers ! Please retry.\n");	
		
		
		String goodUser = "{\"event\": \"identify-user\" ,\"data\":{\"mail\":\"noisette@mail.com\",\"password\":\"fabulousnoisette\"}}";
		FlowOrchestrationResult resGood = flowOrchestrator.orchestrateUsersEntryPoint(goodUser);
		assertEquals(resGood.getHttpStatus(),HttpStatus.OK);
		assertEquals(resGood.getContent(),"Successfully identified.\n");	
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
