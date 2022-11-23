package com.teamproject.petapet.web.company.controller;

import com.teamproject.petapet.web.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CompanyRestController {
    private final CompanyService companyService;

    //아이디 중복검사
    @PostMapping("/checkCompanyId")
    boolean duplicateCheckCompanyId(@RequestParam String companyId) {
        return companyService.duplicateCheckCompanyId(companyId);
    }

    //비밀번호 확인용
    @PostMapping("/checkCompanyPw")
    boolean checkCompanyPw(Principal principal, @RequestParam String companyPw){
        return companyService.checkCompanyPw(principal.getName(),companyPw);
    }

    @PostMapping("/checkCompanyNumber")
    boolean checkCompanyNumber(@RequestParam String companyNumber){
        return companyService.checkCompanyNumber(companyNumber);
    }
}
