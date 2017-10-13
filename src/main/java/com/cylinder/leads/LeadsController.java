package com.cylinder.leads;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.cylinder.leads.Lead;
import com.cylinder.leads.LeadRepository;


@Controller
@RequestMapping("/")
public class LeadsController {

      private LeadRepository leadRepository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for leads.
    	public @ResponseBody Iterable<Lead> index(){
        return leadRepository.findAll();
      }
}
