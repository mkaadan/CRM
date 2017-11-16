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

  /**
  * Is the user enabled? If they are not enabled they will not be able to log in.
  * @return the current value of wheter the user is allowed to log in into the application.
  */
  public boolean getIsEnabled() {
    return this.isEnabled;
  }

  /**
  * Is the user enabled? If they are not enabled they will not be able to log in.
  * @param isEnabled a value that can be used determine if the user can log in.
  */
  public void setIsEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  /**
   * Gets accountId
   *
   * @return value of accountId
   */
  public long getAccountId() {
    return accountId;
  }

  /**
   * Sets accountId
   *
   * @param accountId value of to be set
   */
  public void setAccountId(long accountId) {
    this.accountId = accountId;
  }

  /**
   * Gets email
   *
   * @return value of email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets email
   *
   * @param email value of to be set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets firstName
   *
   * @return value of firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets firstName
   *
   * @param firstName value of to be set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets lastName
   *
   * @return value of lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets lastName
   *
   * @param lastName value of to be set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets role
   *
   * @return value of role
   */
  public Role getRole() {
    return role;
  }

  /**
   * Sets role
   *
   * @param role value of to be set
   */
  public void setRole(Role role) {
    this.role = role;
  }
}
