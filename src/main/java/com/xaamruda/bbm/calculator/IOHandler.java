package com.xaamruda.bbm.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.xaamruda.bbm.offers.model.Offer;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Calculator entry point.
 */
public class IOHandler{
	//TODO DO. Calculations.
	public void doWork(String workData, Offer offer){
		//DO things 
		offer.setOfferPrice(offer.getPrice() + /*do des truccccc*/ 10);
	}
}