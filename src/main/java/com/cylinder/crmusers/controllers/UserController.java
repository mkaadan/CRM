package com.cylinder.crmusers.controllers;

import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.crmusers.model.forms.PasswordForm;
import com.cylinder.shared.controllers.BaseController;
import com.cylinder.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Ryan Piper
 * The web router controller user password alterations.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

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
    private final String moduleName = "Users";

    /**
     * Render a form that allows the user to alter their password.
     *
     * @param userId   the id of the user wants to change their password.
     * @param auth     the authentication context that manages which users are logged in.
     * @param response the http response object
     * @param model    the view model object that is used to render the html.
     * @return The name of the template to be rendered.
     */
    @GetMapping("/edit/{userId}")
    public String editUserPassword(@PathVariable("userId") Long userId,
                                   Authentication auth,
                                   Model model) {
        if (!userRepository.existsByAccountId(userId)) {
          throw new NotFoundException();
        }
        CrmUser currentUser = userRepository.findByEmail(auth.getName());
        // check if the authenticated user is altering their own password; restict otherwise.
        if (userId == currentUser.getAccountId()) {
            super.setCommonModelAttributes(model,
                                           auth,
                                           userRepository,
                                           this.moduleName);
            PasswordForm passForm = new PasswordForm();
            passForm.setAccountId(currentUser.getAccountId());
            model.addAttribute("passForm", passForm);
            return "crmusers/users/userform";
        } else {
            throw new RestrictedException();
        }
    }

    /**
     * Validate a form that allows the user to alter their password.
     *
     * @param userId   the id of the user wants to change their password.
     * @param passForm the password form to be validated.
     * @param result   the object that binds the data from the view and validates passForm.
     * @param auth     the authentication context that manages which users are logged in.
     * @param response the http response object
     * @param model    the view model object that is used to render the html.
     * @return The name of the template to be rendered.
     */
    @PostMapping("/edit/{userId}")
    public String editUser(@PathVariable("userId") Long userId,
                           @Valid @ModelAttribute("passForm") PasswordForm passForm,
                           BindingResult result,
                           Authentication auth,
                           HttpServletResponse response,
                           Model model) {
        if (!userRepository.existsByAccountId(userId)) {
          throw new NotFoundException();
        }
        CrmUser currentUser = userRepository.findByEmail(auth.getName());
        // check if the authenticated user is altering their own password; restict otherwise.
        if (userId == currentUser.getAccountId()) {
            Optional<FieldError> passwordError =
                    passForm.isUserSubPasswordValid(passwordEncoder,
                            currentUser.getPassword(),
                            "currentPassword");
            if (passwordError.isPresent()) {
                result.addError(passwordError.get());
            }
            if (result.hasErrors()) {
                super.setCommonModelAttributes(model,
                                               auth,
                                               userRepository,
                                               this.moduleName);
                return "crmusers/users/userform";
            } else {
                passForm.hashNewPassword(passwordEncoder);
                userRepository.updatePassword(passForm.getNewPassword(),
                        currentUser.getAccountId());
                return "redirect:/user/edit/" + userId;
            }
        } else {
            throw new RestrictedException();
        }
    }

    /**
     * Maps empty string to null when a form is submitted.
     *
     * @param binder The object that allows for empty strings to be turned into nulls.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
