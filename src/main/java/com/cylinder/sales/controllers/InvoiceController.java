package com.cylinder.sales.controllers;

import java.lang.Iterable;

import com.cylinder.sales.model.SalesOrder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import com.cylinder.sales.model.*;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private SalesOrderRepository invoiceRepository;

    @RequestMapping(method=RequestMethod.GET)
    // Render the list view for Invoices.
    public String list(Model model){
        model.addAttribute("moduleName", "SalesOrders");
        Iterable<SalesOrder> invoiceData =  invoiceRepository.findAll();
        model.addAttribute("invoiceData", invoiceData);
        return "sales/invoicelist";
    }

    @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
    public String singleRecord(@PathVariable("id") Long id, Model model) {
        SalesOrder invoiceData = invoiceRepository.findOne(id);
        model.addAttribute("moduleName", "SalesOrders");
        model.addAttribute("invoiceData", invoiceData);
        model.addAttribute("toList", "/invoice");
        return "sales/singleinvoice";
    }
}