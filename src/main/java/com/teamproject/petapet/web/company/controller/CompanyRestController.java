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
    @PostMapping("/checkCompanyEmail")
    boolean duplicateCheckCompanyEmail(@RequestParam String companyEmail) {
        return companyService.duplicateCheckCompanyEmail(companyEmail);
    }
    @PostMapping("/checkCompanyPhoneNum")
    boolean duplicateCheckCompanyPhoneNum(@RequestParam String companyPhoneNum) {
        return companyService.duplicateCheckCompanyPhoneNum(companyPhoneNum);
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

    @PostMapping("/updateCompanyPw")
    int updateMemberPw(Principal principal,@Valid @RequestBody CompanyRequestDTO.UpdateCompanyPwDTO updateCompanyPwDTO){
        if(!companyService.checkCompanyPw(principal.getName(),updateCompanyPwDTO.getNewCompanyPw())) {
            return companyService.updateCompanyPw(principal.getName(), updateCompanyPwDTO.getNewCompanyPw());
        }
        return 0;
    }

    //22.11.25 박채원 추가 - 이하 3개 메소드(사업자가입 시 관리자 페이지에서 승인처리 구현 위함)
    @GetMapping("/getCompanyInfo/{companyId}")
    public CompanyDTO getCompanyInfo(@PathVariable("companyId") String companyId){
        return companyService.companyInfo(companyId);
    }

    @PostMapping("/acceptJoinCompany/{companyId}")
    public void acceptJoinCompany(@PathVariable("companyId") String companyId){
        companyService.acceptJoinCompany(companyId);
    }

    @PostMapping("/refuseJoinCompany/{companyId}")
    public void refuseJoinCompany(@PathVariable("companyId") String companyId){
        companyService.refuseJoinCompany(companyId);
    }
}
