package com.teamproject.petapet.web.Inquired;


import com.teamproject.petapet.web.Inquired.service.InquiredService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class InquiredController {

    public InquiredService inquiredService;

    @GetMapping("/inquiry")
    public String inquiry(){
        return "mypage/inquiry";
    }
}
