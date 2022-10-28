package com.teamproject.petapet.web.member.controller;

import com.teamproject.petapet.jwt.JwtAuthenticationFilter;
import com.teamproject.petapet.web.member.dto.LoginDto;
import com.teamproject.petapet.web.member.dto.TokenDto;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


/**
 * 장사론 22.10.19 작성
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        TokenDto tokenDto = memberService.login(loginDto);

        //토큰 쿠키에 저장
        Cookie cookie = new Cookie(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer"+tokenDto.getToken());
        cookie.setPath("/");
        cookie.setMaxAge((int)tokenDto.getAccessTokenExpiresIn());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/checkId")
    boolean duplicateCheckMemberId(@RequestParam String memberId){
        return memberService.duplicateCheckMemberId(memberId);
    }

}