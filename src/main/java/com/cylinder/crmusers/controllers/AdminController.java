package com.cylinder.crmusers.controllers;

import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.Role;
import com.cylinder.crmusers.model.forms.AdminUserForm;
import com.cylinder.crmusers.model.services.AdminService;
import com.cylinder.errors.NotFoundException;
import com.cylinder.shared.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    /**
     * The name of the module this controller is associated to.
     */
    private final String moduleName = "Admin";
    @Autowired
    private AdminService service;
    /**
     * bcrypt encoder for password logic.
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Render the list view that so that a user with ADMIN privledges can get a
     * overview of the registered viewers.
     *
     * @param auth  the authentication context that manages which users are logged in.
     * @param model the view model object that is used to render the html.
     * @return the template to be rendered.
     */
    @GetMapping("/user/overview")
    public String userOverview(Authentication auth,
                               Model model) {
        Iterable<Role> roles = service.findAllRoles();
        Iterable<CrmUser> userList = service.findAllUsers();
        super.setCommonModelAttributes(model, auth, service.getUserRepository(), this.moduleName);
        model.addAttribute("userRole", roles);
        model.addAttribute("userData", userList);
        return "crmusers/admin/list";
    }

    /**
     * Render the form so that the admin may register a new user.
     *
     * @param auth  the authentication context that manages which users are logged in.
     * @param model the view model object that is used to render the html.
     * @return the template to be rendered.
     */
    @GetMapping("/user/new")
    public String newUserForm(Authentication auth,
                              Model model) {
        Iterable<Role> roles = service.findAllRoles();
        CrmUser newUser = new CrmUser();
        newUser.setIsEnabled(true);
        model.addAttribute("userForm", newUser);
        model.addAttribute("userRole", roles);
        super.setCommonModelAttributes(model, auth, service.getUserRepository(), this.moduleName);
        return "crmusers/admin/newuser";
    }

    /**
     * Process the new user form and handle any validation issues.
     *
     * @param user   the user submitted form for creating a new user.
     * @param result the object that binds the data from the view and validates user.
     * @param auth   the authentication context that manages which users are logged in.
     * @param model  the view model object that is used to render the html.
     * @return the template to be rendered.
     */
    @PostMapping("/user/new")
    public String processNewUserForm(@Valid @ModelAttribute("userForm") CrmUser user,
                                     BindingResult result,
                                     Authentication auth,
                                     Model model) {
        service.checkForRoleError(result, user.getRole());
        if (result.hasErrors()) {
            Iterable<Role> roles = service.findAllRoles();
            super.setCommonModelAttributes(model,
                    auth,
                    service.getUserRepository(),
                    this.moduleName);

            model.addAttribute("userRole", roles);
            return "crmusers/admin/newuser";
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            service.saveUser(user);
            return "redirect:/admin/user/overview";
        }
    }

    /**
     * Render the form so that the admin may alter basic details about a user.
     *
     * @param userId the id of the user to be altered.
     * @param auth   the authentication context that manages which users are logged in.
     * @param model  the view model object that is used to render the html.
     * @return the template to be rendered.
     */
    @GetMapping("/user/edit/{userId}")
    public String adminUserEdit(@PathVariable("userId") Long userId,
                                Authentication auth,
                                Model model) {
        if (!service.userExistsByAccountId(userId)) {
            throw new NotFoundException();
        }
        CrmUser user = service.findUserByAccountId(userId);
        AdminUserForm form = user.toAdminUserForm();
        Iterable<Role> roles = service.findAllRoles();
        super.setCommonModelAttributes(model,
                auth,
                service.getUserRepository(),
                this.moduleName);
        model.addAttribute("userForm", form);
        model.addAttribute("userRole", roles);
        return "crmusers/admin/userform";
    }

    /**
     * Process and validate the form.
     *
     * @param userId   the id of the user to be altered.
     * @param userForm the admin form to processed.
     * @param result   the object that binds the data from the view and validates userForm.
     * @param auth     the authentication context that manages which users are logged in.
     * @param response the http response object.
     * @param model    the view model object that is used to render the html.
     * @return the template to be rendered.
     */
    @PostMapping("/user/edit/{userId}")
    public String adminUserSave(@PathVariable("userId") Long userId,
                                @Valid @ModelAttribute("userForm") AdminUserForm userForm,
                                BindingResult result,
                                Authentication auth,
                                HttpServletResponse response,
                                Model model) {
        if (!service.userExistsByAccountId(userForm.getAccountId())) {
            throw new NotFoundException();
        } else {
            CrmUser user = service.findUserByAccountId(userId);
            service.checkForRoleError(result, userForm.getRole());
            if (userForm.getRole().getRoleId().equals(new Long("2"))) {
                if (service.isOnlyAdmin(userId)) {
                    result.addError(new FieldError("Role",
                            "roleName",
                            "Cannot alter role permission."));
                }
            }
            if (result.hasErrors()) {
                super.setCommonModelAttributes(model,
                        auth,
                        service.getUserRepository(),
                        this.moduleName);
                Iterable<Role> roles = service.findAllRoles();
                model.addAttribute("userForm", userForm);
                model.addAttribute("userRole", roles);
                return "crmusers/admin/userform";
            } else {
                user.mergeFromAdminUserForm(userForm);
                service.saveUser(user);
                return "redirect:/admin/user/overview";
            }
        }
    }

    /**
     * Process a delete request for a user.
     *
     * @param userId   the id of the user to be altered.
     * @param response the http response object.
     * @return the template to be rendered.
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        if (service.userExistsByAccountId(userId)) {
            if (service.findUserByAccountId(userId).getRole().getRoleName() == "ADMIN") {
                if (service.isOnlyAdmin(userId)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete only admin user.");
                }
            }
            service.deleteUserByAccountId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            throw new NotFoundException();
        }
    }
}
