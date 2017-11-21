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

import java.util.ArrayList;
import java.util.Optional;
import java.util.Iterator;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;

import com.cylinder.shared.controllers.BaseController;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.products.model.ProductRepository;
import com.cylinder.products.model.Product;
import com.cylinder.sales.model.*;
import com.cylinder.sales.model.forms.QuoteForm;


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
     * Sql interface for product entites.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * Sql interface for crm user entites.
     */
    @Autowired
    private CrmUserRepository userRepository;

    /**
     * The module name that this controller is associated to.
     */
    private final String moduleName = "Quotes";

    @ModelAttribute("allProducts")
    public List<Product> populateProducts() {
        return iterableToListProduct(productRepository.findAll());
    }


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
        List<ProductQuote> productQuoteData = productQuoteRepository.getProductsByQuoteId(quoteData.getQuoteId());
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
        List<ProductQuote> productList = productQuoteRepository.getProductsByQuoteId(quote.getQuoteId());
        QuoteForm form = new QuoteForm(quote, productList);
        this.bindUserForm(model, auth);
        model.addAttribute("quoteData", form);
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
                                       @Valid @ModelAttribute("quoteData") QuoteForm quoteData,
                                       BindingResult result,
                                       Model model,
                                     Authentication auth) {
        Iterable<ProductQuote> productList = listToIterable(quoteData.getProductList());
        Optional<FieldError> error = itemAlreadyExists(productList);
        if(error.isPresent()){
          result.addError(error.get());
        }
        if (result.hasErrors()) {
            this.bindUserForm(model, auth);
            model.addAttribute("action","edit/" + quoteData.getQuote().getQuoteId());
            return "sales/editsinglequote";
        }
        CrmUser user = userRepository.findByEmail(auth.getName());
        quoteData.getQuote().setLastModifiedBy(user);
        for(ProductQuote productEntry: quoteData.getProductList()) {
          if (productEntry.getQuote() == null) {
            productEntry.setQuote(quoteData.getQuote());
          }
        }
        Long assignedId = quoteRepository.save(quoteData.getQuote()).getQuoteId();
        productQuoteRepository.deleteProductsByQuoteId(quoteData.getQuote().getQuoteId());
        for (ProductQuote productEntry: productList) {
          productQuoteRepository.save(productEntry);
          System.out.println("Assigned ID: " + assignedId);
          System.out.println(productEntry.getQuote().getQuoteId());
          System.out.println(productEntry.getProduct().getProductId());
        }
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
        model.addAttribute("quoteData", new QuoteForm());
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
    public String saveNewQuote(@Valid @ModelAttribute("quoteData") QuoteForm quoteForm,
                                  BindingResult result,
                                  Model model,
                                  Authentication auth) {
        Quote quote = quoteForm.getQuote();
        Iterable<ProductQuote> productList = listToIterable(quoteForm.getProductList());
        if (result.hasErrors()) {
            this.bindUserForm(model,auth);
            model.addAttribute("action","new/");
            return "sales/editsinglequote";
        }
        CrmUser user = userRepository.findByEmail(auth.getName());
        quote.setCreatedBy(user);
        Quote savedQuote = quoteRepository.save(quote);
        Long assignedId = savedQuote.getQuoteId();
        for(ProductQuote productEntry: productList) {
          if (productEntry.getQuote() == null) {
            productEntry.setQuote(savedQuote);
          }
        }
        for (ProductQuote productEntry: productList) {
          productQuoteRepository.save(productEntry);
        }
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

    /**contact
     * Helper function to bind common model attributes whener generating a list form.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     */
    private void bindUserForm(Model model, Authentication auth) {
        super.setCommonModelAttributes(model,auth,userRepository,this.moduleName);
        model.addAttribute("userData", userRepository.findAll());
        model.addAttribute("productData", productQuoteRepository.findAll());
        model.addAttribute("toList", "/quote");
    }
    /**
     * Maps empty string to null when a form is submitted.
     * @param binder The objectiterableToList(productQuoteRepository)); that allows for empty strings to be turned into nulls.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value="/edit/{id}", params={"addRow"})
    public String addRow(final @ModelAttribute("quoteData") QuoteForm quoteData,
                         @PathVariable("id") Long id,
                         Authentication auth,
                         Model model) {
        this.bindUserForm(model,auth);
        model.addAttribute("action","edit/"+ id);
        quoteData.getProductList().add(new ProductQuote());
        return "sales/editsinglequote";
    }

    @RequestMapping(value="/edit/{id}", params={"removeRow"})
    public String removeRow(final @ModelAttribute("quoteData") QuoteForm quoteData,
                         @PathVariable("id") Long id,
                         Authentication auth,
                         Model model,
                         final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        quoteData.getProductList().remove(rowId.intValue());
        this.bindUserForm(model,auth);
        model.addAttribute("action","edit/" + id);
        return "sales/editsinglequote";
    }


    private List<ProductQuote> iterableToList(Iterable<ProductQuote> products){
      List<ProductQuote> productList = new ArrayList<ProductQuote>();
      for(ProductQuote product : products){
        productList.add(product);
      }
      return productList;
    }

    private List<Product> iterableToListProduct(Iterable<Product> products){
      List<Product> productList = new ArrayList<Product>();
      for(Product product : products){
        productList.add(product);
      }
      return productList;
    }

    private Iterable<ProductQuote> listToIterable(List<ProductQuote> products){
      Iterable<ProductQuote> productIterrable = products;
      return productIterrable;
    }

    private Optional<FieldError> itemAlreadyExists(Iterable<ProductQuote> pq){
      HashMap map = new HashMap();
      int counter = 1;
      for(ProductQuote entry : pq){
        if(map.containsKey(entry.getProduct().getProductId())){
          return Optional.of(new FieldError("ProductQuote","productList["+counter+"].product", "Cannot have duplicate products on a quote."));
        }
        else{
          map.put(entry.getProduct().getProductId(), entry);
        }
        counter++;
      }
      return Optional.empty();
    }
}
