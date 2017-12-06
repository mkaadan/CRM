package com.cylinder.crmusers.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ehtasham Munib
 * @author Ryan Piper
 * The sql interface for CrmUsers.
 */
public interface CrmUserRepository extends JpaRepository<CrmUser, Long> {
    /**
     * Find a record in the database with the given email.
     *
     * @param email The email to search with.
     * @return The user associated with the email.
     */
    CrmUser findByEmail(String email);

    /**
     * Update the password for a single user account.
     *
     * @param newPassword the new password to update the record with.
     * @param accountId   the account id the user is associated with.
     * @return The amount of records that were modified.
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE crmuser.accounts SET password=:newPassword WHERE account_id=:accountId",
            nativeQuery = true)
    int updatePassword(@Param("newPassword") String newPassword, @Param("accountId") Long accountId);

    /**
     * Find a record in the database with the given account id.
     *
     * @param accountId the id associated with the record one wants to find.
     * @return The user associated with the account id.
     */
    CrmUser findByAccountId(Long accountId);

    /**
     * Check wheter an account exists by searching for its account id.
     *
     * @param accountId the id associated with the record.
     * @return does the record exist?
     */
    boolean existsByAccountId(Long accountId);

    /**
     * Delete a record that is associated with a specific account id.
     *
     * @param accountId the id of the record one wants to delete.
     * @return the amount of records altered by the query.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM crmuser.accounts WHERE account_id=:accountId", nativeQuery = true)
    int deleteByAccountId(@Param("accountId") Long accountId);

    /**
     * Does there exist a user with the given id and email?
     *
     * @param accountId the id of the record one wants to comfirm is real.
     * @param email     the email of lead the one wants to comfirm is real.
     * @return does the record exist?
     */
    boolean existsByAccountIdAndEmail(Long accountId, String email);

    /**
    *  Count the amount of user that have a particular role.
    *  @param roleId The id of the role.
    *  @return the count of users who have a particular role. 
    **/
    @Query(value="SELECT COUNT(*) FROM crmuser.accounts WHERE role_id=:roleId", nativeQuery=true)
    Long countByRoleId(@Param("roleId") Long roleId);

}
