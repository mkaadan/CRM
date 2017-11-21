package com.cylinder.deals.controllers;

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

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import javax.servlet.http.HttpServletResponse;

import javax.validation.Valid;

import com.cylinder.shared.controllers.BaseController;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.deals.model.*;
import com.cylinder.deals.model.TypeRepository;
import com.cylinder.contacts.model.ContactRepository;
import com.cylinder.accounts.model.AccountRepository;

/**
* @author Ryan Piper
* Manges the deals requests and responses.
*/
@Controller
@RequestMapping("/deal")
public class DealController extends BaseController {

  /**
  * Sql interface for crm user entites.
  */
  @Autowired
  private CrmUserRepository userRepository;

  /**
  * Sql interface for deal entites.
  */
  @Autowired
  private DealRepository dealRepository;

  /**
  * Sql interface for deal stage entites.
  */
  @Autowired
  private StageRepository stageRepository;

  /**
  * Sql interface for deal type entites.
  */
  @Autowired
  private TypeRepository typeRepository;

  /**
  * Sql interface for deal type entites.
  */
  @Autowired
  private AccountRepository accountRepository;

  /**
  * Sql interface for deal type entites.
  */
  @Autowired
  private ContactRepository contactRepository;

  private final String moduleName = "Deals";

  /**
  * Render a general overview of all deals going on.
  * @param model the view model object that is used to render the html.
  * @param auth the authentication context that manages which users are logged in.
  * @return the name of the template to render.
  */
  @GetMapping
  public String renderListView(Model model, Authentication auth) {
    super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
    model.addAttribute("dealData", dealRepository.findAll());
    return "deals/list";
  }

  /**
  * Render a view for a single deal.
  * @param model the view model object that is used to render the html.
  * @param auth the authentication context that manages which users are logged in.
  * @return the name of the template to render.
  */
  @GetMapping("/records/{id}")
  public String renderSingleRecord(@PathVariable("id") Long id,
                                   Model model,
                                   Authentication auth,
                                   HttpServletResponse response) {
      if (dealRepository.existsByDealId(id)) {
        super.setCommonModelAttributes(model,auth,userRepository, this.moduleName);
        model.addAttribute("dealData", dealRepository.findOne(id));
        model.addAttribute("toList", "/deal");
        return "deals/single";
      } else {
        response.setStatus(404);
        return "redirect:/404.html";
      }
  }

  /**
  * Render a edit view for a single deal.
  * @param id the id that is associated to some deal.
  * @param model the view model object that is used to render the html.
  * @param auth the authentication context that manages which users are logged in.
  * @return the name of the template to render.
  */
  @GetMapping(value="/edit/{id}")
  public String editDealRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth,
                               HttpServletResponse response) {
      Deal deal;
      if (dealRepository.existsByDealId(id)) {
           deal = dealRepository.findOne(id);
      } else {
           response.setStatus(404);
           return "redirect:/404.html";
      }
      this.bindDealForm(model, auth);
      model.addAttribute("dealData", deal);
      model.addAttribute("action", "edit/" + id);
      return "deals/dealform";
  }

  /**
  * Process a form for editing a single record.
  * @param id the id that is associated to some deal.
  * @param model the view model object that is used to render the html.
  * @param auth the authentication context that manages which users are logged in.
  * @return the name of the template to render.
  */
  @PostMapping(value="/edit/{id}")
  public String saveEditableDeal(@PathVariable("id") Long id,
                                 @Valid @ModelAttribute("dealData") Deal deal,
                                 BindingResult result,
                                 Model model,
                                 Authentication auth) {
     if (result.hasErrors()) {
         this.bindDealForm(model, auth);
         model.addAttribute("action","edit/" + deal.getDealId());
         return "deals/dealform";
     }
     CrmUser user = userRepository.findByEmail(auth.getName());
     deal.setLastModifiedBy(user);
     Long assignedId = dealRepository.save(deal).getDealId();
     return "redirect:/deal/records/" + assignedId.toString() ;
  }

  /**
  * Render a view for a creating a single deal.
  * @param model the view model object that is used to render the html.
  * @param auth the authentication context that manages which users are logged in.
  * @return the name of the template to render.
  */
  @GetMapping("/new/")
  public String newRecord(Model model,
                          Authentication auth) {
      this.bindDealForm(model,auth);
      model.addAttribute("action","new/");
      model.addAttribute("dealData", new Deal());
      return "deals/dealform";
  }

  /**
  * Process a new deal form and potentially send errors back.
  * @param deal The deal form object to be processed.
  * @param result the object that binds the data from the view and validates user.
  * @param model the view model object that is used to render the html.
  * @param auth the authentication context that manages which users are logged in.
  * @return the name of the template to render.
  */
  @PostMapping("/new/")
  public String saveNewDeal(@Valid @ModelAttribute("dealData") Deal deal,
                            BindingResult result,
                            Model model,
                            Authentication auth) {
    if (result.hasErrors()) {
        this.bindDealForm(model,auth);
        model.addAttribute("action","new/");
        return "deals/dealform";
    }
    CrmUser user = userRepository.findByEmail(auth.getName());
    deal.setCreatedBy(user);
    Long assignedId = dealRepository.save(deal).getDealId();
    return "redirect:/deal/records/" + assignedId.toString() ;
  }

    /**
     * Delete some deal through a delete request.
     * @param id the id that is associated to some deal.
     * @return the name of the template to render.
     */
    @DeleteMapping("/records/{id}")
    @ResponseBody
    public String deleteRecord(@PathVariable("id") Long id) {
        if (dealRepository.existsByDealId(id)) {
            dealRepository.deleteByDealId(id);
            return "";
        } else {
            return "Failed to delete record" + id;
        }
    }

  /**
  *
  *
  *
  */
  @GetMapping("records/lookup")
  public ResponseEntity<Iterable<Deal>> getDealLookup() {
    Iterable<Deal> deals = dealRepository.findAll();
    return new ResponseEntity<Iterable<Deal>>(deals, HttpStatus.OK);
  }

  /**
  * Helper function to bind common model attributes whener generating a list form.
  * @param model the view model object that is used to render the html.
  * @param auth the authentication context that manages which users are logged in.
  */
  private void bindDealForm(Model model, Authentication auth) {
    super.setCommonModelAttributes(model,auth,userRepository,this.moduleName);
    model.addAttribute("userData", userRepository.findAll());
    model.addAttribute("typeData", typeRepository.findAll());
    model.addAttribute("stageData", stageRepository.findAll());
    model.addAttribute("accountData", accountRepository.findAll());
    model.addAttribute("contactData", contactRepository.findAll());
    model.addAttribute("toList", "/deal");
  }

  /**
  * Maps empty string to null when a form is submitted.
  * @param binder The object that allows for empty strings to be turned into nulls.
  */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }
}
