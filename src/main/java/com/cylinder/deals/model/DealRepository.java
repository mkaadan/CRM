package com.cylinder.deals.model;

import org.springframework.data.repository.CrudRepository;

/**
* @author Ryan Piper
* Sql Interface for the deals.
*/
public interface DealRepository extends CrudRepository<Deal, Long> {
  /**
  * Check wheter a deal exists based upon a unique idenitfier.
  * @param dealId the id of the deal one wishes to check existence of.
  * @return does the record exist?
  */
  boolean existsByDealId(Long dealId);

  /**
  * Delete a record based upon it deal id.
  * @param dealId the id of the deal one wishes to delete
  * @return the amount of rows effected.
  */
  int deleteByDealId(Long dealId);
}
