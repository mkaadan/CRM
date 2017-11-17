package com.cylinder.sales.controllers;

import java.lang.Iterable;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.security.core.Authentication;

import com.cylinder.shared.controllers.BaseController;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.sales.model.*;


@Controller
@RequestMapping("/quote")
public class QuotesController extends BaseController{

    /**
     * Sql interface for quote entites.
     */
    @Autowired
    private QuoteRepository quoteRepository;

    /**
     * Sql interface for productQuote entites.
     */
    @Autowired
    private ProductQuoteRepository productQuoteRepository;

    /**
     * Sql interface for crm user entites.
     */
    @Autowired
    private CrmUserRepository userRepository;

    /**
     * The module name that this controller is associated to.
     */
    private final String moduleName = "Quotes";

    /**
     * Render the list view for all available quotes.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping
    public String list(Model model, Authentication auth){
        Iterable<Quote> quoteData =  quoteRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("quoteData", quoteData);
        return "sales/quotelist";
    }

    /**
     * Render a single view for a single quote.
     * @param id the id that is associated to some lead.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth) {
        Quote quoteData = quoteRepository.findOne(id);
        super.setCommonModelAttributes(model,auth,userRepository, this.moduleName);
        model.addAttribute("quoteData", quoteData);
        model.addAttribute("toList", "/quote");
        Iterable<ProductQuote> productQuoteData = findAllProductsForQuote(quoteData);
        model.addAttribute("productQuoteData", productQuoteData);
        return "sales/singlequote";
    }

    /**
     * Find all the products for a single quote
     * @param quoteData the quote that is associated with the desired productquotes.
     * @return the iterable set of productquotes.
     */
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
