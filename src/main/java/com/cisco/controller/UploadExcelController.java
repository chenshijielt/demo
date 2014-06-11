package com.cisco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by root on 6/10/14.
 */
@Controller
@RequestMapping(value ="/upload")
public class UploadExcelController {

    @RequestMapping(value ="/test")
    public String test(Model model){
        model.addAttribute("success");
        model.addAttribute("a","b");
        return "upload";
    }
}
