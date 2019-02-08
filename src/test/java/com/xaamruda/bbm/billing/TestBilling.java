package com.xaamruda.bbm.billing;

import com.google.gson.JsonObject;
import com.xaamruda.bbm.billing.calculator.Calculator;
import com.xaamruda.bbm.commons.json.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBilling {

    private Calculator calculator;
    private static String offerA;
    private static String offerFloat;
    private double distance;
    private double weight;
    private double volume;
    private double floatDistance;
    private double floatWeight;
    private double floatVolume;
    private int nearDate;
    private int farDate;
    private int offer;

    @Before
    public void setup(){
        calculator = new Calculator();
        distance = 20; weight  = 10; volume = 30; farDate = 3;
        floatDistance = 20.6 ; floatWeight = 10.3 ; floatVolume= 30.7 ; nearDate = 1;
        offerA = "{\"ownerID\" : \"user@mail.com\", \"weight\": \""+ String.valueOf(weight)+"\"," +
                "\"volume\":\""+ String.valueOf(volume)+"\", \"date\":\""+ String.valueOf(farDate)+"\"}";
        offerFloat = "{\"ownerID\" : \"user@mail.com\", \"weight\": \""+ String.valueOf(floatWeight)+"\"," +
                "\"volume\":\""+ String.valueOf(floatVolume)+"\", \"date\":\""+ String.valueOf(farDate)+"\"}";
        offer = 100;
    }

    @Test
    public void basicCalculTest (){
        double expected = distance + weight + volume + (farDate > 3 ? 1.0 : 3.0);
        JsonObject data =  JsonUtils.getFromJson(offerA);
        calculator.calcul_price_base(data.get("weight").getAsDouble(), distance,
                data.get("volume").getAsDouble(), data.get("date").getAsInt());
        assertEquals(expected, calculator.getUserPoints(),0.1);
        calculator.finalConfirmation(calculator.getUserPoints());
        double userPoint = Math.floor((expected/100)*90.0);
        double companyPoint = expected-userPoint;
        assertEquals(userPoint, calculator.getUserPoints(),0.1);
        assertEquals(companyPoint, calculator.getCompanyPoints(),0.1);
    }

    @Test
    public void floatCalculTest(){
        double expected = Math.floor(floatDistance + floatWeight + floatVolume + (farDate > 3 ? 1.0 : 3.0));
        JsonObject data =  JsonUtils.getFromJson(offerFloat);
        calculator.calcul_price_base(data.get("weight").getAsDouble(), floatDistance,
                data.get("volume").getAsDouble(), data.get("date").getAsInt());
        assertEquals(expected, calculator.getUserPoints(),0.1);
        calculator.finalConfirmation(calculator.getUserPoints());
        double userPoint = Math.floor((expected/100)*90.0);
        double companyPoint = expected-userPoint;
        assertEquals(userPoint, calculator.getUserPoints(),0.1);
        assertEquals(companyPoint, calculator.getCompanyPoints(),0.1);
    }

    @Test
    public void calculOfferTest(){
        double expected = offer + (nearDate > 3 ? 1.0 : 3.0);
        calculator.advance_date_with_offer(nearDate,offer);
        assertEquals(expected, calculator.getUserPoints(),0.1);
        calculator.finalConfirmation(calculator.getUserPoints());
        double userPoint = Math.floor((expected/100)*90.0);
        double companyPoint = expected-userPoint;
        assertEquals(userPoint, calculator.getUserPoints(),0.1);
        assertEquals(companyPoint, calculator.getCompanyPoints(),0.1);
    }
}
