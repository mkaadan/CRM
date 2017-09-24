package com.cylinder.leads;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class LeadsController {
    	@RequestMapping(method=RequestMethod.GET)
    	public String index(Locale locale, Model model) {
    		model.addAttribute("greeting", "Hello!");

    		Date date = new Date();
    		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
    		String formattedDate = dateFormat.format(date);
    		model.addAttribute("currentTime", formattedDate);
    		return "leads/list";
    }
}
