package com.cylinder.crmusers.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


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

    public Role() {
    }

}
