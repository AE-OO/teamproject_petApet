package com.teamproject.petapet.domain;

import com.teamproject.petapet.web.util.email.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void sendMail() throws Exception {
        emailService.sendEmailMessage("skyair419@naver.com");

    }
}