package com.cylinder.deals.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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
  @Query(value="SELECT exists(SELECT 1 FROM deal.details WHERE deal_id=:dealId)", nativeQuery=true)
  boolean existsByDealId(@Param("dealId") Long dealId);

  /**
  * Delete a record based upon it deal id.
  * @param dealId the id of the deal one wishes to delete
  * @return the amount of rows effected.
  */
  @Transactional
  @Modifying
  @Query(value="DELETE FROM deal.details WHERE deal_id=:dealId", nativeQuery=true)
  int deleteByDealId(@Param("dealId") Long dealId);
}
