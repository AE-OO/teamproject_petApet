package com.teamproject.petapet.web.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamproject.petapet.web.member.dto.sms.SmsDto;
import com.teamproject.petapet.web.member.dto.sms.SmsResponseDto;
import com.teamproject.petapet.web.member.service.SmsService;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 장사론 22.10.23 작성
 */
@Log4j2
@RestController
@RequiredArgsConstructor
public class SmsRestController {
    private final SmsService smsService;

    @PostMapping("/sms/send")
    public SmsResponseDto sendSms(@RequestParam String to) throws JsonProcessingException, RestClientException,
            URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return smsService.sendSms(to);
    }
}
