package com.cylinder.leads.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ryan Piper
 * Sql interface for lead objects.
 */
public interface LeadRepository extends CrudRepository<Lead, Long> {
    /**
     * Check if a certain lead record exists.
     *
     * @param leadId The id of the lead record one wishes to comfirm its existence.
     * @return does the record exist?
     */
    @Query(value = "SELECT exists(SELECT 1 FROM lead.details WHERE lead_id=:leadId)", nativeQuery = true)
    boolean existsById(@Param("leadId") Long leadId);

    /**
     * Delete a lead record based upon its id.
     *
     * @param leadId the id of the lead record one wishes to delete.
     * @return the amount of rows effected.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM lead.details WHERE lead_id=:leadId", nativeQuery = true)
    int deleteById(@Param("leadId") Long leadId);
}
