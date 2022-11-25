package com.teamproject.petapet.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 장사론 22.11.04 작성
 * 로그인시 비밀번호 틀렸을 때..null에러로 반환되서....exception처리함......
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler(NullPointerException.class)
    public Object nullException(Exception e) {
        System.err.println(e.getClass());
        return "redirect:/";
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Object loginException(Exception e,Model model) {
        System.err.println(e.getClass());
        model.addAttribute("error","아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.");
        return "login";
    }

    @ExceptionHandler(LockedException.class)
    public Object stopMemberException(Exception e,Model model) {
        System.err.println(e.getClass());
        model.addAttribute("error","잦은 신고로 이용이 정지된 아이디입니다.");
        return "login";
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public Object expiredJwtException(Exception e,Model model){
        System.err.println(e.getClass());
        model.addAttribute("error","로그인 이용시간이 만료되어 로그아웃 되었습니다. 계속하려면 다시 로그인하세요.");
        return "login";
    }

    @ExceptionHandler(DisabledException.class)
    public Object disabledException(Exception e,Model model){
        System.err.println(e.getClass());
        model.addAttribute("error","승인되지 않은 사업자 회원입니다.");
        return "login";
    }


}