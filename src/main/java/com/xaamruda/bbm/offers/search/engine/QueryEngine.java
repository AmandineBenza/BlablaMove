package com.xaamruda.bbm.offers.search.engine;

import org.springframework.data.jpa.domain.Specification;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.users.model.User;


public class QueryEngine {

	//	// TODO Still again debate
	//	static public Query buildMongoQuery(Filters filters) {
	//		Query query = new Query();
	//		
	//		// For now we decided that only town will affect availability
	//		query.addCriteria(Criteria.where("startCity").is(filters.startAddress));
	//		query.addCriteria(Criteria.where("endCity").is(filters.endAddress));
	//		query.addCriteria(Criteria.where("capacity").gt(filters.weight));
	//		
	//		StringBuilder builder = new StringBuilder();
	//		builder.append("Embedded filters | startCity: " + filters.startAddress
	//				+ ", endCity: " + filters.endAddress + ", capacity: " + filters.weight);
	//		
	//		if (filters.maxPrice > 0) {
	//			query.addCriteria(Criteria.where("price").lte(filters.maxPrice));
	//			builder.append(", price: " + filters.maxPrice);
	//		}
	//		
	//		if (filters.status != null) {
	//			query.addCriteria(Criteria.where("status").lte(filters.status));
	//			builder.append(", status: " + filters.status + ".");
	//		} else {
	//			query.addCriteria(Criteria.where("status").is(OfferStatus.POSTED));
	//		}
	//		
	//		BBMLogger.infoln(builder.toString());
	//		return query;
	//	}
	//	
	//	static public Query buildMongoQuery(int distance) {
	//		Query query = new Query();
	//		query.addCriteria(Criteria.where("distance").lt(distance + 10).gt(distance - 10));
	//		return query;
	//	}




	static public Specification<PostedOffer> buildMySqlQuery(Filters filters) {
		StringBuilder builder = new StringBuilder();
		Specification<PostedOffer> query = (root, request, cb) -> { 
			cb.like(root.get("endCity"),filters.endAddress);
			cb.greaterThan(root.get("capacity"),filters.weight);
			builder.append("Embedded filters | startCity: " + filters.startAddress
					+ ", endCity: " + filters.endAddress + ", capacity: " + filters.weight);
			if (filters.maxPrice > 0) {
				cb.lessThanOrEqualTo(root.get("price"),filters.maxPrice);
				builder.append(", price: " + filters.maxPrice);
			}
			if (filters.status != null) {
				cb.lessThanOrEqualTo(root.get("status"),filters.status);
				builder.append(", status: " + filters.status + ".");
			} else {
				cb.equal(root.get("status"),OfferStatus.POSTED);
			}
			return cb.like(root.get("startCity"), filters.startAddress);
		};
		BBMLogger.infoln(builder.toString());
		return query;
	};

	static public Specification<PostedOffer> buildMySqlQuery(int distance) {
		Specification<PostedOffer> query = (root, request, cb) -> { 
			cb.lessThanOrEqualTo(root.get("distance"),distance + 10);
			return cb.greaterThanOrEqualTo(root.get("distance"), distance - 10);
		};
		return query;
	}
}
