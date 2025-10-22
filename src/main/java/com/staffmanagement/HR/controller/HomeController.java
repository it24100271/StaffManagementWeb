package com.staffmanagement.HR.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "landing"; // This will look for index.html or index.jsp in templates
    }
}

