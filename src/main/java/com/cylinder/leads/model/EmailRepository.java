package com.cylinder.leads.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
* @author Ryan Piper
* Sql interface for a lead's email. 
*/
public interface EmailRepository extends CrudRepository<Email, Long> {
}
