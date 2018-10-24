package com.xaamruda.bbm.communication.external;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;

/**
 * {@link IExternalConnector}
 */
public class ExternalConnector implements IExternalConnector {

	public ExternalConnector(){
		
	}
	
	/**
	 * External API connector mock.
	 * 
	 * @param origins
	 * @param destinations
	 * @return
	 */
	public DistanceMatrixApiRequest connectToGoogleMap(String[] origins, String[] destinations){
		return DistanceMatrixApi.getDistanceMatrix(new GeoApiContext(), origins, destinations);
	}
}
