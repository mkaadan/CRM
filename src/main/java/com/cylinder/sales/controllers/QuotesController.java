package com.cylinder.sales.controllers;

import java.lang.Iterable;

import com.cylinder.sales.model.Quote;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.sales.model.*;

@Controller
@RequestMapping("/quote")
public class QuotesController {

      @Autowired
      private QuoteRepository quoteRepository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for Quotes.
    	public String list(Model model){
        model.addAttribute("moduleName", "Quotes");
        Iterable<Quote> quoteData =  quoteRepository.findAll();
        model.addAttribute("quoteData", quoteData);
        return "sales/quotelist";
      }
}
