package com.cylinder.accounts.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query(value="SELECT exists(SELECT 1 FROM account.details WHERE account_id=:accountId)", nativeQuery=true)
    boolean existsById(@Param("accountId")Long accountId);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM account.details WHERE account_id=:accountId", nativeQuery=true)
    int deleteById(@Param("accountId") Long accountId);
}
