package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.jwt.JwtAuthenticationFilter;
import com.teamproject.petapet.web.member.dto.MemberRequestDTO;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

/**
 * 장사론 22.10.20 작성
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/checkInfo")
    public String myInfo(){return "/member/checkInfo";}

    @GetMapping("/modifyInfo")
    public String myInfo(Principal principal, Model model){
        model.addAttribute("memberDTO", memberService.memberInfo(principal.getName()));
        return "member/modifyInfo";
    }

    @PostMapping("/modifyInfo")
    public String modifyInfo(Principal principal, @Valid MemberRequestDTO.UpdateMemberInfo updateMemberInfo,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "redirect:/member/modifyInfo";
        }
        memberService.updateMemberInfo(principal.getName(),updateMemberInfo);
        return "/member/checkInfo";
    }

    //회원탈퇴
    @GetMapping("/withdrawal")
    public String withdrawalPage(){ return "/member/withdrawal";}

    @PostMapping("/withdrawal")
    public String withdrawal(Principal principal, HttpServletResponse response){
        memberService.deleteMember(principal.getName());
        Cookie cookie = new Cookie(JwtAuthenticationFilter.AUTHORIZATION_HEADER, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }
}