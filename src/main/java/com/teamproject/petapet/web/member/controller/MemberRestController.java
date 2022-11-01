package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 장사론 22.10.19 작성
 */
@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    //아이디 중복검사
    @PostMapping("/checkId")
    boolean duplicateCheckMemberId(@RequestParam String memberId) {
        return memberService.duplicateCheckMemberId(memberId);
    }

    //비밀번호 확인용
    @PostMapping("/checkPw")
    boolean checkMemberPw(Principal principal, @RequestParam String memberPw){
        return memberService.checkMemberPw(principal.getName(),memberPw);
    }
}
