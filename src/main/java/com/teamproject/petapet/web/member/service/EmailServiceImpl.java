package com.teamproject.petapet.web.member.service;

import com.teamproject.petapet.web.company.service.CompanyService;
import com.teamproject.petapet.web.member.dto.email.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final MemberService memberService;

    private final CompanyService companyService;

    @Value("${spring.mail.username}")
    private String adminEmail;


    @Override
    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] charSet2 = new char[]{'$', '~', '!', '@', '%', '*', '&', '-', '_', '=', '+'};

        String str = "";
        String str2 = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 9; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        str2 += charSet2[(int) (charSet2.length * Math.random())];
        return str + str2;
    }

    @Override
    public int emailSend(String email, String memberId) {
        String password = getTempPassword();
        memberService.updateMemberPw(memberId, password);

        String msg = "";
        msg += "<h1 style=\"font-size: 20px; padding-right: 30px; padding-left: 30px;\">임시비밀번호 안내</h1>";
        msg += "<p style=\"font-size: 15px; padding-right: 30px; padding-left: 30px;\">안녕하세요.회원님!";
        msg += "<p style=\"font-size: 15px; padding-right: 30px; padding-left: 30px;\">petApet 임시비밀번호 안내 관련 이메일입니다.</p>";
        msg += "<p style=\"font-size: 15px; padding-right: 30px; padding-left: 30px;\">회원님의 임시비밀번호는 아래와 같습니다.</p>";
        msg += "<p style=\"font-size: 15px; padding-right: 30px; padding-left: 30px;\">로그인 후에는 비밀번호 변경을 부탁드립니다. </p>";
        msg += "<div style=\"padding-right: 15px; padding-left: 30px; margin: 32px 0 40px;\">" +
                "<table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; " +
                "table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"" +
                "text-align: center; vertical-align: middle; font-size: 20px;\">";
        msg += password;
        msg += "</td></tr></tbody></table></div>";

        EmailDTO emailDTO = EmailDTO.builder()
                .address(email)
                .title("petApet 임시비밀번호 안내 이메일입니다.")
                .message(msg)
                .build();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailDTO.getAddress()); // 메일 수신자
            mimeMessageHelper.setSubject(emailDTO.getTitle()); // 메일 제목
            mimeMessageHelper.setText(emailDTO.getMessage(), true); // 메일 본문 내용, HTML 여부
            mimeMessageHelper.setFrom(adminEmail);
            javaMailSender.send(mimeMessage);
            return 1;
        } catch (MessagingException e) {
            return 0;
//            throw new RuntimeException(e);
        }
    }

    @Override
    public int emailSend(String companyId) {
        String password = getTempPassword();
        companyService.updateCompanyPw("*"+companyId, password);

        String msg = "";
        msg += "<h1 style=\"font-size: 20px; padding-right: 30px; padding-left: 30px;\">임시비밀번호 안내</h1>";
        msg += "<p style=\"font-size: 15px; padding-right: 30px; padding-left: 30px;\">안녕하세요.회원님!";
        msg += "<p style=\"font-size: 15px; padding-right: 30px; padding-left: 30px;\">petApet 임시비밀번호 안내 관련 이메일입니다.</p>";
        msg += "<p style=\"font-size: 15px; padding-right: 30px; padding-left: 30px;\">회원님의 임시비밀번호는 아래와 같습니다.</p>";
        msg += "<p style=\"font-size: 15px; padding-right: 30px; padding-left: 30px;\">로그인 후에는 비밀번호 변경을 부탁드립니다. </p>";
        msg += "<div style=\"padding-right: 15px; padding-left: 30px; margin: 32px 0 40px;\">" +
                "<table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; " +
                "table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"" +
                "text-align: center; vertical-align: middle; font-size: 20px;\">";
        msg += password;
        msg += "</td></tr></tbody></table></div>";

        EmailDTO emailDTO = EmailDTO.builder()
                .address(companyService.findEmail(companyId))
                .title("petApet 임시비밀번호 안내 이메일입니다.")
                .message(msg)
                .build();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailDTO.getAddress()); // 메일 수신자
            mimeMessageHelper.setSubject(emailDTO.getTitle()); // 메일 제목
            mimeMessageHelper.setText(emailDTO.getMessage(), true); // 메일 본문 내용, HTML 여부
            mimeMessageHelper.setFrom(adminEmail);
            javaMailSender.send(mimeMessage);
            return 1;
        } catch (MessagingException e) {
            return 0;
        }
    }
}
