package com.cylinder.leads;

import com.cylinder.leads.Lead;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class LeadsController {
    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for leads. 
    	public String index(Model model) {
    		return "leads/list";
}
