package com.xaamruda.bbm.controller;

import javax.xml.ws.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xaamruda.bbm.calculator.IOHandler;
import com.xaamruda.bbm.commons.users.UserCreationContainer;
import com.xaamruda.bbm.communication.internal.FlowOrchestrator;
import com.xaamruda.bbm.communication.internal.IFlowOrchestrator;
import com.xaamruda.bbm.users.UserIOHandler;

@RestController
@RequestMapping("/BBM/")
@SuppressWarnings({"rawtypes", "unchecked"})
public class WebServiceController implements IWebServiceController {
	
	@Autowired
	private IFlowOrchestrator flowOrchestrator;
	
	@Autowired
	private IOHandler calculatorHandler;
	
	@Autowired
	private com.xaamruda.bbm.offers.IOHandler  offerHandler;
	
	@Autowired
	private com.xaamruda.bbm.roads.IOHandler  roadsHandler;
	
	@Autowired
	private  UserIOHandler userHandler;
	
	public WebServiceController() {
		flowOrchestrator = new FlowOrchestrator();
	}
	
	@Override
	@RequestMapping(value = "USER/CREATE", method = RequestMethod.POST)
	public ResponseEntity createUser(@RequestBody UserCreationContainer userInformation) {
		boolean created = flowOrchestrator.createUser(userInformation);
		
		if(!created)
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		
		return new ResponseEntity("User successfully created.", HttpStatus.OK);
	}
	
	
	//Json must be {
	//"event" : "x",
	///...
	//}
	
	//TODO Do better.
	@RequestMapping(value = "USER/", method = RequestMethod.POST)
	public ResponseEntity createAnOffer(@RequestBody String events ) {
		JsonObject jsonObject =  GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().create().fromJson(events, JsonObject.class);
		JsonElement event = jsonObject.get("event");
		switch(event.getAsString()){
		case "create-offer":
		{
			makeOffer(jsonObject.get("data").getAsString());
		}
		case "consult-offers":
		{
			
			this.getFilteredOffers(jsonObject.get("filters").getAsString(),jsonObject.get("data").getAsString());
		}
		default:
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	//TODO rename "data"
	//data are data used for calculation
	private void getFilteredOffers(String filters,String data) {
		 offerHandler.retrieveOffers(filters, data);
		
	}

	@Override
	@RequestMapping(value = "USER/IDENTIFY", method = RequestMethod.POST)
	public ResponseEntity identifyUser(@RequestBody String userInformation) {
		return null;
	}


	@Override
	public ResponseEntity getAllOffers() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity getOfferByPath() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity getOfferByNumberOfPoint() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity getOfferByDate() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity confirmTransaction() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity cancelTransaction() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity confirmOffer(String offerInformation) {
		// TODO Auto-generated method stub
		return null;
	}

	//TODO verify prices
	@Override
	public ResponseEntity makeOffer(String offerInformation) {
		offerHandler.postNewOffer(offerInformation);
		return null;
	}


	@Override
	public ResponseEntity modifyOffer(String offerInformation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity deleteOffer(String offerInformation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity selectOffer(String offerInformation) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
