package com.xaamruda.bbm.billing;

import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.integrity.IntegrityIOHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.assertEquals;

public class TestIOHandler {

    public BillingIOHandler billingIOHandler;
    public int distance;
    private double weight;
    private double volume;
    private int date;
    private int offerPrice;
    public String offerA;
    public String offerB;

    @Mock
    private IntegrityIOHandler integrityIOHandler;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        billingIOHandler = new BillingIOHandler();
        distance = 60; weight  = 10; volume = 30; date = 3; offerPrice = 150;
        Whitebox.setInternalState( billingIOHandler, "integrityIOHandler", integrityIOHandler);
        offerA = "{\"ownerID\" : \"user@mail.com\", \"weight\": \""+ String.valueOf(weight)+"\"," +
                "\"volume\":\""+ String.valueOf(volume)+"\", \"date\":\""+ String.valueOf(date)+"\"}";
        offerB = "{\"ownerID\" : \"user@mail.com\",\"offerPrice\":\""+ offerPrice +"\", \"date\":\""+ String.valueOf(date)+"\"}";
    }

    @Test
    public void testBasicCalcul() {
        double expected = distance + weight + volume + (date > 3 ? 1.0 : 3.0);
        assertEquals(billingIOHandler.calcul_without_offer(offerA, distance),expected,0.1);
    }

    @Test
    public void testBasicCalculWithJson() {
        double userExpected = Math.floor(((offerPrice+(date > 3 ? 1.0 : 3.0))/100)*90.0) ;
        double companyExpected= (offerPrice+(date > 3 ? 1.0 : 3.0)) - userExpected ;
        String expected = "{\"userPoints\":\""+ userExpected +"\",\"companyPoints\":\""+ companyExpected +"\"}";
        assertEquals(billingIOHandler.calcul_with_offer(offerB), JsonUtils.toJson(expected));
    }
}
