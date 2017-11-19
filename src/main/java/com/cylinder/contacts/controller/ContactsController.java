package com.cylinder.contacts.controller;

import java.lang.Iterable;

import com.cylinder.contacts.model.Contact;
import com.cylinder.contacts.model.ContactRepository;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.accounts.model.*;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping(method=RequestMethod.GET)
    //Render list view for Contact.
    public String list(Model model) {
        model.addAttribute("modelName", "Contacts");
        Iterable<Contact> contactData =  contactRepository.findAll();
        model.addAttribute("contactData", contactData);
        return "contacts/list";
    }

    @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
    public String singleRecord(@PathVariable("id") Long id, Model model) {
        Contact contactData = contactRepository.findOne(id);
        model.addAttribute("moduleName", "Contacts");
        model.addAttribute("contactData", contactData);
        model.addAttribute("toList", "/");
        return "contacts/singlecontact";
    }
}
