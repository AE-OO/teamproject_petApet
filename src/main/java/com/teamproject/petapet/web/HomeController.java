
package com.teamproject.petapet.web;


import com.teamproject.petapet.jwt.JwtAuthenticationFilter;
import com.teamproject.petapet.web.member.dto.MemberRequestDTO;
import com.teamproject.petapet.web.member.dto.TokenDTO;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index() {return "index";}

    //회원가입
    @GetMapping("/join")
    public String joinForm(Principal principal, MemberRequestDTO.JoinDTO joinDTO) {
        if (Principal.class.isInstance(principal)) {
            return "redirect:/";
        }
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberRequestDTO.JoinDTO joinDTO, BindingResult bindingResult, Model model, HttpSession session) {
        // 회원가입 요청시 member입력 값에서 Validation에 걸리는 경우
        if (bindingResult.hasErrors()) {
            //회원가입 실패시 입력 데이터 유지
            model.addAttribute("joinDTO", joinDTO);
            //회원가입 실패시 message 값들을 모델에 매핑해서 View로 전달
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "join";
        }
        session.removeAttribute("smsConfirmNum");
        memberService.join(joinDTO);
        return "redirect:/";
    }

    //로그인
    @GetMapping("/login")
    public String loginForm(Principal principal) {
        if (Principal.class.isInstance(principal)) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid MemberRequestDTO.LoginDTO loginDTO, BindingResult bindingResult, Model model, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "login";
        }

        TokenDTO tokenDTO = memberService.login(loginDTO);
        //토큰 쿠키에 저장
        Cookie cookie = new Cookie(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer" + tokenDTO.getToken());
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24); //유효기간 24시간
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return "redirect:/";
    }

    //아이디 찾기
    @GetMapping("/findMemberId")
    public String findMemberIdForm(){ return "/findMemberId"; }

    @PostMapping("/findMemberId")
    public String findMemberId(@Valid MemberRequestDTO.FindMemberIdDTO findMemberIdDTO,BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "/findMemberId";
        }
        model.addAttribute("findMemberId",memberService.findMemberId(findMemberIdDTO));
        return "/findMemberId";
    }

    //비밀번호 찾기
    @GetMapping("/findMemberPw")
    public String findMemberPwForm(){ return "/findMemberPw";}

    @PostMapping("/findMemberPw")
    public String findMemberPw(@Valid MemberRequestDTO.FindMemberPwDTO findMemberPwDTO,BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            Map<String, String> validateResult = memberService.validateHandling(bindingResult);
            for (String key : validateResult.keySet()) {
                model.addAttribute(key, validateResult.get(key));
            }
            return "/findMemberPw";
        }
        model.addAttribute("memberId",memberService.findMemberPw(findMemberPwDTO));
        return "/findMemberPw";
    }
}