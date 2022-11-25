package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.web.member.dto.MemberRequestDTO;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    //인증번호 확인용
    @PostMapping("/checkSmsConfirmNum")
    boolean checkSmsConfirmNum(@RequestParam String smsConfirmNum, HttpSession session){
        if (session.getAttribute("smsConfirmNum") != null) {
            return session.getAttribute("smsConfirmNum").toString().equals(smsConfirmNum);
        }
        return false;
    }

    @PostMapping("/updateMemberPw")
    int updateMemberPw(Principal principal,@Valid @RequestBody MemberRequestDTO.UpdateMemberPwDTO updateMemberPwDTO){
        if(!memberService.checkMemberPw(principal.getName(),updateMemberPwDTO.getNewMemberPw())) {
            return memberService.updateMemberPw(principal.getName(), updateMemberPwDTO.getNewMemberPw());
        }
        return 0;
    }

}
