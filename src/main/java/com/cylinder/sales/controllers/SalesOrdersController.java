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
@RequestMapping("/salesorder")
public class SalesOrdersController extends BaseController{

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
     * The module name that this controller is associated to.
     */
    private final String moduleName = "Sales Orders";

    /**
     * Render the list view for all available sales orders.
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
     * @param id the id that is associated to some lead.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth) {
        SalesOrder salesOrderData = salesOrderRepository.findOne(id);
        super.setCommonModelAttributes(model,auth,userRepository, this.moduleName);
        model.addAttribute("salesOrderData", salesOrderData);
        model.addAttribute("toList", "/salesorder");
        Iterable<ProductSalesOrder> productSalesOrderData = findAllProductsForSalesOrder(salesOrderData);
        model.addAttribute("productSalesOrderData", productSalesOrderData);
        boolean showShippingAddress = showShipping(salesOrderData);
        model.addAttribute("showShippingAddress", showShippingAddress);
        return "sales/singlesalesorder";
    }

    /**
     * Find all the products for a single sales order
     * @param quoteData the quote that is associated with the desired productsalesorder.
     * @return the iterable set of productsalesorders.
     */
    private Iterable<ProductSalesOrder> findAllProductsForSalesOrder(SalesOrder salesOrderData){
        Iterable<ProductSalesOrder> productSalesOrderData =  productSalesOrderRepository.findAll();
        for(ProductSalesOrder productSalesOrder : productSalesOrderData){
            if(productSalesOrder.getSalesOrder() != salesOrderData){
                productSalesOrderRepository.delete(productSalesOrder);
            }
        }
        return productSalesOrderData;
    }

    private Boolean showShipping(SalesOrder salesOrder){
        if(salesOrder.getBillingAddress() == null && salesOrder.getShippingAddress() != null){
            return true;
        }
        if(salesOrder.getShippingAddress() == null ||
                salesOrder.getBillingAddress().equals(salesOrder.getShippingAddress())){
            return false;
        }
        return true;
    }
}