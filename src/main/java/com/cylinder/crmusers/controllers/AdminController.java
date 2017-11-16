package com.cylinder.accounts.controllers;

import java.lang.Iterable;
import java.util.Optional;

import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.cylinder.crmusers.model.*;
import com.cylinder.crmusers.model.forms.*;
import com.cylinder.shared.controllers.BaseController;

import org.springframework.validation.FieldError;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

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
  * bcrypt encoder for password logic.
  */
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  /**
  * The name of the module this controller is associated to.
  */
  private final String moduleName = "Admin";

  /**
  * Render the list view that so that a user with ADMIN privledges can get a
  * overview of the registered viewers.
  * @param auth the authentication context that manages which users are logged in.
  * @param model the view model object that is used to render the html.
  * @return the template to be rendered.
  */
  @GetMapping("/user/overview")
  public String userOverview(Authentication auth,
                             Model model) {
    Iterable<Role> roles = roleRepository.findAll();
    Iterable<CrmUser> userList = userRepository.findAll();
    super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
    model.addAttribute("userRole", roles);
    model.addAttribute("userData", userList);
    return "crmusers/admin/list";
  }

  /**
  * Render the form so that the admin may register a new user.
  * @param auth the authentication context that manages which users are logged in.
  * @param model the view model object that is used to render the html.
  * @return the template to be rendered.
  */
  @GetMapping("/user/new")
  public String newUserForm(Authentication auth,
                            Model model) {
    Iterable<Role> roles = roleRepository.findAll();
    CrmUser newUser = new CrmUser();
    newUser.setIsEnabled(true);
    model.addAttribute("userForm", newUser);
    model.addAttribute("userRole", roles);
    super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
    return "crmusers/admin/newuser";
  }

  /**
  * Process the new user form and handle any validation issues.
  * @param user the user submitted form for creating a new user.
  * @param result the object that binds the data from the view and validates user.
  * @param auth the authentication context that manages which users are logged in.
  * @param model the view model object that is used to render the html.
  * @return the template to be rendered.
  */
  @PostMapping("/user/new")
  public String processNewUserForm(@Valid @ModelAttribute("userForm") CrmUser user,
                                   BindingResult result,
                                   Authentication auth,
                                   Model model) {
    Optional<FieldError> roleError = user.getRole()
                                         .isValid(roleRepository,
                                                  "roleName");
    if(roleError.isPresent()) {
      result.addError(roleError.get());
    }
    if (result.hasErrors()) {
        Iterable<Role> roles = roleRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("userRole", roles);
        return "crmusers/admin/newuser";
    } else {
        userRepository.save(user);
        return "redirect:/admin/user/overview";
    }
  }

  /**
  * Render the form so that the admin may alter basic details about a user.
  * @param userId the id of the user to be altered.
  * @param auth the authentication context that manages which users are logged in.
  * @param model the view model object that is used to render the html.
  * @return the template to be rendered.
  */
  @GetMapping("/user/edit/{userId}")
  public String adminUserEdit(@PathVariable("userId") Long userId,
                              Authentication auth,
                              Model model) {
    CrmUser user = userRepository.findByAccountId(userId);
    AdminUserForm form = user.toAdminUserForm();
    Iterable<Role> roles = roleRepository.findAll();
    super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
    model.addAttribute("userForm", form);
    model.addAttribute("userRole", roles);
    return "crmusers/admin/userform";
  }

  /**
  * Process and validate the form.
  * @param userId the id of the user to be altered.
  * @param userForm the admin form to processed.
  * @param result the object that binds the data from the view and validates userForm.
  * @param auth the authentication context that manages which users are logged in.
  * @param response the http response object.
  * @param model the view model object that is used to render the html.
  * @return the template to be rendered.
  */
  @PostMapping("/user/edit/{userId}")
  public String adminUserSave(@PathVariable("userId") Long userId,
                              @Valid @ModelAttribute("userForm") AdminUserForm userForm,
                              BindingResult result,
                              Authentication auth,
                              HttpServletResponse response,
                              Model model) {
    if (userId != userForm.getAccountId()) {
      response.setStatus(400);
      return "redirect:/400.html";
    } else {
      CrmUser user = userRepository.findByAccountId(userId);
      Optional<FieldError> roleError = userForm.getRole()
                                               .isValid(roleRepository,
                                                        "role.roleName");
      if (roleError.isPresent()) {
        result.addError(roleError.get());
      }
      if (result.hasErrors()) {
        super.setCommonModelAttributes(model,
                                       auth,
                                       userRepository,
                                       this.moduleName);
        Iterable<Role> roles = roleRepository.findAll();
        model.addAttribute("userForm", userForm);
        model.addAttribute("userRole", roles);
        return "crmusers/admin/userform";
      } else {
        user.mergeFromAdminUserForm(userForm);
        userRepository.save(user);
        return "redirect:/admin/user/overview";
      }
    }
  }

    /**
    * Process a delete request for a user.
    * @param userId the id of the user to be altered.
    * @param response the http response object.
    * @return the template to be rendered.
    */
    @DeleteMapping("/user/{userId}")
    @ResponseBody
    public String deleteUser(@PathVariable("userId") Long userId, HttpServletResponse response) {
      if (userRepository.existsByAccountId(userId)) {
        userRepository.deleteByAccountId(userId);
        return "";
      } else {
        response.setStatus(400);
        return "";
      }
   }
}
