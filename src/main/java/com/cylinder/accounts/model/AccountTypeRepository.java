package com.cylinder.accounts.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Ehtasham Munib
 * Sql interface for an account's type.
 */
public interface AccountTypeRepository extends CrudRepository<Type, Long> {
}
