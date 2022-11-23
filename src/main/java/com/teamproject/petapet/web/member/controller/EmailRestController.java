package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.web.member.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailRestController {
    private final EmailService emailService;
    @PostMapping("/send")
    int emailSend(@RequestParam String email, @RequestParam String memberId){
        return emailService.emailSend(email,memberId);
    }

    @PostMapping("/sendCompany")
    int emailSend(@RequestParam String companyId){
        return emailService.emailSend(companyId);
    }
}
