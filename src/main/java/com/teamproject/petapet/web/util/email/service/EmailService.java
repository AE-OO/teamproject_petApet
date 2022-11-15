package com.teamproject.petapet.web.util.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final SpringTemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;


    public void sendEmailMessage(String email) throws Exception {
        String code = createCode(); // 인증코드 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email); // 보낼 이메일 설정
        message.setSubject("[결제 완료] 주식회사 petApet에서 발신한 메일 입니다"); // 이메일 제목
        message.setText(setContext(code), "utf-8", "html"); // 내용 설정(Template Process)

        // 보낼 때 이름 설정하고 싶은 경우
        // message.setFrom(new InternetAddress([이메일 계정], [설정할 이름]));

        javaMailSender.send(message); // 이메일 전송
    }


    private String setContext(String code) { // 타임리프 설정하는 코드
        Context context = new Context();
        context.setVariable("code", code); // Template에 전달할 데이터 설정
        return templateEngine.process("mypage/mail", context); // mail.html
    }

    private String createCode() {
        // 인증 코드 생성
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 7; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    code.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }
        return code.toString();
    }

}