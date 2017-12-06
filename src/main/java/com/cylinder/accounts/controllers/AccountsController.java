package com.cylinder.accounts.controllers;

import com.cylinder.accounts.model.Account;
import com.cylinder.accounts.model.AccountRepository;
import com.cylinder.accounts.model.AccountTypeRepository;
import com.cylinder.contacts.model.ContactRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.errors.NotFoundException;
import com.cylinder.shared.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountsController extends BaseController {
    /**
     * Sql interface for account entites.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Sql interface for type entites.
     */
    @Autowired
    private AccountTypeRepository typeRepository;

    /**
     * Sql interface for contact entites.
     */
    @Autowired
    private ContactRepository contactRepository;

    /**
     * Sql interface for crm user entites.
     */
    @Autowired
    private CrmUserRepository userRepository;
    private final String moduleName = "Accounts";

    /**
     * Render the list view for all available accounts.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping
    public String list(Model model, Authentication auth) {

        Iterable<Account> accountData = accountRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, moduleName);
        model.addAttribute("accountData", accountData);

        return "accounts/list";
    }

    /**
     * Render the list view for all available accounts.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping(value = "/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth) {

        if (accountRepository.existsById(id)) {
            Account accountData = accountRepository.findOne(id);
            super.setCommonModelAttributes(model, auth, userRepository, moduleName);
            model.addAttribute("accountData", accountData);
            model.addAttribute("toList", "/account");

            return "accounts/singleaccount";
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Render a edit view for a single account.
     *
     * @param id    the id that is associated to some account.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping(value = "/edit/{id}")
    public String editAccount(@PathVariable("id") Long id,
                              Model model,
                              Authentication auth,
                              HttpServletResponse response) {

        Account account;
        if (accountRepository.existsById(id)) {
            account = accountRepository.findOne(id);
        } else {
            throw new NotFoundException();
        }

        bindAccountUserForm(model, auth);

        model.addAttribute("accountData", account);
        model.addAttribute("action", "edit/" + id);

        return "accounts/editsingle";
    }

    /**
     * Process a form for editing a single record.
     *
     * @param id    the id that is associated to some account.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping(value = "/edit/{id}")
    public String saveEditableAccount(@PathVariable("id") Long id,
                                      @Valid @ModelAttribute("accountData") Account account,
                                      BindingResult result,
                                      Model model,
                                      Authentication auth) {

        if (accountRepository.existsById(account.getAccountId())) {
            if (result.hasErrors()) {
                this.bindAccountUserForm(model, auth);
                model.addAttribute("action", "edit/" + account.getAccountId());
                return "accounts/editsingle";
            }
            areAddressesNull(account);

            CrmUser user = userRepository.findByEmail(auth.getName());
            account.setLastModifiedBy(user);
            Long assignedId = accountRepository.save(account).getAccountId();
            return "redirect:/account/records/" + assignedId.toString();
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Render a view for a creating a single account.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping(value = "/new/")
    public String newRecord(Model model, Authentication auth) {
        bindAccountUserForm(model, auth);
        model.addAttribute("action", "new/");
        model.addAttribute("accountData", new Account());
        return "accounts/editsingle";
    }

    /**
     * Process a new account form and potentially send errors back.
     *
     * @param account The account form object to be processed.
     * @param result  the object that binds the data from the view and validates user.
     * @param model   the view model object that is used to render the html.
     * @param auth    the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping("/new/")
    public String saveNewAccount(@Valid @ModelAttribute("accountData") Account account,
                                 BindingResult result,
                                 Model model,
                                 Authentication auth) {
        if (result.hasErrors()) {
            this.bindAccountUserForm(model, auth);
            model.addAttribute("action", "new/");
            return "accounts/editsingle";
        }
        areAddressesNull(account);

        CrmUser user = userRepository.findByEmail(auth.getName());
        account.setCreatedBy(user);
        Long assignedId = accountRepository.save(account).getAccountId();
        return "redirect:/account/records/" + assignedId.toString();
    }

    /**
     * Delete some account through a delete request.
     *
     * @param id the id that is associated to some lead.
     * @return the name of the template to render.
     */
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

    /**
     * Helper function to bind common model attributes whenever generating a list form.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     */
    private void bindAccountUserForm(Model model, Authentication auth) {
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("userData", userRepository.findAll());
        model.addAttribute("accountType", typeRepository.findAll());
        model.addAttribute("parentData", accountRepository.findAll());
        model.addAttribute("contactData", contactRepository.findAll());
        model.addAttribute("toList", "/account");
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

    private void areAddressesNull(Account account) {

        if (account.getBillingAddress().areFieldsNull()) {
            account.setBillingAddress(null);
        }
        if (account.getShippingAddress().areFieldsNull()) {
            account.setShippingAddress(null);
        }
    }
}
