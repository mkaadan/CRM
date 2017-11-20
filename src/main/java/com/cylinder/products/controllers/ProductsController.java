package com.cylinder.products.controllers;

import java.lang.Iterable;


import java.lang.Iterable;
import java.util.Optional;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.products.model.*;
import com.cylinder.shared.controllers.BaseController;
import com.cylinder.accounts.model.AccountRepository;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.crmusers.model.CrmUser;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import java.text.SimpleDateFormat;
import java.sql.Date;



@Controller
@RequestMapping("/product")
public class ProductsController extends BaseController {

      /**
      * Sql interface for product entites.
      */
      @Autowired
      private ProductRepository productRepository;

      /**
      * Sql interface for product category entites.
      */
      @Autowired
      private CategoriesRepository categoriesRepository;

      /**
      * Sql interface for crm user entites.
      */
      @Autowired
      private CrmUserRepository userRepository;

      private final String moduleName = "Products";

      /**
      * Render the list view for all available products.
      * @param model the view model object that is used to render the html.
      * @param auth the authentication context that manages which users are logged in.
      * @return the name of the template to render.
      */
    	@GetMapping
    	public String list(Model model, Authentication auth){
        Iterable<Product> productData =  productRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("productData", productData);
        return "products/list";
      }

      /**
      * Render the list view for all available products.
      * @param model the view model object that is used to render the html.
      * @param auth the authentication context that manages which users are logged in.
      * @return the name of the template to render.
      */
      @GetMapping("/records/{id}")
      public String singleRecord(@PathVariable("id") Long id,
                                 Model model,
                                 Authentication auth) {
          Product productData = productRepository.findOne(id);
          super.setCommonModelAttributes(model,auth,userRepository, this.moduleName);
          model.addAttribute("productData", productData);
          model.addAttribute("toList", "/product");
          return "products/singleproduct";
      }

      /**
      * Maps empty string to null when a form is submitted.
      * @param binder The object that allows for empty strings to be turned into nulls.
      */
      @InitBinder
      public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
      }

      /**
      * Render a edit view for a single product.
      * @param id the id that is associated to some product.
      * @param model the view model object that is used to render the html.
      * @param auth the authentication context that manages which users are logged in.
      * @return the name of the template to render.
      */
      @GetMapping(value="/edit/{id}")
      public String editProduct(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth,
                               HttpServletResponse response) {
          Product product;
          if (productRepository.existsByProductId(id)) {
               product = productRepository.findOne(id);
          } else {
               response.setStatus(404);
               return "redirect:/404.html";
          }
          this.bindProductForm(model, auth);
          model.addAttribute("productData", product);
          model.addAttribute("action", "edit/" + id);
          return "products/editsingle";
      }

      /**
      * Process a form for editing a single record.
      * @param id the id that is associated to some product.
      * @param model the view model object that is used to render the html.
      * @param auth the authentication context that manages which users are logged in.
      * @return the name of the template to render.
      */
      @PostMapping(value="/edit/{id}")
      public String saveEditableproduct(@PathVariable("id") Long id,
                                     @Valid @ModelAttribute("productData") Product product,
                                     BindingResult result,
                                     Model model,
                                     Authentication auth) {
         if (result.hasErrors()) {
             this.bindProductForm(model, auth);
             model.addAttribute("action","edit/" + product.getProductId());
             return "products/editsingle";
         }
         CrmUser user = userRepository.findByEmail(auth.getName());
         product.setLastModifiedBy(user);
         Long assignedId = productRepository.save(product).getProductId();
         return "redirect:/product/records/" + assignedId.toString() ;
      }
      /**
      * Helper function to bind common model attributes whener generating a list form.
      * @param model the view model object that is used to render the html.
      * @param auth the authentication context that manages which users are logged in.
      */
      private void bindProductForm(Model model, Authentication auth) {
        super.setCommonModelAttributes(model,auth,userRepository,this.moduleName);
        model.addAttribute("userData", userRepository.findAll());
        model.addAttribute("toList", "/product");
      }
}