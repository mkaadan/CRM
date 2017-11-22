package com.cylinder.leads.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Ryan Piper
 * Sql interface for a lead's email.
 */
public interface EmailRepository extends CrudRepository<Email, Long> {
}
