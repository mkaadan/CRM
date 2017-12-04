package com.cylinder.crmusers.model.services;

import com.cylinder.crmusers.model.RoleRepository;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.Role;

import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Optional;
import java.util.List;

/**
* @author Ryan Piper
* Implements the Admin Buisness Logic.
*/
@Component
public class AdminServiceImpl implements AdminService {
  /**
  * Sql interface for security role entites.
  */
  @Autowired
  private RoleRepository roleRepository;

  /**
   * Sql interface for crm user entites.
   */
  @Autowired
  private CrmUserRepository userRepository;

   /**
   * gets the CrmUserRepository
   * @return CrmUserRepository
   */
   public CrmUserRepository getUserRepository() {
      return userRepository;
   }

   /**
   * @return the available roles.
   *
   */
   public List<Role> findAllRoles() {
     return roleRepository.findAll();
   }

   /**
   * @return all available users.
   *
   */
   public List<CrmUser> findAllUsers() {
     return userRepository.findAll();
   }

   /**
   * @return the user that was saved.
   */
   public CrmUser saveUser(CrmUser user) {
     return userRepository.save(user);
   }

   /**
   * @return return a user based upon its account id.
   *
   */
   public CrmUser findUserByAccountId(Long accountId) {
     return userRepository.findByAccountId(accountId);
   }

   /**
   * @return check if a user exists based upon its account id.
   *
   */
   public boolean userExistsByAccountId(Long accountId) {
     return userRepository.existsByAccountId(accountId);
   }

   /**
   * Checks if the user selected a valid role.
   *
   */
   public void checkForRoleError(BindingResult result, Role role) {
     Optional<FieldError> roleError;
     if (roleRepository.existsByRoleAndId(role.getRoleName(), role.getRoleId())) {
         roleError = Optional.empty();
     } else {
         roleError =  Optional.of(new FieldError("Role", "roleName", "This role doesn't exist."));
     }
     if (roleError.isPresent()) {
         result.addError(roleError.get());
     }
   }

   /**
   * @return the amount of rows effected.
   *
   */
   public int deleteUserByAccountId(Long accountId) {
     return userRepository.deleteByAccountId(accountId);
   }

   /**
   * The user being alted or deleted the only admin user. 
   *
   */
   public boolean isOnlyAdmin(Long accountId) {
     Long roleId = roleRepository.findByRoleName("ADMIN").getRoleId();
     CrmUser user = this.findUserByAccountId(accountId);
     String count = userRepository.countByRoleId(roleId).toString();
     if (user.getRole().getRoleId() == roleId) {
       if(userRepository.countByRoleId(roleId) == 1) {
         return true;
       } else {
         return false;
       }
     } else {
       return false;
     }

   }
}
