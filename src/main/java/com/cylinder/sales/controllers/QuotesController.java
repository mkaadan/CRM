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

      @Autowired
      private ProductQuoteRepository productQuoteRepository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for Quotes.
    	public String list(Model model){
        model.addAttribute("moduleName", "Quotes");
        Iterable<Quote> quoteData =  quoteRepository.findAll();
        model.addAttribute("quoteData", quoteData);
        return "sales/quotelist";
      }

    @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
    public String singleRecord(@PathVariable("id") Long id, Model model) {
        Quote quoteData = quoteRepository.findOne(id);
        model.addAttribute("moduleName", "Quotes");
        model.addAttribute("quoteData", quoteData);
        model.addAttribute("toList", "/quote");
        Iterable<ProductQuote> productQuoteData = findAllProductsForQuote(quoteData);
        model.addAttribute("productQuoteData", productQuoteData);
        return "sales/singlequote";
    }

    //Find all the products for a single quote
    private Iterable<ProductQuote> findAllProductsForQuote(Quote quoteData){
        Iterable<ProductQuote> productQuoteData =  productQuoteRepository.findAll();
        for(ProductQuote productQuote : productQuoteData){
            if(productQuote.getQuote() != quoteData){
                productQuoteRepository.delete(productQuote);
            }
        }
        return productQuoteData;
    }
}
