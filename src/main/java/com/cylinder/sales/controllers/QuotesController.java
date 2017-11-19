package com.cylinder.sales.controllers;

import java.lang.Iterable;

import java.util.List;

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

import org.springframework.util.AutoPopulatingList;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import javax.validation.Valid;

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
     * Render a edit view for a single quote.
     * @param id the id that is associated to some quote.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping(value="/edit/{id}")
    public String editQuote(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth,
                               HttpServletResponse response) {
        Quote quote;
        if (quoteRepository.existsById(id)) {
            quote = quoteRepository.findOne(id);
        } else {
            response.setStatus(404);
            return "redirect:/404.html";
        }
        this.bindUserForm(model, auth);
        model.addAttribute("quoteData", quote);
        model.addAttribute("action", "edit/" + id);
        return "sales/editsinglequote";
    }

    /**
     * Process a form for editing a single record.
     * @param id the id that is associated to some quote.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping(value="/edit/{id}")
    public String saveEditableQuote(@PathVariable("id") Long id,
                                       @Valid @ModelAttribute("quoteData") Quote quote,
                                       BindingResult result,
                                       Model model,
                                       Authentication auth) {
        if (result.hasErrors()) {
            this.bindUserForm(model, auth);
            model.addAttribute("action","edit/" + quote.getQuoteId());
            return "sales/editsinglequote";
        }
        Long assignedId = quoteRepository.save(quote).getQuoteId();
        return "redirect:/quote/records/" + assignedId.toString() ;
    }

    /**
     * Render a view for a creating a single quote.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/new/")
    public String newRecord(Model model,
                            Authentication auth) {
        this.bindUserForm(model,auth);
        model.addAttribute("action","new/");
        model.addAttribute("quoteData", new Quote());
        return "sales/editsinglequote";
    }

    /**
     * Process a new quote form and potentially send errors back.
     * @param quote The quote form object to be processed.
     * @param result the object that binds the data from the view and validates user.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping("/new/")
    public String saveNewQuote(@Valid @ModelAttribute("quoteData") Quote quote,
                                  BindingResult result,
                                  Model model,
                                  Authentication auth) {
        if (result.hasErrors()) {
            this.bindUserForm(model,auth);
            model.addAttribute("action","new/");
            return "sales/editsinglequote";
        }
        Long assignedId = quoteRepository.save(quote).getQuoteId();
        return "redirect:/quote/records/" + assignedId.toString() ;
    }


    /**
     * Delete some quote through a delete request.
     * @param id the id that is associated to some quote.
     * @return the name of the template to render.
     */
    @DeleteMapping("/records/{id}")
    @ResponseBody
    public String deleteRecord(@PathVariable("id") Long id) {
        if (quoteRepository.existsById(id)) {
            quoteRepository.deleteById(id);
            return "";
        } else {
            return "Failed to delete record" + id;
        }

    }

    /**
     * Helper function to bind common model attributes whener generating a list form.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     */
    private void bindUserForm(Model model, Authentication auth) {
        super.setCommonModelAttributes(model,auth,userRepository,this.moduleName);
        model.addAttribute("userData", userRepository.findAll());
        model.addAttribute("toList", "/quote");
    }
    /**
     * Maps empty string to null when a form is submitted.
     * @param binder The object that allows for empty strings to be turned into nulls.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
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






//    private List<ProductQuote> manageProducts(Quote quoteData) {
//        // Store the productQuotes which shouldn't be persisted
//        List<ProductQuote> productQuotesToRemove = new ArrayList<ProductQuote>();
//        if (quoteData.getProductQuote() != null) {
//            for (Iterator<ProductQuote> i = quoteData.getProductQuote().iterator(); i.hasNext();) {
//                ProductQuote productQuoteData = i.next();
//                // If the remove flag is true, remove the productQuote from the list
//                if (productQuoteData.getRemove() == 1) {
//                    productQuotesToRemove.add(productQuoteData);
//                    i.remove();
//                    // Otherwise, perform the links
//                } else {
//                    productQuoteData.setQuote(quoteData);
//                }
//            }
//        }
//        return productQuotesToRemove;
//    }
//
//    @RequestMapping(value = "create", method = RequestMethod.GET)
//    public String create(@PathVariable("id") Long id, @ModelAttribute Quote quoteData, Model model) {
//        // Should init the AutoPopulatingList
//        return create(id, quoteData, model, true);
//    }
//
//    private String create(@PathVariable("id") Long id, Quote quoteData, Model model, boolean init) {
//        if (init) {
//            // Init the AutoPopulatingList
//            quoteData.setProductQuote(new AutoPopulatingList<ProductQuote>(ProductQuote.class));
//        }
//        model.addAttribute("type", "create");
//        return "/edit/{id}";
//    }
//
//    @RequestMapping(value = "create", method = RequestMethod.POST)
//    public String create(@PathVariable("id") Long id, @Valid @ModelAttribute Quote quoteData, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            // Should not re-init the AutoPopulatingList
//            return create(id, quoteData, model, false);
//        }
//        // Call the private method
//        manageProducts(quoteData);
//        // Persist the employer
//       // employerService.save(employer);
//        return "sales/singlequote";
//    }
}
