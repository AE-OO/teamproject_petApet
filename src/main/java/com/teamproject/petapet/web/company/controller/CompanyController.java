package com.teamproject.petapet.web.company.controller;

import com.teamproject.petapet.jwt.JwtAuthenticationFilter;
import com.teamproject.petapet.web.company.dto.CompanyRequestDTO;
import com.teamproject.petapet.web.company.service.CompanyService;
import com.teamproject.petapet.web.member.dto.MemberRequestDTO;
import com.teamproject.petapet.web.member.dto.TokenDTO;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final MemberService memberService;

    @GetMapping("/companyJoin")
    public String companyJoinForm(CompanyRequestDTO.JoinDTO joinDTO){return "company/companyJoin";}

    @PostMapping("/companyJoin")
    public String join(@Valid CompanyRequestDTO.JoinDTO joinDTO, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("joinDTO", joinDTO);
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "company/companyJoin";
        }
        session.removeAttribute("smsConfirmNum");
        companyService.companyJoin(joinDTO);
        return "redirect:/";
    }

    @GetMapping("/companyLogin")
    public String companyLoginForm(){return "redirect:/login";}

    @PostMapping("/companyLogin")
    public String companyLogin(@Valid CompanyRequestDTO.LoginDTO loginDTO, BindingResult bindingResult, Model model, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "login";
        }
        TokenDTO tokenDTO = companyService.login(loginDTO);
        //토큰 쿠키에 저장
        Cookie cookie = new Cookie(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer" + tokenDTO.getToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @PostMapping("/findCompanyId")
    public String findCompanyId(@Valid CompanyRequestDTO.FindCompanyIdDTO findCompanyIdDTO,BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "findId";
        }
        model.addAttribute("findCompanyId",companyService.findCompanyId(findCompanyIdDTO));
        return "findId";
    }

    @PostMapping("/findCompanyPw")
    public String findMemberPw(@Valid CompanyRequestDTO.FindCompanyPwDTO findCompanyPwDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "findPw";
        }
        model.addAttribute("companyId",companyService.findCompanyPw(findCompanyPwDTO));
        return "findPw";
    }

    @GetMapping("/company/info")
    public String companyInfoForm(){return "company/checkCompanyInfo";}

    @PostMapping("/company/info")
    public String companyInfo(Principal principal, Model model){
        model.addAttribute("companyDTO", companyService.companyInfo(principal.getName()));
        return "company/modifyCompanyInfo";
    }

    @PostMapping("/company/modifyInfo")
    public String UpdateCompanyInfo(Principal principal, @Valid CompanyRequestDTO.UpdateCompanyInfo updateCompanyInfo,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "redirect:/company/info";
        }
        companyService.updateCompanyInfo(principal.getName(),updateCompanyInfo);
        return "redirect:/company/info";
    }

    //회원탈퇴
    @GetMapping("/company/withdrawal")
    public String withdrawalPage(){ return "company/companyWithdrawal";}

    @PostMapping("/company/withdrawal")
    public String withdrawal(Principal principal, HttpServletResponse response){
        companyService.deleteCompany(principal.getName());
        Cookie cookie = new Cookie(JwtAuthenticationFilter.AUTHORIZATION_HEADER, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

}
