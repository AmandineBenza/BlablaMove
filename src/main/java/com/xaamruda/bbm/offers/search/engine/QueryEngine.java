package com.xaamruda.bbm.offers.search.engine;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.xaamruda.bbm.offers.model.Offer;

public class QueryEngine {
	
    //TODO Still again debate
	static public Query BuildMongoQuery(Filters filters){
		Query query = new Query();
		
		//TODO Query - Criteria - Evolve ->  
		//For now we decided that only town will affect availability 
		query.addCriteria(Criteria.where("startAddress").is(filters.startAddress));
		query.addCriteria(Criteria.where("endAddress").is(filters.endAddress));
		//
		
		query.addCriteria(Criteria.where("capacity").is(filters.weith));
		if(filters.maxPrice > 0 ){
			query.addCriteria(Criteria.where("price").lte(filters.maxPrice));
		}
		if(filters.status != null){
			query.addCriteria(Criteria.where("status").lte(filters.status));
		}
		return query;
	}
}
