package com.cylinder.contacts.model;

import org.springframework.data.repository.CrudRepository;

/**
* @author Ryan Piper
* Sql interface the contact's address information. 
*/
public interface AddressRepository extends CrudRepository<Address, Long> {
}
