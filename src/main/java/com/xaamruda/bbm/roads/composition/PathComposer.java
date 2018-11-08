package com.xaamruda.bbm.roads.composition;

import java.util.HashMap;
import java.util.Random;

import com.xaamruda.bbm.roads.model.Path;

public class PathComposer implements IPathComposer {

	static private final  IPathComposer instance = new PathComposer();
	static private final  HashMap<String,Integer> cache = new HashMap<>();
	
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
	
	public int  computeDistance(String departureLocation, String arrivalLocation) {
		 String localValue = departureLocation +  arrivalLocation;
		 if(cache.containsKey(localValue) ) {
			 return cache.get(localValue);
		 }
		 int value = ((new Random().nextInt(950) + 50) / new Random().nextInt(500)) +10;
		 cache.put(localValue, value);
		 return value;
	}
}
