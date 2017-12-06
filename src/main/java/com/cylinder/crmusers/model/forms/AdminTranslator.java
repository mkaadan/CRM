package com.cylinder.crmusers.model.forms;

import com.cylinder.crmusers.model.CrmUser;

public class AdminTranslator {

  public AdminTranslator(){}

  /**
  * Alter the state of the user based upon a form submission.
  *
  * @param form The form that was filled out by the user.
  * @param user The CrmUser that is to be merged with the new data.
  */
  public void mergeWithCrmUser(AdminUserForm form, CrmUser user) {
    user.setEmail((form.getEmail() == null) ? user.getEmail() : form.getEmail());
    user.setFirstName((form.getFirstName() == null) ? user.getFirstName() : form.getFirstName());
    user.setLastName((form.getLastName() == null) ? user.getLastName() : form.getLastName());
    user.setIsEnabled((form.getIsEnabled() != form.getIsEnabled()) ? user.getIsEnabled() : form.getIsEnabled());
    user.setRole((form.getRole() == null) ? user.getRole() : form.getRole());
  }

  /**
   * Map a subset of the user data into a form so that it may edited.
   *
   * @return the form.
   */
  public AdminUserForm fromCrmUser(CrmUser user) {
    return new AdminUserForm(
            user.getAccountId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getRole(),
            user.getIsEnabled()
    );
  }
}
