package com.cylinder.sales.controllers;

import java.lang.Iterable;

import com.cylinder.sales.model.SalesOrder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.sales.model.*;

@Controller
@RequestMapping("/salesorder")
public class SalesOrdersController {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @RequestMapping(method=RequestMethod.GET)
    // Render the list view for Sales Orders.
    public String list(Model model){
        model.addAttribute("moduleName", "SalesOrders");
        Iterable<SalesOrder> salesOrderData =  salesOrderRepository.findAll();
        model.addAttribute("salesOrderData", salesOrderData);
        return "sales/salesorderlist";
    }

    @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
    public String singleRecord(@PathVariable("id") Long id, Model model) {
        SalesOrder salesOrderData = salesOrderRepository.findOne(id);
        model.addAttribute("moduleName", "SalesOrders");
        model.addAttribute("salesOrderData", salesOrderData);
        model.addAttribute("toList", "/salesorder");
        return "sales/singlesalesorder";
    }
}