package com.cylinder.leads;

import java.util.ArrayList;
import com.cylinder.leads.Lead;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class LeadsController {
    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for leads. 
    	public String index(Model model) {
    		return "leads/list";
    }
}
