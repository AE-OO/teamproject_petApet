package com.teamproject.petapet.web.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamproject.petapet.web.member.dto.sms.SmsDTO;
import com.teamproject.petapet.web.member.dto.sms.SmsRequestDTO;
import com.teamproject.petapet.web.member.dto.sms.SmsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService{

    @Value("${sms.serviceId}")
    private String serviceId;
    @Value("${sms.accessKey}")
    private String accessKey;
    @Value("${sms.secretKey}")
    private String secretKey;
    @Value("${sms.adminPhoneNum}")
    private String adminPhoneNum;

    //인증번호
    private final String smsConfirmNum = randomNumber();

    @Override
    public String sendSms(String to) throws JsonProcessingException, RestClientException,
            URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Long time = System.currentTimeMillis();

        String content = "[petApet] 인증번호 ["+smsConfirmNum+"]를 입력해주세요."; // 보낼 문자 내용
        byte[] decoded = Base64.decodeBase64(adminPhoneNum);
        String adminPhoneNumResult = new String(decoded);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

        List<SmsDTO> messages = new ArrayList<>();
        messages.add(new SmsDTO(to, content));

        SmsRequestDTO request = SmsRequestDTO.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(adminPhoneNumResult)
                .content(content)
                .messages(messages)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);

        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        SmsResponseDTO response = restTemplate.postForObject(
                new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"),
                httpBody, SmsResponseDTO.class);
        return smsConfirmNum;
    }

    @Override
    public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = method +
                space +
                url +
                newLine +
                timestamp +
                newLine +
                accessKey;

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeBase64String(rawHmac);
    }

    // 인증번호 생성(6자리 난수)
    @Override
    public String randomNumber() {
        Random random = new Random();
        int number = 0; // 1자리 난수
        String stringNumber = ""; //1자리 난수를 String 으로 형변환
        String resultNumber = ""; // 최종적으로 만들 6자리 난수 string

        for (int i = 0; i < 6; i++) {
            number = random.nextInt(9);
            stringNumber = Integer.toString(number);
            resultNumber += stringNumber;
        }
        return  resultNumber;
    }
}
