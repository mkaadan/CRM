package com.cylinder.contacts.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;



public interface ContactRepository extends CrudRepository<Contact, Long> {
  /**
  * Check if a certain contact record exists.
  * @param contactId The id of the contact record one wishes to comfirm its existence.
  * @return does the record exist?
  */
  boolean existsByContactId(Long contactId);

  @Query("SELECT c FROM Contact c WHERE c.contactId != :contactId")
  public List<Contact> findAllWithoutContactId(@Param("contactId") Long contactId);

  /**
  * Delete a contact record based upon its id.
  * @param contactId the id of the =contactr record one wishes to delete.
  * @return the amount of rows effected.
  */
  @Transactional
  @Modifying
  @Query(value="DELETE FROM contact.details WHERE contact_id=:contactId", nativeQuery=true)
  public int deleteByContactId(@Param("contactId") Long contactId);

}
