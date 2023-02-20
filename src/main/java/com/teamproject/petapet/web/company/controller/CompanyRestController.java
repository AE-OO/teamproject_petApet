package com.teamproject.petapet.web.company.controller;

import com.teamproject.petapet.web.company.dto.CompanyDTO;
import com.teamproject.petapet.web.company.dto.CompanyRequestDTO;
import com.teamproject.petapet.web.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CompanyRestController {
    private final CompanyService companyService;

    //아이디 중복검사
    @PostMapping("/checkCompanyId")
    boolean duplicateCheckCompanyId(@RequestParam String companyId) {
        return companyService.duplicateCheckCompanyId(companyId);
    }
    //이메일 확인
    @PostMapping("/checkCompanyEmail")
    boolean duplicateCheckCompanyEmail(@RequestParam String companyEmail) {
        return companyService.duplicateCheckCompanyEmail(companyEmail);
    }
    //휴대전화 확인
    @PostMapping("/checkCompanyPhoneNum")
    boolean duplicateCheckCompanyPhoneNum(@RequestParam String companyPhoneNum) {
        return companyService.duplicateCheckCompanyPhoneNum(companyPhoneNum);
    }

    //비밀번호 확인
    @PostMapping("/checkCompanyPw")
    boolean checkCompanyPw(Principal principal, @RequestParam String companyPw){
        return companyService.checkCompanyPw(principal.getName(),companyPw);
    }

    //사업자 번호 확인
    @PostMapping("/checkCompanyNumber")
    boolean checkCompanyNumber(@RequestParam String companyNumber){
        return companyService.checkCompanyNumber(companyNumber);
    }

    //비밀번호 변경
    @PostMapping("/updateCompanyPw")
    int updateMemberPw(Principal principal,@Valid @RequestBody CompanyRequestDTO.UpdateCompanyPwDTO updateCompanyPwDTO){
        if(!companyService.checkCompanyPw(principal.getName(),updateCompanyPwDTO.getNewCompanyPw())) {
            return companyService.updateCompanyPw(principal.getName(), updateCompanyPwDTO.getNewCompanyPw());
        }
        return 0;
    }
}
