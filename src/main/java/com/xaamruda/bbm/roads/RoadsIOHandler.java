package com.xaamruda.bbm.roads;

import org.springframework.stereotype.Component;

import com.xaamruda.bbm.commons.json.JsonUtils;
import com.xaamruda.bbm.roads.composition.PathComposer;
import com.xaamruda.bbm.roads.model.Path;

/**
 * Entry point to roads module. 
 */
@Component
public class RoadsIOHandler {

	public RoadsIOHandler() {
		
	}
	
	// Mock
	public String getPathComposition(String departureLocation, String arrivalLocation) {
		Path path = PathComposer.getInstance().compose(departureLocation, arrivalLocation);
		return JsonUtils.toJson(path);
	}
	
	// Mock
	public String getPathDistances(String departureLocation, String arrivalLocation) {
			Path path = PathComposer.getInstance().compose(departureLocation, arrivalLocation);
			return JsonUtils.toJson(path);
		}
}
