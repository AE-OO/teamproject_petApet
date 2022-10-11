package com.teamproject.petapet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {

    @GetMapping("/")
    public String index(){ return "index";}

    @GetMapping("/join")
    public String join(){
        return "join";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
