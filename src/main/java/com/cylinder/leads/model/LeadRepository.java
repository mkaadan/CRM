package com.cylinder.leads.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

public interface LeadRepository extends CrudRepository<Lead, Long> {
  @Query(value="SELECT exists(SELECT 1 FROM lead.details WHERE lead_id=:leadId)", nativeQuery=true)
  boolean existsById(@Param("leadId")Long leadId);

  @Transactional
  @Modifying
  @Query(value="DELETE FROM lead.details WHERE lead_id=:leadId", nativeQuery=true)
  int deleteById(@Param("leadId") Long leadId);
}
