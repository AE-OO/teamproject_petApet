package com.teamproject.petapet.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {

    @GetMapping("/")
    public String test(){
        return "index";
    }

//    @GetMapping("/admin")
//    public String adminTest(){
//        return "admin";
//    }
}
