package com.cylinder.leads.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Ryan Piper
 * Sql Interface for lead status.
 */
public interface StatusRepository extends CrudRepository<Status, Long> {
}
