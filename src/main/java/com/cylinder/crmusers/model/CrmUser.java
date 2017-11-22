package com.cylinder.crmusers.model;

import com.cylinder.crmusers.model.forms.AdminUserForm;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author Ehtasham Munib
 * @author Ryan Piper
 * The users the application allows to work with the data.
 */
@Entity
@Table(name = "accounts", schema = "crmuser")
public class CrmUser {
    /**
     * The id that uniquely identifies the user.
     *
     * @param accountId a value which would uniquely identify the user record.
     * @return The current account id that is associated to the record.
     */
    @Getter
    @Setter
    @Id
    @Column(name = "account_id")
    private long accountId;

    /**
     * The email that is associated to the user.
     *
     * @param email the email address that is associated to the user.
     * @return The current email that is associated to the user..
     */
    @Getter
    @Setter
    @Column(name = "email")
    @Email
    @NotEmpty(message = "Please provide an email.")
    private String email;

    /**
     * The password that user uses to authenicate with.
     *
     * @param password a value that can be used to authenticate the account.
     * @return The current password for the user.
     */
    @Getter
    @Setter
    @Column(name = "password")
    @NotEmpty(message = "Please provide a password.")
    private String password;

    /**
     * The first name that the user is associated with.
     *
     * @param firstName a value that can be used to associate a user to a first name.
     * @return The first name of the user.
     */
    @Getter
    @Setter
    @Column(name = "first_name")
    @NotEmpty(message = "Please provide a first name.")
    private String firstName;

    /**
     * The last name that the user is associated with.
     *
     * @param lastName a value that can be used to associate a user to a last name.
     * @return The last name of the user.
     */
    @Getter
    @Setter
    @Column(name = "last_name")
    @NotEmpty(message = "Please provide a last name")
    private String lastName;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    /**
     * The secuirty role of the user.
     *
     * @param role a value that can be used to determine the secuirty role of the user.
     * @return the current role of the user.
     */
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    public CrmUser() {
    }

    /**
     * Shortcut method to get the full name of the user.
     *
     * @return the full name of the user.
     */
    public String getName() {
        return firstName + " " + lastName;
    }

    /**
     * Alter the state of the user based upon a form submission.
     *
     * @param form The form that was filled out by the user.
     */
    public void mergeFromAdminUserForm(AdminUserForm form) {
        this.email = (form.getEmail() == null) ? this.email : form.getEmail();
        this.firstName = (form.getFirstName() == null) ? this.firstName : form.getFirstName();
        this.lastName = (form.getLastName() == null) ? this.lastName : form.getLastName();
        this.isEnabled = (form.getIsEnabled() != form.getIsEnabled()) ? this.isEnabled : form.getIsEnabled();
        this.role = (form.getRole() == null) ? this.role : form.getRole();
    }

    /**
     * Map a subset of the user data into a form so that it may edited.
     *
     * @return the form.
     */
    public AdminUserForm toAdminUserForm() {
        return new AdminUserForm(this.accountId,
                this.email,
                this.firstName,
                this.lastName,
                this.role,
                this.isEnabled
        );
    }

    /**
     * Is the user enabled? If they are not enabled they will not be able to log in.
     *
     * @return the current value of wheter the user is allowed to log in into the application.
     */
    public boolean getIsEnabled() {
        return this.isEnabled;
    }

    /**
     * Is the user enabled? If they are not enabled they will not be able to log in.
     *
     * @param isEnabled a value that can be used determine if the user can log in.
     */
    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
