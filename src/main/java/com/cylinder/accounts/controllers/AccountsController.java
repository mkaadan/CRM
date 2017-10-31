package com.cylinder.accounts.controllers;

import java.lang.Iterable;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.accounts.model.*;

@Controller
@RequestMapping("/account")
public class AccountsController {

      @Autowired
      private AccountRepository accountRepository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for Accounts.
    	public String list(Model model){
        model.addAttribute("moduleName", "Accounts");
        Iterable<Account> accountData =  accountRepository.findAll();
        model.addAttribute("accountData", accountData);
        return "accounts/list";
      }
}
