package com.xaamruda.bbm.offers.search.engine;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.xaamruda.bbm.commons.logging.BBMLogger;

public class QueryEngine {

	// TODO Still again debate
	static public Query buildMongoQuery(Filters filters) {
		Query query = new Query();
		// TODO Query - Criteria - Evolve ->
		// For now we decided that only town will affect availability
		query.addCriteria(Criteria.where("startCity").is(filters.startAddress));
		query.addCriteria(Criteria.where("endCity").is(filters.endAddress));

		query.addCriteria(Criteria.where("capacity").gt(filters.weight));
		
		if (filters.maxPrice > 0) {
			query.addCriteria(Criteria.where("price").lte(filters.maxPrice));
		}
		
		if (filters.status != null) {
			query.addCriteria(Criteria.where("status").lte(filters.status));
		}
		return query;
	}
	
	static public Query buildMongoQuery(int distance) {
		Query query = new Query();
		query.addCriteria(Criteria.where("distance").lt(distance + 10).gt(distance - 10));
		
		return query;
	}
}
