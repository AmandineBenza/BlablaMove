package com.xaamruda.bbm.roads.composition;

import com.xaamruda.bbm.roads.model.Path;

public class PathComposer implements IPathComposer {

	private final static IPathComposer instance = new PathComposer();
	
	private PathComposer() {
		// to prevent instanciation
	}
	
	public static IPathComposer getInstance() {
		return instance;
	}
	
	/**
	 * Mock.
	 */
	@Override
	public Path compose(String departureLocation, String arrivalLocation) {
		return new Path(departureLocation, arrivalLocation);
	}

}
