package com.xaamruda.bbm.offers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Verifier;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.billing.BillingIOHandler;
import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.dbaccess.service.IOfferService;
import com.xaamruda.bbm.offers.dbaccess.service.IOffersTransactionService;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.offers.model.OffersTransaction;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.offers.utils.Range;
import com.xaamruda.bbm.roads.RoadsIOHandler;
import com.xaamruda.bbm.users.UsersIOHandler;
import com.xaamruda.bbm.offers.search.engine.Filters;
import com.xaamruda.bbm.offers.search.engine.QueryEngine;

@RunWith(MockitoJUnitRunner.class)
public class TestOffersIOHandler {

	private OffersIOHandler offersIOHandler;

	@Mock
	private IOfferService offerService;

	@Mock
	private IOffersTransactionService offerTransactionService;

	@Mock
	private BillingIOHandler calculatorHandler;

	@Mock
	private UsersIOHandler usersHandler;

	@Mock
	private RoadsIOHandler pathHandler;

	private String userEmail;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		offersIOHandler = new OffersIOHandler();
		userEmail = "user@mail.com";
		Whitebox.setInternalState(offersIOHandler, "offerService", offerService);
		Whitebox.setInternalState(offersIOHandler, "pathHandler", pathHandler);
		Whitebox.setInternalState(offersIOHandler, "calculatorHandler", calculatorHandler);
		Whitebox.setInternalState(offersIOHandler, "usersHandler", usersHandler);
		Whitebox.setInternalState(offersIOHandler, "offerTransactionService", offerTransactionService);
	}

	@Test
	public void testGetOffers() {
		Mockito.when(offerService.getAvailableOffers()).thenReturn(new ArrayList<>());
		offersIOHandler.getOffers();
		verify(offerService,Mockito.times(1)).getAvailableOffers();
	}

	@Test
	public void testPostNewOffer() {
		ArrayList<PostedOffer> emptyList = new ArrayList<>();
		String parameter = "{\"ownerID\" : \"user@mail.com\", \"price\": \"10\",\"startCity\":\"Town1\", \"endCity\":\"Town2\", \"capacity\":\"20\"}"; 

		Mockito.when(pathHandler.getPathDistances("Town1", "Town2")).thenReturn(10);
		Mockito.when(calculatorHandler.checkPrice(emptyList, 10)).thenReturn(new Range(20,5,10));


		String result = offersIOHandler.postNewOffer(parameter);
		PostedOffer offer = JsonUtils.getFromJson(result, PostedOffer.class);
		verify(offerService,Mockito.times(1)).saveOffer(Mockito.any());
		assertEquals((Integer)20,offer.getCapacity());
		assertEquals(OfferStatus.POSTED,offer.getStatus());
		assertEquals(10,offer.getDistance());

		parameter = "{\"ownerID\" : \"user@mail.com\", \"price\": \"10\",\"startCity\":\"Town1\", \"endCity\":\"Town3\", \"capacity\":\"20\"}"; 
		Mockito.when(pathHandler.getPathDistances("Town1", "Town3")).thenReturn(2);
		Mockito.when(calculatorHandler.checkPrice(emptyList, 2)).thenReturn(new Range(4,1,2));
		result = offersIOHandler.postNewOffer(parameter);

		assertEquals("Incorrect price ! For this distance ("
				+ 2 + ") the authorized points amount is within [" + 1 + " : "
				+ 4 + "].\n", result);


	} 

	//		PostedOffer historicalOffer = new PostedOffer();
	//	public List<PostedOffer> TestRetrieveOffers() {
	//		String workData = "{\"weight\": \"10\", \"volume\":\"5\", \"date\":\"5\" }";
	//		String filters  = "{\"weight\": \"10\",\"startAddress\": \"Town1\",\"endAddress\": \"Town2\",\"maxPrice\": \"10000\"}";
	//	
	//		Filters filtersObject = JsonUtils.getFromJson(filters, Filters.class);
	//		List<PostedOffer> offers = null;
	//
	//		offers = offerService.getAvailableOffers(QueryEngine.buildMySqlQuery(filtersObject));
	//		
	//		BBMLogger.infoln("Calculating offers prices...");
	//		for (PostedOffer offer : offers) {
	//			offer.setPrice(offer.getPrice() + calculatorHandler.calcul_without_offer(workData, offer.getDistance()));
	//		}
	//
	//		return offers.stream().filter(offer -> offer.getPrice() < filtersObject.getMaxPrice())
	//				.collect(Collectors.toList());
	//	} 

	@Test
	public void TestvalidatePrice() {
		ArrayList<PostedOffer> emptyList = new ArrayList<>();
		String workData = "{\"weight\": \"10\", \"volume\":\"5\", \"date\":\"5\" }";
		String filters  = "{\"weight\": \"10\",\"startAddress\": \"Town1\",\"endAddress\": \"Town2\",\"maxPrice\": \"10\"}";

		Mockito.when(pathHandler.getPathDistances("Town1", "Town2")).thenReturn(10);
		Mockito.when(calculatorHandler.checkPrice(emptyList, 10)).thenReturn(new Range(20,5,10));

		Filters fil = JsonUtils.getFromJson(filters, Filters.class);
		int distance = pathHandler.getPathDistances(fil.startAddress, fil.endAddress);
		List<PostedOffer> offers = null;
		String res = offersIOHandler.validatePrice(filters, workData);
		String expected = "Correct price ! For the distance the authorized amount is [" + 5 + " : "
				+ 20 + "]\n";
		assertEquals(expected,res);

		workData = "{\"weight\": \"10\", \"volume\":\"5\", \"date\":\"5\" }";
		filters  = "{\"weight\": \"10\",\"startAddress\": \"Town1\",\"endAddress\": \"Town2\",\"maxPrice\": \"100\"}";

		res = offersIOHandler.validatePrice(filters, workData);
		expected = "Incorrect price ! For this distance ("
				+ distance + ") the authorized points amount is within [" + 5 + " : "
				+ 20 + "]\n";

		assertEquals(expected,res);
	} 


	// TODO verify
	@Test
	public void testAskValidate() {

		String workData = "{\"offerID\": \"1\",\"buyerID\": \"user@mail.com\",\"weight\": \"6\", \"volume\":\"5\", \"date\":\"3\" }";


		PostedOffer offerMock = new PostedOffer();
		Mockito.when(offerService.getOfferByID("1")).thenReturn( Collections.singletonList(offerMock));

		offerMock.setStatus(OfferStatus.ABORTED);
		String res = offersIOHandler.askForValidation(workData);
		assertEquals("INVALID OPERATION\n",res);
		
		offerMock.setStatus(OfferStatus.POSTED);
		offerMock.setOwnerID("violet@mail.com");
		offerMock.setDistance(10);
		offerMock.setPrice(10);
		Mockito.when(calculatorHandler.calcul_without_offer(workData, 10)).thenReturn(20);
		res = offersIOHandler.askForValidation(workData);
		OffersTransaction resOffer = JsonUtils.getFromJson(res, OffersTransaction.class);
		
		assertEquals(resOffer.getBuyerID(),"user@mail.com");
		assertEquals(resOffer.getFinalPrice(),(Integer)30);
		assertEquals(resOffer.getStatus(),OfferStatus.AWAITING_CONFIRMATION);
		assertEquals(resOffer.getOfferID(),"1");
		assertEquals(resOffer.getOwnerID(),"violet@mail.com");
		assertEquals(resOffer.getWeigth(),(Integer) 6);
		assertEquals(resOffer.getVolume(),(Integer) 5);
		assertEquals(resOffer.getDateBeforeOrder(),(Integer) 3);
		assertEquals(offerMock.getStatus(), OfferStatus.AWAITING_CONFIRMATION);
		
		
	}

	@Test
	public void testConsultAwaitingOffers() {

		String workData = "{\"ownerID\": \"user@mail.com\"}";
		OffersTransaction offerMock = new OffersTransaction();
		Mockito.when(offerTransactionService.getOffersByOwnerId("user@mail.com")).thenReturn( Collections.singletonList(offerMock));
		offerMock.setStatus(OfferStatus.ABORTED);
		String res = offersIOHandler.consultAwaitingOffers(workData);
		assertEquals("No offers waiting for confirmation.\n",res);
		offerMock.setStatus(OfferStatus.AWAITING_CONFIRMATION);
		offerMock.setTransactionID(42);
		res = offersIOHandler.consultAwaitingOffers(workData);
		assertEquals(JsonUtils.toJson(Collections.singletonList(offerMock)),res);
		
		}

	//TODO list vide
	@Test	
	public void testConfirmAwaitingOffer() {
		String workData = "{\"transactionID\": 42}";
		OffersTransaction offerMock = new OffersTransaction();
		Mockito.when(offerTransactionService.getOffersByTransactionID("42")).thenReturn( Collections.singletonList(offerMock));
		offerMock.setOwnerID("user@mail.com");
		offerMock.setBuyerID("violet@mail.com");
		offerMock.setFinalPrice(42);
		String res = offersIOHandler.confirmAwaitingOffer(workData);
		assertEquals(OfferStatus.CONFIRMED,offerMock.getStatus());
		assertEquals(JsonUtils.toJson(Collections.singletonList(offerMock)),res);
		assertNotNull(offerMock.getConfirmationDate());
		}
	@Test
	public void claimReceipt() {
		String workData = "{\"transactionID\": 42}";
		OffersTransaction offerMock = new OffersTransaction();
		Mockito.when(offerTransactionService.getOffersByTransactionID("42")).thenReturn( Collections.singletonList(offerMock));
		offerMock.setOwnerID("user@mail.com");
		offerMock.setBuyerID("violet@mail.com");
		offerMock.setFinalPrice(42);
		String res = offersIOHandler.claimReceipt(workData);
		assertEquals(OfferStatus.AWAITING_RECEIPT_CONFIRMATION,offerMock.getStatus());
		assertEquals(JsonUtils.toJson(offerMock),res);
		assertNotNull(offerMock.getClientDepositDate());
	}
	@Test
	public void testConfirmReceipt(){
		String workData = "{\"transactionID\": 42}";
		OffersTransaction offerMock = new OffersTransaction();
		Mockito.when(offerTransactionService.getOffersByTransactionID("42")).thenReturn( Collections.singletonList(offerMock));
		offerMock.setOwnerID("user@mail.com");
		offerMock.setBuyerID("violet@mail.com");
		offerMock.setFinalPrice(42);
		String res = offersIOHandler.confirmReceipt(workData);
		assertEquals(OfferStatus.RECEIPT_DONE,offerMock.getStatus());
		assertEquals(JsonUtils.toJson(Collections.singletonList(offerMock)),res);
		assertNotNull(offerMock.getClientDepositConfimationDate());
		}

	@Test
	public void testClaimDeposit() {
		String workData = "{\"transactionID\": 42}";
		OffersTransaction offerMock = new OffersTransaction();
		Mockito.when(offerTransactionService.getOffersByTransactionID("42")).thenReturn( Collections.singletonList(offerMock));
		offerMock.setOwnerID("user@mail.com");
		offerMock.setBuyerID("violet@mail.com");
		offerMock.setFinalPrice(42);
		String res = offersIOHandler.claimDeposit(workData);
		assertEquals(OfferStatus.AWAITING_DEPOSIT_CONFIRMATION,offerMock.getStatus());
		assertEquals(JsonUtils.toJson(Collections.singletonList(offerMock)),res);
		assertNotNull(offerMock.getClientDepositConfimationDate());
	}

//TODO last call

}
