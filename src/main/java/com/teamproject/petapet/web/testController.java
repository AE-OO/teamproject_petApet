package com.teamproject.petapet.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class testController {

    @GetMapping("/")
    public String test(Principal principal, Model model){
        if(Principal.class.isInstance(principal)){
            model.addAttribute("memberId", principal.getName());
            return "index";
        }
        return "index";}

}
