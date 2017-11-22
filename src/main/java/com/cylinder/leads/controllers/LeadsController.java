package com.cylinder.leads.controllers;

import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.leads.model.Lead;
import com.cylinder.leads.model.LeadRepository;
import com.cylinder.leads.model.SourceRepository;
import com.cylinder.leads.model.StatusRepository;
import com.cylinder.shared.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequestMapping("/lead")
public class LeadsController extends BaseController {

    /**
     * Sql interface for lead entites.
     */
    @Autowired
    private LeadRepository leadRepository;

    /**
     * Sql interface for lead status entites.
     */
    @Autowired
    private StatusRepository statusRespository;

    /**
     * Sql interface for source entites.
     */
    @Autowired
    private SourceRepository sourceRespository;

    /**
     * Sql interface for crm user entites.
     */
    @Autowired
    private CrmUserRepository userRepository;

    /**
     * The module name that this controller is associated to.
     */
    private final String moduleName = "Leads";

    /**
     * Render the list view for all available leads.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping
    public String list(Model model, Authentication auth) {
        Iterable<Lead> leadData = leadRepository.findAll();
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("leadData", leadData);
        return "leads/list";
    }

    /**
     * Render the list view for all available leads.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/records/{id}")
    public String singleRecord(@PathVariable("id") Long id,
                               Model model,
                               Authentication auth) {
        Lead leadData = leadRepository.findOne(id);
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("leadData", leadData);
        model.addAttribute("toList", "/lead");
        return "leads/singlelead";
    }

    /**
     * Render a edit view for a single lead.
     *
     * @param id    the id that is associated to some lead.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping(value = "/edit/{id}")
    public String editLead(@PathVariable("id") Long id,
                           Model model,
                           Authentication auth,
                           HttpServletResponse response) {
        Lead lead;
        if (leadRepository.existsById(id)) {
            lead = leadRepository.findOne(id);
        } else {
            response.setStatus(404);
            return "redirect:/404.html";
        }
        this.bindUserForm(model, auth);
        model.addAttribute("leadData", lead);
        model.addAttribute("action", "edit/" + id);
        return "leads/editsingle";
    }

    /**
     * Process a form for editing a single record.
     *
     * @param id    the id that is associated to some lead.
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping(value = "/edit/{id}")
    public String saveEditableLead(@PathVariable("id") Long id,
                                   @Valid @ModelAttribute("leadData") Lead lead,
                                   BindingResult result,
                                   Model model,
                                   Authentication auth) {
        if (result.hasErrors()) {
            this.bindUserForm(model, auth);
            model.addAttribute("action", "edit/" + lead.getLeadId());
            return "leads/editsingle";
        }
        if (lead.getAddress().areFieldsNull()) {
            lead.setAddress(null);
        }
        CrmUser user = userRepository.findByEmail(auth.getName());
        lead.setLastModifiedBy(user);
        Long assignedId = leadRepository.save(lead).getLeadId();
        return "redirect:/lead/records/" + assignedId.toString();
    }

    /**
     * Render a view for a creating a single lead.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @GetMapping("/new/")
    public String newRecord(Model model,
                            Authentication auth) {
        this.bindUserForm(model, auth);
        model.addAttribute("action", "new/");
        model.addAttribute("leadData", new Lead());
        return "leads/editsingle";
    }

    /**
     * Process a new lead form and potentially send errors back.
     *
     * @param lead   The lead form object to be processed.
     * @param result the object that binds the data from the view and validates user.
     * @param model  the view model object that is used to render the html.
     * @param auth   the authentication context that manages which users are logged in.
     * @return the name of the template to render.
     */
    @PostMapping("/new/")
    public String saveNewLead(@Valid @ModelAttribute("leadData") Lead lead,
                              BindingResult result,
                              Model model,
                              Authentication auth) {
        if (result.hasErrors()) {
            this.bindUserForm(model, auth);
            model.addAttribute("action", "new/");
            return "leads/editsingle";
        }
        if (lead.getAddress().areFieldsNull()) {
            lead.setAddress(null);
        }
        CrmUser user = userRepository.findByEmail(auth.getName());
        lead.setCreatedBy(user);
        Long assignedId = leadRepository.save(lead).getLeadId();
        return "redirect:/lead/records/" + assignedId.toString();
    }


    /**
     * Delete some lead through a delete request.
     *
     * @param id the id that is associated to some lead.
     * @return the name of the template to render.
     */
    @DeleteMapping("/records/{id}")
    @ResponseBody
    public String deleteRecord(@PathVariable("id") Long id) {
        if (leadRepository.existsById(id)) {
            leadRepository.deleteById(id);
            return "";
        } else {
            return "Failed to delete record" + id;
        }
    }

    /**
     * Helper function to bind common model attributes whener generating a list form.
     *
     * @param model the view model object that is used to render the html.
     * @param auth  the authentication context that manages which users are logged in.
     */
    private void bindUserForm(Model model, Authentication auth) {
        super.setCommonModelAttributes(model, auth, userRepository, this.moduleName);
        model.addAttribute("userData", userRepository.findAll());
        model.addAttribute("leadSource", sourceRespository.findAll());
        model.addAttribute("leadStatus", statusRespository.findAll());
        model.addAttribute("toList", "/lead");
    }

    /**
     * Maps empty string to null when a form is submitted.
     *
     * @param binder The object that allows for empty strings to be turned into nulls.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
