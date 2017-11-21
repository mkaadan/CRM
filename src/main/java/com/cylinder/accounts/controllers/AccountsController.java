package com.cylinder.accounts.controllers;

import java.lang.Iterable;

import com.cylinder.accounts.model.Account;
import com.cylinder.contacts.model.ContactRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.shared.controllers.BaseController;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.accounts.model.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountsController extends BaseController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private CrmUserRepository userRepository;
    private final String moduleName = "Accounts";

    @GetMapping
    public String list(Model model, Authentication auth) {

        Iterable<Account> accountData = accountRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, moduleName);
        model.addAttribute("accountData", accountData);

        return "accounts/list";
    }

    @GetMapping(value = "/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth) {

        Account accountData = accountRepository.findOne(id);
        super.setCommonModelAttributes(model, auth, userRepository, moduleName);
        model.addAttribute("accountData", accountData);
        model.addAttribute("toList", "/account");

        return "accounts/singleaccount";
    }

    @GetMapping(value = "/edit/{id}")
    public String editAccount(@PathVariable("id") Long id,
                              Model model,
                              Authentication auth,
                              HttpServletResponse response) {

        Account account;
        if (accountRepository.existsById(id)) {
            account = accountRepository.findOne(id);
        } else {
            response.setStatus(404);
            return "redirect:/404.html";
        }

        bindUserForm(model, auth);

        model.addAttribute("accountData", account);
        model.addAttribute("action", "edit/" + id);

        return "accounts/editsingle";
    }

    @PostMapping(value = "/edit/{id}")
    public String saveEditableAccount(@PathVariable("id") Long id,
                                      @Valid @ModelAttribute("accountData") Account account,
                                      BindingResult result,
                                      Model model,
                                      Authentication auth) {

        if (result.hasErrors()) {
            this.bindUserForm(model, auth);
            model.addAttribute("action", "edit/" + account.getAccountId());
            return "accounts/editsingle";
        }
        if (account.getBillingAddress().areFieldsNull()) {
            account.setBillingAddress(null);
        }
        if (account.getShippingAddress().areFieldsNull()) {
            account.setShippingAddress(null);
        }

        CrmUser user = userRepository.findByEmail(auth.getName());
        account.setLastModifiedBy(user);
        Long assignedId = accountRepository.save(account).getAccountId();
        return "redirect:/account/records/" + assignedId.toString();
    }

    @GetMapping(value = "/new/")
    public String newRecord(Model model, Authentication auth) {
        bindUserForm(model, auth);
        model.addAttribute("action", "new/");
        model.addAttribute("accountData", new Account());
        return "accounts/editsingle";
    }

    @PostMapping("/new/")
    public String saveNewAccount(@Valid @ModelAttribute("accountData") Account account,
                                 BindingResult result,
                                 Model model,
                                 Authentication auth) {
        if (result.hasErrors()) {
            this.bindUserForm(model, auth);
            model.addAttribute("action", "new/");
            return "accounts/editsingle";
        }
        if (account.getBillingAddress().areFieldsNull()) {
            account.setBillingAddress(null);
        }
        if (account.getShippingAddress().areFieldsNull()) {
            account.setShippingAddress(null);
        }

        CrmUser user = userRepository.findByEmail(auth.getName());
        account.setCreatedBy(user);
        Long assignedId = accountRepository.save(account).getAccountId();
        return "redirect:/account/records/" + assignedId.toString();
    }

    @DeleteMapping("/records/{id}")
    @ResponseBody
    public String deleteRecord(@PathVariable("id") Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return "";
        } else {
            return "Failed to delete record" + id;
        }
    }

    private void bindUserForm(Model model, Authentication auth) {
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("userData", userRepository.findAll());
        model.addAttribute("accountType", typeRepository.findAll());
        model.addAttribute("parentData", accountRepository.findAll());
        model.addAttribute("contactData", contactRepository.findAll());
        model.addAttribute("toList", "/account");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
