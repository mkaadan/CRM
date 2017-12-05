package com.cylinder.sales.controllers;

import com.cylinder.accounts.model.Account;
import com.cylinder.accounts.model.AccountRepository;
import com.cylinder.contacts.model.Contact;
import com.cylinder.contacts.model.ContactRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.products.model.Product;
import com.cylinder.products.model.ProductRepository;
import com.cylinder.sales.model.ProductSalesOrder;
import com.cylinder.sales.model.ProductSalesOrderRepository;
import com.cylinder.sales.model.Contract;
import com.cylinder.sales.model.ContractRepository;
import com.cylinder.sales.model.Quote;
import com.cylinder.sales.model.ListIterableServiceObject;
import com.cylinder.sales.model.QuoteRepository;
import com.cylinder.sales.model.SalesOrder;
import com.cylinder.sales.model.SalesOrderRepository;
import com.cylinder.sales.model.forms.SalesOrderForm;
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
@RequestMapping("/salesorder")
public class SalesOrderController extends BaseController{

    /**
     * Sql interface for sales order entites.
     */
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    /**
     * Sql interface for productSalesOrder entites.
     */
    @Autowired
    private ProductSalesOrderRepository productSalesOrderRepository;

    /**
     * Sql interface for crm user entites.
     */
    @Autowired
    private CrmUserRepository userRepository;

    /**
     * Sql interface for product entites.
     */
    @Autowired
    private ProductRepository productRepository;

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
     * Sql interface for contract entites.
     */
    @Autowired
    private ContractRepository contractRepository;

    /**
     * Sql interface for quote entites.
     */
    @Autowired
    private QuoteRepository quoteRepository;

    /**
     * The module name that this controller is associated to.
     */
    private final String moduleName = "Sales Orders";

    @ModelAttribute("allProducts")
    public List<Product> populateProducts() {
        return ListIterableServiceObject.iterableToList(productRepository.findAll());
    }


    /**
     * Render the list view for all available sales orders.
     *
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping
    public String list(Model model, Authentication auth){
        Iterable<SalesOrder> salesOrderData =  salesOrderRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("salesOrderData", salesOrderData);
        return "sales/salesorderlist";
    }

    /**
     * Render a single view for a single sales order.
     *
     * @param id    the id that is associated to some sales order.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth) {
        if (salesOrderRepository.existsById(id)) {
            SalesOrder salesOrderData = salesOrderRepository.findOne(id);
            super.setCommonModelAttributes(model,auth,userRepository, this.moduleName);
            model.addAttribute("salesOrderData", salesOrderData);
            model.addAttribute("toList", "/salesorder");
            List<ProductSalesOrder> productSalesOrderData = productSalesOrderRepository.getProductsBySalesOrderId(id);
            model.addAttribute("productSalesOrderData", productSalesOrderData);
            boolean showShippingAddress = showShipping(salesOrderData);
            model.addAttribute("showShippingAddress", showShippingAddress);
            return "sales/singlesalesorder";
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Render a edit view for a single sales order.
     *
     * @param id    the id that is associated to some sales order.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping(value="/edit/{id}")
    public String editSalesOrder(@PathVariable("id") Long id,
                                 Model model,
                                 Authentication auth,
                                 HttpServletResponse response) {
        if (salesOrderRepository.existsById(id)) {
            SalesOrder salesOrder = salesOrderRepository.findOne(id);
            List<ProductSalesOrder> productList = productSalesOrderRepository.getProductsBySalesOrderId(id);
            SalesOrderForm form = new SalesOrderForm(salesOrder, productList);
            this.bindSalesForm(model, auth);
            model.addAttribute("salesOrderData", form);
            model.addAttribute("action", "edit/" + id);
            return "sales/editsinglesalesorder";
        }
        else {
            throw new NotFoundException();
        }
    }

    /**
     * Process a form for editing a single record.
     *
     * @param id    the id that is associated to some sales order.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping(value="/edit/{id}")
    public String saveEditableSalesOrder(@PathVariable("id") Long id,
                                    @Valid @ModelAttribute("salesOrderData") SalesOrderForm salesOrderData,
                                    BindingResult result,
                                    Model model,
                                    Authentication auth) {
        if (salesOrderData.getSalesOrder() != null && salesOrderRepository.existsById(id)) {
            Iterable<ProductSalesOrder> productList = ListIterableServiceObject.listToIterable(salesOrderData.getProductList());
            Optional<FieldError> error = itemAlreadyExists(productList);
            if (error.isPresent()) {
                result.addError(error.get());
            }
            if (result.hasErrors()) {
                this.bindSalesForm(model, auth);
//                for (FieldError error2: result.getFieldErrors())  {
//                    System.out.println(" " + error2.getField());
//                }
                model.addAttribute("action", "edit/" + salesOrderData.getSalesOrder().getSalesOrderId());
                return "sales/editsinglesalesorder";
            }
            if (salesOrderData.getSalesOrder().getBillingAddress().areFieldsNull()) {
                salesOrderData.getSalesOrder().setBillingAddress(null);
            }
            if (salesOrderData.getSalesOrder().getShippingAddress().areFieldsNull()) {
                salesOrderData.getSalesOrder().setShippingAddress(null);
            }
            CrmUser user = userRepository.findByEmail(auth.getName());
            salesOrderData.getSalesOrder().setLastModifiedBy(user);
            Account account = salesOrderData.getSalesOrder().getAccount();
            Contact contact = salesOrderData.getSalesOrder().getContact();
            for (ProductSalesOrder productEntry : salesOrderData.getProductList()) {
                if (productEntry.getSalesOrder() == null) {
                    productEntry.setSalesOrder(salesOrderData.getSalesOrder());
                }
            }
            Long assignedId = salesOrderRepository.save(salesOrderData.getSalesOrder()).getSalesOrderId();
            productSalesOrderRepository.deleteProductsBySalesOrderId(salesOrderData.getSalesOrder().getSalesOrderId());
            for (ProductSalesOrder productEntry : productList) {
                productSalesOrderRepository.save(productEntry);
            }
            return "redirect:/salesorder/records/" + assignedId.toString();
        } else {
            throw new NotFoundException();
        }
    }

    /**
     * Render a view for a creating a single sales order.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/new/")
    public String newRecord(Model model,
                            Authentication auth) {
        this.bindSalesForm(model,auth);
        model.addAttribute("action","new/");
        model.addAttribute("salesOrderData", new SalesOrderForm());
        return "sales/editsinglesalesorder";
    }

    /**
     * Process a new sales order form and potentially send errors back.
     *
     * @param quote  The sales order form object to be processed.
     * @param result the object that binds the data from the view and validates user.
     * @param model  the view model object that is used to render the html.
     * @param auth   the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping("/new/")
    public String saveNewSalesOrder(@Valid @ModelAttribute("salesOrderData") SalesOrderForm salesOrderForm,
                                    BindingResult result,
                                    Model model,
                                    Authentication auth) {
        SalesOrder salesOrder = salesOrderForm.getSalesOrder();
        Iterable<ProductSalesOrder> productList = ListIterableServiceObject.listToIterable(salesOrderForm.getProductList());
        if (result.hasErrors()) {
            this.bindSalesForm(model,auth);
            model.addAttribute("action","new/");
            return "sales/editsinglesalesorder";
        }

        CrmUser user = userRepository.findByEmail(auth.getName());
        salesOrder.setCreatedBy(user);
        SalesOrder savedSalesOrder = salesOrderRepository.save(salesOrder);
        Account account = savedSalesOrder.getAccount();
        Contact contact = savedSalesOrder.getContact();
        Long assignedId = savedSalesOrder.getSalesOrderId();
        for(ProductSalesOrder productEntry: productList) {
            if (productEntry.getSalesOrder() == null) {
                productEntry.setSalesOrder(savedSalesOrder);
            }
        }
        for (ProductSalesOrder productEntry: productList) {
            productSalesOrderRepository.save(productEntry);
        }
        return "redirect:/salesorder/records/" + assignedId.toString() ;
    }


    /**
     * Delete some sales order through a delete request.
     *
     * @param id the id that is associated to some sales order.
     * @return the name of the template to render.
     */
    @DeleteMapping("/records/{id}")
    @ResponseBody
    public String deleteRecord(@PathVariable("id") Long id) {
        if (salesOrderRepository.existsById(id)) {
            salesOrderRepository.deleteById(id);
            return "";
        } else {
            return "Failed to delete record" + id;
        }
    }

    /**
     * Helper function to bind common model attributes whener generating a list form.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     */
    private void bindSalesForm(Model model, Authentication auth) {
        super.setCommonModelAttributes(model,auth,userRepository,this.moduleName);
        model.addAttribute("userData", userRepository.findAll());
        model.addAttribute("contactData", contactRepository.findAll());
        model.addAttribute("accountData", accountRepository.findAll());
        model.addAttribute("productData", productSalesOrderRepository.findAll());
        model.addAttribute("contractData", contractRepository.findAll());
        model.addAttribute("quoteData", quoteRepository.findAll());
        model.addAttribute("toList", "/salesorder");
    }

    /**
     * Maps empty string to null when a form is submitted.
     *
     * @param binder The object that allows for empty strings to be turned into nulls.
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
    public String addRow(final @ModelAttribute("salesOrderData") SalesOrderForm salesOrderData,
                         @PathVariable("id") Long id,
                         Authentication auth,
                         Model model) {
        this.bindSalesForm(model,auth);
        model.addAttribute("action","edit/"+ id);
        salesOrderData.getProductList().add(new ProductSalesOrder());
        return "sales/editsinglesalesorder";
    }

    /**
     * Adds a row for a product to occupy on the new sales order
     *
     * @param salesOrderData the form for the sales order to follow
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     */
    @RequestMapping(value = "/new/", params = {"addRow"})
    public String addRowToNew(final @ModelAttribute("salesOrderData") SalesOrderForm salesOrderData,
                              Authentication auth,
                              Model model) {
        this.bindSalesForm(model,auth);
        model.addAttribute("action","new/");
        salesOrderData.getProductList().add(new ProductSalesOrder());
        return "sales/editsinglesalesorder";
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
    public String removeRow(final @ModelAttribute("salesOrderData") SalesOrderForm salesOrderData,
                            @PathVariable("id") Long id,
                            Authentication auth,
                            Model model,
                            final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        salesOrderData.getProductList().remove(rowId.intValue());
        this.bindSalesForm(model,auth);
        model.addAttribute("action","edit/" + id);
        return "sales/editsinglesalesorder";
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
    public String removeRowFromNew(final @ModelAttribute("salesOrderData") SalesOrderForm salesOrderData,
                                   Authentication auth,
                                   Model model,
                                   final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        salesOrderData.getProductList().remove(rowId.intValue());
        this.bindSalesForm(model,auth);
        model.addAttribute("action","new/");
        return "sales/editsinglesalesorder";
    }

    /**
     * checks if an item already exists
     *
     * @param pso  an iterable to check if the new item is a duplicate
     */
    private Optional<FieldError> itemAlreadyExists(Iterable<ProductSalesOrder> pso){
        HashMap map = new HashMap();
        int counter = 1;
        for(ProductSalesOrder entry : pso){
            if(map.containsKey(entry.getProduct().getProductId())){
                return Optional.of(new FieldError("ProductSalesOrder","productList["+counter+"].product", "Cannot have duplicate products on a sales order."));
            } else{
                map.put(entry.getProduct().getProductId(), entry);
            }
            counter++;
        }
        return Optional.empty();
    }

    /**
     * decides if the shipping address should be shown or not
     *      depending on if it is the same as the billing one
     *
     * @param salesOrder  sales order holding the two addresses
     * @return a list of all the products in the iterable
     */
    private Boolean showShipping(SalesOrder salesOrder){
        if(salesOrder != null) {
            if (salesOrder.getBillingAddress() == null && salesOrder.getShippingAddress() != null) {
                return true;
            }
            if (salesOrder.getShippingAddress() == null ||
                    salesOrder.getBillingAddress().equals(salesOrder.getShippingAddress())) {
                return false;
            }
        }
        return true;
    }
}
