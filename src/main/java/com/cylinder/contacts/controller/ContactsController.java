package com.cylinder.contacts.controller;

import com.cylinder.accounts.model.AccountRepository;
import com.cylinder.contacts.model.Contact;
import com.cylinder.contacts.model.ContactRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.errors.NotFoundException;
import com.cylinder.shared.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Optional;

/**
 * @author Ryan Piper
 */
@Controller
@RequestMapping("/contact")
public class ContactsController extends BaseController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CrmUserRepository userRepository;

    @Autowired
    private AccountRepository AccountRepository;

    private final String moduleName = "Contacts";

    /**
     * Render the list view for all available contacts.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     * @author Justin
     */
    @GetMapping
    public String list(Model model, Authentication auth) {
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("contactData", contactRepository.findAll());
        return "contacts/list";
    }

    /**
     * Render the contact view for a contact.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Authentication auth,
                               Model model) {
        if (contactRepository.existsByContactId(id)) {
            Contact contactData = contactRepository.findOne(id);
            super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
            model.addAttribute("contactData", contactData);
            model.addAttribute("toList", "/contact/");
            return "contacts/singlecontact";
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Render a edit view for a single contact.
     *
     * @param id    the id that is associated to some contact.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping(value = "/edit/{id}")
    public String editLead(@PathVariable("id") Long id,
                           Model model,
                           Authentication auth,
                           HttpServletResponse response) {
        Contact contact;
        if (contactRepository.existsByContactId(id)) {
            contact = contactRepository.findOne(id);
        } else {
            throw new NotFoundException();
        }
        this.bindContactForm(model, auth, Optional.of(id));
        model.addAttribute("contactData", contact);
        model.addAttribute("action", "edit/" + id);
        return "contacts/contactform";
    }

    /**
     * Process a form for editing a single contact.
     *
     * @param id    the id that is associated to some contact.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping(value = "/edit/{id}")
    public String saveEditableContact(@PathVariable("id") Long id,
                                      @Valid @ModelAttribute("contactData") Contact contact,
                                      BindingResult result,
                                      Model model,
                                      Authentication auth) {
        if (contactRepository.existsByContactId(contact.getContactId())) {
            if (result.hasErrors()) {
                this.bindContactForm(model, auth, Optional.of(id));
                model.addAttribute("action", "edit/" + contact.getContactId());
                return "contacts/contactform";
            }
            checkAddressesForNull(contact);
            CrmUser user = userRepository.findByEmail(auth.getName());
            contact.setLastModifiedBy(user);
            Long assignedId = contactRepository.save(contact).getContactId();
            return "redirect:/contact/records/" + assignedId.toString();
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Render a view for a creating a single contact.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/new/")
    public String newRecord(Model model,
                            Authentication auth) {
        this.bindContactForm(model, auth, Optional.empty());
        model.addAttribute("action", "new/");
        model.addAttribute("contactData", new Contact());
        return "contacts/contactform";
    }

    /**
     * Process a new contact form and potentially send errors back.
     *
     * @param contact The contact form object to be processed.
     * @param result  the object that binds the data from the view and validates user.
     * @param model   the view model object that is used to render the html.
     * @param auth    the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping("/new/")
    public String saveNewLead(@Valid @ModelAttribute("contactData") Contact contact,
                              BindingResult result,
                              Model model,
                              Authentication auth) {
        if (result.hasErrors()) {
            this.bindContactForm(model, auth, Optional.empty());
            model.addAttribute("action", "new/");
            return "contacts/contactform";
        }
        checkAddressesForNull(contact);
        CrmUser user = userRepository.findByEmail(auth.getName());
        contact.setCreatedBy(user);
        Long assignedId = contactRepository.save(contact).getContactId();
        return "redirect:/contact/records/" + assignedId.toString();
    }

    /**
     * Helper function to bind common model attributes whener generating a list form.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     */
    private void bindContactForm(Model model, Authentication auth, Optional<Long> contactId) {
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("accountData", AccountRepository.findAll());
        if (contactId.isPresent()) {
            model.addAttribute("otherContactData",
                    contactRepository.findAllWithoutContactId(contactId.get()));
        } else {
            model.addAttribute("otherContactData", contactRepository.findAll());
        }
        model.addAttribute("userData", userRepository.findAll());
        model.addAttribute("toList", "/contact/");
    }

    /**
     * Delete some contact through a delete request.
     *
     * @param id the id that is associated to some contact.
     * @return the name of the template to render.
     */
    @DeleteMapping("/records/{id}")
    @ResponseBody
    public String deleteRecord(@PathVariable("id") Long id) {
        if (contactRepository.existsByContactId(id)) {
            contactRepository.deleteByContactId(id);
            return "";
        } else {
            return "Failed to delete record" + id;
        }

    }

    /**
     * Maps empty string to null when a form is submitted.
     *
     * @param binder The object that allows for empty strings to be turned into nulls.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private void checkAddressesForNull(Contact contact) {
        if (contact.getMailingAddress().areFieldsNull()) {
            contact.setMailingAddress(null);
        }
        if (contact.getOtherAddress().areFieldsNull()) {
            contact.setOtherAddress(null);
        }
    }
}
