package com.xaamruda.bbm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/BBM/")
@SuppressWarnings({ "rawtypes", "unchecked"})
public class WebServiceController implements IWebServiceController {

	@Override
	public ResponseEntity getAllOffers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity getAveragePriceByPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity getPointsByUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity getPathByOffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity identifyUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
