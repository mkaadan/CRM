package com.cylinder.sales.controllers;

import java.lang.Iterable;

import com.cylinder.sales.model.PurchaseOrder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.sales.model.*;

@Controller
@RequestMapping("/purchaseorder")
public class PurchaseOrdersController {

      @Autowired
      private PurchaseOrderRepository purchaseOrderRepository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for Purchase Orders.
    	public String list(Model model){
        model.addAttribute("moduleName", "Purchase Orders");
        Iterable<PurchaseOrder> purchaseOrderData =  purchaseOrderRepository.findAll();
        model.addAttribute("purchaseOrderData", purchaseOrderData);
        return "sales/purchaseorderlist";
      }

    @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
    public String singleRecord(@PathVariable("id") Long id, Model model) {
        PurchaseOrder purchaseOrderData = purchaseOrderRepository.findOne(id);
        model.addAttribute("moduleName", "PurchaseOrders");
        model.addAttribute("purchaseOrderData", purchaseOrderData);
        model.addAttribute("toList", "/purchaseorder");
        return "sales/singlepurchaseorder";
    }
}
