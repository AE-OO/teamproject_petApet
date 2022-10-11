package com.teamproject.petapet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/mypage")
    public String mypage(){
        return "mypage/mypage";
    }
}
