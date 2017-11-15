package com.cylinder.accounts.controllers;

import java.lang.Iterable;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.cylinder.crmusers.model.forms.*;
import com.cylinder.crmusers.model.*;
import com.cylinder.shared.BaseController;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.validation.BindingResult;

/**
* @author Ryan Piper
* The web router controller for logins.
*/
@Controller
@RequestMapping("/")
public class LoginController {

      /**
      * Maps the / namespace so that the user is automatically redirected to the
      * the login view.
      * @return the redirect response.
      */
      @RequestMapping(value="/")
      public String index() {
        return "redirect:/login";
      }

      /**
      * Renders the login form for the user to authenticate against.
      * @param model the model view object that renders the html.
      * @param auth the authentication context that manages which users are logged in.
      */
    	@RequestMapping(value="/login")
    	public String renderLogin(Model model, Authentication auth){
        // If the user is logged in already then redirect to lead.
        if (auth != null && auth.isAuthenticated()) {
          return "redirect:/lead";
        } else {
          return "crmusers/login";
        }
      }

      /**
      * End the session and log the user out .
      * @param model the model view object that renders the html.
      * @param auth the authentication context that manages which users are logged in.
      */
      @PostMapping(value="/logout")
      public String logout(HttpServletRequest request,
                           HttpServletResponse response,
                           Authentication auth) {
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return "redirect:/login?logout=true";
        } else {
            return "redirect:/errors";
        }
      }

      /**
      * Maps empty string to null when a form is submitted.
      * @param binder The object that allows for empty strings to be turned into nulls.
      */
      @InitBinder
      public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
      }

}
