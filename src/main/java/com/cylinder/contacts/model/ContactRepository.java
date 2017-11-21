package com.cylinder.contacts.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends CrudRepository<Contact, Long> {
  /**
  * Check if a certain contact record exists.
  * @param contactId The id of the contact record one wishes to comfirm its existence.
  * @return does the record exist?
  */
  boolean existsByContactId(Long contactId);

  @Query("SELECT c FROM Contact c WHERE c.contactId != :contactId")
  public List<Contact> findAllWithoutContactId(@Param("contactId") Long contactId);


}
