package com.cylinder.accounts.controllers;

import java.lang.Iterable;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.accounts.model.*;

@Controller
@RequestMapping("/accounts")
public class AccountsController {

      @Autowired
      private AccountRepository AccountRepository;

      @Autowired
      private StatusRepository statusRespository;

      @Autowired
      private SourceRepository sourceRespository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for Accounts.
    	public String list(Model model){
        model.addAttribute("moduleName", "Account");
        Iterable<Account> AccountData =  AccountRepository.findAll();
        model.addAttribute("AccountData", AccountData);
        return "accounts/list";
      }

}
