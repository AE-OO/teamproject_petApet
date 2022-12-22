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
    int memberEmailSend(@RequestParam String memberId){return emailService.memberEmailSend(memberId);}
    @PostMapping("/sendCompany")
    int companyEmailSend(@RequestParam String companyId){
        return emailService.companyEmailSend(companyId);
    }
    @PostMapping("/sendRefuseReason")
    int sendRefuseReason(@RequestParam String companyId, @RequestParam String reason){
        return emailService.refuseReasonSend(companyId, reason);
    }
}
