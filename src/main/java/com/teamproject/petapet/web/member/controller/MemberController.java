package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.web.member.dto.JoinDto;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

/**
 * 장사론 22.10.20 작성
 * 장사론 22.10.27 수정 - join
 */
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(JoinDto joinDto) {
        // 여기서 JoinDto를 받아줘야 회원가입 실패시 그 입력값이 그대로 유지된다.
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid JoinDto joinDto, BindingResult bindingResult, Model model) {

        // 회원가입 요청시 member입력 값에서 Validation에 걸리는 경우
        if (bindingResult.hasErrors()) {
            //회원가입 실패시 입력 데이터 유지
            model.addAttribute("joinDto", joinDto);
            //회원가입 실패시 message 값들을 모델에 매핑해서 View로 전달
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "join";
        }

        memberService.join(joinDto);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


}