package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.web.member.dto.MemberDto;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * 장사론 22.10.20 작성
 */
@Controller
@RequiredArgsConstructor
public class MemberController {

    MemberService memberService;

    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberDto memberDto) {
        System.out.println(memberDto.getMemberId());
        memberService.join(memberDto);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}