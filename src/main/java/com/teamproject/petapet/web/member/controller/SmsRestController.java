package com.teamproject.petapet.web.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamproject.petapet.web.member.service.SmsService;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 장사론 22.10.23 작성
 * 장사론 22.10.27 추가 - 문자 인증번호 세션에 저장
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SmsRestController {
    private final SmsService smsService;


    ////테스트용임// - 실제로 문자서비스 안됨
    @PostMapping("/test")
    public String sendTest(@RequestParam String to, HttpSession session){
        session.setAttribute("smsConfirmNum",smsService.randomNumber());
        session.setMaxInactiveInterval(60*5);
        return session.getAttribute("smsConfirmNum").toString();
    }

    @PostMapping("/send")
    public String sendSms(@RequestParam String to, HttpSession session) throws JsonProcessingException, RestClientException,
            URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String smsConfirmNum= smsService.sendSms(to);

        //인증번호 세션에 저장 - JoinDto에서 유효성 검사할 때 사용
        session.setAttribute("smsConfirmNum",smsConfirmNum);
        session.setMaxInactiveInterval(60*10);
        System.out.println(session.getAttribute("smsConfirmNum"));

        return smsConfirmNum;
    }

}
