package com.cylinder.deals.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
* @author Ryan Piper
* Sql Interface for the deal stage.
*/
@Repository
public interface TypeRepository extends CrudRepository<Type, Long> {
}
