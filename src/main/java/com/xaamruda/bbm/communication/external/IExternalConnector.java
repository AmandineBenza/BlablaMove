package com.xaamruda.bbm.communication.external;

import com.google.maps.DistanceMatrixApiRequest;

/**
 * External provider connector.
 */
public interface IExternalConnector {
	
	// TODO
	public DistanceMatrixApiRequest connectToGoogleMap(String[] origins, String[] destinations);

}
