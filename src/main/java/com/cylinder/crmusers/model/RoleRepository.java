package com.cylinder.crmusers.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
* @author Ryan Piper
* The sql interface for secuirty roles.
*/
public interface RoleRepository extends JpaRepository<Role, Long> {
  /**
  * Find the role based upon it's name.
  * @param roleName The name of the role one wishes to seasrch by.
  */
  Role findByRoleName(String roleName);

  /**
  * Check wheter the role name and id are congruent with the database.
  * @param roleName the name of secuirty role
  * @param roleId the id that identifies the secuirty role.
  * @return does the record exist? 
  */
  @Query(value="SELECT exists(SELECT 1 FROM crmuser.roles WHERE role=:roleName AND role_id=:roleId)",
         nativeQuery=true)
  boolean existsByRoleAndId(@Param("roleName")String roleName, @Param("roleId") Long roleId);
}
