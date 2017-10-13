package com.cylinder.leads;

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
    	public @ResponseBody String index(){
        Lead lead = leadRepository.findOne(1L);
         return lead.toString();
      }
}
