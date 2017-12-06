package com.cylinder.sales.controllers;

import com.cylinder.accounts.model.Account;
import com.cylinder.accounts.model.AccountRepository;
import com.cylinder.contacts.model.Contact;
import com.cylinder.contacts.model.ContactRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.products.model.Product;
import com.cylinder.products.model.ProductRepository;
import com.cylinder.sales.model.ProductQuote;
import com.cylinder.sales.model.ProductQuoteRepository;
import com.cylinder.sales.model.Quote;
import com.cylinder.sales.model.QuoteRepository;
import com.cylinder.sales.model.forms.QuoteForm;
import com.cylinder.sales.model.ListIterableServiceObject;
import com.cylinder.shared.controllers.BaseController;
import com.cylinder.errors.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/quote")
public class QuotesController extends BaseController {

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
     * Sql interface for contact entites.
     */
    @Autowired
    private ContactRepository contactRepository;

    /**
     * Sql interface for account entites.
     */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * The module name that this controller is associated to.
     */
    private final String moduleName = "Quotes";

    /**
     * Creates a list of all the products
     *
     * @return the list of all the products
     */
    @ModelAttribute("allProducts")
    public List<Product> populateProducts() {
        return ListIterableServiceObject.iterableToList(productRepository.findAll());
    }


    /**
     * Render the list view for all available quotes.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping
    public String list(Model model, Authentication auth) {
        Iterable<Quote> quoteData = quoteRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("quoteData", quoteData);
        return "sales/quotelist";
    }

    /**
     * Render a single view for a single quote.
     *
     * @param id    the id that is associated to some quote.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth) {
        if (quoteRepository.existsById(id)) {
            Quote quoteData = quoteRepository.findOne(id);
            super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
            model.addAttribute("quoteData", quoteData);
            model.addAttribute("toList", "/quote");
            List<ProductQuote> productQuoteData = productQuoteRepository.getProductsByQuoteId(id);
            model.addAttribute("productQuoteData", productQuoteData);
            return "sales/singlequote";
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Render a edit view for a single quote.
     *
     * @param id    the id that is associated to some quote.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping(value = "/edit/{id}")
    public String editQuote(@PathVariable("id") Long id,
                            Model model,
                            Authentication auth,
                            HttpServletResponse response) {
        if (quoteRepository.existsById(id)) {
            Quote quote = quoteRepository.findOne(id);
            List<ProductQuote> productList = productQuoteRepository.getProductsByQuoteId(id);
            QuoteForm form = new QuoteForm(quote, productList);
            this.bindUserForm(model, auth);
            model.addAttribute("quoteData", form);
            model.addAttribute("action", "edit/" + id);
            return "sales/editsinglequote";
        }
        else {
            throw new NotFoundException();
        }
    }

    /**
     * Process a form for editing a single record.
     *
     * @param id    the id that is associated to some quote.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping(value = "/edit/{id}")
    public String saveEditableQuote(@PathVariable("id") Long id,
                                    @Valid @ModelAttribute("quoteData") QuoteForm quoteData,
                                    BindingResult result,
                                    Model model,
                                    Authentication auth) {
        if (quoteData.getQuote() != null && quoteRepository.existsById(id)) {
            Iterable<ProductQuote> productList = ListIterableServiceObject.listToIterable(quoteData.getProductList());
            Optional<FieldError> error = itemAlreadyExists(productList);
            if (error.isPresent()) {
                result.addError(error.get());
            }
            if (result.hasErrors()) {
                this.bindUserForm(model, auth);
                model.addAttribute("action", "edit/" + quoteData.getQuote().getQuoteId());
                return "sales/editsinglequote";
            }
            CrmUser user = userRepository.findByEmail(auth.getName());
            quoteData.getQuote().setLastModifiedBy(user);
            Account account = quoteData.getQuote().getAccount();
            Contact contact = quoteData.getQuote().getContact();
            for (ProductQuote productEntry : quoteData.getProductList()) {
                if (productEntry.getQuote() == null) {
                    productEntry.setQuote(quoteData.getQuote());
                }
            }
            Long assignedId = quoteRepository.save(quoteData.getQuote()).getQuoteId();
            productQuoteRepository.deleteProductsByQuoteId(quoteData.getQuote().getQuoteId());
            for (ProductQuote productEntry : productList) {
                productQuoteRepository.save(productEntry);
            }
            return "redirect:/quote/records/" + assignedId.toString();
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Render a view for a creating a single quote.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/new/")
    public String newRecord(Model model,
                            Authentication auth) {
        this.bindUserForm(model, auth);
        model.addAttribute("action", "new/");
        model.addAttribute("quoteData", new QuoteForm());
        return "sales/editsinglequote";
    }

    /**
     * Process a new quote form and potentially send errors back.
     *
     * @param quote  The quote form object to be processed.
     * @param result the object that binds the data from the view and validates user.
     * @param model  the view model object that is used to render the html.
     * @param auth   the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping("/new/")
    public String saveNewQuote(@Valid @ModelAttribute("quoteData") QuoteForm quoteForm,
                               BindingResult result,
                               Model model,
                               Authentication auth) {
        Quote quote = quoteForm.getQuote();
        Iterable<ProductQuote> productList = ListIterableServiceObject.listToIterable(quoteForm.getProductList());
        if (result.hasErrors()) {
            this.bindUserForm(model, auth);
            model.addAttribute("action", "new/");
            return "sales/editsinglequote";
        }
        CrmUser user = userRepository.findByEmail(auth.getName());
        quote.setCreatedBy(user);
        Quote savedQuote = quoteRepository.save(quote);
        Account account = savedQuote.getAccount();
        Contact contact = savedQuote.getContact();
        Long assignedId = savedQuote.getQuoteId();
        for (ProductQuote productEntry : productList) {
            if (productEntry.getQuote() == null) {
                productEntry.setQuote(savedQuote);
            }
        }
        for (ProductQuote productEntry : productList) {
            productQuoteRepository.save(productEntry);
        }
        return "redirect:/quote/records/" + assignedId.toString();
    }


    /**
     * Delete some quote through a delete request.
     *
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
     * contact
     * Helper function to bind common model attributes whener generating a list form.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     */
    private void bindUserForm(Model model, Authentication auth) {
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("userData", userRepository.findAll());
        model.addAttribute("contactData", contactRepository.findAll());
        model.addAttribute("accountData", accountRepository.findAll());
        model.addAttribute("productData", productQuoteRepository.findAll());
        model.addAttribute("toList", "/quote");
    }

    /**
     * Maps empty string to null when a form is submitted.
     *
     * @param binder The objectiterableToList(productQuoteRepository)); that allows for empty strings to be turned into nulls.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /**
     * Adds a row for a product to occupy on the existing sales order
     *
     * @param salesOrderData the form for the sales order to follow
     * @param id    the id that is associated to some sales order.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     */
    @RequestMapping(value = "/edit/{id}", params = {"addRow"})
    public String addRow(@Valid @ModelAttribute("quoteData") QuoteForm quoteData,
                         @PathVariable("id") Long id,
                         Authentication auth,
                         Model model) {
        this.bindUserForm(model, auth);
        model.addAttribute("action", "edit/" + id);
        quoteData.getProductList().add(new ProductQuote());
        return "sales/editsinglequote";
    }

    /**
     * Adds a row for a product to occupy on the new sales order
     *
     * @param salesOrderData the form for the sales order to follow
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     */
    @RequestMapping(value = "/new/", params = {"addRow"})
    public String addRowToNew(@Valid @ModelAttribute("quoteData") QuoteForm quoteData,
                              Authentication auth,
                              Model model) {
        this.bindUserForm(model, auth);
        model.addAttribute("action", "new/");
        quoteData.getProductList().add(new ProductQuote());
        return "sales/editsinglequote";
    }

    /**
     * removes a row for a product to occupy on the existing sales order
     *
     * @param salesOrderData the form for the sales order to follow
     * @param id    the id that is associated to some sales order.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @param req
     */
    @RequestMapping(value = "/edit/{id}", params = {"removeRow"})
    public String removeRow(@Valid @ModelAttribute("quoteData") QuoteForm quoteData,
                            @PathVariable("id") Long id,
                            Authentication auth,
                            Model model,
                            final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        quoteData.getProductList().remove(rowId.intValue());
        this.bindUserForm(model, auth);
        model.addAttribute("action", "edit/" + id);
        return "sales/editsinglequote";
    }

    /**
     * removes a row for a product to occupy on the new sales order
     *
     * @param salesOrderData the form for the sales order to follow
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @param req
     */
    @RequestMapping(value = "/new/", params = {"removeRow"})
    public String removeRowFromNew(@Valid @ModelAttribute("quoteData") QuoteForm quoteData,
                                   Authentication auth,
                                   Model model,
                                   final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        quoteData.getProductList().remove(rowId.intValue());
        this.bindUserForm(model, auth);
        model.addAttribute("action", "new/");
        return "sales/editsinglequote";
    }

    /**
     * checks if an item already exists
     *
     * @param pso  an iterable to check if the new item is a duplicate
     */
    private Optional<FieldError> itemAlreadyExists(Iterable<ProductQuote> pq) {
        HashMap map = new HashMap();
        int counter = 1;
        for (ProductQuote entry : pq) {
            if (map.containsKey(entry.getProduct().getProductId())) {
                return Optional.of(new FieldError("ProductQuote", "productList[" + counter + "].product", "Cannot have duplicate products on a quote."));
            } else {
                map.put(entry.getProduct().getProductId(), entry);
            }
            counter++;
        }
        return Optional.empty();
    }
}
