package com.cylinder.contacts.model;

import org.springframework.data.repository.CrudRepository;

/**
* @author Ryan Piper
* Sql interface the contact's email information.
*/
public interface EmailRepository extends CrudRepository<Email, Long> {
}
