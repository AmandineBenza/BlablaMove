package com.xaamruda.bbm.offers.search.engine;

import org.springframework.data.jpa.domain.Specification;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.offers.model.OfferStatus;
import com.xaamruda.bbm.offers.model.PostedOffer;

public class QueryEngine {

	static public Specification<PostedOffer> buildMySqlQuery(Filters filters) {
		StringBuilder builder = new StringBuilder();
		Specification<PostedOffer> query = (root, request, cb) ->  cb.like(root.get("endCity"), filters.endAddress);;
		
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
			query =	query.and((root, request, cb) -> cb.lessThanOrEqualTo(root.get("status"),filters.status));		
			builder.append(", status: " + filters.status + ".");
		} else {
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
