package com.cylinder.leads.controller;

import java.lang.Iterable;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.leads.model.*;

@Controller
@RequestMapping("/lead")
public class LeadsController {

      @Autowired
      private LeadRepository leadRepository;

      @Autowired
      private StatusRepository statusRespository;

      @Autowired
      private SourceRepository sourceRespository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for leads.
    	public String list(Model model){
        model.addAttribute("moduleName", "Leads");
        Iterable<Lead> leadData =  leadRepository.findAll();
        model.addAttribute("leadData", leadData);
        return "leads/list";
      }

      @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
      public String singleRecord(@PathVariable("id") Long id, Model model) {
          Lead leadData = leadRepository.findOne(id);
          model.addAttribute("moduleName", "Leads");
          model.addAttribute("leadData", leadData);
          model.addAttribute("toList", "/");
          return "leads/singlelead";
      }

      @RequestMapping(value="/new/{id}", method=RequestMethod.GET)
      public String newRecord(@PathVariable("id"), Model model) {
          model.addAttribute("moduleName", "Leads");
          Iterable<Source> sourceData = sourceRespository.findAll();
          Iterable<Status> statusData = statusRespository.findAll();
          model.addAttribute("leadStatus", statusData);
          model.addAttribute("leadSource", sourceData);
          return "leads/editsingle";
      }

      @RequestMapping(value="/records", method=RequestMethod.POST)
      @ResponseBody
      public void saveRecord(@PathVariable("id") Optional<Long> id,
                              @RequestBody Lead lead) {
      leadRepository.save(lead);
    }
}
