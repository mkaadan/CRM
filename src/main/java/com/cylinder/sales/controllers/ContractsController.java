package com.cylinder.sales.controllers;

import java.lang.Iterable;

import com.cylinder.sales.model.Contract;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.Valid;

import com.cylinder.sales.model.*;

@Controller
@RequestMapping("/contract")
public class ContractsController {

      @Autowired
      private ContractRepository contractRepository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for Contracts.
    	public String list(Model model){
        model.addAttribute("moduleName", "Contracts");
        Iterable<Contract> contractData =  contractRepository.findAll();
        model.addAttribute("contractData", contractData);
        return "sales/contractlist";
      }

    @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
    public String singleRecord(@PathVariable("id") Long id, Model model) {
        Contract contractData = contractRepository.findOne(id);
        model.addAttribute("moduleName", "Contracts");
        model.addAttribute("contractData", contractData);
        model.addAttribute("toList", "/contract");
        return "sales/singlecontract";
    }
}
