package com.cylinder.leads.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Ryan Piper
 * Sql Interface for the lead address.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
}
