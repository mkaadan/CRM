package com.cylinder.accounts.model;

import org.springframework.data.repository.CrudRepository;
/**
 * @author Ehtasham Munib
 * Sql interface for an account's email.
 */
public interface EmailRepository extends CrudRepository<Email, Long> {
}
