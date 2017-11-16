package com.cylinder.shared.controllers;

import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.crmusers.model.CrmUser;

public class BaseController {

  protected void setCommonModelAttributes(Model model,
                                        Authentication auth,
                                        CrmUserRepository userRepository,
                                        String moduleName) {
    CrmUser currentUser = userRepository.findByEmail(auth.getName());
    String welcomeMessage = "Hello " + currentUser.getFirstName() + " " + currentUser.getLastName();
    model.addAttribute("welcomeMessage", welcomeMessage);
    model.addAttribute("moduleName", moduleName);
    model.addAttribute("userId", currentUser.getAccountId());
  }

}
