package com.rafin.reg_login.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String loginUser(){
        return "login";
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }
}
