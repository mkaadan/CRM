package com.cylinder.products.controllers;

import java.lang.Iterable;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


import com.cylinder.products.model.*;

@Controller
@RequestMapping("/product")
public class ProductsController {

      @Autowired
      private ProductRepository productRepository;

      @Autowired
      private CategoriesRepository categoriesRepository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for products.
    	public String list(Model model){
        model.addAttribute("moduleName", "Products");
        Iterable<Product> productData =  productRepository.findAll();
        model.addAttribute("productData", productData);
        return "products/list";
      }

      @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
      public String singleRecord(@PathVariable("id") Long id, Model model) {
          Product productData = productRepository.findOne(id);
          model.addAttribute("moduleName", "Products");
          model.addAttribute("productData", productData);
          model.addAttribute("toList", "/");
          return "products/singleProduct";
      }

      // @RequestMapping(value="/new", method=RequestMethod.GET)
      // public String newRecord(Model model) {
      //     model.addAttribute("moduleName", "Product");
      //     Iterable<Source> sourceData = sourceRespository.findAll();
      //     Iterable<Status> statusData = statusRespository.findAll();
      //     model.addAttribute("productStatus", statusData);
      //     model.addAttribute("ProductSource", sourceData);
      //     return "Products/editsingle";
      // }

}
