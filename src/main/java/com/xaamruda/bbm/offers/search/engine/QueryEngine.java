package com.xaamruda.bbm.offers.search.engine;

import org.springframework.data.jpa.domain.Specification;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.offers.model.PostedOffer;
import com.xaamruda.bbm.users.model.User;


public class QueryEngine {

	// TODO Still again debate
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

		Specification<PostedOffer> query = (root, request, cb) ->  cb.like(root.get("endCity"), filters.endAddress);
		;
		//BBMwpphpmyadmin   | 127.0.0.1 -  12/Jan/2019:16:48:34 +0000 "GET /sql.php" 200
 
		BBMLogger.infoln(builder.toString());
		builder.append("Embedded filters | startCity: " + filters.startAddress
				+ ", endCity: " + filters.endAddress + ", capacity: " + filters.weight);
		
		query = query.and((root, request, cb) ->  cb.like(root.get("startCity"), filters.startAddress));
		query = query.and((root, request, cb) ->  cb.greaterThan(root.get("capacity"),filters.weight));

		if (filters.maxPrice > 0) {
			query =	query.and((root, request, cb) ->  cb.lessThanOrEqualTo(root.get("price"),filters.maxPrice));
			builder.append(", price: " + filters.maxPrice);
		}
		if (filters.status != null) {
			System.out.println("yo");
			query =	query.and((root, request, cb) -> cb.lessThanOrEqualTo(root.get("status"),filters.status));		
			builder.append(", status: " + filters.status + ".");
		} else {
			System.out.println("yi");
			query = query.and((root, request, cb) ->  cb.equal(root.get("status"),OfferStatus.POSTED));		
		}
		return query;
	};

	static public Specification<PostedOffer> buildMySqlQuery(int distance) {
		Specification<PostedOffer> query = (root, request, cb) -> { 
			return cb.greaterThanOrEqualTo(root.get("distance"), distance - 10);
		};
		query = query.and( (root, request, cb) -> { return cb.lessThanOrEqualTo(root.get("distance"),distance + 10);});
		return query;
	}
}
