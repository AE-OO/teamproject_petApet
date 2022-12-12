package com.teamproject.petapet.web.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * 장사론 22.10.24 작성
 */

public interface SmsService {


    String sendSms(String to) throws JsonProcessingException, RestClientException,
            URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException;
    String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException;

    String randomNumber();

}