package com.cylinder.accounts.controllers;

import java.lang.Iterable;

import com.cylinder.accounts.model.Account;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.accounts.model.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountsController {

      @Autowired
      private AccountRepository accountRepository;
      @Autowired
      private TypeRepository typeRepository;

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

    @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
    public String editRecord(@ModelAttribute("accountData") Account account,
                             @PathVariable("id") Long id,
                             Model model) {
        if (accountRepository.existsById(id)) {
            account = accountRepository.findOne(id);
        } else {
            return "redirect:/errors";
        }
        Iterable<Account> accountData =  accountRepository.findAll();
        model.addAttribute("accountData", accountData);
        Iterable<Type> typeData = typeRepository.findAll();
        model.addAttribute("accountType", typeData);
        model.addAttribute("moduleName", "Accounts");
        model.addAttribute("accountData", account);
        model.addAttribute("toList", "/account");
        return "accounts/editsingle";
    }

    @RequestMapping(value="/new/", method=RequestMethod.GET)
    public String newRecord(@ModelAttribute("accountData") Account account,
                            Model model) {
        Iterable<Type> typeData = typeRepository.findAll();
        model.addAttribute("accountType", typeData);
        model.addAttribute("moduleName", "Accounts");
        model.addAttribute("accountData", new Account());
        model.addAttribute("toList", "/account");
        return "accounts/editsingle";
    }

    @RequestMapping(value="/records/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public String deleteRecord(@PathVariable("id") Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return "";
        } else {
            return "Failed to delete record" + id;
        }
    }

    @RequestMapping(value="/records", method=RequestMethod.POST)
    public String saveRecord(@Valid @ModelAttribute("accountData") Account account,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("moduleName", "Accounts");
            Iterable<Type> typeData = typeRepository.findAll();
            model.addAttribute("accountType", typeData);
            model.addAttribute("toList", "/account");
            return "accounts/editsingle";
        }
        if (account.getShippingAddress().areFieldsNull()) {
            account.setShippingAddress(null);
        }
        if (account.getBillingAddress().areFieldsNull()) {
            account.setBillingAddress(null);
        }
        Long assignedId = accountRepository.save(account).getAccountId();
        return "redirect:/account/records/" + assignedId.toString() ;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
