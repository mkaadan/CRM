package com.cylinder.sales.controllers;

import java.lang.Iterable;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;

import javax.validation.Valid;

import com.cylinder.shared.controllers.BaseController;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.sales.model.*;

@Controller
@RequestMapping("/contract")
public class ContractsController extends BaseController{

    /**
     * Sql interface for contract entites.
     */
    @Autowired
    private ContractRepository contractRepository;

    /**
     * Sql interface for crm user entites.
     */
    @Autowired
    private CrmUserRepository userRepository;

    /**
     * The module name that this controller is associated to.
     */
    private final String moduleName = "Contracts";

    /**
     * Render the list view for all available contracts.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping
    public String list(Model model, Authentication auth){
        Iterable<Contract> contractData =  contractRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("contractData", contractData);
        return "sales/contractlist";
    }

    /**
     * Render the list view for all available contracts.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth) {
        Contract contractData = contractRepository.findOne(id);
        super.setCommonModelAttributes(model,auth,userRepository, this.moduleName);
        model.addAttribute("contractData", contractData);
        model.addAttribute("toList", "/contract");
        return "sales/singlecontract";
    }

    /**
     * Render a edit view for a single contract.
     * @param id the id that is associated to some contract.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping(value="/edit/{id}")
    public String editContract(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth,
                               HttpServletResponse response) {
        Contract contract;
        if (contractRepository.existsById(id)) {
            contract = contractRepository.findOne(id);
        } else {
            response.setStatus(404);
            return "redirect:/404.html";
        }
        this.bindUserForm(model, auth);
        model.addAttribute("contractData", contract);
        model.addAttribute("action", "edit/" + id);
        return "sales/editsinglecontract";
    }

    /**
     * Process a form for editing a single record.
     * @param id the id that is associated to some contract.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping(value="/edit/{id}")
    public String saveEditableContract(@PathVariable("id") Long id,
                                       @Valid @ModelAttribute("contractData") Contract contract,
                                       BindingResult result,
                                       Model model,
                                       Authentication auth) {
        if (result.hasErrors()) {
            this.bindUserForm(model, auth);
            model.addAttribute("action","edit/" + contract.getContractId());
            return "sales/editsinglecontract";
        }
        Long assignedId = contractRepository.save(contract).getContractId();
        return "redirect:/contract/records/" + assignedId.toString() ;
    }

    /**
     * Render a view for a creating a single contract.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/new/")
    public String newRecord(Model model,
                            Authentication auth) {
        this.bindUserForm(model,auth);
        model.addAttribute("action","new/");
        model.addAttribute("contractData", new Contract());
        return "sales/editsinglecontract";
    }

    /**
     * Process a new contract form and potentially send errors back.
     * @param contract The contract form object to be processed.
     * @param result the object that binds the data from the view and validates user.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping("/new/")
    public String saveNewContract(@Valid @ModelAttribute("contractData") Contract contract,
                                  BindingResult result,
                                  Model model,
                                  Authentication auth) {
        if (result.hasErrors()) {
            this.bindUserForm(model,auth);
            model.addAttribute("action","new/");
            return "sales/editsinglecontract";
        }
        Long assignedId = contractRepository.save(contract).getContractId();
        return "redirect:/contract/records/" + assignedId.toString() ;
    }


    /**
     * Delete some contract through a delete request.
     * @param id the id that is associated to some contract.
     * @return the name of the template to render.
     */
    @DeleteMapping("/records/{id}")
    @ResponseBody
    public String deleteRecord(@PathVariable("id") Long id) {
        if (contractRepository.existsById(id)) {
            contractRepository.deleteById(id);
            return "";
        } else {
            return "Failed to delete record" + id;
        }

    }

    /**
     * Helper function to bind common model attributes whener generating a list form.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     */
    private void bindUserForm(Model model, Authentication auth) {
        super.setCommonModelAttributes(model,auth,userRepository,this.moduleName);
        //model.addAttribute("userData", userRepository.findAll());
        model.addAttribute("toList", "/contract");
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
