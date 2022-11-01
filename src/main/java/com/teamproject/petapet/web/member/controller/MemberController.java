package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.jwt.JwtAuthenticationFilter;
import com.teamproject.petapet.web.member.dto.JoinDto;
import com.teamproject.petapet.web.member.dto.LoginDto;
import com.teamproject.petapet.web.member.dto.TokenDto;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

/**
 * 장사론 22.10.20 작성
 */
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @GetMapping("/join")
    public String joinForm(Principal principal, JoinDto joinDto) {
        if(Principal.class.isInstance(principal)) {
            return "redirect:/";
        }
        return "join"; }

    @PostMapping("/join")
    public String join(@Valid JoinDto joinDto, BindingResult bindingResult, Model model, HttpSession session) {
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
        session.removeAttribute("smsConfirmNum");
        memberService.join(joinDto);
        return "redirect:/";
    }

    //로그인
    @GetMapping("/login")
    public String loginForm(Principal principal, LoginDto loginDto) {
        if(Principal.class.isInstance(principal)) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginDto loginDto, BindingResult bindingResult, Model model, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("loginDto", loginDto);
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "login";
        }

        TokenDto tokenDto = memberService.login(loginDto);
        //토큰 쿠키에 저장
        Cookie cookie = new Cookie(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer" + tokenDto.getToken());
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24); //유효기간 24시간

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/member/info")
    public String myInfo(){return "/member/checkInfo";}

    @GetMapping("/member/info/modify")
    public String myInfo(Principal principal, Model model){
        model.addAttribute("memberDto", memberService.memberInfo(principal.getName()));
        return "/member/modifyInfo";
    }

    @GetMapping("/member/withdrawal")
    public String withdrawalPage(){return "/member/withdrawal";}

    @PostMapping("/member/withdrawal")
    public String witdrawal(Principal principal, HttpServletResponse response){
        memberService.deleteMember(principal.getName());
        Cookie cookie = new Cookie(JwtAuthenticationFilter.AUTHORIZATION_HEADER, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

}