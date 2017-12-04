package com.cylinder.crmusers.model.services;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Service;
import com.cylinder.crmusers.model.Role;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.CrmUserRepository;

/**
* @author Ryan Piper
* Interface for the buisness logic for the admin controller. 
*/
@Service
public interface AdminService {
  public List<Role> findAllRoles();
  public CrmUserRepository getUserRepository();
  public List<CrmUser> findAllUsers();
  public CrmUser saveUser(CrmUser user);
  public CrmUser findUserByAccountId(Long accountId);
  public boolean userExistsByAccountId(Long accountId);
  public void checkForRoleError(BindingResult result, Role role);
  public boolean isOnlyAdmin(Long accountId);
  public int deleteUserByAccountId(Long accountId);
}
