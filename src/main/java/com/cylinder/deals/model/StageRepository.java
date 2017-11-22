package com.cylinder.deals.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Ryan Piper
 * Sql Interface for the deal stage.
 */
public interface StageRepository extends CrudRepository<Stage, Long> {
}
