package com.cylinder.sales.controllers;

import java.lang.Iterable;

import com.cylinder.sales.model.Contract;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


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
}
