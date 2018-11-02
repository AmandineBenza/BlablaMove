package com.xaamruda.bbm.roads.composition;

import com.xaamruda.bbm.roads.model.Path;

/**
 * Composes travel path.
 */
public interface IPathComposer {

	public Path compose(String departureLocation, String arrivalLocation);
	public int computeDistance(String departureLocation, String arrivalLocation);
	
}
