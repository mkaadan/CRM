package com.cylinder.leads.controller;

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

import javax.validation.Valid;

import com.cylinder.leads.model.*;

@Controller
@RequestMapping("/lead")
public class LeadsController {

      @Autowired
      private LeadRepository leadRepository;

      @Autowired
      private StatusRepository statusRespository;

      @Autowired
      private SourceRepository sourceRespository;

    	@RequestMapping(method=RequestMethod.GET)
      // Render the list view for leads.
    	public String list(Model model){
        model.addAttribute("moduleName", "Leads");
        Iterable<Lead> leadData =  leadRepository.findAll();
        model.addAttribute("leadData", leadData);
        return "leads/list";
      }

      @RequestMapping(value="/records/{id}", method=RequestMethod.GET)
      public String singleRecord(@PathVariable("id") Long id, Model model) {
          Lead leadData = leadRepository.findOne(id);
          model.addAttribute("moduleName", "Leads");
          model.addAttribute("leadData", leadData);
          model.addAttribute("toList", "/");
          return "leads/singlelead";
      }

      @RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
      public String editRecord(@ModelAttribute("leadData") Lead lead,
                               @PathVariable("id") Long id,
                               Model model) {
          if (leadRepository.existsById(id)) {
               lead = leadRepository.findOne(id);
          } else {
               return "redirect:/errors";
          }
          Iterable<Source> sourceData = sourceRespository.findAll();
          Iterable<Status> statusData = statusRespository.findAll();
          model.addAttribute("moduleName", "Leads");
          model.addAttribute("leadStatus", statusData);
          model.addAttribute("leadSource", sourceData);
          model.addAttribute("leadData", lead);
          return "leads/editsingle";
      }

      @RequestMapping(value="/new/", method=RequestMethod.GET)
      public String newRecord(@ModelAttribute("leadData") Lead lead,
                              Model model) {
          Iterable<Source> sourceData = sourceRespository.findAll();
          Iterable<Status> statusData = statusRespository.findAll();
          model.addAttribute("moduleName", "Leads");
          model.addAttribute("leadStatus", statusData);
          model.addAttribute("leadSource", sourceData);
          model.addAttribute("leadData", new Lead());
          return "leads/editsingle";
      }

      @RequestMapping(value="/records/{id}", method=RequestMethod.DELETE)
      @ResponseBody
      public String deleteRecord(@PathVariable("id") Long id) {
        if (leadRepository.existsById(id)) {
          leadRepository.deleteById(id);
          return "";
        } else {
          return "Failed to delete record" + id;
        }

      }

      @RequestMapping(value="/records", method=RequestMethod.POST)
      public String saveRecord(@Valid @ModelAttribute("leadData") Lead lead,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            System.out.println("------------------------------------------");
            for (FieldError error: result.getFieldErrors()) {
              System.out.println(error);
            }
            System.out.println("------------------------------------------");
            model.addAttribute("moduleName", "Leads");
            Iterable<Source> sourceData = sourceRespository.findAll();
            Iterable<Status> statusData = statusRespository.findAll();
            model.addAttribute("leadStatus", statusData);
            model.addAttribute("leadSource", sourceData);
            return "leads/editsingle";
        }
        if (lead.getAddress().areFieldsNull()) {
          lead.setAddress(null);
        }
        Long assignedId = leadRepository.save(lead).getLeadId();
        return "redirect:/lead/records/" + assignedId.toString() ;
      }

      @InitBinder
      public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
      }
}
