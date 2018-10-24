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
    Calculator facturation = new Calculator();

    public void doWork(String workData, Offer offer){
        //DO things
        offer.setOfferPrice(offer.getPrice() + /*do des truccccc*/ 10);
    }
    
	public int calcul_without_offer(String workData){
	    //TODO
        //facturation.calcul_price_base(weight,distance,volume,day=0);
    }

	public int calcul_with_offer(String workData, Offer offer){
	    //TODO
        //facturation.advance_date_with_offer(int data, int offer);
    }

    public String reponse_flow(){
	    //TODO
    }

    private int offer_Parser(Offer offer){
	    //TODO
    }

    private int workData_Parser(String workData){
	    //TODO
    }
}