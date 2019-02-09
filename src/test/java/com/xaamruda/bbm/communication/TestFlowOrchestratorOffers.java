package com.xaamruda.bbm.communication;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.commons.exceptions.DatabaseException;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.communication.internal.FlowOrchestrationResult;
import com.xaamruda.bbm.communication.internal.FlowOrchestrator;
import com.xaamruda.bbm.offers.OffersIOHandler;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.users.UsersIOHandler;

@RunWith(MockitoJUnitRunner.class)
public class TestFlowOrchestratorOffers {
	
	private FlowOrchestrator flowOrchestrator;
	
	@Mock 
	private UsersIOHandler userIO;
	
	@Mock
	private OffersIOHandler offerIO;
	
	
	@Before
	@Test
	public void setup() {
		MockitoAnnotations.initMocks(this);
		flowOrchestrator = new FlowOrchestrator();
		
		Whitebox.setInternalState(flowOrchestrator, "userIO", userIO);
		Whitebox.setInternalState(flowOrchestrator, "offerIO", offerIO);
				
		assertEquals(Whitebox.getInternalState(flowOrchestrator, "offerIO"), offerIO);
		assertEquals(Whitebox.getInternalState(flowOrchestrator, "userIO"), userIO);
	}
	
	
	//------- OFFERS ENTRY POINTS BY IDENTIFIED USERS
	
	
	@Test
	public void testValidatePriceByIdentifierUser() throws DatabaseException {		
		String jsonString = "{\"event\" : \"validate-price\" , \"data\" : {\"data\" : \"x\"}, \"filters\": {\"startAddress\": \"Nice\",\"endAddress\": \"Sophia\",\"maxPrice\": \"0\"}, \"identification\":{\"userID\":\"noisette@mail.com\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
		String filtersString = jsonObject.get("filters").toString();
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(true);
		when(offerIO.validatePrice(filtersString,dataString)).thenReturn("OK");
		
		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"OK");		
	}

	
	@Test
	public void testCreateOfferByIdentifierUser() throws DatabaseException {
		String jsonString = "{\"event\":\"create-offer\",\"data\":{\"ownerID\":\"noisette@mail.com\", \"price\": \"21\", \"startCity\":\"Nice\", \"endCity\":\"Sophia\", \"capacity\":\"1\" },\"identification\":{\"userID\":\"noisette@mail.com\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(true);
		when(offerIO.postNewOffer(dataString)).thenReturn("OK");

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"OK");		
	}
	
	
	@Test
	public void testAskOfferByIdentifierUser() throws DatabaseException {
		String jsonString = 	"{\"event\":\"ask-offer\" ,\"data\": {\"offerID\": \"IDOFFER\",\"buyerID\": \"reglisse@mail.com\",\"weight\": \"12\", \"volume\":\"12\", \"date\":\"01/10/2018\" }, \"identification\":{\"userID\":\"reglisse@mail.com\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(true);
		when(offerIO.askForValidation(dataString)).thenReturn("OK");

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"OK");		
	}
	
	
	@Test
	public void testConsultOffersByIdentifierUser() throws DatabaseException {
		String jsonString =  "{\"event\":\"consult-offers\",\"data\": {\"weight\": \"12\", \"volume\":\"12\", \"date\":\"01/10/2018\" },\"filters\": {\"weight\": \"12\",\"startAddress\": \"Nice\",\"endAddress\": \"Sophia\",\"maxPrice\": \"10000\"},\"identification\":{\"userID\":\"reglisse@mail.com\"}}";

		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
		String filtersString = jsonObject.get("filters").toString();
		
		PostedOffer noisetteOffer = new PostedOffer();
		List<PostedOffer> offers = singletonList(noisetteOffer);
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(true);
		when(offerIO.retrieveOffers(filtersString, dataString)).thenReturn(offers);

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),offers);		
	}
	
	
	@Test
	public void testConsultAwaitingOffersByIdentifierUser() throws DatabaseException {
		String jsonString = "{\"event\": \"consult-awaiting-offers\" ,\"data\": {\"ownerID\": \noisette@mail.com\"}, \"identification\":{\"userID\":\"noisette@mail.com\"}}";

		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(true);
		when(offerIO.consultAwaitingOffers(dataString)).thenReturn("OK");

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"OK");		
	}
	
	
	@Test
	public void testConfirmAwaitingOffersByIdentifierUser() throws DatabaseException {
		String jsonString = "{\"event\": \"confirm-awaiting-offers\" ,\"data\": {\"transactionID\": IDOFFER}, \"identification\":{\"userID\":\"noisette@mail.com\"}}";

		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(true);
		when(offerIO.confirmAwaitingOffer(dataString)).thenReturn("OK");

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"OK");		
	}
	
	
	@Test
	public void testClaimReceiptByIdentifierUser() throws DatabaseException {
		String jsonString = "{\"event\": \"claim-receipt\" ,\"data\": {\"transactionID\": TRANSACTIONID}, \"identification\":{\"userID\":\"reglisse@mail.com\"}}";

		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(true);
		when(offerIO.claimReceipt(dataString)).thenReturn("OK");

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"OK");		
	}
	
	
	@Test
	public void testConfirmReceiptByIdentifierUser() throws DatabaseException {
		String jsonString = "{\"event\": \"confirm-receipt\" ,\"data\": {\"transactionID\": \"TRANSACTIONID\"}, \"identification\":{\"userID\":\"noisette@mail.com\"}}";

		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(true);
		when(offerIO.confirmReceipt(dataString)).thenReturn("OK");

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"OK");		
	}
	
	@Test
	public void testClaimDepositByIdentifierUser() throws DatabaseException {
		String jsonString = "{\"event\": \"claim-deposit\" ,\"data\": {\"transactionID\": $offerIdD}, \"identification\":{\"userID\":\"$driver\"}}";

		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(true);
		when(offerIO.claimDeposit(dataString)).thenReturn("OK");

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"OK");		
	}
	
	
	@Test
	public void testConfirmDepositByIdentifierUser() throws DatabaseException {
		String jsonString = "{\"event\": \"confirm-deposit\" ,\"data\": {\"transactionID\": \"TRANSACTIONID\"}, \"identification\":{\"userID\":\"reglisse@mail.com\"}}";

		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(true);
		when(offerIO.confirmDeposit(dataString)).thenReturn("OK");

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(),"OK");		
	}
	
	@Ignore
	@Test
	public void testConfirmCommand() {
		String jsonString = "{\"event\": \"confirm-command\" ,\"data\": {\"data\": \"x\"}}";

		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		JsonElement data = jsonObject.get("data");
		String dataString = data.toString();
				
		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.OK);
		assertEquals(res.getContent(), JsonUtils.getFromJson(dataString, Mockito.any()));		
	}
	
	//------- OFFERS ENTRY POINTS BY UNIDENTIFIER USERS
	
	
	@Test
	public void testValidatePriceByUnidentifierUser() {		
		String jsonString = "{\"event\" : \"validate-price\" , \"data\" : {\"data\" : \"x\"}, \"filters\": {\"startAddress\": \"Nice\",\"endAddress\": \"Sophia\",\"maxPrice\": \"0\"}, \"identification\":{\"userID\":\"noisette@mail.com\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(false);
		
		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(res.getContent(),"Please login to your account.\n");		
	}
	
	
	@Test
	public void testCreateOfferByUnidentifierUser() {
		String jsonString = "{\"event\":\"create-offer\",\"data\":{\"ownerID\":\"noisette@mail.com\", \"price\": \"21\", \"startCity\":\"Nice\", \"endCity\":\"Sophia\", \"capacity\":\"1\" },\"identification\":{\"userID\":\"noisette@mail.com\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(false);

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(res.getContent(),"Please login to your account.\n");		
	}
	
	
	@Test
	public void testAskOfferByUnidentifierUser() {
		String jsonString = 	"{\"event\":\"ask-offer\" ,\"data\": {\"offerID\": \"IDOFFER\",\"buyerID\": \"reglisse@mail.com\",\"weight\": \"12\", \"volume\":\"12\", \"date\":\"01/10/2018\" }, \"identification\":{\"userID\":\"reglisse@mail.com\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(false);

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(res.getContent(),"Please login to your account.\n");			
	}
	
	
	@Test
	public void testConsultOffersByUnidentifierUser() {
		String jsonString =  "{\"event\":\"consult-offers\",\"data\": {\"weight\": \"12\", \"volume\":\"12\", \"date\":\"01/10/2018\" },\"filters\": {\"weight\": \"12\",\"startAddress\": \"Nice\",\"endAddress\": \"Sophia\",\"maxPrice\": \"10000\"},\"identification\":{\"userID\":\"reglisse@mail.com\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(false);

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(res.getContent(),"Please login to your account.\n");		
	}
	
	
	@Test
	public void testConsultAwaitingOffersByUnidentifierUser() {
		String jsonString = "{\"event\": \"consult-awaiting-offers\" ,\"data\": {\"ownerID\": \noisette@mail.com\"}, \"identification\":{\"userID\":\"noisette@mail.com\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);

		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(false);

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(res.getContent(),"Please login to your account.\n");	
	}
	
	
	@Test
	public void testConfirmAwaitingOffersByUnidentifierUser() {
		String jsonString = "{\"event\": \"confirm-awaiting-offers\" ,\"data\": {\"transactionID\": IDOFFER}, \"identification\":{\"userID\":\"noisette@mail.com\"}}";

		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(false);

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(res.getContent(),"Please login to your account.\n");		
	}
	
	
	@Test
	public void testClaimReceiptByunIdentifierUser() {
		String jsonString = "{\"event\": \"claim-receipt\" ,\"data\": {\"transactionID\": TRANSACTIONID}, \"identification\":{\"userID\":\"reglisse@mail.com\"}}";

		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(false);

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(res.getContent(),"Please login to your account.\n");			
	}
	
	
	@Test
	public void testConfirmReceiptByUnidentifierUser() {
		String jsonString = "{\"event\": \"confirm-receipt\" ,\"data\": {\"transactionID\": \"TRANSACTIONID\"}, \"identification\":{\"userID\":\"noisette@mail.com\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);
		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(false);

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(res.getContent(),"Please login to your account.\n");		
	}
	
	
	@Test
	public void testClaimDepositByUnidentifierUser() {
		String jsonString = "{\"event\": \"claim-deposit\" ,\"data\": {\"transactionID\": $offerIdD}, \"identification\":{\"userID\":\"$driver\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);

		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(false);

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(res.getContent(),"Please login to your account.\n");			
	}
	
	
	@Test
	public void testConfirmDepositByUnidentifierUser() {
		String jsonString = "{\"event\": \"confirm-deposit\" ,\"data\": {\"transactionID\": \"TRANSACTIONID\"}, \"identification\":{\"userID\":\"reglisse@mail.com\"}}";
		JsonObject jsonObject = JsonUtils.getFromJson(jsonString);

		
		when(userIO.isIdentified(jsonObject.get("identification").getAsJsonObject().get("userID").getAsString())).thenReturn(false);

		FlowOrchestrationResult res = flowOrchestrator.orchestrateOffersEntryPoint(jsonString);
		
		assertEquals(res.getHttpStatus(),HttpStatus.NOT_ACCEPTABLE);
		assertEquals(res.getContent(),"Please login to your account.\n");	
	}
}
