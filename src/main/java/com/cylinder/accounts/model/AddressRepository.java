package com.cylinder.accounts.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Ryan Piper
 * Sql Interface for the account address.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
}
