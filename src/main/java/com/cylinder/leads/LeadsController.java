package com.cylinder.leads;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class LeadsController {
    @RequestMapping("")
      @ResponseBody
    public String index() {
        return "This is a lead response!";
    }
}
