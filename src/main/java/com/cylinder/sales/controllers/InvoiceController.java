package com.cylinder.sales.controllers;

import java.lang.Iterable;
import java.lang.Boolean;

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
@RequestMapping("/invoice")
public class InvoiceController extends BaseController{

//    @RequestMapping(method=RequestMethod.GET)
//    // Render the list view for Invoices.
//    public String list(Model model){
//        model.addAttribute("moduleName", "SalesOrders");
//        Iterable<SalesOrder> invoiceData =  invoiceRepository.findAll();
//        model.addAttribute("invoiceData", invoiceData);
//        return "sales/invoicelist";
//    }
//
//    @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
//    public String singleRecord(@PathVariable("id") Long id, Model model) {
//        SalesOrder invoiceData = invoiceRepository.findOne(id);
//        model.addAttribute("moduleName", "SalesOrders");
//        model.addAttribute("invoiceData", invoiceData);
//        model.addAttribute("toList", "/invoice");
//        return "sales/singleinvoice";
//    }



    /**
     * Sql interface for sales order entites.
     */
    @Autowired
    private SalesOrderRepository invoiceRepository;

    /**
     * Sql interface for productSalesOrder entites.
     */
    @Autowired
    private ProductSalesOrderRepository productInvoiceRepository;

    /**
     * Sql interface for crm user entites.
     */
    @Autowired
    private CrmUserRepository userRepository;

    /**
     * The module name that this controller is associated to.
     */
    private final String moduleName = "Invoices";

    /**
     * Render the list view for all available invoices.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping
    public String list(Model model, Authentication auth){
        Iterable<SalesOrder> invoiceData =  invoiceRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("invoiceData", invoiceData);
        return "sales/invoicelist";
    }

    /**
     * Render a single view for a single invoice.
     * @param id the id that is associated to some lead.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth) {
        SalesOrder invoiceData = invoiceRepository.findOne(findSalesOrderNumber(id));
        super.setCommonModelAttributes(model,auth,userRepository, this.moduleName);
        model.addAttribute("invoiceData", invoiceData);
        model.addAttribute("toList", "/invoice");
        Iterable<ProductSalesOrder> productInvoiceData = findAllProductsForInvoice(invoiceData);
        model.addAttribute("productInvoiceData", productInvoiceData);
        boolean showShippingAddress = showShipping(invoiceData);
        model.addAttribute("showShippingAddress", showShippingAddress);
        return "sales/singleinvoice";
    }

    /**
     * Find all the products for a single invoice
     * @param invoiceData the invoice that is associated with the desired productsalesorder.
     * @return the iterable set of productsalesorders.
     */
    private Iterable<ProductSalesOrder> findAllProductsForInvoice(SalesOrder invoiceData){
        Iterable<ProductSalesOrder> productInvoiceData =  productInvoiceRepository.findAll();
        for(ProductSalesOrder productInvoice : productInvoiceData){
            if(productInvoice.getSalesOrder() != invoiceData){
                productInvoiceRepository.delete(productInvoice);
            }
        }
        return productInvoiceData;
    }

    /**
     * Decide if the invoice should show the shipping address
     * @param invoiceData the invoice that we are checking the addresses for.
     * @return true if should show, false otherwise.
     */
    private Boolean showShipping(SalesOrder invoice){
        if(invoice.getBillingAddress() == null && invoice.getShippingAddress() != null){
            return true;
        }
        if(invoice.getShippingAddress() == null ||
                invoice.getBillingAddress().equals(invoice.getShippingAddress())){
            return false;
        }
        return true;
    }

    private Long findSalesOrderNumber(Long invoiceNumber){
        Iterable<SalesOrder> invoiceData =  invoiceRepository.findAll();
        for(SalesOrder invoice : invoiceData){
            if(invoice.getInvoiceNumber() == invoiceNumber){
                return invoice.getSalesOrderId();
            }
        }
        return null;
    }

}