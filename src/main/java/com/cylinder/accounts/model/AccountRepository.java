package com.cylinder.accounts.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ehtasham Munib
 * Sql interface for account objects.
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
    /**
     * Check if a certain account record exists.
     *
     * @param accountId The id of the account record one wishes to comfirm its existence.
     * @return does the record exist?
     */
    @Query(value = "SELECT exists(SELECT 1 FROM account.details WHERE account_id=:accountId)", nativeQuery = true)
    boolean existsById(@Param("accountId") Long accountId);

    /**
     * Delete a account record based upon its id.
     *
     * @param accountId the id of the account record one wishes to delete.
     * @return the amount of rows effected.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM account.details WHERE account_id=:accountId", nativeQuery = true)
    int deleteById(@Param("accountId") Long accountId);
}
