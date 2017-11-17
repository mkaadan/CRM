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
@RequestMapping("/purchaseorder")
public class PurchaseOrdersController extends BaseController{

    /**
     * Sql interface for purchase order entites.
     */
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    /**
     * Sql interface for productQuote entites.
     */
    @Autowired
    private ProductPurchaseOrderRepository productPurchaseOrderRepository;

    /**
     * Sql interface for crm user entites.
     */
    @Autowired
    private CrmUserRepository userRepository;

    /**
     * The module name that this controller is associated to.
     */
    private final String moduleName = "Purchase Orders";

    /**
     * Render the list view for all available purchase orders.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping
    public String list(Model model, Authentication auth){
        Iterable<PurchaseOrder> purchaseOrderData =  purchaseOrderRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("purchaseOrderData", purchaseOrderData);
        return "sales/purchaseorderlist";
    }

    /**
     * Render a single view for a single purchase order.
     * @param id the id that is associated to some lead.
     * @param model the view model object that is used to render the html.
     * @param auth the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth) {
        PurchaseOrder purchaseOrderData = purchaseOrderRepository.findOne(id);
        super.setCommonModelAttributes(model,auth,userRepository, this.moduleName);
        model.addAttribute("purchaseOrderData", purchaseOrderData);
        model.addAttribute("toList", "/purchaseorder");
        Iterable<ProductPurchaseOrder> productPurchaseOrderData = findAllProductsForPurchaseOrder(purchaseOrderData);
        model.addAttribute("productPurchaseOrderData", productPurchaseOrderData);
        return "sales/singlepurchaseorder";
    }

    /**
     * Find all the products for a single purchase order
     * @param purchase order the purchase order that is associated with the desired productpurchaseorders.
     * @return the iterable set of productpurchaseorders.
     */
    private Iterable<ProductPurchaseOrder> findAllProductsForPurchaseOrder(PurchaseOrder purchaseOrderData){
        Iterable<ProductPurchaseOrder> productPurchaseOrderData =  productPurchaseOrderRepository.findAll();
        for(ProductPurchaseOrder productPurchaseOrder : productPurchaseOrderData){
            if(productPurchaseOrder.getPurchaseOrder() != purchaseOrderData){
                productPurchaseOrderRepository.delete(productPurchaseOrder);
            }
        }
        return productPurchaseOrderData;
    }
}
