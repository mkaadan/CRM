package com.cylinder.crmusers.model.forms;

import lombok.*;

import com.cylinder.crmusers.model.Role;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;

/**
* @author Ryan Piper
* A form object used to update a crm user details. 
*/
public class AdminUserForm {

  /**
  * The id of the user account to be edited.
  *
  * @param accountId new value for account id.
  * @return the id of the account.
  */
  @Getter
  @Setter
  protected long accountId;

  /**
  * The email associated to a user's account.
  *
  * @param email the new email to associated to the user account.
  * @return the email of the account.
  */
  @Getter
  @Setter
  @Email
  @NotEmpty(message="Please provide an email.")
  protected String email;

  /**
  * The first name associated to a user's account.
  *
  * @param firstName the new first name to associated to the user account.
  * @return the first name of the user.
  */
  @Getter
  @Setter
  @NotEmpty(message="Please provide the first name of the user.")
  protected String firstName;

  /**
  * The last name associated to a user's account.
  *
  * @param lastName the new last name to associated to the user account.
  * @return the last name of the user.
  */
  @Getter
  @Setter
  @NotEmpty(message="Please provide the last name of the user.")
  protected String lastName;

  /**
  * The secuirty role associated to a user's account.
  *
  * @param role the new role to be associated to the user account.
  * @return the secuirty role of the user.
  */
  @Getter
  @Setter
  private Role role;

  protected boolean isEnabled;

  public AdminUserForm(){}

  public AdminUserForm(Long accountId,
                       String email,
                       String firstName,
                       String lastName,
                       Role role,
                       boolean isEnabled){
    this.accountId = accountId;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.isEnabled = isEnabled;
  }
}
