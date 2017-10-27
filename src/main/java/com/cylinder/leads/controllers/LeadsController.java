package com.cylinder.leads;

import java.lang.Iterable;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.leads.Lead;
import com.cylinder.leads.LeadRepository;


@Controller
@RequestMapping("/")
public class LeadsController {
      @Autowired
      private LeadRepository leadRepository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for leads.
    	public String list(Model model){
        model.addAttribute("moduleName", "Leads");
        Iterable<Lead> leadData =  leadRepository.findAll();
        model.addAttribute("leadData", leadData);
        return "leads/list";
      }

      @RequestMapping(value="/{id}", method=RequestMethod.GET)
      public String singleRecord(@PathVariable("id") Long id, Model model) {
          Lead leadData = leadRepository.findOne(id);
          model.addAttribute("moduleName", "Leads");
          model.addAttribute("leadData", leadData);
          model.addAttribute("toList", "/");
          return "leads/singlelead";
      }
}