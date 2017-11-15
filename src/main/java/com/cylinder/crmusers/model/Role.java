package com.cylinder.crmusers.model;

import java.util.Optional;

import javax.persistence.*;

import org.springframework.validation.FieldError;

import lombok.*;


/**
* @author Ryan Piper
* The security roles the users can have in the application.
*/
@Entity
@Table(name = "roles", schema = "crmuser")
public class Role {
    /**
    * The id associated with the role.
    *
    * @param roleId the id that can be used to identify the role.
    * @return the id of the role.
    */
    @Getter
    @Setter
    @Id
    @Column(name = "role_id")
    protected Long roleId;

    /**
    * The name of the role.
    *
    * @param roleName the name of the role the user can user.
    * @return the name of the role.
    */
    @Getter
    @Setter
    @Column(name = "role")
    protected String roleName;

    public Role() {}

    /**
    * Check wheter the id and name is consistent with what is in the database.
    * @param roleRepository the sql database interface for getting security roles.
    * @param fieldName the name of the error field.
    * @return the field error if any. 
    */
    public Optional<FieldError> isValid(RoleRepository roleRepository, String fieldName) {
      if (roleRepository.existsByRoleAndId(this.roleName,this.roleId)) {
        return Optional.empty();
      } else {
        return Optional.of(new FieldError("Role", fieldName, "This role doesn't exist."));
      }
    }
}
