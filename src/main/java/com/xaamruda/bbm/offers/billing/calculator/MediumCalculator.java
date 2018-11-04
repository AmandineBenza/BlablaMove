package com.xaamruda.bbm.offers.billing.calculator;

import java.util.HashMap;
import java.util.List;

import com.xaamruda.bbm.offers.model.PostedOffer;

public class MediumCalculator {
	static private HashMap<Integer, Range> cache = new HashMap<>();
	static private HashMap<Integer, Integer> validateCache = new HashMap<>();
	static private int CACHE_OFFSET = 5;

	static private MediumCalculator instance = new MediumCalculator();

	private MediumCalculator() {

	}

	/***
	 * Basicaly the clients pay 1 point for 1 killometer
	 * 
	 * @param offers   The list of offer
	 * @param distance
	 * @return
	 */
	static public Range compute(List<PostedOffer> offers, int distance) {
		if (cache.containsKey(distance) && Math.abs(validateCache.get(distance) - offers.size()) < CACHE_OFFSET) {

			return cache.get(distance);
		}

		int value = 0;

		for (PostedOffer offer : offers) {
			value += offer.getPrice();
		}
		if (offers.isEmpty()) {
			return new Range(distance * 3, distance / 2, distance);
		} else {

			int average = value / offers.size();
			Range range = new Range(average * 3, average / 2, average);
			cache.put(distance, range);
			validateCache.put(distance, offers.size());
			return range;

		}
	}

	public static MediumCalculator getInstance() {
		return instance;
	}

}