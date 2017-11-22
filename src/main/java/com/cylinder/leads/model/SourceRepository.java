package com.cylinder.leads.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Ryan Piper
 * Sql Interface for lead sources
 */
public interface SourceRepository extends CrudRepository<Source, Long> {
}
