package com.cylinder.accounts.controllers;

import java.lang.Iterable;

import com.cylinder.accounts.model.Account;
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

    @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
    public String singleRecord(@PathVariable("id") Long id, Model model) {
        Account accountData = accountRepository.findOne(id);
        model.addAttribute("moduleName", "Accounts");
        model.addAttribute("accountData", accountData);
        model.addAttribute("toList", "/");
        return "accounts/singleaccount";
    }
}
